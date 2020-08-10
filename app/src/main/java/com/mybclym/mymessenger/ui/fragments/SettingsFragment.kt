package com.mybclym.mymessenger.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.mybclym.mymessenger.MainActivity
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.ui.activities.RegisterActivity
import com.mybclym.mymessenger.utilits.AUTH
import com.mybclym.mymessenger.utilits.USER
import com.mybclym.mymessenger.utilits.replaceActivity
import com.mybclym.mymessenger.utilits.replaceFragment
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        settings_username.text = USER.fullname
        btn_change_phone_phonetext.text = USER.phone
        settings_network_activity.text = USER.status
        btn_aboutMe_text.text = USER.bio
        btn_change_login_logintext.text = USER.username
        btn_change_login_block.setOnClickListener { replaceFragment(ChangeLoginFragment()) }
        btn_aboutMe_block.setOnClickListener { replaceFragment(ChangeBioFragment()) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name -> {
                replaceFragment(ChangeNameFragment())
            }
        }
        return true
    }
}