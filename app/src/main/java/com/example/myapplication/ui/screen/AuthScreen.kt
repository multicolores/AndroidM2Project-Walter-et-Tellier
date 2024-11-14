package com.example.myapplication.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.myapplication.R
import com.example.myapplication.ui.viewModel.AuthViewModel

@Composable
fun AuthScreen(navController: NavController) {
    val mViewModel: AuthViewModel = viewModel()
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
            text = context.getString(R.string.authentification_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(context.getString(R.string.password),) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (checkConformityFields(email, password)) {
                        mViewModel.registerNewUser(email, password)
                    } else {
                        showToast(context, context.getString(R.string.field_empty_error),)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black))
            {
                Text(context.getString(R.string.register_button),)
            }

            Button(
                onClick = {
                    if (checkConformityFields(email, password)) {
                        mViewModel.loginUser(email, password)
                    } else {
                        showToast(context, context.getString(R.string.field_empty_error),)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black))
            {
                Text(context.getString(R.string.log_in_button),)
            }

            Button(
                onClick = {
                    mViewModel.disconnectUser()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(context.getString(R.string.logout_button),)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        when (val user = currentUser) {
            null -> {
                Text(text = context.getString(R.string.user_not_connected), style = MaterialTheme.typography.bodyLarge)
            }
            else -> {
                Text(text = "Email : ${user.email ?: "-"}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "UID : ${user.uid ?: "-"}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
fun checkConformityFields(email: String, password: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return email.isNotEmpty() && password.isNotEmpty() && email.matches(emailPattern)
}
