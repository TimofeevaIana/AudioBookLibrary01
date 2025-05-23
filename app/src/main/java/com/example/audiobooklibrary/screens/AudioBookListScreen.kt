package com.example.audiobooklibrary.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.audiobooklibrary.model.AudioBook
import com.example.audiobooklibrary.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AudioBookListScreen(navController: NavController) {
    var audioBooks by remember { mutableStateOf<List<AudioBook>>(emptyList()) }

    // Загрузка списка аудиокниг с сервера
    LaunchedEffect(true) {
        withContext(Dispatchers.IO) {
            val books = RetrofitClient.api.getAudioBooks()
            audioBooks = books
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(audioBooks) { book ->
            AudioBookItem(book = book) {
                navController.navigate("detail/${book.id}")
            }
        }
    }
}

@Composable
fun AudioBookItem(book: AudioBook, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = book.authors,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = book.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2
            )
        }
    }
}