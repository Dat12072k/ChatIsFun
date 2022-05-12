package stu.ntdat.chatisfun.model.remote

import stu.ntdat.chatisfun.model.ChatConversation
import stu.ntdat.chatisfun.util.getConvTitle

data class RemoteConversation(
    var convId: String? = null,
    var convTitle: String? = null,
    var convLastMess: String = "",
    var convUnRead: Int = 0,
    var convNo: Boolean = true,
    var convLastSender: String = "",
    var convLastTime: Long = System.currentTimeMillis()
) : RemoteModel {
    fun toModel(userName: String): ChatConversation? {
        return convertTo {
            ChatConversation(
                convId!!,
                convTitle!!.getConvTitle(userName),
                convLastMess,
                convUnRead,
                convNo,
                convLastSender,
                convLastTime
            )
        }
    }
}