package stu.ntdat.chatisfun.model.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import stu.ntdat.chatisfun.model.ChatUser
import stu.ntdat.chatisfun.util.FireStoreAction
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val fireStoreHelper: FireStoreAction,
) {
    private val firebaseAuth = FirebaseAuth.getInstance()
    suspend fun register(email: String, password: String): String {
        val message: String = try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            addUser2FireBase()
        } catch (e: Exception) {
            e.message.toString()
        }
        return message
    }

    suspend fun login(email: String, password: String): String {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            "Success"
        } catch (e: Exception) {
            e.message.toString()
        }
    }

    private suspend fun addUser2FireBase(): String {
        return withTimeout(10_000) {
            try {
                firebaseAuth.currentUser?.let {
                    val user = ChatUser(it.uid, it.email!!, it.email!!)
                    fireStoreHelper.getUserRef(it.uid).set(user)
                    "Success"
                }
            } catch (e: Exception) {
                "Some error happened"
            }
        } ?: "Time out: Add user to firebase database"
    }

    fun isUserLogin(): Boolean = firebaseAuth.currentUser != null

    companion object {
        @Volatile
        private lateinit var instance: AccountRepository

        fun getInstance(
            fireStoreHelper: FireStoreAction,
        ): AccountRepository {
            if (::instance.isInitialized.not()) {
                instance = AccountRepository(fireStoreHelper)

            }
            return instance
        }
    }
}