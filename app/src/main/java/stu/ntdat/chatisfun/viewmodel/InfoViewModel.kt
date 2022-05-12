package stu.ntdat.chatisfun.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import stu.ntdat.chatisfun.model.repository.MainRepository
import stu.ntdat.chatisfun.util.getDate
import stu.ntdat.chatisfun.model.ChatUser
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var user: LiveData<ChatUser?>
    lateinit var userEmail: LiveData<String>
    lateinit var userName: LiveData<String>
    lateinit var userBirthday: LiveData<String>
    lateinit var userAddress: LiveData<String>
    lateinit var userAvatar: LiveData<String>
    lateinit var userPhone: LiveData<String>

    fun updateInfo() {
        user = mainRepository.database.chatUserDao.getUserById(firebaseAuth.uid ?: "").asLiveData()
        userEmail = Transformations.map(user) { it?.userEmail ?: "" }
        userName = Transformations.map(user) { it?.userName ?: "" }
        userBirthday = Transformations.map(user) {
            if (it?.userBirthday == null) ""
            else it.userBirthday.getDate()
        }
        userAddress = Transformations.map(user) { it?.userAddress ?: "" }
        userAvatar = Transformations.map(user) { it?.userAvatar ?: "" }
        userPhone = Transformations.map(user) { it?.userPhone ?: "" }
    }

    fun signOut() {
        mainRepository.signOut()
    }
}