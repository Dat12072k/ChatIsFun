package stu.ntdat.chatisfun.view.adapter

import android.view.View
import com.google.firebase.auth.FirebaseAuth
import stu.ntdat.chatisfun.R
import stu.ntdat.chatisfun.databinding.ItemConversationBinding
import stu.ntdat.chatisfun.model.ChatUser
import stu.ntdat.chatisfun.model.relation.ConvAndUser
import stu.ntdat.chatisfun.util.toTime
import stu.ntdat.chatisfun.util.toUnRead

class ConvAdapter(
    var onClickItem: (ChatUser) -> Unit = {},
) : DataBindingListAdapter<ConvAndUser, ItemConversationBinding>(R.layout.item_conversation) {
    private val auth = FirebaseAuth.getInstance()
    override fun onBindViewHolder(
        holder: DataBindingViewHolder<ItemConversationBinding>,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind { binding ->
            item.user?.let { user ->
                item.conversation?.let { conv ->
                    binding.user = user
                    binding.root.setOnClickListener { onClickItem(user) }
                    binding.conversation = conv
                    binding.itemConvTime.text = conv.convLastTime.toTime()
                    auth.currentUser?.uid?.let {
                        if (it != conv.convLastSender && conv.convUnRead != 0) {
                            binding.itemConvUnreadContainer.visibility = View.VISIBLE
                            binding.itemConvUnread.text = conv.convUnRead.toUnRead()
                        } else {
                            binding.itemConvUnreadContainer.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}