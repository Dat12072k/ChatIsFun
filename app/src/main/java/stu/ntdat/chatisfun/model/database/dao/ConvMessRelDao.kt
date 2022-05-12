package stu.ntdat.chatisfun.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import stu.ntdat.chatisfun.model.relation.ConvMessRel

@Dao
interface ConvMessRelDao : BaseDao<ConvMessRel> {
    @Query("DELETE FROM conv_mess_rel")
    suspend fun cleanTable()

//    @Query("SELECT * FROM message JOIN conversation WHERE")
}