package com.rocknhoney.listingapp.core.api

import com.rocknhoney.listingapp.core.data.Post
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

/**
 * Interface defining the API endpoints for retrieving posts.
 */
interface PostApiService {

    /**
     * Sends a GET request to fetch a list of posts.
     * @return A [Single] emitting a list of posts when the request is successful.
     */
    @GET("posts")
    fun getPosts(): Single<List<Post>>

}