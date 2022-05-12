package stu.ntdat.chatisfun.view.adapter

import stu.ntdat.chatisfun.R
import stu.ntdat.chatisfun.databinding.ItemUserBinding
import stu.ntdat.chatisfun.model.ChatUser

class UserAdapter(
    var onClickItem: (ChatUser) -> Unit = {},
) :
    DataBindingListAdapter<ChatUser, ItemUserBinding>(R.layout.item_user) {
    override fun onBindViewHolder(holder: DataBindingViewHolder<ItemUserBinding>, position: Int) {
        val item = getItem(position)
        holder.bind {
            it.user = item
            it.root.setOnClickListener { onClickItem(item) }
        }
    }
}