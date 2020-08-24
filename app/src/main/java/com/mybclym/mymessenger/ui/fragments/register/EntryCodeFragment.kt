package com.mybclym.mymessenger.ui.fragments.register

import androidx.fragment.app.Fragment
import com.google.firebase.auth.PhoneAuthProvider
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.database.AUTH
import com.mybclym.mymessenger.utilits.*
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EntryCodeFragment(val phoneNumber: String, val id: String) :
    Fragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.title = phoneNumber
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
        AUTH.signInWithCredential(credential).addOnCompleteListener() { task1 ->
            if (task1.isSuccessful) {
                createFirebaseUser(phoneNumber)
            } else showToast(task1.exception?.message.toString())
        }
    }
}