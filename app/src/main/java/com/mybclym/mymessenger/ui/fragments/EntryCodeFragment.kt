package com.mybclym.mymessenger.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.utilits.AppTextWatcher
import com.mybclym.mymessenger.utilits.showToast
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EntryCodeFragment : Fragment(R.layout.fragment_enter_code) {
    override fun onStart() {
        super.onStart()
        register_code_entry.addTextChangedListener(AppTextWatcher {
            val string = register_code_entry.text.toString()
            if (string.length == 6) {
                verifyCode()
            }
        })
    }

    fun verifyCode() {
        showToast("OK")
    }
}