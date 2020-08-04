package com.mybclym.mymessenger.ui.fragments

import android.net.wifi.hotspot2.pps.Credential
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.mybclym.mymessenger.MainActivity
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.ui.activities.RegisterActivity
import com.mybclym.mymessenger.utilits.AUTH
import com.mybclym.mymessenger.utilits.AppTextWatcher
import com.mybclym.mymessenger.utilits.replaceActivity
import com.mybclym.mymessenger.utilits.showToast
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EntryCodeFragment(val phoneNumber: String, val id: String) :
    Fragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = phoneNumber
        register_code_entry.addTextChangedListener(AppTextWatcher {
            val string = register_code_entry.text.toString()
            if (string.length == 6) {
                verifyCode()
            }
        })
    }

    fun verifyCode() {
        val code = register_code_entry.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener() {
            if (it.isSuccessful) {
                showToast("OK")
                (activity as RegisterActivity).replaceActivity(MainActivity())
            } else showToast(it.exception?.message.toString())
        }
    }
}