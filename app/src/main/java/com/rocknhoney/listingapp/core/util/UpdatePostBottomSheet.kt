package com.rocknhoney.listingapp.core.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rocknhoney.listingapp.R
import com.rocknhoney.listingapp.core.adapter.PostUpdateListener
import com.rocknhoney.listingapp.core.data.Post
import com.rocknhoney.listingapp.databinding.FragmentBottomSheetBinding

/**
 * BottomSheetDialogFragment for updating Post items in RecyclerView.
 */
class UpdatePostBottomSheet(
    private val post: Post,
    private val postUpdateListener: PostUpdateListener
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBottomSheetBinding.bind(view)
        binding.lifecycleOwner = this
        binding.post = post
        binding.imageUrl = Utils.getPictureUrl(post.id - 1)
        (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED

        binding.saveButton.setOnClickListener {
            val updatedPostValue = post.copy(
                title = binding.titleInput.text.toString(),
                body = binding.subtitleInput.text.toString()
            )
            postUpdateListener.onPostUpdate(updatedPostValue)
            Toast.makeText(context,context?.getString(R.string.post_updated), Toast.LENGTH_LONG).show()
            dismiss()
        }
    }
}