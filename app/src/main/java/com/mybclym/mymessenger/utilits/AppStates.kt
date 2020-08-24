package com.mybclym.mymessenger.utilits

import com.mybclym.mymessenger.database.*

enum class AppStates(val state: String) {
    ONLINE("в сети"),
    OFFLINE("не в сети"),
    TYPING("печатает");

    companion object {
        fun updateState(appStates: AppStates) {
            if (AUTH.currentUser != null) {
                REF_DATABASE_ROOT
                    .child(NODE_USERS)
                    .child(UID)
                    .child(CHILD_STATUS).setValue(appStates.state)
                    .addOnSuccessListener { USER.status = appStates.state }
                    .addOnFailureListener { showToast(it.message.toString()) }
            }
        }
    }
}
