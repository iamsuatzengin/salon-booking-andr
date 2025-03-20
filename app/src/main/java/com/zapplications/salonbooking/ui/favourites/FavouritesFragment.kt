package com.zapplications.salonbooking.ui.favourites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.adapter.decoration.MarginDecoration
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentFavouritesBinding
import com.zapplications.salonbooking.ui.favourites.adapter.FavoritesSalonAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private val binding by viewBinding(FragmentFavouritesBinding::bind)
    private val viewModel by viewModels<FavouritesViewModel>()

    private val adapter by lazy { FavoritesSalonAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllFavoriteSalonsFromLocal()

        initRecyclerView()
        collectData()
    }

    private fun initRecyclerView() = with(binding) {
        rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        rvFavorites.itemAnimator = null
        rvFavorites.addItemDecoration(MarginDecoration(marginBottomPx = 16))
        rvFavorites.adapter = adapter
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    adapter.submitList(it)
                }
            }
        }
    }
}