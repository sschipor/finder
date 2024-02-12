package com.example.myapplication.ui.binding

import android.content.Intent
import android.net.Uri
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
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
    @BindingAdapter("imageUrlRounded")
    fun setPetImageRounded(
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

    @JvmStatic
    @BindingAdapter("imageUrlSquare")
    fun setPetImageSquare(
        imageView: ImageView,
        petImageUrl: String,
    ) {
        Glide.with(imageView)
            .load(petImageUrl)
            .fallback(android.R.drawable.stat_notify_error)
            .error(android.R.drawable.stat_notify_error)
            .centerCrop()
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("detailsHyperlink")
    fun detailsLink(
        button: Button,
        link: String,
    ) {
        //todo will be more UX friendly to open in inner WebView OR chromeTabs
        button.setOnClickListener {
            val webpage = Uri.parse(link)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            try {
                button.context.startActivity(intent)
            } catch (e: Exception) {
                //no activity found to support this action
            }
        }
    }

    @JvmStatic
    @BindingAdapter("htmlText")
    fun setHtmlText(
        textView: TextView,
        text: String
    ) {
        textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    }

}