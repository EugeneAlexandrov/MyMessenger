package com.mybclym.mymessenger.ui.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.database.AUTH
import com.mybclym.mymessenger.utilits.*
import kotlinx.android.synthetic.main.fragment_entry_phone_number.*
import java.util.concurrent.TimeUnit

class EntryPhoneNumberFragment : Fragment(R.layout.fragment_entry_phone_number) {

    private lateinit var phoneNumber: String
    private lateinit var phoneCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        phoneCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener() { task1 ->
                    if (task1.isSuccessful) {
                        showToast("Добро пожаловать")
                        restartActivity()
                    } else showToast(task1.exception?.message.toString())
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(
                    EntryCodeFragment(
                        phoneNumber,
                        id
                    )
                )
            }
        }
        register_btn_next.setOnClickListener { sendCode() }
    }

    private fun sendCode() {
        if (register_phone_entry_edittext.text.toString().isEmpty()) {
            showToast(getString(R.string.register_toast_emptyphonenumber))
        } else {
            authUser()
        }
    }

    private fun authUser() {
        phoneNumber = register_phone_entry_edittext.text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            APP_ACTIVITY,
            phoneCallback
        )
    }
}