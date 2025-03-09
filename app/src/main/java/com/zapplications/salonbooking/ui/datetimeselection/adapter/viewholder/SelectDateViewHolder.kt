package com.zapplications.salonbooking.ui.datetimeselection.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.BLANK
import com.zapplications.salonbooking.core.extensions.color
import com.zapplications.salonbooking.databinding.ItemViewSelectDateBinding
import com.zapplications.salonbooking.databinding.ItemViewSelectDateRowBinding
import com.zapplications.salonbooking.domain.model.datetime.DateUiModel
import com.zapplications.salonbooking.ui.datetimeselection.adapter.item.SelectDateViewItem

class SelectDateViewHolder(
    private val binding: ItemViewSelectDateRowBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(selectDateViewItem: SelectDateViewItem) {
        binding.first.apply {
            val firstItem = selectDateViewItem.dateUiModel[0]
            setDateView(firstItem, selectDateViewItem.selectedDate)
            root.setOnClickListener {
                selectDateViewItem.clickHandler(firstItem, selectDateViewItem, adapterPosition)
            }
        }

        binding.second.apply {
            val secondItem = selectDateViewItem.dateUiModel[1]
            setDateView(secondItem, selectDateViewItem.selectedDate)
            root.setOnClickListener {
                selectDateViewItem.clickHandler(
                    secondItem,
                    selectDateViewItem,
                    adapterPosition
                )
            }
        }

        binding.third.apply {
            val thirdItem = selectDateViewItem.dateUiModel[2]
            setDateView(thirdItem, selectDateViewItem.selectedDate)
            root.setOnClickListener {
                selectDateViewItem.clickHandler(
                    thirdItem,
                    selectDateViewItem,
                    adapterPosition
                )
            }
        }

        binding.more.root.setOnClickListener { selectDateViewItem.moreClickHandler() }
    }

    private fun ItemViewSelectDateBinding.setDateView(
        dateUiModel: DateUiModel,
        selectedDateUiModel: DateUiModel?,
    ) {
        tvDayOfMonthAndName.text = buildString {
            append(dateUiModel.month)
            append(BLANK)
            append(dateUiModel.dayOfMonthNumber)
        }
        tvDayName.text = dateUiModel.dayOfWeekText

        if (selectedDateUiModel == dateUiModel) {
            root.setBackgroundResource(R.drawable.bg_stylist_item_selected)
            val selectedColor = root.resources.color(R.color.color_main)
            tvDuration.setTextColor(selectedColor)
            tvDayOfMonthAndName.setTextColor(selectedColor)
            tvDayName.setTextColor(selectedColor)
        } else {
            root.setBackgroundResource(R.drawable.bg_stylist_item)
            tvDuration.setTextColor(root.resources.color(R.color.color_gray1))
            tvDayOfMonthAndName.setTextColor(root.resources.color(R.color.color_dark1))
            tvDayName.setTextColor(root.resources.color(R.color.color_gray1))
        }
    }
}
