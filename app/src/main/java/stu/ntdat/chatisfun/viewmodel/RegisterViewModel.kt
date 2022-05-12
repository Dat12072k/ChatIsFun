package stu.ntdat.chatisfun.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import stu.ntdat.chatisfun.model.repository.AccountRepository
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {



}