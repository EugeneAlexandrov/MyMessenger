package com.mybclym.mymessenger.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.google.firebase.storage.StorageReference
import com.mybclym.mymessenger.MainActivity
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.ui.activities.RegisterActivity
import com.mybclym.mymessenger.utilits.*
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageActivity
import com.theartofdev.edmodo.cropper.CropImageView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_settings.*
import java.net.URI

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
        btn_change_photo.setOnClickListener { changeUserPhoto() }
        settings_user_photo.downloadAndSetImage(USER.photoUrl)
    }

    private fun changeUserPhoto() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(APP_ACTIVITY, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == RESULT_OK
            && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE).child(UID)
            putImageToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToDataBase(it) {
                        settings_user_photo.downloadAndSetImage(it)
                        showToast(getString(R.string.changename_toast_dataupdate))
                        USER.photoUrl = it
                        APP_ACTIVITY.appDrawer.updateHeader()
                    }
                }
            }
//            path.putFile(uri).addOnCompleteListener { task1 ->
//                if (task1.isSuccessful) {
//                    path.downloadUrl.addOnCompleteListener { task2 ->
//                        if (task2.isSuccessful) {
//                            val photoURL = task2.result.toString()
//                            REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
//                                .child(CHILD_PHOTO_URL).setValue(photoURL).addOnCompleteListener {
//                                    if (it.isSuccessful) {
//                                        settings_user_photo.downloadAndSetImage(photoURL)
//                                        showToast(getString(R.string.changename_toast_dataupdate))
//                                        USER.photoUrl = photoURL
//                                    }
//                                }
//                        }
//                    }
//                }
//            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                APP_ACTIVITY.replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name -> {
                replaceFragment(ChangeNameFragment())
            }
        }
        return true
    }
}