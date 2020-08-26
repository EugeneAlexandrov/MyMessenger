package com.mybclym.mymessenger.ui.fragments.singleChat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.database.*
import com.mybclym.mymessenger.models.CommonModel
import com.mybclym.mymessenger.models.UserModel
import com.mybclym.mymessenger.ui.fragments.BaseFragment
import com.mybclym.mymessenger.utilits.*
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.chat_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_single_chat.*

class SingleChatFragment(private val contact: CommonModel) :
    BaseFragment(R.layout.fragment_single_chat) {

    private lateinit var chatToolbarListener: AppValueEventListener
    private lateinit var companionUser: UserModel
    private lateinit var chatToolbarInfo: View
    private lateinit var refUsers: DatabaseReference
    private lateinit var refMessages: DatabaseReference
    private lateinit var adapter: SingleChatAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var messagesListener: AppChildEventListener
    private var countMessages = 15
    private var isScrolling = false
    private var isSmoothScrollPosition = true
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        initFields()
        initToolbar()
        initRecyclerView()
    }

    private fun initFields() {
        recyclerView = singlechat_messages_recyclerview
        layoutManager = LinearLayoutManager(this.context)
        singlechat_message_et.addTextChangedListener(AppTextWatcher {
            val string = singlechat_message_et.text.toString()
            if (string.isEmpty()) {
                btn_send.visibility = View.GONE
                btn_attach.visibility = View.VISIBLE
            } else {
                btn_send.visibility = View.VISIBLE
                btn_attach.visibility = View.GONE
            }
        })
        btn_attach.setOnClickListener {
            attachFile()
        }
    }

    private fun attachFile() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(600, 600)
            .start(APP_ACTIVITY, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == Activity.RESULT_OK
            && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri
            val messageKey = REF_DATABASE_ROOT
                .child(NODE_MESSAGES)
                .child(UID)
                .child(contact.id)
                .push().key.toString()
            val path = REF_STORAGE_ROOT.child(FOLDER_MESSAGE_FILE).child(messageKey)
            putImageToStorage(uri, path) {
                getUrlFromStorage(path) {
                    sendFile(contact.id, it, messageKey, TYPE_IMAGE)
                    isSmoothScrollPosition = true
                }
            }
        }
    }

    private fun initRecyclerView() {


        recyclerView.layoutManager = layoutManager
        adapter = SingleChatAdapter()
        recyclerView.adapter = adapter
        refMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(UID).child(contact.id)
        messagesListener = AppChildEventListener {
            val message = it.getCommonModel()
            if (isSmoothScrollPosition) {
                adapter.addItemToBottom(message)
                recyclerView.smoothScrollToPosition(adapter.itemCount)
            } else {
                adapter.addItemToTop(message)
            }
        }

        refMessages.limitToLast(countMessages).addChildEventListener(messagesListener)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isScrolling && dy < 0 && layoutManager.findFirstVisibleItemPosition() <= 5) {
                    println("UpdateData")
                    updateData()
                }
            }


            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }
        })
    }

    private fun updateData() {
        isSmoothScrollPosition = false
        isScrolling = false
        countMessages += 15
        refMessages.removeEventListener(messagesListener)
        refMessages.limitToLast(countMessages).addChildEventListener(messagesListener)
    }

    private fun initToolbar() {
        chatToolbarInfo = APP_ACTIVITY.toolbar.chat_toolbar
        chatToolbarInfo.visibility = View.VISIBLE
        btn_send.setOnClickListener {
            isSmoothScrollPosition = true
            val message = singlechat_message_et.text.toString()
            if (message.isEmpty()) showToast("Введите сообщение")
            else sendMessage(message, contact.id, TYPE_TEXT) {
                singlechat_message_et.setText("")
                hideKeyBoard()
            }
        }
        chatToolbarListener = AppValueEventListener {
            companionUser = it.getUserModel()
            initInfoToolbar()
        }
        refUsers = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        refUsers.addValueEventListener(chatToolbarListener)
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
        refUsers.removeEventListener(chatToolbarListener)
        refMessages.removeEventListener(messagesListener)
        println()
    }

}


