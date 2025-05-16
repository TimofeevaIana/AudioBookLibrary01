package com.example.audiobooklibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audiobooks")
data class AudioBook(
    @PrimaryKey val id: String, // MD5 checksum as unique identifier
    val title: String,
    val authors: String,
    val description: String,
    val imageUrl: String?,
    val parts: List<AudioPart>,
    val isListened: Boolean = false
)