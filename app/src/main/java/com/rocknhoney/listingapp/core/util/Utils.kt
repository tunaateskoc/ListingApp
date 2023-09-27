package com.rocknhoney.listingapp.core.util

object Utils {
    const val API_URL = "https://jsonplaceholder.typicode.com/"

    fun getPictureUrl(position: Int): String {
        return "https://picsum.photos/300/300?random=$position&grayscale"
    }
}