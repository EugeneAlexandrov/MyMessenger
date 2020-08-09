package com.mybclym.mymessenger.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.mybclym.mymessenger.MainActivity
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.utilits.*
import kotlinx.android.synthetic.main.fragment_change_login.*
import kotlinx.android.synthetic.main.fragment_change_name.*
import java.util.*

class ChangeLoginFragment : BaseFragment(R.layout.fragment_change_login) {

    lateinit var newUserName: String

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        changelogin_login_edittext.setText(USER.username)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(
            R.menu.settings_changename_action_menu,
            menu
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_confirm_button -> change()
        }
        return true
    }

    private fun change() {
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