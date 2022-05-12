package stu.ntdat.chatisfun.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import stu.ntdat.chatisfun.model.relation.UserConvRel

@Dao
interface UserConvRelDao : BaseDao<UserConvRel> {
    @Query("SELECT * FROM user_conv_rel WHERE userId == :id")
    suspend fun getConvByUserId(id: String): UserConvRel?

    @Query("SELECT convId FROM user_conv_rel")
    fun getAllConv(): Flow<List<String>?>

    @Query("DELETE FROM user_conv_rel")
    suspend fun cleanTable()

    @Query("SELECT convId FROM user_conv_rel WHERE userId == :userId")
    fun getConvId(userId: String): Flow<String?>

}