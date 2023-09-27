package com.rocknhoney.listingapp.core.di

import android.content.Context
import androidx.annotation.StringRes
import com.rocknhoney.listingapp.core.api.PostApiService
import com.rocknhoney.listingapp.core.api.repository.PostRepository
import com.rocknhoney.listingapp.core.api.repository.PostRepositoryInterface
import com.rocknhoney.listingapp.core.util.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl(Utils.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providePostApiService(retrofit: Retrofit): PostApiService =
        retrofit.create(PostApiService::class.java)

    @Singleton
    @Provides
    fun providePostRepository(apiService: PostApiService) =
        PostRepository(apiService) as PostRepositoryInterface

    @Singleton
    class ResourcesProvider @Inject constructor(
        @ApplicationContext private val context: Context
    ) {
        fun getString(@StringRes stringResId: Int): String {
            return context.getString(stringResId)
        }
    }
}