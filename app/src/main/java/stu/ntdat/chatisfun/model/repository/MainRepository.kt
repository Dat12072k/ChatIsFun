package stu.ntdat.chatisfun.model.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import stu.ntdat.chatisfun.model.database.AppDatabase
import stu.ntdat.chatisfun.model.*
import stu.ntdat.chatisfun.model.relation.UserConvRel
import stu.ntdat.chatisfun.util.FireStoreAction
import stu.ntdat.chatisfun.util.makeMessageContent
import stu.ntdat.chatisfun.util.trace
import javax.inject.Inject

class MainRepository @Inject constructor(
    val fireStoreHelper: FireStoreAction,
    val database: AppDatabase,
) {
    private val firebaseAuth = FirebaseAuth.getInstance()
    suspend fun checkUnReadUser(userId: String) {
        try {
            database.userConvRelDao.getConvByUserId(userId)?.convId?.let{
                database.chatConversationDao.getConvSender(it).collect { senderId ->
                    if (senderId != getCurrentUser())
                        fireStoreHelper.getConvRef(it).update("convUnRead", 0).await()
                }
            }
        } catch (e: Exception) {
            trace(e)
        }

    }

    val authState = callbackFlow {
        val authListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        firebaseAuth.addAuthStateListener(authListener)
        awaitClose {
            firebaseAuth.removeAuthStateListener(authListener)
        }
    }

    private fun getConvSendRef(convId: String) =
        fireStoreHelper.getUserConvRef(getCurrentUser(), convId)

    private fun getConvRecRef(userId: String, convId: String) =
        fireStoreHelper.getUserConvRef(userId, convId)

    fun getCurrentUser() = firebaseAuth.currentUser?.uid?: ""

    suspend fun getCurrentUserName(): String  {
        return database.chatUserDao.getUserName(getCurrentUser())?: ""
    }

    suspend fun makeConversation(receiverId: String): String {
        val convId = "conv_${System.currentTimeMillis()}"
        val convSend = UserConvRel(receiverId, convId)
        val convRec = UserConvRel(getCurrentUser(), convId)
        getConvSendRef(convId).set(convSend).await()
        getConvRecRef(receiverId, convId).set(convRec).await()

        val title = "${database.chatUserDao.getUserName(getCurrentUser())}|${
            database.chatUserDao.getUserName(receiverId)
        }"
        val conv = ChatConversation(convId, title)
        fireStoreHelper.getConvRef(convId).set(conv)
        return convId
    }

    suspend fun makeMessage(
        convId: String,
        receiverId: String,
        content: String,
        image: String
    ) {
        try {
            val time = System.currentTimeMillis()
            val messId = "mess_$time"
            val message = ChatMessage(
                messId,
                content.makeMessageContent(receiverId, getCurrentUser()),
                time,
                image
            )
            fireStoreHelper.getMessageRef(convId, messId).set(message).await()
            val data = mapOf(
                "convLastMess" to content,
                "convUnRead" to FieldValue.increment(1),
                "convLastSender" to getCurrentUser(),
                "convLastTime" to time
            )
            fireStoreHelper.getConvRef(convId).update(data)
        } catch (e: Exception) {
            Log.e("TAG", "makeMessage: $e")
        }
    }

    suspend fun isFirstConversation(userId: String): String? {
        val userConvRel = database.userConvRelDao.getConvByUserId(userId)
        return userConvRel?.convId
    }

    fun signOut() {
        clearDatabase()
        FirebaseFirestore.getInstance().clearPersistence()
        firebaseAuth.signOut()
    }

    private fun clearDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            database.clearAllTables()
        }
    }

    companion object {
        @Volatile
        private lateinit var instance: MainRepository

        fun getInstance(
            fireStoreHelper: FireStoreAction,
            database: AppDatabase,
        ): MainRepository {
            if (::instance.isInitialized.not()) {
                instance = MainRepository(fireStoreHelper, database)
            }
            return instance
        }
    }
}