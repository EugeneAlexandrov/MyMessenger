package com.mybclym.mymessenger.database

import android.net.Uri
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.mybclym.mymessenger.R
import com.mybclym.mymessenger.models.CommonModel
import com.mybclym.mymessenger.models.UserModel
import com.mybclym.mymessenger.utilits.APP_ACTIVITY
import com.mybclym.mymessenger.utilits.AppValueEventListener
import com.mybclym.mymessenger.utilits.showToast


fun initFirebase() {
    AUTH = Firebase.auth
    REF_DATABASE_ROOT = Firebase.database.reference
    REF_STORAGE_ROOT = Firebase.storage.reference
    USER = UserModel()
    UID = AUTH.currentUser?.uid.toString()
}

inline fun putUrlToDataBase(url: String, crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(
        UID
    ).child(CHILD_PHOTO_URL).setValue(url)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun getUrlFromStorage(path: StorageReference, crossinline function: (url: String) -> Unit) {
    path.downloadUrl
        .addOnSuccessListener { function(it.toString()) }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun putFileToStorage(uri: Uri, path: StorageReference, crossinline function: () -> Unit) {
    path.putFile(uri)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun initUser(crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
        .addListenerForSingleValueEvent(AppValueEventListener {
            USER = it.getValue(UserModel::class.java) ?: UserModel()
            if (USER.username.isEmpty()) {
                USER.username =
                    UID
            }
            function()
        })
}

fun updatePhonesToDataBase(arrayContacts: ArrayList<CommonModel>) {
    if (AUTH.currentUser != null) {
        REF_DATABASE_ROOT.child(NODE_PHONES)
            .addListenerForSingleValueEvent(AppValueEventListener {
                it.children.forEach { snapshot ->
                    arrayContacts.forEach { contact ->
                        if (snapshot.key == contact.phone) {
                            REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS)
                                .child(UID)
                                .child(snapshot.value.toString())
                                .child(CHILD_ID)
                                .setValue(snapshot.value.toString())
                                .addOnFailureListener {
                                    showToast(
                                        it.message.toString()
                                    )
                                }
                            REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS)
                                .child(UID)
                                .child(snapshot.value.toString())
                                .child(CHILD_FULLNAME)
                                .setValue(contact.fullname)
                                .addOnFailureListener {
                                    showToast(
                                        it.message.toString()
                                    )
                                }
                        }
                    }
                }
            })
    }
}

fun DataSnapshot.getCommonModel(): CommonModel =
    this.getValue(CommonModel::class.java) ?: CommonModel()

fun DataSnapshot.getUserModel(): UserModel =
    this.getValue(UserModel::class.java) ?: UserModel()

fun sendMessage(
    message: String,
    companionUserId: String,
    messageType: String,
    function: () -> Unit
) {
    val refDialogUser = "$NODE_MESSAGES/$UID/$companionUserId"
    val refDialogCompanionUser = "$NODE_MESSAGES/$companionUserId/$UID"
    val messageKey = REF_DATABASE_ROOT.child(refDialogUser).push().key
    val mapMessage = hashMapOf<String, Any>()
    mapMessage[CHILD_FROM] = UID
    mapMessage[CHILD_TYPE] = messageType
    mapMessage[CHILD_ID] = messageKey.toString()
    mapMessage[CHILD_TEXT] = message
    mapMessage[CHILD_TIMESTAMP] = ServerValue.TIMESTAMP
    val mapDialog = hashMapOf<String, Any>()
    mapDialog["$refDialogUser/$messageKey"] = mapMessage
    mapDialog["$refDialogCompanionUser/$messageKey"] = mapMessage
    REF_DATABASE_ROOT
        .updateChildren(mapDialog)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

fun setLoginToDataBase(newLogin: String) {
    REF_DATABASE_ROOT.child(NODE_USERNAMES)
        .child(newLogin).setValue(UID)
        .addOnSuccessListener {
            updateCurrentLogin(newLogin)
        }
}

fun updateCurrentLogin(newLogin: String) {
    REF_DATABASE_ROOT.child(NODE_USERS)
        .child(UID).child(CHILD_USERNAME).setValue(newLogin)
        .addOnSuccessListener {
            showToast(APP_ACTIVITY.getString(R.string.changename_toast_dataupdate))
            deleteOldLogin(newLogin)
        }
}

private fun deleteOldLogin(newLogin: String) {
    REF_DATABASE_ROOT.child(NODE_USERNAMES)
        .child(USER.username).removeValue()
        .addOnSuccessListener {
            showToast(APP_ACTIVITY.getString(R.string.changename_toast_dataupdate))
            APP_ACTIVITY.supportFragmentManager.popBackStack()
            USER.username = newLogin
        }
}

fun setBioToDataBase(bio: String) {
    REF_DATABASE_ROOT.child(NODE_USERS)
        .child(UID).child(CHILD_BIO).setValue(bio)
        .addOnSuccessListener {
            showToast(APP_ACTIVITY.getString(R.string.changename_toast_dataupdate))
            USER.bio = bio
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }
}

fun setNameToDataBase(fullname: String) {
    REF_DATABASE_ROOT.child(NODE_USERS)
        .child(UID).child(CHILD_FULLNAME)
        .setValue(fullname)
        .addOnSuccessListener {
            showToast(APP_ACTIVITY.getString(R.string.changename_toast_dataupdate))
            USER.fullname = fullname
            APP_ACTIVITY.appDrawer.updateHeader()
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }
}

fun sendFile(companionUserId: String, fileUrl: String, messageKey: String, messageType: String) {
    val refDialogUser = "$NODE_MESSAGES/$UID/$companionUserId"
    val refDialogCompanionUser = "$NODE_MESSAGES/$companionUserId/$UID"
    val mapMessage = hashMapOf<String, Any>()
    mapMessage[CHILD_FROM] = UID
    mapMessage[CHILD_TYPE] = messageType
    mapMessage[CHILD_ID] = messageKey
    mapMessage[CHILD_TIMESTAMP] = ServerValue.TIMESTAMP
    mapMessage[CHILD_FILE_URL] = fileUrl
    val mapDialog = hashMapOf<String, Any>()
    mapDialog["$refDialogUser/$messageKey"] = mapMessage
    mapDialog["$refDialogCompanionUser/$messageKey"] = mapMessage
    REF_DATABASE_ROOT
        .updateChildren(mapDialog)
        .addOnFailureListener { showToast(it.message.toString()) }
}

fun getMessageKey(id: String): String {
    return REF_DATABASE_ROOT
        .child(NODE_MESSAGES)
        .child(UID)
        .child(id)
        .push().key.toString()
}

fun uploadFileToStorage(uri: Uri, messageKey: String, companionID: String, messageType: String) {
    val path = REF_STORAGE_ROOT.child(FOLDER_FILES).child(messageKey)
    putFileToStorage(uri, path) {
        getUrlFromStorage(path) {
            sendFile(companionID, it, messageKey, messageType)
        }
    }
}


