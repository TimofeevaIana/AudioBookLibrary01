package com.example.audiobooklibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey val id: String,
    val bookId: String,
    val partId: String,
    val timestamp: Long, // Time position in milliseconds
    val userId: String,
    val text: String?,
    val isLike: Boolean = false
)