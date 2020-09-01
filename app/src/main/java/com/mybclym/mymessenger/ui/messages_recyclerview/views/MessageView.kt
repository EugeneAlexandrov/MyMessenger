package com.mybclym.mymessenger.ui.messages_recyclerview.views

interface MessageView {
    val id: String
    val from: String
    val timeStamp: String
    val fileUrl: String
    val text: String

    companion object {
        val TEXT_MESSAGE: Int
            get() = 0
        val IMAGE_MESSAGE: Int
            get() = 1
        val AUDIO_MESSAGE: Int
            get() = 2
    }

    fun getTypeView(): Int
}