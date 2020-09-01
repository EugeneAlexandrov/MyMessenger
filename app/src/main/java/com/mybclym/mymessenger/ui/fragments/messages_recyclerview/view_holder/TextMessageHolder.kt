package com.mybclym.mymessenger.ui.fragments.messages_recyclerview.view_holder

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.mymessenger.database.UID
import com.mybclym.mymessenger.ui.fragments.messages_recyclerview.views.MessageView
import com.mybclym.mymessenger.utilits.asTime
import kotlinx.android.synthetic.main.message_item_text.view.*

class TextMessageHolder(view: View) : RecyclerView.ViewHolder(view) {
    val userMessageBlock: ConstraintLayout = view.user_message_block
    val companionMessageBlock: ConstraintLayout = view.companion_message_block
    val userMessageText: TextView = view.user_message_text
    val userMessageTime: TextView = view.user_message_time
    val companionMessageText: TextView = view.companion_message_text
    val companionMessageTime: TextView = view.companion_message_time

    fun drawMessageText(holder: TextMessageHolder, view: MessageView) {
        if (view.from == UID) {
            holder.companionMessageBlock.visibility = View.GONE
            holder.userMessageBlock.visibility = View.VISIBLE
            holder.userMessageText.text = view.text
            holder.userMessageTime.text = view.timeStamp.asTime()
        } else {
            holder.companionMessageBlock.visibility = View.VISIBLE
            holder.userMessageBlock.visibility = View.GONE
            holder.companionMessageText.text = view.text
            holder.companionMessageTime.text =
                view.timeStamp.asTime()
        }
    }
}