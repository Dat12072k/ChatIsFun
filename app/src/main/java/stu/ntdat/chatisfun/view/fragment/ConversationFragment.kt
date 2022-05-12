package stu.ntdat.chatisfun.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import stu.ntdat.chatisfun.R
import stu.ntdat.chatisfun.databinding.FragmentConversationBinding
import stu.ntdat.chatisfun.databinding.SendImageDialogBinding
import stu.ntdat.chatisfun.util.logE
import stu.ntdat.chatisfun.view.adapter.MessageAdapter
import stu.ntdat.chatisfun.viewmodel.ConversationViewModel

@AndroidEntryPoint
class ConversationFragment : Fragment() {
    private lateinit var binding: FragmentConversationBinding
    private val args by navArgs<ConversationFragmentArgs>()
    private val viewModel by activityViewModels<ConversationViewModel>()
    private val previewDialog by lazy { PreviewDialog(requireContext(), null) }
    private val messageAdapter by lazy { MessageAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConversationBinding.inflate(inflater, container, false)
        binding.apply {
            userAvatar = args.userAvatar
            userName = args.userName
        }
        binding.convImage.setOnClickListener {
            selectImage()
        }
        binding.convSend.setOnClickListener {
            val message = binding.convMessage.text.toString()
            lifecycleScope.launch {
                viewModel.sendTextMessage(args.userId, message)
            }
            binding.convMessage.text?.clear()
        }
        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.listenUnreadMessage(args.userId)
        binding.convToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.recyclerView.adapter = messageAdapter
//        lifecycleScope.launch {
//            viewModel.test.collectLatest {
//                messageAdapter.submitData(PagingData.from())
//            }
//        }
        viewModel.test1.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                messageAdapter.submitData(PagingData.from(it))
            }

        }

        return binding.root
    }

    private fun selectImage() {
        val dialog = Dialog(requireContext())
        val binding = SendImageDialogBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setTitle("Please select image to send")
        binding.sidOpenLibBtn.setOnClickListener {
            getContent.launch("image/*")
            dialog.dismiss()
        }
        binding.sidTakePhotoBtn.setOnClickListener {
            getImageCapture.launch(null)
            dialog.dismiss()
        }
        binding.sidCancelBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it == null)
            Toast.makeText(requireContext(), "Canceled send image", Toast.LENGTH_SHORT).show()
        else {
            previewDialog.onSendClick = {
                lifecycleScope.launch {
                    viewModel.sendImageMessage(args.userId, it)
                }
                Dialog(requireContext()).apply {
                    viewModel.isDone.observe(viewLifecycleOwner) { d -> if (d) dismiss() }
                    setContentView(R.layout.upload_image_dialog)
                }.show()
                previewDialog.dismiss()
            }
            previewDialog.show(it)
        }
    }

    private val getImageCapture =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            if (it == null)
                Toast.makeText(requireContext(), "Canceled send image", Toast.LENGTH_SHORT).show()
            else {
                previewDialog.onSendClick = {
                    lifecycleScope.launch {
                        viewModel.sendImageMessage(args.userId, it)
                    }
                }
                previewDialog.show(it)
            }
        }
}