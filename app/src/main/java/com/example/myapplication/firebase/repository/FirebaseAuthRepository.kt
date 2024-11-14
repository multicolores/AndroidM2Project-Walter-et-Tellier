package com.example.myapplication.firebase.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FirebaseAuthRepository {
    private var mFirebaseAuth: FirebaseAuth = Firebase.auth
    private val _mCurrentUser = MutableStateFlow<FirebaseUser?>(null)
    val mCurrentUser: StateFlow<FirebaseUser?> get() = _mCurrentUser

    private val _mErrorProcess = MutableStateFlow(0)
    val mErrorProcess: StateFlow<Int> get() = _mErrorProcess

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        coroutineScope.launch {
            if (mFirebaseAuth.currentUser != null) {
                _mCurrentUser.emit(mFirebaseAuth.currentUser)
            } else {
                _mErrorProcess.emit(9)
            }
        }
    }

    fun registerNewUser(email: String, password: String) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                coroutineScope.launch {
                    if (task.isSuccessful) {
                        if (mFirebaseAuth.currentUser != null) {
                            _mCurrentUser.emit(mFirebaseAuth.currentUser)
                        } else {
                            _mErrorProcess.emit(9)
                        }
                    } else {
                        _mErrorProcess.emit(10)
                    }
                }
            }
    }

    fun loginUser(email: String, password: String) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                coroutineScope.launch {
                    if (task.isSuccessful) {
                        _mCurrentUser.emit(mFirebaseAuth.currentUser)
                    } else {
                        _mErrorProcess.emit(11)
                    }
                }
            }
    }

    fun disconnectUser() {
        coroutineScope.launch {
            mFirebaseAuth.signOut()
            _mErrorProcess.emit(5)
            _mCurrentUser.emit(null)
        }
    }
}