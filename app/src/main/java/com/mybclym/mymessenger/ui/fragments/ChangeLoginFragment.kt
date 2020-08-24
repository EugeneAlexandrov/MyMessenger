package com.mybclym.mymessenger.ui.fragments

import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.database.*
import com.mybclym.mymessenger.utilits.*
import kotlinx.android.synthetic.main.fragment_change_login.*
import java.util.*

class ChangeLoginFragment : BaseChangeFragment(R.layout.fragment_change_login) {

    lateinit var newLogin: String

    override fun onResume() {
        super.onResume()
        changelogin_login_edittext.setText(USER.username)
    }

    override fun change() {
        newLogin =
            changelogin_login_edittext.text.toString().toLowerCase(Locale.getDefault()).trim()
        if (newLogin.isEmpty()) showToast("Login is empty")
        else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(AppValueEventListener {
                    if (it.hasChild(newLogin)) showToast("Такой пользователь уже существует")
                    else setLoginToDataBase(newLogin)
                })
        }
    }


}