package com.mybclym.mymessenger.ui.fragments.messages_recyclerview.view_holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.mymessenger.database.UID
import com.mybclym.mymessenger.ui.fragments.messages_recyclerview.views.MessageView
import com.mybclym.mymessenger.utilits.asTime
import com.mybclym.mymessenger.utilits.downloadAndSetImage
import kotlinx.android.synthetic.main.message_item_image.view.*

class ImageMessageHolder(view: View) : RecyclerView.ViewHolder(view) {
    val userMessageBlock: ConstraintLayout = view.user_message_block
    val userMessageImage: ImageView = view.user_message_image
    val userMessageTime: TextView = view.user_message_time
    val companionMessageBlock: ConstraintLayout = view.companion_message_block
    val companionMessageImage: ImageView = view.companion_message_image
    val companionMessageTime: TextView = view.companion_message_time

    fun drawMessageImage(holder: ImageMessageHolder, view: MessageView) {
        if (view.from == UID) {
            holder.companionMessageBlock.visibility = View.GONE
            holder.userMessageBlock.visibility = View.VISIBLE
            holder.userMessageImage.downloadAndSetImage(view.fileUrl)
            holder.userMessageTime.text = view.timeStamp.asTime()
        } else {
            holder.companionMessageBlock.visibility = View.VISIBLE
            holder.userMessageBlock.visibility = View.GONE
            holder.companionMessageImage.downloadAndSetImage(view.fileUrl)
            holder.companionMessageTime.text = view.timeStamp.asTime()
        }
    }
}