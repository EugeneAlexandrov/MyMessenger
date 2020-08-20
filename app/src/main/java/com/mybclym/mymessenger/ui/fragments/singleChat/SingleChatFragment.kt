package com.mybclym.mymessenger.ui.fragments.singleChat

import android.os.Bundle
import android.view.View
import com.google.firebase.database.DatabaseReference
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.models.CommonModel
import com.mybclym.mymessenger.models.UserModel
import com.mybclym.mymessenger.ui.fragments.BaseFragment
import com.mybclym.mymessenger.utilits.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.chat_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_single_chat.*

class SingleChatFragment(private val contact: CommonModel) :
    BaseFragment(R.layout.fragment_single_chat) {

    lateinit var listenerChatToolbar: AppValueEventListener
    lateinit var companionUser: UserModel
    lateinit var chatToolbarInfo: View
    lateinit var refUsers: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        chatToolbarInfo = APP_ACTIVITY.toolbar.chat_toolbar
        chatToolbarInfo.visibility = View.VISIBLE
        btn_send.setOnClickListener {
            val message = message_et.text.toString()
            if (message.isEmpty()) showToast("Введите сообщение")
            else sendMessage(message, contact.id, TYPE_TEXT) {
                message_et.setText("")
            }
        }
        listenerChatToolbar = AppValueEventListener {
            companionUser = it.getUserModel()
            initInfoToolbar()
        }
        refUsers = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        refUsers.addValueEventListener(listenerChatToolbar)
    }



    private fun initInfoToolbar() {
        if (companionUser.fullname.isEmpty()) {
            chatToolbarInfo.toolbar_singlechat_fulname.text = contact.fullname
        } else chatToolbarInfo.toolbar_singlechat_fulname.text = companionUser.fullname
        chatToolbarInfo.toolbar_singlechat_image.downloadAndSetImage(companionUser.photoUrl)
        chatToolbarInfo.toolbar_singlechat_status.text = companionUser.status
    }

    override fun onPause() {
        super.onPause()
        chatToolbarInfo.visibility = View.GONE
        refUsers.removeEventListener(listenerChatToolbar)
    }
}


