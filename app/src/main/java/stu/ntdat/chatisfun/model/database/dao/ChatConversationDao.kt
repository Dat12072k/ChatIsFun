package stu.ntdat.chatisfun.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import stu.ntdat.chatisfun.model.ChatConversation
import stu.ntdat.chatisfun.model.relation.ConvAndMess
import stu.ntdat.chatisfun.model.relation.ConvAndUser

@Dao
interface ChatConversationDao : BaseDao<ChatConversation> {
    @Query("SELECT * FROM conversation WHERE convId = :id")
    fun getConversationById(id: String): Flow<ChatConversation>

    @Query("DELETE FROM conversation")
    suspend fun cleanTable()

    @Query("SELECT convId FROM conversation")
    suspend fun getAllConvId(): List<String>?

    @Query("SELECT convLastSender FROM conversation WHERE convId = :id")
    fun getConvSender(id: String): Flow<String?>

    @Query("SELECT * FROM conversation ORDER BY convLastTime DESC")
    fun getConvAndUser(): Flow<List<ConvAndUser>?>

    @Query("SELECT * FROM conversation WHERE convId = :convId")
    fun getConvAndMessByConvId(convId: String): Flow<ConvAndMess>

}