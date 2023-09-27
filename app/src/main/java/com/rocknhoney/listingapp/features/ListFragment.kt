package com.rocknhoney.listingapp.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.rocknhoney.listingapp.R
import com.rocknhoney.listingapp.core.util.UpdatePostBottomSheet
import com.rocknhoney.listingapp.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentListBinding.bind(view)

        binding.lifecycleOwner = this

        binding.listViewModel = viewModel

        setSwipeToRefreshListener()

        initRecyclerView()

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.openBottomSheetLiveData.observe(viewLifecycleOwner, Observer { post ->
            val bottomSheetFragment = UpdatePostBottomSheet(post, viewModel)
            bottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                bottomSheetFragment.tag
            )
        })
    }

    private fun setSwipeToRefreshListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPosts()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initRecyclerView() {
        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        val itemDivider = ContextCompat.getDrawable(requireContext(), R.drawable.item_divider)
        itemDivider?.let {
            divider.setDrawable(it)
            binding.postsRecyclerView.addItemDecoration(divider)
        }
    }
}