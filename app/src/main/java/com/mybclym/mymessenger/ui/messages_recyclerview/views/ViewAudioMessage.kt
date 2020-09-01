package com.mybclym.mymessenger.ui.messages_recyclerview.views

data class ViewAudioMessage(
    override val id: String,
    override val from: String,
    override val timeStamp: String,
    override val fileUrl: String,
    override val text: String = ""
) : MessageView {
    override fun getTypeView(): Int {
        return MessageView.AUDIO_MESSAGE
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ViewAudioMessage) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}