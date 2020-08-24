package com.mybclym.mymessenger.ui.fragments

import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.database.*
import com.mybclym.mymessenger.utilits.*
import kotlinx.android.synthetic.main.fragment_change_bio.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {
    override fun onResume() {
        super.onResume()
        changebio_bio_edittext.setText(USER.bio)
    }

    override fun change() {
        super.change()
        val bio = changebio_bio_edittext.text.toString()
        setBioToDataBase(bio)
    }
}