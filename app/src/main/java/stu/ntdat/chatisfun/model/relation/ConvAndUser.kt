package stu.ntdat.chatisfun.model.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import stu.ntdat.chatisfun.model.ChatConversation
import stu.ntdat.chatisfun.model.ChatUser

data class ConvAndUser(
    @Embedded val conversation: ChatConversation?,
    @Relation(
        parentColumn = "convId",
        entity = ChatUser::class,
        entityColumn = "userId",
        associateBy = Junction(UserConvRel::class)
    )
    val user: ChatUser?,

    )