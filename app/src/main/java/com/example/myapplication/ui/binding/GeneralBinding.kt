package com.example.myapplication.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.data.model.AnimalData
import com.example.myapplication.ui.adapter.PetListAdapter
import com.example.myapplication.ui.callback.PetListCallback

object GeneralBinding {
    @JvmStatic
    @BindingAdapter("itemList")
    fun setPetList(
        recyclerView: RecyclerView,
        itemsList: List<AnimalData>,
    ) {
        (recyclerView.adapter as? PetListAdapter)?.updateItems(itemsList)
    }

    @JvmStatic
    @BindingAdapter("callback")
    fun setPaginationListener(
        recyclerView: RecyclerView,
        callback: PetListCallback
    ) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(1).not()) {
                    callback.onLoadNextPage()
                }
            }
        })
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setPetImage(
        imageView: ImageView,
        petImageUrl: String,
    ) {
        Glide.with(imageView)
            .load(petImageUrl)
            .circleCrop()
            .fallback(android.R.drawable.stat_notify_error)
            .error(android.R.drawable.stat_notify_error)
            .into(imageView)
    }
}