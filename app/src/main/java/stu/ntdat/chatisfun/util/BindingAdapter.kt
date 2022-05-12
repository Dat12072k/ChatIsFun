package stu.ntdat.chatisfun.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import stu.ntdat.chatisfun.R

@BindingAdapter("app:bindImageUri")
fun ImageView.bindImageUri(uri: String?){
    if(uri.isNullOrEmpty().not()){
        Glide.with(this.context).load(uri).into(this)
    }else {
        setImageResource(R.drawable.ic_launcher_background)
    }
}

@BindingAdapter("app:bindImage4Fun")
fun CircleImageView.bindImage4Fun(uri: String?) {
    if(uri.isNullOrEmpty().not()){
        Glide.with(this.context).load(uri).into(this)
    }else {
        setImageResource(R.drawable.ic_launcher_background)
    }
}

@BindingAdapter("app:isOnline")
fun CircleImageView.isOnline(check: Boolean) {
    if (check)
        setImageResource(R.color.green)
    else
        setImageResource(R.color.gray)
}