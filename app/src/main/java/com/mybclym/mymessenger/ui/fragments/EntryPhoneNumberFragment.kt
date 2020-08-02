package com.mybclym.mymessenger.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.utilits.replaceFragment
import com.mybclym.mymessenger.utilits.showToast
import kotlinx.android.synthetic.main.fragment_entry_phone_number.*

class EntryPhoneNumberFragment : Fragment(R.layout.fragment_entry_phone_number) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        register_btn_next.setOnClickListener { sendCode() }
    }

    private fun sendCode() {
        if (register_phone_entry_edittext.text.toString().isEmpty()) {
            showToast(getString(R.string.register_toast_emptyphonenumber))
        } else {
            replaceFragment(EntryCodeFragment())
        }
    }
}