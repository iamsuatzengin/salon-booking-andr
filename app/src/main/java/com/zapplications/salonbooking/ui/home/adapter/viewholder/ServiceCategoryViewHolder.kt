package com.zapplications.salonbooking.ui.home.adapter.viewholder

import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.core.ui.ServiceCategoryView
import com.zapplications.salonbooking.databinding.ItemViewServiceCategoryBinding
import com.zapplications.salonbooking.domain.model.ServiceCategoryUiModel
import com.zapplications.salonbooking.ui.home.adapter.item.ServiceCategoryViewItem

class ServiceCategoryViewHolder(
    private val binding: ItemViewServiceCategoryBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(serviceCategoryViewItem: ServiceCategoryViewItem) {
        val categories = serviceCategoryViewItem.categories.also {
            it.firstOrNull()?.isSelected = true
        }

        categories.forEach { categoryUiModel ->
            categoryUiModel?.let {
                val view = initServiceCategoryView(it, serviceCategoryViewItem.onCategoryClick)
                binding.llContainer.addView(view)
            }
        }
    }

    private fun initServiceCategoryView(
        categoryUiModel: ServiceCategoryUiModel,
        onCategoryClick: (ServiceCategoryUiModel) -> Unit,
    ) = ServiceCategoryView(binding.root.context).apply {
        id = View.generateViewId()
        isCategorySelected = categoryUiModel.isSelected
        initView(categoryUiModel.categoryName, categoryUiModel.type)
        setOnClickListener { v ->
            setSelectedView(v.id)
            categoryUiModel.isSelected = isCategorySelected
            onCategoryClick(categoryUiModel)

            (v as? ServiceCategoryView)?.let { smoothScroll(it) }
        }
    }

    private fun setSelectedView(viewId: Int) {
        val container = binding.llContainer.children
        container.forEach {
            if (it.id == viewId) {
                (it as? ServiceCategoryView)?.updateIsSelected(true)
            } else {
                (it as? ServiceCategoryView)?.updateIsSelected(false)
            }
        }
    }

    private fun smoothScroll(targetView: ServiceCategoryView) {
        val targetLeft = targetView.left
        val targetRight = targetView.right

        binding.root.smoothScrollTo((targetLeft + targetRight) / 2 - binding.root.width / 2, 0)
    }
}
