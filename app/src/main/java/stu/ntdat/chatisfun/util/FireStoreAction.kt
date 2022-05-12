package stu.ntdat.chatisfun.util

import com.google.firebase.firestore.*
import stu.ntdat.chatisfun.model.remote.RemoteModel

interface FireStoreAction {

    fun getCollection(patch: String): CollectionReference
    fun getDocument(patch: String): DocumentReference
    fun getUserRef(userId: String): DocumentReference
    fun getUserConvRef(userId: String, conversationId: String): DocumentReference
    fun getUserConvCol(userId: String): CollectionReference
    fun getConvRef(conversationId: String): DocumentReference
    fun getMessageRef(conversationId: String, messageId: String): DocumentReference
    fun getMessageCol(conversationId: String): CollectionReference

    fun <T : RemoteModel> getDocumentModel(
        type: Class<T>,
        document: DocumentReference,
        onFailure: (Exception?) -> Unit = {},
        onSuccess: (T?) -> Unit = {}
    )

    suspend fun <T : RemoteModel> simpleGetDocumentModel(
        type: Class<T>,
        document: DocumentReference
    ): T?

    fun listenDocument(
        documentReference: DocumentReference,
        onSuccess: (DocumentSnapshot?) -> Unit
    ): ListenerRegistration

    fun listenCollection(
        collectionReference: CollectionReference,
        onSuccess: (QuerySnapshot?) -> Unit
    ): ListenerRegistration
}