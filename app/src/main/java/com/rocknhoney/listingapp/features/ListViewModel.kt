package com.rocknhoney.listingapp.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocknhoney.listingapp.R
import com.rocknhoney.listingapp.core.adapter.PostClickListener
import com.rocknhoney.listingapp.core.adapter.PostDeleteListener
import com.rocknhoney.listingapp.core.adapter.PostUpdateListener
import com.rocknhoney.listingapp.core.api.repository.PostRepositoryInterface
import com.rocknhoney.listingapp.core.data.Post
import com.rocknhoney.listingapp.core.data.ScreenState
import com.rocknhoney.listingapp.core.di.AppModule
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(
    private val postRepository: PostRepositoryInterface,
    private val resourcesProvider: AppModule.ResourcesProvider
) : ViewModel(), PostClickListener, PostDeleteListener, PostUpdateListener {

    private val openBottomSheetMutableLiveData = MutableLiveData<Post>()
    val openBottomSheetLiveData: LiveData<Post> = openBottomSheetMutableLiveData

    private val postsMutableLiveData = MutableLiveData<List<Post>>(emptyList())
    val postsLiveData: LiveData<List<Post>> = postsMutableLiveData

    private val screenStateMutableLiveData = MutableLiveData(ScreenState.LOADING)
    val screenStateLiveData: LiveData<ScreenState> = screenStateMutableLiveData

    private val errorMessageMutableLiveData: MutableLiveData<String> = MutableLiveData()
    val errorMessageLiveData: LiveData<String> = errorMessageMutableLiveData

    private val compositeDisposable = CompositeDisposable()

    init {
        getPosts()
    }

    fun getPosts() {
        screenStateMutableLiveData.value = ScreenState.LOADING
        viewModelScope.launch {
            delay(1500)
            val disposable = postRepository.getPosts()
                .observeOn(Schedulers.single())
                .subscribe({ posts ->
                    postsMutableLiveData.postValue(posts)
                    screenStateMutableLiveData.postValue(ScreenState.SUCCESS)
                }, { throwable ->
                    errorMessageMutableLiveData.postValue(
                        "${throwable.localizedMessage}. ${
                            resourcesProvider.getString(
                                R.string.error_message
                            )
                        }"
                    )
                    screenStateMutableLiveData.postValue(ScreenState.ERROR)
                })
            compositeDisposable.add(disposable)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    override fun onPostClick(item: Post) {
        openBottomSheetMutableLiveData.value = item
    }

    override fun onPostUpdate(updatedItem: Post) {
        postsMutableLiveData.value = postsMutableLiveData.value?.toMutableList()?.apply {
            find { it.id == updatedItem.id }?.apply {
                this.title = updatedItem.title
                this.body = updatedItem.body
            }
        }?.toList()
    }

    override fun onDeletePost(position: Int) {
        postsMutableLiveData.value = postsMutableLiveData.value?.toMutableList()?.apply {
            removeAt(position)
        }?.toList()
    }
}