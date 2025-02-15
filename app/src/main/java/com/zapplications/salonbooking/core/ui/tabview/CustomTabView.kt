package com.zapplications.salonbooking.core.ui.tabview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import com.zapplications.salonbooking.core.customobserver.CustomObservable
import com.zapplications.salonbooking.core.customobserver.ObservableClass
import com.zapplications.salonbooking.core.extensions.ZERO
import com.zapplications.salonbooking.core.extensions.orZero
import com.zapplications.salonbooking.core.ui.tabview.adapter.TabAdapter
import com.zapplications.salonbooking.databinding.LayoutTabviewBinding
import com.zapplications.salonbooking.domain.model.ServiceCategoryUiModel
import com.zapplications.salonbooking.domain.model.ServiceUiModel

class CustomTabView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr), TabLayout.OnTabSelectedListener {
    private val binding = LayoutTabviewBinding.inflate(LayoutInflater.from(context), this, true)

    var tabItems: Map<ServiceCategoryUiModel, List<ServiceUiModel>>? = null

    private var tabAdapter: TabAdapter? = null

    val observable: CustomObservable<List<ServiceUiModel>> by lazy { ObservableClass() }
    private var selectedServices: MutableList<ServiceUiModel>? = mutableListOf()

    fun initTabs() = with(binding) {
        tabItems?.keys?.forEach { tab ->
            tabLayout.addTab(
                tabLayout.newTab().apply {
                    text = tab.categoryName
                    contentDescription = tab.categoryName
                }
            )
        }

        initRecyclerView(tabItems?.values?.toList()?.getOrNull(ZERO).orEmpty())

        tabLayout.addOnTabSelectedListener(this@CustomTabView)
    }

    private fun initRecyclerView(list: List<ServiceUiModel>) {
        tabAdapter = TabAdapter().apply {
            submitList(list)
            onItemClicked = ::handleItemClick
        }

        binding.rvTabItems.apply {
            this.setItemViewCacheSize(5)
            setHasFixedSize(true)
            adapter = tabAdapter
        }
    }

    private fun handleItemClick(uiModel: ServiceUiModel, position: Int) {
        uiModel.selected = !uiModel.selected
        tabAdapter?.notifyItemChanged(position)

        if (uiModel.selected) selectedServices?.add(uiModel) else selectedServices?.remove(uiModel)
        selectedServices?.let { observable.notifyObservers(it) }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val position = tab?.position.orZero()
        tabAdapter?.submitList(tabItems?.values?.toList()?.getOrNull(position).orEmpty())
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
    override fun onTabReselected(tab: TabLayout.Tab?) = Unit
}
