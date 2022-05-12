package stu.ntdat.chatisfun.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import stu.ntdat.chatisfun.view.adapter.ConvAdapter
import stu.ntdat.chatisfun.view.adapter.UserAdapter
import stu.ntdat.chatisfun.databinding.FragmentMessageBinding
import stu.ntdat.chatisfun.model.ChatUser
import stu.ntdat.chatisfun.viewmodel.MessageViewModel

@AndroidEntryPoint
class MessageFragment: Fragment() {
    private lateinit var binding: FragmentMessageBinding
    private val viewModel by activityViewModels<MessageViewModel>()
    private val userAdapter by lazy { UserAdapter(onClickItem) }
    private val convAdapter by lazy { ConvAdapter(onClickItem) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        initControls()
        return binding.root
    }

    private fun initControls() {
        binding.messageUserList.apply {
            adapter = userAdapter
            itemAnimator = null
        }
        binding.messageContainer.apply {
            adapter = convAdapter
            itemAnimator = null
        }
        viewModel.userList.observe(viewLifecycleOwner) {
            userAdapter.submitList(it)
        }
        viewModel.convList.observe(viewLifecycleOwner) {
            convAdapter.submitList(it)
        }
    }

    private val onClickItem: (ChatUser) -> Unit = {
        findNavController().navigate(
            MessageFragmentDirections.actionMessageFragmentToConversationFragment(
                it.userName,
                it.userAvatar,
                it.userId
            )
        )
    }
}