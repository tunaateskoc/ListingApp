package com.rocknhoney.listingapp.core.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rocknhoney.listingapp.core.adapter.PostAdapter
import com.rocknhoney.listingapp.core.adapter.PostClickListener
import com.rocknhoney.listingapp.core.adapter.PostDeleteListener
import com.rocknhoney.listingapp.core.adapter.SwipeToDeleteCallback
import com.rocknhoney.listingapp.core.data.Post

/**
 * Custom BindingAdapter for setting the visibility of a View.
 */
@BindingAdapter("setVisibility")
fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

/**
 * Custom BindingAdapter for loading image to ImageView using Glide.
 */
@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String) {
    Glide.with(this.context).load(url).apply(RequestOptions.circleCropTransform()).into(this)
}

/**
 * Custom BindingAdapter for setting data and listeners for a RecyclerView displaying a list of posts.
 */
@BindingAdapter(
    "app:dataList",
    "app:postClickListener",
    "app:postDeleteListener",
    requireAll = true
)
fun RecyclerView.setRecyclerViewData(
    dataList: List<Post>?,
    postClickListener: PostClickListener?,
    postDeleteListener: PostDeleteListener?
) {
    if (!dataList.isNullOrEmpty() && postClickListener != null && postDeleteListener != null) {
        val adapter = PostAdapter(postClickListener, postDeleteListener)
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(this)

        val recyclerViewState = this.layoutManager?.onSaveInstanceState()
        this.layoutManager?.onRestoreInstanceState(recyclerViewState)

        adapter.setData(dataList)
        this.adapter = adapter
    }
}