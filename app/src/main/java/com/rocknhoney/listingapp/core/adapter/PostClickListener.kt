package com.rocknhoney.listingapp.core.adapter

import com.rocknhoney.listingapp.core.data.Post

/**
 * Interface for handling click events on Posts in RecyclerView.
 */
interface PostClickListener {
    fun onPostClick(item: Post)
}