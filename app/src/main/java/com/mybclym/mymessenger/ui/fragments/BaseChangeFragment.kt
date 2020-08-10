package com.mybclym.mymessenger.ui.fragments

import android.view.*
import androidx.fragment.app.Fragment
import com.mybclym.mymessenger.MainActivity
import com.mybclym.mymessenger.R

open class BaseChangeFragment(val layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        (activity as MainActivity).appDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(
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