package stu.ntdat.chatisfun.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import stu.ntdat.chatisfun.model.ChatMessage

@Dao
interface ChatMessageDao : BaseDao<ChatMessage> {
    @Query("SELECT * FROM message WHERE messageId = :id")
    fun getMessageById(id: String): Flow<ChatMessage>

    @Query("DELETE FROM message")
    suspend fun cleanTable()

    @Query("SELECT * FROM message " +
            "JOIN conv_mess_rel ON message.messageId == conv_mess_rel.messageId " +
            "JOIN conversation ON conv_mess_rel.convId == conversation.convId " +
            "WHERE conversation.convId == :convId")
    suspend fun getListMessByConvId(convId: String): List<ChatMessage>
}