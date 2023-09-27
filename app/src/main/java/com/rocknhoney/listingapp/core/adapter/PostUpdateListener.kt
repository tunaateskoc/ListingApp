package com.rocknhoney.listingapp.core.adapter

import com.rocknhoney.listingapp.core.data.Post

/**
 * Interface for handling updates to a post item in RecyclerView.
 */
interface PostUpdateListener {
    fun onPostUpdate(updatedItem: Post)
}