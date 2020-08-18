package com.mybclym.mymessenger.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.databinding.ActivityRegisterBinding
import com.mybclym.mymessenger.ui.fragments.EntryPhoneNumberFragment
import com.mybclym.mymessenger.utilits.initFirebase
import com.mybclym.mymessenger.utilits.replaceFragment

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var toolBar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFirebase()
    }

    override fun onStart() {
        super.onStart()
        toolBar = binding.registerToolBar
        setSupportActionBar(toolBar)
        title = getString(R.string.register_toolbar_text)
        replaceFragment(EntryPhoneNumberFragment(), false)
    }
}