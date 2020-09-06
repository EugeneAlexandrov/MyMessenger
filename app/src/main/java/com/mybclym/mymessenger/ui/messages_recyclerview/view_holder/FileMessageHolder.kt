package com.mybclym.mymessenger.ui.messages_recyclerview.view_holder

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.mymessenger.database.UID
import com.mybclym.mymessenger.ui.messages_recyclerview.views.MessageView
import com.mybclym.mymessenger.utilits.asTime
import kotlinx.android.synthetic.main.message_item_file.view.*

class FileMessageHolder(view: View) : RecyclerView.ViewHolder(view), MessageHolder {
    private val userMessageBlock: ConstraintLayout = view.user_message_block
    private val userMessageTitle: TextView = view.user_message_file_title
    private val userMessageTime: TextView = view.user_message_time
    private val companionMessageBlock: ConstraintLayout = view.companion_message_block
    private val companionMessageTitle: TextView = view.companion_message_file_title
    private val companionMessageTime: TextView = view.companion_message_time

    override fun drawMessage(message: MessageView) {
        if (message.from == UID) {
            companionMessageBlock.visibility = View.GONE
            userMessageBlock.visibility = View.VISIBLE
            userMessageTitle.text = message.text
            userMessageTime.text = message.timeStamp.asTime()
        } else {
            companionMessageBlock.visibility = View.VISIBLE
            userMessageBlock.visibility = View.GONE
            companionMessageTitle.text = message.text
            companionMessageTime.text = message.timeStamp.asTime()
        }
    }

    override fun onAttach(message: MessageView) {

    }

    override fun onDettach() {

    }
}