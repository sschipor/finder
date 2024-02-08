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
    @BindingAdapter("itemList", "callback")
    fun setPetList(
        recyclerView: RecyclerView,
        itemsList: List<AnimalData>,
        callback: PetListCallback
    ) {
        var adapter = recyclerView.adapter as? PetListAdapter?
        if (adapter == null) {
            adapter = PetListAdapter(callback)
            val layoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (recyclerView.canScrollVertically(1).not()) {
                        callback.onLoadNextPage()
                    }
                }
            })
        }
        adapter.updateItems(itemsList)
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