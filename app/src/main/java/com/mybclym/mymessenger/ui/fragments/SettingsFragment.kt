package com.mybclym.mymessenger.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import com.mybclym.mymessenger.R

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_action_menu, menu)
    }
}