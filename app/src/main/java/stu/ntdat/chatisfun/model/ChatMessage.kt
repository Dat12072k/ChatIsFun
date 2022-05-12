package stu.ntdat.chatisfun.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class ChatMessage(
    @PrimaryKey(autoGenerate = false)
    var messageId: String,
    var messageContent: String,
    var messageTime: Long = System.currentTimeMillis(),
    var messageImage: String = "",
    var messageStatus: String = MESSAGE_SENDING,
    var messageType: Int = TYPE_SENDER
) {
    companion object {
        const val MESSAGE_SENT = "sent"
        const val MESSAGE_SEEN = "seen"
        const val MESSAGE_SENDING = "sending"
        const val MESSAGE_FAIL = "fail"
        const val TYPE_SENDER = 0
        const val TYPE_RECEIVER = 1
    }
}
