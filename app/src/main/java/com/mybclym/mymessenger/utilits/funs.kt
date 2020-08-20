package com.mybclym.mymessenger.utilits

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mybclym.mymessenger.MainActivity
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.models.CommonModel
import com.mybclym.mymessenger.ui.activities.RegisterActivity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_settings.*
import java.text.SimpleDateFormat
import java.util.*

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
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

fun Fragment.createFirebaseUser(phone: String) {
    val uid = AUTH.currentUser?.uid.toString()
    val dataMap = mutableMapOf<String, Any>()
    dataMap[CHILD_ID] = uid
    dataMap[CHILD_PHONE] = phone
    REF_DATABASE_ROOT.child(NODE_PHONES).child(phone).setValue(uid)
        .addOnFailureListener { showToast(it.message.toString()) }.addOnCompleteListener {
            REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dataMap)
                .addOnSuccessListener {
                    showToast("OK")
                    (activity as RegisterActivity).replaceActivity(MainActivity())
                }
                .addOnFailureListener {
                    showToast(it.message.toString())
                }
        }
}

fun hideKeyBoard() {
    val imm: InputMethodManager =
        APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(APP_ACTIVITY.window.decorView.windowToken, 0)
}

fun ImageView.downloadAndSetImage(url: String) {
    Picasso.get().load(url)
        .fit()
        .placeholder(R.drawable.default_user)
        .into(this)
}

fun initContacts() {
    if (com.mybclym.mymessenger.utilits.checkPermission(READ_CONTACTS)) {
        var arrayContacts = arrayListOf<CommonModel>()
        var cursor = APP_ACTIVITY.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        cursor?.let {
            while (it.moveToNext()) {
                val fullName =
                    it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phone =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val newModel = CommonModel()
                newModel.fullname = fullName
                newModel.phone = phone.replace(Regex("[\\s,-]"), "")
                arrayContacts.add(newModel)
            }
        }
        cursor?.close()
        updatePhonesToDataBase(arrayContacts)
    }
}

fun String.asTime(): String {
    val time = Date(this.toLong())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}