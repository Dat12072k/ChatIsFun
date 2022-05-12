package stu.ntdat.chatisfun.model.relation

import androidx.room.Entity

@Entity(primaryKeys = ["userId","convId"], tableName = "user_conv_rel")
data class UserConvRel(
    val userId: String,
    val convId: String,
)

