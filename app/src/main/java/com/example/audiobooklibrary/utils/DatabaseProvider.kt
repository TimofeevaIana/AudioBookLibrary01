package com.example.audiobooklibrary.utils

import android.content.Context
import androidx.room.Room
import com.example.audiobooklibrary.database.AppDatabase

object DatabaseProvider {
    private var database: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if (database == null) {
            synchronized(this) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "audiobook_db"
                ).build()
            }
        }
        return database!!
    }
}