package stu.ntdat.chatisfun.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import stu.ntdat.chatisfun.model.repository.AccountRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    val firebaseAuth = FirebaseAuth.getInstance()
    private val _message: MutableLiveData<String> = MutableLiveData()
    var message: LiveData<String> = _message

    fun isLogin() = firebaseAuth.currentUser != null

    fun resetMessage() {
        _message.postValue("")
    }

    fun login(email: String, password: String) {
        val mail = email.trim { it <= ' ' }
        val pass = password.trim { it <= ' ' }
        when {
            mail.isEmpty() -> {
                _message.postValue("Please enter email")
            }
            !Patterns.EMAIL_ADDRESS.matcher(mail).matches() -> {
                _message.postValue("Your email doesn't exists")
            }
            pass.isEmpty() -> {
                _message.postValue("Please enter password")
            }
            pass.length < 6 -> {
                _message.postValue("Password must be at least 6 characters")
            }
            else -> {
                CoroutineScope(Dispatchers.IO).launch {
                    _message.postValue(accountRepository.login(mail, pass))
                }
            }
        }
    }

    fun register(email: String, password: String, re_password: String) {
        val mail = email.trim { it <= ' ' }
        val pass = password.trim { it <= ' ' }
        val rePass = re_password.trim { it <= ' ' }
        when {
            mail.isEmpty() -> {
                _message.postValue("Please enter email")
            }
            !Patterns.EMAIL_ADDRESS.matcher(mail).matches() -> {
                _message.postValue("Your email doesn't exists")
            }
            pass.isEmpty() -> {
                _message.postValue("Please enter password")
            }
            pass.length < 6 -> {
                _message.postValue("Password must be at least 6 characters")
            }
            rePass.isEmpty() -> {
                _message.postValue("Please enter re-password")
            }
            (pass != rePass) -> {
                _message.postValue("Two password does not match")
            }
            else -> {
                CoroutineScope(Dispatchers.IO).launch {
                    _message.postValue(accountRepository.register(mail, pass))
                }
            }
        }
    }
}