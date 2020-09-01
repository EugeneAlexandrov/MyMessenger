package com.mybclym.mymessenger.ui.fragments.singleChat

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.mymessenger.ui.messages_recyclerview.view_holder.*
import com.mybclym.mymessenger.ui.messages_recyclerview.views.MessageView

class SingleChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messagesCacheList = mutableListOf<MessageView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppHolderFactory.getHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MessageHolder).drawMessage(messagesCacheList[position])
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        (holder as MessageHolder).onAttach(messagesCacheList[holder.adapterPosition])
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        (holder as MessageHolder).onDettach()
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int = messagesCacheList.size

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
