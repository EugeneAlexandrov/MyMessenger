package com.mybclym.mymessenger.ui.messages_recyclerview.view_holder

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.mymessenger.database.UID
import com.mybclym.mymessenger.ui.messages_recyclerview.views.MessageView
import com.mybclym.mymessenger.utilits.AppVoicePlayer
import com.mybclym.mymessenger.utilits.asTime
import kotlinx.android.synthetic.main.message_item_audio.view.*

class AudioMessageHolder(view: View) : RecyclerView.ViewHolder(view), MessageHolder {
    private val voicePlayer = AppVoicePlayer()

    val userMessageBlock: ConstraintLayout = view.user_message_block
    val userMessagePlayBtn: ImageView = view.user_message_audio_playbtn
    val userMessageStopBtn: ImageView = view.user_message_audio_stopbtn
    val userMessageTime: TextView = view.user_message_time

    val companionMessageBlock: ConstraintLayout = view.companion_message_block
    val companionMessagePlayBtn: ImageView = view.companion_message_audio_playbtn
    val companionMessageStopBtn: ImageView = view.companion_message_audio_stopbtn
    val companionMessageTime: TextView = view.companion_message_time

    override fun drawMessage(message: MessageView) {
        if (message.from == UID) {
            companionMessageBlock.visibility = View.GONE
            userMessageBlock.visibility = View.VISIBLE
            userMessagePlayBtn.visibility = View.VISIBLE
            userMessageStopBtn.visibility = View.GONE
            userMessageTime.text = message.timeStamp.asTime()
        } else {
            companionMessageBlock.visibility = View.VISIBLE
            userMessageBlock.visibility = View.GONE
            companionMessagePlayBtn.visibility = View.VISIBLE
            companionMessageStopBtn.visibility = View.GONE
            companionMessageTime.text = message.timeStamp.asTime()
        }
    }

    override fun onAttach(message: MessageView) {
        voicePlayer.init()
        if (message.from == UID) {
            userMessagePlayBtn.setOnClickListener {
                userMessagePlayBtn.visibility = View.GONE
                userMessageStopBtn.visibility = View.VISIBLE
                voicePlayer.start(message.id, message.fileUrl) {
                    Log.d("Test", "end playing and change stop on play")
                    userMessagePlayBtn.visibility = View.VISIBLE
                    userMessageStopBtn.visibility = View.GONE
                }
            }
            userMessageStopBtn.setOnClickListener {
                voicePlayer.stop {
                    userMessagePlayBtn.visibility = View.VISIBLE
                    userMessageStopBtn.visibility = View.GONE
                }
            }
        } else {
            companionMessagePlayBtn.setOnClickListener {
                companionMessagePlayBtn.visibility = View.GONE
                companionMessageStopBtn.visibility = View.VISIBLE
                voicePlayer.start(message.id, message.fileUrl) {
                    companionMessagePlayBtn.visibility = View.VISIBLE
                    companionMessageStopBtn.visibility = View.GONE
                }
            }
            companionMessageStopBtn.setOnClickListener {
                voicePlayer.stop {
                    companionMessagePlayBtn.visibility = View.VISIBLE
                    companionMessageStopBtn.visibility = View.GONE
                }
            }
        }
    }

    override fun onDettach() {
        userMessagePlayBtn.setOnClickListener(null)
        companionMessagePlayBtn.setOnClickListener(null)
        voicePlayer.release()
    }
}