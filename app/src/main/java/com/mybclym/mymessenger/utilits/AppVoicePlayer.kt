package com.mybclym.mymessenger.utilits

import android.media.MediaPlayer
import android.util.Log
import com.mybclym.mymessenger.database.getFileFromStorage
import java.io.File

class AppVoicePlayer {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var file: File

    fun start(messagekey: String, fileUrl: String, function: () -> Unit) {
        file = File(APP_ACTIVITY.filesDir, messagekey)
        if (file.exists() && file.length() > 0 && file.isFile) {
            Log.d("Test", "file exist, play from local")
            play { function() }
        } else {
            Log.d("Test", "file not exist, play from firebase")
            file.createNewFile()
            getFileFromStorage(file, fileUrl) {
                play { function() }
            }
        }
    }

    private fun play(function: () -> Unit) {
        try {
            mediaPlayer.setDataSource(file.absolutePath)
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                Log.d("Test", "file finished, change buttons")
                stop { function() }
            }
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    fun stop(function: () -> Unit) {
        try {
            mediaPlayer.stop()
            mediaPlayer.reset()
            function()
        } catch (e: Exception) {
            showToast(e.message.toString())
            function()
        }
    }

    fun release() {
        mediaPlayer.release()
    }

    fun init() {
        mediaPlayer = MediaPlayer()
    }
}