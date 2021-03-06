package com.mybclym.mymessenger.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mybclym.mymessenger.MainActivity
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.utilits.APP_ACTIVITY

open class BaseFragment(val layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        Log.d("TEST", "BaseFragment onStart")
       // APP_ACTIVITY.appDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        Log.d("TEST", "BaseFragment onStop")
       // APP_ACTIVITY.appDrawer.enableDrawer()
    }
}