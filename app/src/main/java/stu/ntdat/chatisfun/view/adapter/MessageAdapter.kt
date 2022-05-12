package stu.ntdat.chatisfun.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import stu.ntdat.chatisfun.databinding.ItemMessageReceiveBinding
import stu.ntdat.chatisfun.databinding.ItemMessageSendBinding
import stu.ntdat.chatisfun.model.ChatMessage

class MessageAdapter(): PagingDataAdapter<ChatMessage, MessageAdapter.MessageViewHolder>(BestBaseCallbackItem()) {

    inner class MessageViewHolder(private var binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMessage: ChatMessage) {
            when(chatMessage.messageType) {
                ChatMessage.TYPE_SENDER -> (binding as ItemMessageSendBinding).itemMessContent.text = chatMessage.messageContent
                else -> (binding as ItemMessageReceiveBinding).itemMessContent.text = chatMessage.messageContent
            }
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return when(viewType) {
            ChatMessage.TYPE_SENDER ->
                MessageViewHolder(ItemMessageSendBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            else ->
                MessageViewHolder(ItemMessageReceiveBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return message?.messageType ?: 0
    }
}