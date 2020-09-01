package com.mybclym.mymessenger.ui.fragments.messages_recyclerview.view_holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.mymessenger.database.UID
import com.mybclym.mymessenger.ui.fragments.messages_recyclerview.views.MessageView
import com.mybclym.mymessenger.utilits.asTime
import kotlinx.android.synthetic.main.message_item_audio.view.*

class AudioMessageHolder(view: View) : RecyclerView.ViewHolder(view) {
    val userMessageBlock: ConstraintLayout = view.user_message_block
    val userMessagePlayBtn: ImageView = view.user_message_audio_playbtn
    val userMessageStopBtn: ImageView = view.user_message_audio_stopbtn
    val userMessageTime: TextView = view.user_message_time

    val companionMessageBlock: ConstraintLayout = view.companion_message_block
    val companionMessagePlayBtn: ImageView = view.companion_message_audio_playbtn
    val companionMessageStopBtn: ImageView = view.companion_message_audio_stopbtn
    val companionMessageTime: TextView = view.companion_message_time

    fun drawMessageVoice(holder: AudioMessageHolder, view: MessageView) {
        if (view.from == UID) {
            holder.companionMessageBlock.visibility = View.GONE
            holder.userMessageBlock.visibility = View.VISIBLE
            holder.userMessageTime.text = view.timeStamp.asTime()
        } else {
            holder.companionMessageBlock.visibility = View.VISIBLE
            holder.userMessageBlock.visibility = View.GONE
            holder.companionMessageTime.text = view.timeStamp.asTime()
        }
    }
}