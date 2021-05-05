package com.peter.stylishtool

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Log.d("Run","BindingAdapterImage")
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.place)
                .error(R.drawable.image_strikethrough))
            .into(imgView)
    }
}

@BindingAdapter("listImage")
fun bindDetailRecyclerView(recyclerView: RecyclerView, data: List<Product>){
    data?.let {
        Log.d("Run","BindingAdapter")
        recyclerView.adapter?.apply {
            Log.d("Run","Hellow Adapter")
            when (this) {
                is MainAdapter -> submitList(it)
            }
        }
    }
}