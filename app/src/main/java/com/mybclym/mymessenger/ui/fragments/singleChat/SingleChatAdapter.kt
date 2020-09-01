package com.mybclym.mymessenger.ui.fragments.singleChat

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.mymessenger.ui.fragments.messages_recyclerview.view_holder.AppHolderFactory
import com.mybclym.mymessenger.ui.fragments.messages_recyclerview.view_holder.AudioMessageHolder
import com.mybclym.mymessenger.ui.fragments.messages_recyclerview.view_holder.ImageMessageHolder
import com.mybclym.mymessenger.ui.fragments.messages_recyclerview.view_holder.TextMessageHolder
import com.mybclym.mymessenger.ui.fragments.messages_recyclerview.views.MessageView

class SingleChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messagesCacheList = mutableListOf<MessageView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppHolderFactory.getHolder(parent, viewType)
    }

    override fun getItemCount(): Int = messagesCacheList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextMessageHolder -> holder.drawMessageText(holder, messagesCacheList[position])
            is ImageMessageHolder -> holder.drawMessageImage(holder, messagesCacheList[position])
            is AudioMessageHolder -> holder.drawMessageVoice(holder, messagesCacheList[position])
            else -> {
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return messagesCacheList[position].getTypeView()
    }

    fun addItemToBottom(item: MessageView) {
        if (!messagesCacheList.contains(item)) {
            messagesCacheList.add(item)
            notifyItemInserted(messagesCacheList.size)
        }
    }

    fun addItemToTop(item: MessageView) {
        if (!messagesCacheList.contains(item)) {
            messagesCacheList.add(item)
            messagesCacheList.sortBy { it.timeStamp.toString() }
            notifyItemInserted(0)
        }
    }
}
