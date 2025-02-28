package com.zapplications.salonbooking.ui.datetimeselection.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.databinding.ItemViewSelectDateBinding
import com.zapplications.salonbooking.databinding.ItemViewSelectDateRowBinding
import com.zapplications.salonbooking.domain.model.datetime.DateUiModel
import com.zapplications.salonbooking.ui.datetimeselection.adapter.item.SelectDateViewItem

class SelectDateViewHolder(
    private val binding: ItemViewSelectDateRowBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(selectDateViewItem: SelectDateViewItem) {
        binding.first.apply {
            val firstItem = selectDateViewItem.dateUiModel[0]
            setDateView(firstItem)
            root.setOnClickListener {
                selectDateViewItem.clickHandler(firstItem)
            }
        }

        binding.second.apply {
            val secondItem = selectDateViewItem.dateUiModel[1]
            setDateView(secondItem)
            root.setOnClickListener { selectDateViewItem.clickHandler(secondItem) }
        }

        binding.third.apply {
            val thirdItem = selectDateViewItem.dateUiModel[2]
            setDateView(thirdItem)
            root.setOnClickListener { selectDateViewItem.clickHandler(thirdItem) }
        }

        binding.more.root.setOnClickListener { selectDateViewItem.moreClickHandler() }
    }

    private fun ItemViewSelectDateBinding.setDateView(dateUiModel: DateUiModel) {
        tvDuration.text = "40 mins"
        tvDayOfMonthAndName.text = dateUiModel.month + " " + dateUiModel.dayOfMonthNumber
        tvDayName.text = dateUiModel.dayOfWeekText
    }
}
