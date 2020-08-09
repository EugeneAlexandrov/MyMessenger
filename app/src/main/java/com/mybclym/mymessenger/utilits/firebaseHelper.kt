package com.mybclym.mymessenger.utilits

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mybclym.mymessenger.models.User

lateinit var AUTH: FirebaseAuth
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var USER: User
lateinit var UID: String

const val NODE_USERS = "users"
const val NODE_USERNAMES = "userNames"

const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "username"
const val CHILD_FULLNAME = "fullname"

fun initFirebase() {
    AUTH = Firebase.auth
    REF_DATABASE_ROOT = Firebase.database.reference
    USER = User()
    UID = AUTH.currentUser?.uid.toString()
}