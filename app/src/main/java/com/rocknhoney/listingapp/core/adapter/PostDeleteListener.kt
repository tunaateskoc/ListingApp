package com.rocknhoney.listingapp.core.adapter

/**
 * Interface for handling the deletion of a post item in RecyclerView.
 */
interface PostDeleteListener {
    fun onDeletePost(position: Int)
}