package com.mybclym.mymessenger.ui.fragments

import androidx.fragment.app.Fragment
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.utilits.APP_ACTIVITY

class MainFragment : Fragment(R.layout.fragment_chats) {
    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.toolbar.title = "My messanger"
    }
}