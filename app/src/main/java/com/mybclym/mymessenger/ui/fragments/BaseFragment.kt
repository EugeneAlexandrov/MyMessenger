package com.mybclym.mymessenger.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mybclym.mymessenger.MainActivity
import com.mybclym.mymessenger.R

open class BaseFragment(val layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).appDrawer.enableDrawer()
    }
}