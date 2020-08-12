package com.mybclym.mymessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mybclym.mymessenger.databinding.ActivityMainBinding
import com.mybclym.mymessenger.models.User
import com.mybclym.mymessenger.ui.activities.RegisterActivity
import com.mybclym.mymessenger.ui.fragments.ChartsFragment
import com.mybclym.mymessenger.ui.objects.AppDrawer
import com.mybclym.mymessenger.utilits.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var appDrawer: AppDrawer
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
        initFirebase()
        initUser() {
            initFields()
            initFunc()
        }
    }

    override fun onStart() {
        super.onStart()
        AppStates.updateState(AppStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        Log.d("TEST","onStop")
        AppStates.updateState(AppStates.OFFLINE)
    }

    private fun initFields() {
        toolbar = binding.mainToolBar
        appDrawer = AppDrawer(this, toolbar)

    }

    private fun initFunc() {
        if (AUTH.currentUser != null) {
            setSupportActionBar(toolbar)
            appDrawer.create()
            replaceFragment(ChartsFragment(), false)
        } else {
            replaceActivity(RegisterActivity())
        }
    }


}