package com.example.audiobooklibrary.network

import com.example.audiobooklibrary.model.AudioBook
import com.example.audiobooklibrary.model.Comment
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AudioBookApi {
    @GET("audiobooks")
    suspend fun getAudioBooks(): List<AudioBook>

    @GET("audiobooks/{id}")
    suspend fun getAudioBookById(@Path("id") id: String): AudioBook

    @GET("comments/{bookId}/{partId}")
    suspend fun getComments(@Path("bookId") bookId: String, @Path("partId") partId: String): List<Comment>

    @POST("comments")
    suspend fun postComment(@Body comment: Comment): Comment
}