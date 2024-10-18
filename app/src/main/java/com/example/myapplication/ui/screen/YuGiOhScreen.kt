package com.example.myapplication.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.ui.model.ItemUi
import com.example.myapplication.ui.viewModel.YuGiOhViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YuGiOhScreen(navController: NavController) {
    val viewModel: YuGiOhViewModel = viewModel()
    val list = viewModel.card.collectAsState(emptyList()).value
    val context = LocalContext.current

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(context.getString(R.string.cards_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { viewModel.insertNewCard() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333)),
                ) {
                    Text(context.getString(R.string.add), color = Color.White)
                }
                Button(
                    onClick = { viewModel.deleteAllCards() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                ) {
                    Text(context.getString(R.string.remove), color = Color.White)
                }
            }
        }

    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp, bottom = 70.dp, start = 8.dp, end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(list) { item ->
                when (item) {
                    is ItemUi.Header ->
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 35.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                            shape = MaterialTheme.shapes.medium,
                            elevation = CardDefaults.outlinedCardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = item.type,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                    is ItemUi.YuGiOhObject -> {
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    showToast(context, "${item.title} ATK: ${item.atk} / DEF: ${item.def}")
                                },
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                            shape = MaterialTheme.shapes.large,
                            elevation = CardDefaults.outlinedCardElevation(6.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                val painter = rememberAsyncImagePainter(
                                    ImageRequest
                                        .Builder(LocalContext.current)
                                        .data(data = item.url)
                                        .build()
                                )

                                Image(
                                    modifier = Modifier.size(250.dp),
                                    painter = painter,
                                    contentDescription = "Image d'une carte YuGiOh",
                                )

                                Text(
                                    text = item.title,
                                    color = Color(0xFF333333),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp
                                )
                                Text(
                                    text = "Type: ${item.type}",
                                    color = Color(0xFF555555),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )

                                if (item.level != 0) {
                                    Text(
                                        text = context.getString(R.string.level) + ": ${item.level}",
                                        color = Color(0xFF555555),
                                        fontSize = 16.sp
                                    )
                                }

                                if (item.atk != 0 || item.def != 0) {
                                    Text(
                                        text = "ATK: ${item.atk} / DEF: ${item.def}",
                                        color = Color(0xFF555555),
                                        fontSize = 16.sp
                                    )
                                }

                                Text(
                                    text =  context.getString(R.string.added_date) + ": ${item.current_timestamp}",
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    is ItemUi.Footer ->
                        OutlinedCard(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF333333)),
                            shape = MaterialTheme.shapes.medium,
                            elevation = CardDefaults.outlinedCardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text =  context.getString(R.string.total_number_of_cards) +": ${item.numberOfElements}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                )
                            }
                        }
                }
            }
        }
    }
}
