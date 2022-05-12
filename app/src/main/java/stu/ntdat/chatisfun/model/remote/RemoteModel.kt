package stu.ntdat.chatisfun.model.remote

interface RemoteModel {
}

inline fun <T>RemoteModel.convertTo(
    convertBlock: RemoteModel.() -> T
): T?{
    return try {
        convertBlock(this)
    }catch (e: Exception){
        null
    }
}