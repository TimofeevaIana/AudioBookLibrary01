package com.example.audiobooklibrary.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.audiobooklibrary.model.AudioBook
import com.example.audiobooklibrary.model.AudioPart
import com.example.audiobooklibrary.model.Comment
import com.example.audiobooklibrary.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AudioBookDetailScreen(bookId: String, navController: NavController) {
    var audioBook by remember { mutableStateOf<AudioBook?>(null) }
    var comments by remember { mutableStateOf<List<Comment>>(emptyList()) }
    var commentText by remember { mutableStateOf("") }

    // Загрузка деталей книги
    LaunchedEffect(bookId) {
        withContext(Dispatchers.IO) {
            val book = RetrofitClient.api.getAudioBookById(bookId)
            audioBook = book
            // Загрузка комментариев для первой части (для примера)
            if (book.parts.isNotEmpty()) {
                val partId = book.parts[0].id
                val commentsList = RetrofitClient.api.getComments(bookId, partId)
                comments = commentsList
            }
        }
    }

    audioBook?.let { book ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = book.authors,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = book.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Parts:",
                style = MaterialTheme.typography.headlineSmall
            )
            LazyColumn {
                items(book.parts) { part ->
                    AudioPartItem(part = part)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Comments:",
                style = MaterialTheme.typography.headlineSmall
            )
            LazyColumn {
                items(comments) { comment ->
                    Text(text = "${comment.timestamp / 1000}s: ${comment.text}")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row {
                BasicTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = {
                    // Отправка комментария
                    val newComment = Comment(
                        id = "tempId",
                        bookId = bookId,
                        partId = book.parts[0].id,
                        timestamp = 0, // TODO: Получить текущую позицию плеера
                        userId = "user1",
                        text = commentText,
                        isLike = false
                    )
                    // Асинхронная отправка на сервер
                    LaunchedEffect(true) {
                        withContext(Dispatchers.IO) {
                            RetrofitClient.api.postComment(newComment)
                        }
                        commentText = ""
                    }
                }) {
                    Text("Post")
                }
            }
        }
    } ?: run {
        Text(text = "Loading...", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun AudioPartItem(part: AudioPart) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Part ID: ${part.id}")
            Button(onClick = { /* TODO: Play audio */ }) {
                Text(text = "Play")
            }
        }
    }
}