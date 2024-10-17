package com.example.myapplication.firebase.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(errorProcess) {
        if (errorProcess != 0) {
            val errorMessage = when (errorProcess) {
                5 -> "Disconnected"
                9 -> "Current user null"
                10 -> "Error when creating"
                11 -> "Error when login"
                else -> "All is good"
            }
            showToast(context, errorMessage)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 250.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Authentication",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 24.dp) // Espace avant le titre
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp)) // Espace entre les champs

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(24.dp)) // Espace entre le champ et les boutons

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (checkConformityFields(email, password)) {
                        mViewModel.registerNewUser(email, password)
                    } else {
                        showToast(context, "Au moins 1 champ est vide")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black))
            {
                Text("Register")
            }

            Button(
                onClick = {
                    if (checkConformityFields(email, password)) {
                        mViewModel.loginUser(email, password)
                    } else {
                        showToast(context, "Au moins 1 champ est vide")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black))
            {
                Text("Login")
            }

            Button(
                onClick = {
                    mViewModel.disconnectUser()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Disconnect")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        when (val user = currentUser) {
            null -> {
                Text(text = "Aucun utilisateur connecté", style = MaterialTheme.typography.bodyLarge)
            }
            else -> {
                Text(text = "Email : ${user.email ?: "Inconnu"}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "UID : ${user.uid ?: "Inconnu"}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
fun checkConformityFields(email: String, password: String): Boolean {
    return email.isNotEmpty() && password.isNotEmpty()
}
