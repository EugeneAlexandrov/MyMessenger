package com.mybclym.mymessenger.ui.fragments

import android.view.*
import com.mybclym.mymessenger.MainActivity
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.utilits.*
import kotlinx.android.synthetic.main.fragment_change_login.*
import java.util.*

class ChangeLoginFragment : BaseChangeFragment(R.layout.fragment_change_login) {

    lateinit var newUserName: String

    override fun onResume() {
        super.onResume()
        changelogin_login_edittext.setText(USER.username)
    }

    override fun change() {
        newUserName =
            changelogin_login_edittext.text.toString().toLowerCase(Locale.getDefault()).trim()
        if (newUserName.isEmpty()) showToast("Login is empty")
        else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(AppValueEventListener {
                    if (it.hasChild(newUserName)) showToast("Такой пользователь уже существует")
                    else changeLogin()
                })

        }
    }

    private fun changeLogin() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(newUserName).setValue(UID)
            .addOnCompleteListener {
                if (it.isSuccessful) updateCurrentUserName()
            }
    }

    private fun updateCurrentUserName() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_USERNAME).setValue(newUserName)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.changename_toast_dataupdate))
                    deleteOldUserName()
                } else showToast(it.exception?.message.toString())
            }
    }

    private fun deleteOldUserName() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.changename_toast_dataupdate))
                    fragmentManager?.popBackStack()
                    USER.username = newUserName
                } else showToast(it.exception?.message.toString())
            }
    }
}