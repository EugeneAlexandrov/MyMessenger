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


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CommonModel) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
