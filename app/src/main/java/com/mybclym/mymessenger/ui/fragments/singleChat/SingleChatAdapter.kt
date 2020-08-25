package com.mybclym.mymessenger.ui.fragments.singleChat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.database.UID
import com.mybclym.mymessenger.models.CommonModel
import com.mybclym.mymessenger.utilits.DiffUtilCallBack
import com.mybclym.mymessenger.utilits.asTime
import kotlinx.android.synthetic.main.message_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    private var messagesCacheList = mutableListOf<CommonModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return SingleChatHolder(view)
    }

    override fun getItemCount(): Int = messagesCacheList.size

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {
        if (messagesCacheList[position].from == UID) {
            holder.companionMessageBlock.visibility = View.GONE
            holder.userMessageBlock.visibility = View.VISIBLE
            holder.userMessageText.text = messagesCacheList[position].text
            holder.userMessageTime.text = messagesCacheList[position].timeStamp.toString().asTime()
        } else {
            holder.companionMessageBlock.visibility = View.VISIBLE
            holder.userMessageBlock.visibility = View.GONE
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
        val userMessageTime: TextView = view.user_message_time

        val companionMessageBlock: ConstraintLayout = view.companion_message_block
        val companionMessageText: TextView = view.companion_message_text
        val companionMessageTime: TextView = view.companion_message_time
    }
}
