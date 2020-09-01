package com.mybclym.mymessenger.ui.fragments.messages_recyclerview.view_holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.ui.fragments.messages_recyclerview.views.MessageView


class AppHolderFactory {
    companion object {
        fun getHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                MessageView.IMAGE_MESSAGE -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.message_item_image, parent, false)
                    ImageMessageHolder(view)
                }
                MessageView.AUDIO_MESSAGE -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.message_item_audio, parent, false)
                    AudioMessageHolder(view)
                }
                else -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.message_item_text, parent, false)
                    TextMessageHolder(view)
                }
            }
        }
    }
}