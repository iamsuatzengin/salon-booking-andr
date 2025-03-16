package com.zapplications.salonbooking.core.ui

import android.content.Context
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updateLayoutParams
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.domain.model.enums.ServiceCategoryType

class ServiceCategoryView(private val context: Context) : LinearLayout(context) {
    private var text: TextView? = null
    private var icon: ImageView? = null

    var isCategorySelected: Boolean = false

    init {
        gravity = Gravity.CENTER
        orientation = HORIZONTAL
    }

    fun initView(
        categoryName: String,
        categoryType: ServiceCategoryType,
    ) {
        background = getBackgroundDrawable(isCategorySelected)

        addIcon(categoryType)
        addText(categoryName)
        setPadding(
            /* left = */ resources.getDimension(R.dimen.padding10).toInt(),
            /* top = */ resources.getDimension(R.dimen.padding12).toInt(),
            /* right = */ resources.getDimension(R.dimen.padding10).toInt(),
            /* bottom = */ resources.getDimension(R.dimen.padding12).toInt()
        )
        post {
            updateLayoutParams<LayoutParams> {
                marginEnd = resources.getDimension(R.dimen.margin8).toInt()
            }
        }
    }

    private fun addText(categoryName: String) {
        text = TextView(context).apply {
            text = categoryName
            typeface = ResourcesCompat.getFont(context, R.font.inter_18pt_medium)
            textSize = TEXT_SIZE
            setTextColor(getTextColor(isCategorySelected))
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            gravity = Gravity.CENTER
        }

        addView(text)
    }

    private fun addIcon(categoryType: ServiceCategoryType) {
        icon = ImageView(context).apply {
            setImageResource(categoryType.icon)
            val height = resources.getDimension(R.dimen.small_icon)

            layoutParams = LayoutParams(height.toInt(), height.toInt())
            imageTintList =
                ResourcesCompat.getColorStateList(resources, getIconTint(isCategorySelected), null)
            this.updateLayoutParams<LayoutParams> {
                marginEnd = resources.getDimension(R.dimen.margin8).toInt()
            }
        }

        addView(icon)
    }

    private fun getTextColor(isSelected: Boolean) = if (isSelected) {
        ResourcesCompat.getColor(resources, R.color.white, null)
    } else {
        ResourcesCompat.getColor(resources, R.color.color_gray2, null)
    }

    private fun getIconTint(isSelected: Boolean) =
        if (isSelected) R.color.white else R.color.color_gray2

    private fun getBackgroundDrawable(isSelected: Boolean) = if (isSelected) {
        ResourcesCompat.getDrawable(resources, R.drawable.bg_service_category_selected, null)
    } else {
        ResourcesCompat.getDrawable(resources, R.drawable.bg_service_category, null)
    }

    fun updateIsSelected(isSelected: Boolean) {
        isCategorySelected = isSelected
        text?.setTextColor(getTextColor(isSelected))
        icon?.imageTintList = ResourcesCompat.getColorStateList(
            resources,
            getIconTint(isSelected),
            null
        )
        background = getBackgroundDrawable(isSelected)

        invalidate()
    }

    companion object {
        const val TEXT_SIZE = 14f
    }
}
