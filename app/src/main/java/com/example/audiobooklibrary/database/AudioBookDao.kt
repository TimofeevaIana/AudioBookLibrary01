package com.example.audiobooklibrary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.audiobooklibrary.model.AudioBook
import com.example.audiobooklibrary.model.AudioPart
import com.example.audiobooklibrary.model.Comment

@Dao
interface AudioBookDao {
    // AudioBook queries
    @Insert
    suspend fun insertAudioBook(book: AudioBook)

    @Update
    suspend fun updateAudioBook(book: AudioBook)

    @Query("SELECT * FROM audiobooks")
    suspend fun getAllAudioBooks(): List<AudioBook>

    @Query("SELECT * FROM audiobooks WHERE id = :bookId")
    suspend fun getAudioBookById(bookId: String): AudioBook?

    @Query("SELECT * FROM audiobooks WHERE isListened = 1")
    suspend fun getListenedAudioBooks(): List<AudioBook>

    // AudioPart queries
    @Insert
    suspend fun insertAudioPart(part: AudioPart)

    @Update
    suspend fun updateAudioPart(part: AudioPart)

    @Query("SELECT * FROM audioparts WHERE bookId = :bookId")
    suspend fun getPartsByBookId(bookId: String): List<AudioPart>

    // Comment queries
    @Insert
    suspend fun insertComment(comment: Comment)

    @Query("SELECT * FROM comments WHERE bookId = :bookId AND partId = :partId")
    suspend fun getCommentsByPart(bookId: String, partId: String): List<Comment>
}