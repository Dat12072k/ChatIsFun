package stu.ntdat.chatisfun.model.remote

import stu.ntdat.chatisfun.model.ChatMessage
import stu.ntdat.chatisfun.util.getMessageContent
import stu.ntdat.chatisfun.util.getMessageType

data class RemoteMessage(
    var messageId: String? = null,
    var messageContent: String? = null,
    var messageTime: Long = System.currentTimeMillis(),
    var messageImage: String = "",
    var messageStatus: String = ChatMessage.MESSAGE_SENDING,
    var messageType: Int = ChatMessage.TYPE_SENDER
) : RemoteModel {
    fun toModel(userId: String): ChatMessage? {
        return convertTo {
            ChatMessage(
                messageId!!,
                messageContent!!.getMessageContent(),
                messageTime,
                messageImage,
                messageStatus,
                messageContent!!.getMessageType(userId)
            )
        }
    }
}