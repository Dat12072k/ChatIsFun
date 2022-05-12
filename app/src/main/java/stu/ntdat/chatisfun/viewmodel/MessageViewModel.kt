package stu.ntdat.chatisfun.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import stu.ntdat.chatisfun.model.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    var userList = liveData {
        mainRepository.database.chatUserDao.getAllUsers(mainRepository.getCurrentUser()).collect {
            emit(it)
        }
    }
    var convList = liveData {
        mainRepository.database.chatConversationDao.getConvAndUser().collect {
            it?.let { emit(it) }
        }
    }

}