package com.example.myapplication.firebase.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.firebase.viewmodel.FirebaseAuthViewModel

@Composable
fun FirebaseAuthScreen(navController: NavController) {
    val mViewModel: FirebaseAuthViewModel = viewModel()
    val currentUser by mViewModel.mCurrentUser.observeAsState()
    val errorProcess by mViewModel.mErrorProcess.observeAsState(0)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }
    var logText by remember { mutableStateOf("") }

    LaunchedEffect(currentUser) {
        currentUser?.let {
            logText = "${it.uid}-${it.email}"
        }
    }

    LaunchedEffect(errorProcess) {
        errorText = when (errorProcess) {
            5 -> "disconnected"
            9 -> "current user null"
            10 -> "Error when creating"
            11 -> "Error when login"
            else -> "All is good"
        }
        if (errorProcess == 5) {
            logText = "none"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Text(text = errorText, color = MaterialTheme.colorScheme.error)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                if (checkConformityFields(email, password)) {
                    mViewModel.registerNewUser(email, password)
                } else {
                    errorText = "empty field"
                }
            }) {
                Text("Register")
            }

            Button(onClick = {
                if (checkConformityFields(email, password)) {
                    mViewModel.loginUser(email, password)
                } else {
                    errorText = "empty field"
                }
            }) {
                Text("Login")
            }

            Button(onClick = {
                mViewModel.disconnectUser()
            }) {
                Text("Disconnect")
            }
        }

        Text(text = logText)
    }
}

fun checkConformityFields(email: String, password: String): Boolean {
    return email.isNotEmpty() && password.isNotEmpty()
}
