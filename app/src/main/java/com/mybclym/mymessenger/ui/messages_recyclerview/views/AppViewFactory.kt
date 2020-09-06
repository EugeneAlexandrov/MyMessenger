package com.mybclym.mymessenger.ui.messages_recyclerview.views

import com.mybclym.mymessenger.database.TYPE_AUDIO
import com.mybclym.mymessenger.database.TYPE_FILE
import com.mybclym.mymessenger.database.TYPE_IMAGE
import com.mybclym.mymessenger.models.CommonModel

class AppViewFactory {
    companion object {
        fun getView(message: CommonModel): MessageView {
            return when (message.type) {
                TYPE_IMAGE -> {
                    ViewImageMessage(
                        message.id,
                        message.from,
                        message.timeStamp.toString(),
                        message.fileUrl
                    )
                }
                TYPE_AUDIO -> {
                    ViewAudioMessage(
                        message.id,
                        message.from,
                        message.timeStamp.toString(),
                        message.fileUrl
                    )
                }
                TYPE_FILE -> {
                    ViewFileMessage(
                        message.id,
                        message.from,
                        message.timeStamp.toString(),
                        message.fileUrl,
                        message.text
                    )
                }
                else -> {
                    ViewTextMessage(
                        message.id,
                        message.from,
                        message.timeStamp.toString(),
                        "",
                        message.text
                    )
                }
            }
        }
    }
}