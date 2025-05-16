package com.example.audiobooklibrary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.audiobooklibrary.model.AudioBook
import com.example.audiobooklibrary.model.AudioPart
import com.example.audiobooklibrary.model.Comment

@Database(entities = [AudioBook::class, AudioPart::class, Comment::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun audioBookDao(): AudioBookDao
}