package com.mybclym.mymessenger.ui.fragments

import android.view.*
import androidx.fragment.app.Fragment
import com.mybclym.mymessenger.MainActivity
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.utilits.APP_ACTIVITY
import com.mybclym.mymessenger.utilits.hideKeyBoard

open class BaseChangeFragment(val layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        APP_ACTIVITY.appDrawer.disableDrawer()
        hideKeyBoard()
    }

    override fun onStop() {
        super.onStop()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        APP_ACTIVITY.menuInflater.inflate(
            R.menu.settings_change_info_action_menu,
            menu
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_confirm_button -> change()
        }
        return true
    }

    open fun change() {

    }
}