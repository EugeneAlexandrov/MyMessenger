package com.mybclym.mymessenger.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mybclym.mymessenger.R
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
            Toast.makeText(
                activity,
                getString(R.string.register_toast_emptyphonenumber),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.register_data_container, EnterCodeFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}