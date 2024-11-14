package com.example.myapplication.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.ui.viewModel.AuthViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun MainScreen(
    onButtonClick: () -> Unit,
    onButtonClick2: () -> Unit,
    ) {
    val authViewModel: AuthViewModel = viewModel()
    val currentUser by authViewModel.currentUser.observeAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            Text(
                text = "Florian TELLIER ${context.getString(R.string.and)} Maxence WALTER",
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            Text(text = "${context.getString(R.string.home_page_text)} Yu-Gi-Oh", textAlign = TextAlign.Center)
            if (currentUser == null) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    content = { Text(context.getString(R.string.log_in_button)) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    onClick = { onButtonClick() }
                )
            } else {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    content = { Text(context.getString(R.string.already_connected) + " - " + currentUser?.email) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    onClick = { onButtonClick() }
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                content = { Text(context.getString(R.string.see_card_button)) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                onClick = { onButtonClick2() }
            )
        }
        val painter = rememberAsyncImagePainter(
            ImageRequest
                .Builder(LocalContext.current)
                .data(data = "https://upload.wikimedia.org/wikipedia/commons/1/11/Yu-Gi-Oh%21_%28Logo%29.jpg")
                .build()
        )
        Image(
            modifier = Modifier.size(400.dp),
            painter = painter,
            contentDescription = "Yu gi oh logo",
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        MainScreen({}, {})
    }
}