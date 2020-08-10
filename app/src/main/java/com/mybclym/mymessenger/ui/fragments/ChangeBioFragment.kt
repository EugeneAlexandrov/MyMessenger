package com.mybclym.mymessenger.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.utilits.*
import kotlinx.android.synthetic.main.fragment_change_bio.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {
    override fun onResume() {
        super.onResume()
        changebio_bio_edittext.setText(USER.bio)
    }

    override fun change() {
        val bio = changebio_bio_edittext.text.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_BIO).setValue(bio)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.changename_toast_dataupdate))
                    USER.bio = bio
                    fragmentManager?.popBackStack()
                } else showToast(it.exception?.message.toString())
            }
    }
}