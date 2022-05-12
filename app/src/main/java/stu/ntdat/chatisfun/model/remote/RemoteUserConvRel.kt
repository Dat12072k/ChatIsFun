package stu.ntdat.chatisfun.model.remote

import stu.ntdat.chatisfun.model.relation.UserConvRel

data class RemoteUserConvRel(
    val userId: String? = null,
    val convId: String? = null,
) : RemoteModel {
    fun toModel(): UserConvRel? {
        return convertTo {
            UserConvRel(userId!!, convId!!)
        }
    }
}