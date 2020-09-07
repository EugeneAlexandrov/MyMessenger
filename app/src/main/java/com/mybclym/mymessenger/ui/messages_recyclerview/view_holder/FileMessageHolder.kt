package com.mybclym.mymessenger.ui.messages_recyclerview.view_holder

import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.mymessenger.database.UID
import com.mybclym.mymessenger.database.getFileFromStorage
import com.mybclym.mymessenger.database.getUrlFromStorage
import com.mybclym.mymessenger.ui.messages_recyclerview.views.MessageView
import com.mybclym.mymessenger.utilits.WRITE_FILES
import com.mybclym.mymessenger.utilits.asTime
import com.mybclym.mymessenger.utilits.checkPermission
import com.mybclym.mymessenger.utilits.showToast
import kotlinx.android.synthetic.main.message_item_file.view.*
import java.io.File
import java.lang.Exception

class FileMessageHolder(view: View) : RecyclerView.ViewHolder(view), MessageHolder {
    private val userMessageBlock: ConstraintLayout = view.user_message_block
    private val userMessageFileTitle: TextView = view.user_message_file_title
    private val userMessageTime: TextView = view.user_message_time
    private val userMessageFileIcon: ImageView = view.user_message_file_icon
    private val userMessageProgressBar: ProgressBar = view.user_message_file_progressbar
    private val userMessageDownloadComplete: ImageView = view.user_message_file_download_complete

    private val companionMessageBlock: ConstraintLayout = view.companion_message_block
    private val companionMessageFileTitle: TextView = view.companion_message_file_title
    private val companionMessageFileIcon: ImageView = view.companion_message_file_icon
    private val companionMessageTime: TextView = view.companion_message_time
    private val companionMessageProgressBar: ProgressBar = view.companion_message_file_progressbar
    private val companionMessageDownloadComplete: ImageView =
        view.companion_message_file_download_complete

    override fun drawMessage(message: MessageView) {
        if (message.from == UID) {
            companionMessageBlock.visibility = View.GONE
            userMessageBlock.visibility = View.VISIBLE
            userMessageFileTitle.text = message.text
            userMessageTime.text = message.timeStamp.asTime()
        } else {
            companionMessageBlock.visibility = View.VISIBLE
            userMessageBlock.visibility = View.GONE
            companionMessageFileTitle.text = message.text
            companionMessageTime.text = message.timeStamp.asTime()
        }
    }

    override fun onAttach(message: MessageView) {
        if (message.from == UID) userMessageBlock.setOnClickListener { clickToBtnFile(message) }
        else userMessageBlock.setOnClickListener { clickToBtnFile(message) }
    }

    override fun onDettach() {
        userMessageBlock.setOnClickListener(null)
        companionMessageBlock.setOnClickListener(null)
    }

    private fun clickToBtnFile(message: MessageView) {
        if (message.from == UID) {
            userMessageFileIcon.visibility = View.INVISIBLE
            userMessageProgressBar.visibility = View.VISIBLE
        } else {
            companionMessageFileIcon.visibility = View.INVISIBLE
            companionMessageProgressBar.visibility = View.VISIBLE
        }
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            message.text
        )
        try {
            if (checkPermission(WRITE_FILES)) {
                Log.d("Test","check permission write file")
                file.createNewFile()
                getFileFromStorage(file, message.fileUrl) {
                    if (message.from == UID) {
                            userMessageDownloadComplete.visibility = View.VISIBLE
                            userMessageProgressBar.visibility = View.INVISIBLE
                            Log.d("Test", "downloading complete, progressBar invisible")
                    } else {
                            companionMessageDownloadComplete.visibility = View.VISIBLE
                            companionMessageProgressBar.visibility = View.INVISIBLE
                    }
                }
            }
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }
}