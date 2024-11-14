package com.example.myapplication.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.myapplication.firebase.repository.FirebaseAuthRepository

class AuthViewModel : ViewModel() {
    private val mFirebaseAuthRepository: FirebaseAuthRepository by lazy { FirebaseAuthRepository() }
    val currentUser = mFirebaseAuthRepository.mCurrentUser
    val errorProcess = mFirebaseAuthRepository.mErrorProcess

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