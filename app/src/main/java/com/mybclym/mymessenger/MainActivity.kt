package com.mybclym.mymessenger

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.mybclym.mymessenger.database.AUTH
import com.mybclym.mymessenger.database.initFirebase
import com.mybclym.mymessenger.database.initUser
import com.mybclym.mymessenger.databinding.ActivityMainBinding
import com.mybclym.mymessenger.ui.fragments.MainFragment
import com.mybclym.mymessenger.ui.fragments.register.EntryPhoneNumberFragment
import com.mybclym.mymessenger.ui.objects.AppDrawer
import com.mybclym.mymessenger.utilits.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var appDrawer: AppDrawer
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TEST", "MainActivity onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
        initFirebase()
        initUser() {
            initContacts()
            initFields()
            initFunc()
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d("TEST", "MainActivity onStart")
        AppStates.updateState(AppStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        Log.d("TEST", "MainActivity onStop")
        AppStates.updateState(AppStates.OFFLINE)
    }

    private fun initFields() {
        toolbar = binding.mainToolBar
        appDrawer = AppDrawer()

    }

    private fun initFunc() {
        setSupportActionBar(toolbar)
        if (AUTH.currentUser != null) {
            appDrawer.create()
            replaceFragment(MainFragment(), false)
        } else {
            replaceFragment(EntryPhoneNumberFragment(), false)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(APP_ACTIVITY, READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            initContacts()
        }
    }
}