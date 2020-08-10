package com.mybclym.mymessenger.ui.fragments

import android.view.*
import androidx.fragment.app.Fragment
import com.mybclym.mymessenger.MainActivity
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.utilits.*
import kotlinx.android.synthetic.main.fragment_change_name.*

/**
 * A simple [Fragment] subclass.
 */
class ChangeNameFragment : BaseChangeFragment(R.layout.fragment_change_name) {
    override fun onResume() {
        super.onResume()
        val fullNameList = USER.fullname.split(" ")
        if (fullNameList.size > 1) {
            changename_name_edittext.setText(fullNameList[0])
            changename_surname_edittext.setText(fullNameList[1])
        } else changename_name_edittext.setText(fullNameList[0])
    }

    override fun change() {
        val name = changename_name_edittext.text.toString()
        val surname = changename_surname_edittext.text.toString()
        if (name.isEmpty()) {
            showToast(getString(R.string.changename_toast_empty_name))
        } else {
            val fullname = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_FULLNAME).setValue(fullname)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.changename_toast_dataupdate))
                        USER.fullname = fullname
                        fragmentManager?.popBackStack()
                    }
                }
        }
    }
}