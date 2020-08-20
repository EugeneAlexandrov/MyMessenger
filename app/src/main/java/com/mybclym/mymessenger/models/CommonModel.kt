package com.mybclym.mymessenger.models

data class CommonModel(
    val id: String = "",
    var username: String = "",
    var bio: String = "",
    var fullname: String = "",
    var status: String = "",
    var phone: String = "",
    var photoUrl: String = "empty",
    var text: String = "",
    var type: String = "",
    var from: String = "",
    var timeStamp: Long = 0
)