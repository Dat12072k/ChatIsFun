package stu.ntdat.chatisfun.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import stu.ntdat.chatisfun.model.MessagePagingSource
import stu.ntdat.chatisfun.model.repository.MainRepository
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _isDone = MutableLiveData<Boolean>()
    private val _message = MutableLiveData<String>()

    val isDone: LiveData<Boolean> = _isDone
    val message: LiveData<String> = _message
    private val storageRef = FirebaseStorage.getInstance().reference

    fun getConvId(userId: String) = liveData {
        mainRepository.database.userConvRelDao.getConvId(userId).collect{
            emit(it)
        }
    }
    val test = Pager(
        PagingConfig(
            pageSize = 10, enablePlaceholders = false
        )
    )
    {MessagePagingSource(mainRepository.database,"conv_1650687020100")}.flow.cachedIn(viewModelScope)

    val test1 = liveData {
        emit(mainRepository.database.chatMessageDao.getListMessByConvId("conv_1650687020100"))
    }

    private suspend fun sendMessage(userId: String, content: String, image: String = "") {
        val convId =
            mainRepository.isFirstConversation(userId) ?: mainRepository.makeConversation(userId)
        mainRepository.makeMessage(convId, userId, content, image)
        _isDone.postValue(true)
    }

    private fun getImageName(): String =
        "${mainRepository.getCurrentUser()}_${System.currentTimeMillis()}"


    suspend fun sendImageMessage(userId: String, uri: Uri) {
        try {
            _isDone.postValue(false)
            val imageName = getImageName()
            storageRef.child("images/$imageName").putFile(uri).await()
            sendMessage(
                userId,
                "Send you a photo",
                storageRef.child("images/$imageName").downloadUrl.await().toString()
            )
        } catch (e: Exception) {
            Log.e("TAG", "uploadImageAndGetLink: $e")
        }
    }

    suspend fun sendImageMessage(userId: String, bitmap: Bitmap) {
        try {
            _isDone.postValue(false)
            val bao = ByteArrayOutputStream()
            val imageName = getImageName()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bao)
            storageRef.child("images/$imageName").putBytes(bao.toByteArray()).await()
            sendMessage(
                userId,
                "Send you a photo",
                storageRef.child("images/$imageName").downloadUrl.toString()
            )
        } catch (e: Exception) {
            Log.e("TAG", "uploadImageAndGetLink: $e")
        }
    }

    suspend fun sendTextMessage(userId: String, content: String) {
        val ct = content.trim { it <= ' ' }
        if (ct.isEmpty()) _message.postValue("Please text something to send")
        else {
            sendMessage(userId, content)
        }
    }

    fun listenUnreadMessage(userId: String) {
        viewModelScope.launch {
            mainRepository.checkUnReadUser(userId)
        }
    }

}