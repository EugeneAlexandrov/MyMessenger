package com.mybclym.mymessenger.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.databinding.ActivityRegisterBinding
import com.mybclym.mymessenger.ui.fragments.EntryPhoneNumberFragment

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var toolBar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        toolBar = binding.RegisterToolBar
        setSupportActionBar(toolBar)
        title = getString(R.string.register_toolbar_text)
        supportFragmentManager.beginTransaction()
            .add(R.id.register_data_container, EntryPhoneNumberFragment())
            .commit()
    }
}