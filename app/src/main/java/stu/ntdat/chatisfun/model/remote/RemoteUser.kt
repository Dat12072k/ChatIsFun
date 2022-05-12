package stu.ntdat.chatisfun.model.remote

import stu.ntdat.chatisfun.model.ChatUser

data class RemoteUser(
    var userId: String? = null,
    var userEmail: String? = null,
    var userName: String? = null,
    var userBirthday: Long = System.currentTimeMillis(),
    var userAddress: String = "Vietnam",
    var userAvatar: String = "https://firebasestorage.googleapis.com/v0/b/funchat-7640e.appspot.com/o/no_avatar.jpg?alt=media&token=776693f4-e7f0-4f73-a20e-4feb9e42bd54",
    var userPhone: String = "016-0000-000",
    var isActive: Boolean = false
) : RemoteModel {
    fun toModel() : ChatUser? {
        return convertTo {
            ChatUser(userId!!,userEmail!!,userName!!,userBirthday, userAddress, userAvatar, userPhone, isActive)
        }
    }
}