package com.mybclym.mymessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.mybclym.mymessenger.databinding.ActivityMainBinding
import com.mybclym.mymessenger.ui.activities.RegisterActivity
import com.mybclym.mymessenger.ui.fragments.ChartsFragment
import com.mybclym.mymessenger.ui.objects.AppDrawer
import com.mybclym.mymessenger.utilits.replaceActivity
import com.mybclym.mymessenger.utilits.replaceFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appDrawer: AppDrawer
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()

    }

    private fun initFields() {

        toolbar = binding.mainToolBar
        appDrawer = AppDrawer(this, toolbar)
    }

    private fun initFunc() {
        if (true) {
            setSupportActionBar(toolbar)
            appDrawer.create()
            replaceFragment(ChartsFragment())
        } else {
            replaceActivity(RegisterActivity())
        }
    }
}