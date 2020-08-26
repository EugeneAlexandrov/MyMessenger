package com.mybclym.mymessenger.ui.fragments.singleChat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.database.TYPE_IMAGE
import com.mybclym.mymessenger.database.TYPE_TEXT
import com.mybclym.mymessenger.database.UID
import com.mybclym.mymessenger.models.CommonModel
import com.mybclym.mymessenger.utilits.asTime
import com.mybclym.mymessenger.utilits.downloadAndSetImage
import kotlinx.android.synthetic.main.message_item.view.*

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    private var messagesCacheList = mutableListOf<CommonModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return SingleChatHolder(view)
    }

    override fun getItemCount(): Int = messagesCacheList.size

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {
        when (messagesCacheList[position].type) {
            TYPE_TEXT -> drawMessageText(holder, position)
            TYPE_IMAGE -> drawMessageImage(holder, position)
        }

    }

    private fun drawMessageImage(holder: SingleChatAdapter.SingleChatHolder, position: Int) {
        holder.userMessageText.visibility = View.GONE
        holder.companionMessageText.visibility = View.GONE
        if (messagesCacheList[position].from == UID) {
            holder.companionMessageBlock.visibility = View.GONE
            holder.userMessageBlock.visibility = View.VISIBLE
            holder.userMessageImage.visibility = View.VISIBLE
            holder.userMessageImage.downloadAndSetImage(messagesCacheList[position].fileUrl)
            holder.userMessageTime.text = messagesCacheList[position].timeStamp.toString().asTime()
        } else {
            holder.companionMessageBlock.visibility = View.VISIBLE
            holder.userMessageBlock.visibility = View.GONE
            holder.companionMessageImage.visibility = View.VISIBLE
            holder.companionMessageImage.downloadAndSetImage(messagesCacheList[position].fileUrl)
            holder.companionMessageTime.text =
                messagesCacheList[position].timeStamp.toString().asTime()
        }
    }

    private fun drawMessageText(holder: SingleChatAdapter.SingleChatHolder, position: Int) {
        holder.userMessageImage.visibility = View.GONE
        holder.companionMessageImage.visibility = View.GONE
        if (messagesCacheList[position].from == UID) {
            holder.companionMessageBlock.visibility = View.GONE
            holder.userMessageBlock.visibility = View.VISIBLE
            holder.userMessageText.visibility = View.VISIBLE
            holder.userMessageText.text = messagesCacheList[position].text
            holder.userMessageTime.text = messagesCacheList[position].timeStamp.toString().asTime()
        } else {
            holder.companionMessageBlock.visibility = View.VISIBLE
            holder.userMessageBlock.visibility = View.GONE
            holder.companionMessageText.visibility = View.VISIBLE
            holder.companionMessageText.text = messagesCacheList[position].text
            holder.companionMessageTime.text =
                messagesCacheList[position].timeStamp.toString().asTime()
        }
    }

    fun addItemToBottom(item: CommonModel) {
        if (!messagesCacheList.contains(item)) {
            messagesCacheList.add(item)
            notifyItemInserted(messagesCacheList.size)
        }
    }

    fun addItemToTop(item: CommonModel) {
        if (!messagesCacheList.contains(item)) {
            messagesCacheList.add(item)
            messagesCacheList.sortBy { it.timeStamp.toString() }
            notifyItemInserted(0)
        }
    }

    class SingleChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userMessageBlock: ConstraintLayout = view.user_message_block
        val userMessageText: TextView = view.user_message_text
        val userMessageImage: ImageView = view.user_message_image
        val userMessageTime: TextView = view.user_message_time

        val companionMessageBlock: ConstraintLayout = view.companion_message_block
        val companionMessageText: TextView = view.companion_message_text
        val companionMessageImage: ImageView = view.companion_message_image
        val companionMessageTime: TextView = view.companion_message_time
    }
}
