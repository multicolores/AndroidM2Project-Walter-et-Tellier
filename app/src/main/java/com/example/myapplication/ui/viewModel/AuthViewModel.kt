package com.example.myapplication.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.firebase.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {
    private val mFirebaseAuthRepository: FirebaseAuthRepository by lazy { FirebaseAuthRepository() }
    var mCurrentUser = MutableLiveData<FirebaseUser?>()
    var mErrorProcess = MutableLiveData<Int>()
    val currentUser: LiveData<FirebaseUser?> get() = mCurrentUser


    init {
        mCurrentUser = mFirebaseAuthRepository.mCurrentUser
        mErrorProcess = mFirebaseAuthRepository.mErrorProcess
    }


    fun loginUser(email: String, password: String) {
        mFirebaseAuthRepository.loginUser(email, password)
    }


    fun registerNewUser(email: String, password: String) {
        mFirebaseAuthRepository.registerNewUser(email, password)
    }


    fun disconnectUser() {
        mFirebaseAuthRepository.disconnectUser()
    }
}