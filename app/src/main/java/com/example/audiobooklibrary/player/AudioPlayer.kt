package com.example.audiobooklibrary.player

import android.content.Context
import android.media.MediaPlayer
import com.example.audiobooklibrary.model.AudioPart
import java.io.IOException

class AudioPlayer(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private var currentPart: AudioPart? = null

    fun play(part: AudioPart) {
        if (mediaPlayer != null && currentPart?.id == part.id) {
            mediaPlayer?.start()
            return
        }
        release()
        currentPart = part
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(part.fileUrl) // Используем URL из сервера
                prepare()
                seekTo(part.currentPosition.toInt())
                start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun pause() {
        mediaPlayer?.pause()
        currentPart?.let {
            it.currentPosition = mediaPlayer?.currentPosition?.toLong() ?: 0
        }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
        currentPart = null
    }

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }
}