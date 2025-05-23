package com.example.audiobooklibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audioparts")
data class AudioPart(
    @PrimaryKey val id: String,
    val bookId: String,
    val fileUrl: String, // URL to audio file
    val duration: Long, // Duration in milliseconds
    val currentPosition: Long = 0 // Current position for playback resume
)