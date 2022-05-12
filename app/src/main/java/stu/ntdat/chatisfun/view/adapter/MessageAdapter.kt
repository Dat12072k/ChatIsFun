package stu.ntdat.chatisfun.view.adapter

import android.util.Log
import android.view.View
import stu.ntdat.chatisfun.R
import stu.ntdat.chatisfun.databinding.ItemMessageBinding
import stu.ntdat.chatisfun.model.ChatMessage

class MessageAdapter(): DataBindingListAdapter<ChatMessage, ItemMessageBinding>(R.layout.item_message) {
    override fun onBindViewHolder(
        holder: DataBindingViewHolder<ItemMessageBinding>,
        position: Int
    ) {
        val message = getItem(position)
        holder.bind {
            it.message = message
            if (message.messageType == ChatMessage.TYPE_SENDER) {
                it.itemMessContainerSender.visibility = View.GONE
                it.itemMessContainerReceiver.visibility = View.VISIBLE
            } else {
                it.itemMessContainerSender.visibility = View.VISIBLE
                it.itemMessContainerReceiver.visibility = View.GONE
            }
        }
    }
}