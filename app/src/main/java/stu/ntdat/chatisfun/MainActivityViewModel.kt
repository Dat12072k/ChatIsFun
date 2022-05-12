package stu.ntdat.chatisfun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import stu.ntdat.chatisfun.model.repository.MainRepository
import stu.ntdat.chatisfun.model.relation.ConvMessRel
import stu.ntdat.chatisfun.model.remote.RemoteConversation
import stu.ntdat.chatisfun.model.remote.RemoteMessage
import stu.ntdat.chatisfun.model.remote.RemoteUser
import stu.ntdat.chatisfun.model.remote.RemoteUserConvRel
import stu.ntdat.chatisfun.util.FireStoreHelper
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val fireStore: FireStoreHelper
) : ViewModel() {

    val isLogin = liveData(Dispatchers.IO) {
        mainRepository.authState.collect {
            emit(it)
        }
    }

    fun listenDataChange() {
        fireStore.listenCollection(fireStore.getCollection(FireStoreHelper.USER_ROOT_COL)) { query ->
            query?.toObjects<RemoteUser>()?.mapNotNull {
                it.toModel()
            }?.let {
                viewModelScope.launch {
                    mainRepository.database.chatUserDao.insertAll(it)
                }
            }
        }
        fireStore.listenCollection(fireStore.getUserConvCol(mainRepository.getCurrentUser())) { query ->
            query?.toObjects<RemoteUserConvRel>()?.mapNotNull {
                it.toModel()
            }?.let {
                viewModelScope.launch {
                    mainRepository.database.userConvRelDao.insertAll(it)
                }
            }

        }
        viewModelScope.launch {
            mainRepository.database.userConvRelDao.getAllConv().collect { listConv ->
                listConv?.forEach { convId ->
                    fireStore.listenDocument(fireStore.getConvRef(convId)) { snap ->
                        viewModelScope.launch {
                            snap?.toObject<RemoteConversation>()
                                ?.toModel(mainRepository.getCurrentUserName())?.let {
                                    viewModelScope.launch {
                                        mainRepository.database.chatConversationDao.insertOne(it)
                                    }
                                }
                        }
                    }
                    fireStore.listenCollection(
                        fireStore.getMessageCol(convId)
                    ) { query ->
                        val mess = query?.toObjects<RemoteMessage>()
                        mess?.mapNotNull {
                            it.toModel(mainRepository.getCurrentUser())
                        }?.let {
                            viewModelScope.launch {
                                mainRepository.database.chatMessageDao.insertAll(it)
                            }
                        }
                        mess?.mapNotNull { remote ->
                            remote.messageId?.let { ConvMessRel(convId, it) }
                        }?.let {
                            viewModelScope.launch {
                                mainRepository.database.convMessRelDao.insertAll(it)
                            }
                        }
                    }
                }
            }
        }
    }

}