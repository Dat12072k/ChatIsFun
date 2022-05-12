package stu.ntdat.chatisfun.view.fragment

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import stu.ntdat.chatisfun.databinding.PreviewImageDialogBinding

class PreviewDialog(context: Context, container: ViewGroup?) : BottomSheetDialog(context) {
    private val binding =
        PreviewImageDialogBinding.inflate(LayoutInflater.from(context), container, false)
    var onSendClick: () -> Unit = {}

    init {
        binding.pidCancel.setOnClickListener {
            onCancelClick()
        }
        binding.pidSend.setOnClickListener {
            onSendClick()
        }
        setTitle("Preview Image")
        behavior.isDraggable = false
        setContentView(binding.root)
    }

    private fun onCancelClick() {
        dismiss()
    }

     fun show(uri: Uri) {
         Log.e("TAG", "show: $uri", )
         Glide.with(context).load(uri).centerInside().into(binding.pidImage)
         show()
     }
    fun show(bitmap: Bitmap) {
        Log.e("TAG", "show: $bitmap", )
        Glide.with(context).load(bitmap).centerInside().into(binding.pidImage)
        show()
    }
}