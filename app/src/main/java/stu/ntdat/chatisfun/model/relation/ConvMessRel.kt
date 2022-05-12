package stu.ntdat.chatisfun.model.relation

import androidx.room.Entity

@Entity(primaryKeys = ["convId","messageId"], tableName = "conv_mess_rel")
data class ConvMessRel(
    val convId: String,
    val messageId: String
)
