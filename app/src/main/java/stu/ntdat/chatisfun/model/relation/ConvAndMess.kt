package stu.ntdat.chatisfun.model.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import stu.ntdat.chatisfun.model.ChatConversation
import stu.ntdat.chatisfun.model.ChatMessage

data class ConvAndMess(
    @Embedded var conversation: ChatConversation,
    @Relation(
        parentColumn = "convId",
        entity = ChatMessage::class,
        entityColumn = "messageId",
        associateBy = Junction(ConvMessRel::class)
    )
    var message: List<ChatMessage>
)