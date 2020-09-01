package com.mybclym.mymessenger.ui.messages_recyclerview.view_holder

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.mymessenger.database.UID
import com.mybclym.mymessenger.ui.messages_recyclerview.views.MessageView
import com.mybclym.mymessenger.utilits.asTime
import kotlinx.android.synthetic.main.message_item_text.view.*

class TextMessageHolder(view: View) : RecyclerView.ViewHolder(view), MessageHolder {
    val userMessageBlock: ConstraintLayout = view.user_message_block
    val companionMessageBlock: ConstraintLayout = view.companion_message_block
    val userMessageText: TextView = view.user_message_text
    val userMessageTime: TextView = view.user_message_time
    val companionMessageText: TextView = view.companion_message_text
    val companionMessageTime: TextView = view.companion_message_time

    override fun drawMessage(message: MessageView) {
        if (message.from == UID) {
            companionMessageBlock.visibility = View.GONE
            userMessageBlock.visibility = View.VISIBLE
            userMessageText.text = message.text
            userMessageTime.text = message.timeStamp.asTime()
        } else {
            companionMessageBlock.visibility = View.VISIBLE
            userMessageBlock.visibility = View.GONE
            companionMessageText.text = message.text
            companionMessageTime.text = message.timeStamp.asTime()
        }
    }

    override fun onAttach(message: MessageView) {

    }

    override fun onDettach() {

    }
}