package stu.ntdat.chatisfun.util

import android.util.Log
import com.google.firebase.firestore.*
import kotlinx.coroutines.suspendCancellableCoroutine
import stu.ntdat.chatisfun.model.remote.RemoteModel

class FireStoreHelper private constructor() : FireStoreAction {

    companion object {
        @Volatile
        private lateinit var instance: FireStoreHelper

        fun getInstance(): FireStoreHelper {
            if (::instance.isInitialized.not()) {
                instance = FireStoreHelper()

            }
            return instance
        }

        const val USER_ROOT_COL = "users"
        const val CONVERSATION_ROOT_COL = "conversation"
        const val MESSAGE_ROOT_COL = "message"
    }

    private val fireStore = FirebaseFirestore.getInstance()

    override fun getCollection(patch: String): CollectionReference = fireStore.collection(patch)

    override fun getDocument(patch: String): DocumentReference = fireStore.document(patch)

    override fun getUserRef(userId: String): DocumentReference =
        getDocument("$USER_ROOT_COL/$userId")

    override fun getUserConvRef(userId: String, conversationId: String): DocumentReference =
        getUserRef("$userId/$CONVERSATION_ROOT_COL/$conversationId")

    override fun getUserConvCol(userId: String): CollectionReference =
        getUserRef(userId).collection(CONVERSATION_ROOT_COL)


    override fun getMessageRef(conversationId: String, messageId: String): DocumentReference =
        getDocument("$CONVERSATION_ROOT_COL/$conversationId/$MESSAGE_ROOT_COL/$messageId")

    override fun getMessageCol(conversationId: String): CollectionReference =
        getConvRef(conversationId).collection(MESSAGE_ROOT_COL)

    override fun <T : RemoteModel> getDocumentModel(
        type: Class<T>,
        document: DocumentReference,
        onFailure: (Exception?) -> Unit,
        onSuccess: (T?) -> Unit
    ) {
        document.get().addOnFailureListener(onFailure).addOnSuccessListener {
            try {
                onSuccess(it.toObject(type))
            } catch (e: Exception) {
                trace(e)
                onFailure(e)
            }
        }
    }

    override suspend fun <T : RemoteModel> simpleGetDocumentModel(
        type: Class<T>,
        document: DocumentReference
    ) = suspendCancellableCoroutine<T?> { cont ->
        getDocumentModel(
            type,
            document,
            {
                trace(it)
                cont.safeResume { null }
            },
            {
                cont.safeResume { it }
            }
        )
    }

    override fun listenDocument(
        documentReference: DocumentReference,
        onSuccess: (DocumentSnapshot?) -> Unit
    ): ListenerRegistration {
        return documentReference.addSnapshotListener { value, error ->
            if (error != null) {
                Log.e("TAG", "listenDocument Error: $error")
                return@addSnapshotListener
            }
            onSuccess(value)
        }
    }

    override fun listenCollection(
        collectionReference: CollectionReference,
        onSuccess: (QuerySnapshot?) -> Unit
    ): ListenerRegistration {
        return collectionReference.addSnapshotListener { value, error ->
            if (error != null) {
                Log.e("TAG", "listenCollection Error: $error")
                return@addSnapshotListener
            }
            onSuccess(value)
        }
    }

    override fun getConvRef(conversationId: String): DocumentReference =
        getDocument("$CONVERSATION_ROOT_COL/$conversationId")
}