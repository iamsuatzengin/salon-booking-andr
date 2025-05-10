package com.zapplications.salonbooking.ui.favourites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.adapter.decoration.MarginDecoration
import com.zapplications.salonbooking.core.ui.BaseFragment
import com.zapplications.salonbooking.core.ui.applyinset.InsetSides
import com.zapplications.salonbooking.core.ui.applyinset.applySystemBarInsetsAsPadding
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentFavouritesBinding
import com.zapplications.salonbooking.ui.favourites.adapter.FavoritesSalonAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : BaseFragment<FavouritesViewModel>(R.layout.fragment_favourites) {
    private val binding by viewBinding(FragmentFavouritesBinding::bind)
    override val viewModel by viewModels<FavouritesViewModel>()

    private val adapter by lazy { FavoritesSalonAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.applySystemBarInsetsAsPadding(InsetSides(top = true))

        viewModel.getAllFavoriteSalonsFromLocal()

        initRecyclerView()
    }

    private fun initRecyclerView() = with(binding) {
        rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        rvFavorites.itemAnimator = null
        rvFavorites.addItemDecoration(MarginDecoration(marginBottomPx = 16))
        rvFavorites.adapter = adapter
    }

    override suspend fun collectUiStates() {
        viewModel.uiState.collect {
            adapter.submitList(it)
        }
    }

    override fun canRootViewApplyInset(): Boolean = true
    override fun adjustRootViewInsetSides() = super.adjustRootViewInsetSides().copy(top = true)
}
