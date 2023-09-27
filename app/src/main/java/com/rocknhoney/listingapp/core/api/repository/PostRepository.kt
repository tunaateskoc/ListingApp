package com.rocknhoney.listingapp.core.api.repository

import com.rocknhoney.listingapp.core.api.PostApiService
import javax.inject.Inject

/**
 * Repository responsible for fetching posts from a API.
 * @param apiService An instance of [PostApiService] used to make network requests to retrieve posts.
 */
class PostRepository @Inject constructor(
    private val apiService: PostApiService
) : PostRepositoryInterface {

    /**
     * Fetches a list of posts asynchronously.
     * @return List of posts retrieved from the remote API.
     */
    override suspend fun getPosts() = apiService.getPosts()

}