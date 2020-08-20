package com.mybclym.mymessenger.ui.fragments

import androidx.fragment.app.Fragment
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.utilits.APP_ACTIVITY

class ChartsFragment : Fragment(R.layout.fragment_chats) {
    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.toolbar.title = "Чаты"
    }
}