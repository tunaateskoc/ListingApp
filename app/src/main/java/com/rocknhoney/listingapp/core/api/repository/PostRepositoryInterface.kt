package com.rocknhoney.listingapp.core.api.repository

import com.rocknhoney.listingapp.core.data.Post
import io.reactivex.rxjava3.core.Single

/**
 * Interface defining the contract for fetching posts.
 */
interface PostRepositoryInterface {

    /**
     * Retrieves a list of posts with API call.
     * @return A [Single] emitting a list of posts when the operation is complete.
     */
    suspend fun getPosts(): Single<List<Post>>

}