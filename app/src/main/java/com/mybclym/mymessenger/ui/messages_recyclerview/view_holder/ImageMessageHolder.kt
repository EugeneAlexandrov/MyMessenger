package com.mybclym.mymessenger.ui.messages_recyclerview.view_holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.mymessenger.database.UID
import com.mybclym.mymessenger.ui.messages_recyclerview.views.MessageView
import com.mybclym.mymessenger.utilits.asTime
import com.mybclym.mymessenger.utilits.downloadAndSetImage
import kotlinx.android.synthetic.main.message_item_image.view.*

class ImageMessageHolder(view: View) : RecyclerView.ViewHolder(view), MessageHolder {
    private val userMessageBlock: ConstraintLayout = view.user_message_block
    private val userMessageImage: ImageView = view.user_message_image
    private val userMessageTime: TextView = view.user_message_time
    private val companionMessageBlock: ConstraintLayout = view.companion_message_block
    private val companionMessageImage: ImageView = view.companion_message_image
    private val companionMessageTime: TextView = view.companion_message_time

    override fun drawMessage(message: MessageView) {
        if (message.from == UID) {
            companionMessageBlock.visibility = View.GONE
            userMessageBlock.visibility = View.VISIBLE
            userMessageImage.downloadAndSetImage(message.fileUrl)
            userMessageTime.text = message.timeStamp.asTime()
        } else {
            companionMessageBlock.visibility = View.VISIBLE
            userMessageBlock.visibility = View.GONE
            companionMessageImage.downloadAndSetImage(message.fileUrl)
            companionMessageTime.text = message.timeStamp.asTime()
        }
    }

    override fun onAttach(message: MessageView) {

    }

    override fun onDettach() {

    }
}