package stu.ntdat.chatisfun.util

import android.util.Log
import kotlinx.coroutines.CancellableContinuation
import stu.ntdat.chatisfun.model.ChatMessage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume

fun String.makeMessageContent(sendID: String, recId: String): String {
    return "$sendID|$recId|$this"
}

fun String.getMessageType(userId: String): Int {
    val temp = this.split("|")
    return if (temp[0] == userId) ChatMessage.TYPE_SENDER
    else ChatMessage.TYPE_RECEIVER
}

fun String.getConvTitle(userId: String): String {
    val temp = this.split("|")
    return if (temp[0] == userId) temp[1]
    else temp[0]
}

fun String.getMessageContent(): String {
    val temp = this.split("|")
    return try {
        temp[2]
    } catch (e: Exception) {
        "Can't find message content: $e"
    }
}

inline fun <T> CancellableContinuation<T>.safeResume(block: () -> T) {
    if (isActive) {
        resume(block())
    }
}

fun String?.logE(tag: String = "XinChao") {
    Log.e(tag, this.toString())
}

fun Any.trace(e: Exception?) {
    "${this::class.simpleName}! ${e?.stackTraceToString()}".logE()
}

fun Long.toTime(): String {
    val temp = System.currentTimeMillis() - this
    val date = Date(this)
    return if (temp < 86_400_000L)
        SimpleDateFormat("HH:mm", Locale.US).format(date)
    else
        SimpleDateFormat("dd-MMM", Locale.US).format(date)
}

fun Int.toUnRead(): String {
    return when {
        this > 99 -> "99+"
        this > 10 -> "${(this / 10)}0+"
        else -> this.toString()
    }


}