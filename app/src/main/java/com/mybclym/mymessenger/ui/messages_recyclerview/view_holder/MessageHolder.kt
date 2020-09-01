package com.mybclym.mymessenger.ui.messages_recyclerview.view_holder

import com.mybclym.mymessenger.ui.messages_recyclerview.views.MessageView

interface MessageHolder {
    fun drawMessage(message: MessageView)
    fun onAttach(message: MessageView)
    fun onDettach()
}