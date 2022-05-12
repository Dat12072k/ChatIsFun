package stu.ntdat.chatisfun.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversation")
data class ChatConversation(
    @PrimaryKey(autoGenerate = false)
    var convId: String,
    var convTitle: String,
    var convLastMess: String = "",
    var convUnRead: Int = 0,
    var convNo: Boolean = true,
    var convLastSender: String = "",
    var convLastTime: Long = System.currentTimeMillis()
)
