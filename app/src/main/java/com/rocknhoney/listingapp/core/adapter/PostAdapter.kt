package com.rocknhoney.listingapp.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rocknhoney.listingapp.R
import com.rocknhoney.listingapp.core.data.Post
import com.rocknhoney.listingapp.core.util.Utils
import com.rocknhoney.listingapp.databinding.PostRowBinding

class PostAdapter(
    private val postClickListener: PostClickListener,
    private val postDeleteListener: PostDeleteListener
) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var postList = mutableListOf<Post>()

    class PostDiffCallBack(
        private val oldList: List<Post>,
        private val newList: List<Post>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: PostRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.post_row,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = postList[position]
        holder.bind(item, Utils.getPictureUrl(position))
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun setData(newPostList: List<Post>) {
        val diffResult = DiffUtil.calculateDiff(PostDiffCallBack(postList, newPostList))
        postList = newPostList.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    fun deletePost(position: Int) {
        postList.removeAt(position)
        notifyItemRemoved(position)
        postDeleteListener.onDeletePost(position)
    }

    inner class ViewHolder(private val binding: PostRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.postClickListener = postClickListener
        }

        fun bind(item: Post, imageUrl: String) {
            binding.post = item
            binding.imageUrl = imageUrl
            binding.executePendingBindings()
        }
    }
}