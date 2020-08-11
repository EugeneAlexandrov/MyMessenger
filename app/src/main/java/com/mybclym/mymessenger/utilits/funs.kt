package com.mybclym.mymessenger.utilits

import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mybclym.mymessenger.MainActivity
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.ui.activities.RegisterActivity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_settings.*

fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.replaceActivity(activity: AppCompatActivity) {
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
    this.finish()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack: Boolean = true) {
    if (addStack) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.data_container,
                fragment
            ).commit()
    } else {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.data_container,
                fragment
            ).commit()
    }
}

fun Fragment.replaceFragment(fragment: Fragment) {
    fragmentManager?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(
            R.id.data_container,
            fragment
        )?.commit()
}

fun Fragment.initUser(phone: String) {
    val uid = AUTH.currentUser?.uid.toString()
    val dataMap = mutableMapOf<String, Any>()
    dataMap[CHILD_ID] = uid
    dataMap[CHILD_PHONE] = phone
    dataMap[CHILD_USERNAME] = uid
    REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dataMap)
        .addOnCompleteListener { task2 ->
            if (task2.isSuccessful) {
                showToast("OK")
                (activity as RegisterActivity).replaceActivity(MainActivity())
            } else showToast(task2.exception?.message.toString())
        }
}

fun hideKeyBoard() {
    val imm: InputMethodManager =
        APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(APP_ACTIVITY.window.decorView.windowToken, 0)
}

fun CircleImageView.downloadAndSetImage(url: String) {
    Picasso.get().load(url)
        .placeholder(R.drawable.default_user)
        .into(this)
}
