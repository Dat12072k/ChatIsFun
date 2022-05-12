package stu.ntdat.chatisfun.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import stu.ntdat.chatisfun.model.ChatUser

@Dao
interface ChatUserDao : BaseDao<ChatUser> {
    @Query("SELECT * FROM users WHERE userId = :id")
    fun getUserById(id: String): Flow<ChatUser>

    @Query("DELETE FROM users")
    suspend fun cleanTable()

    @Query("SELECT * FROM users WHERE userId != :id ORDER BY userId")
    fun getAllUsers(id: String): Flow<List<ChatUser>>

    @Query("SELECT userName FROM users WHERE userId == :id")
    suspend fun getUserName(id: String): String?
}