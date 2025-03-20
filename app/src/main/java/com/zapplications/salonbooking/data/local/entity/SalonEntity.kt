package com.zapplications.salonbooking.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteSalonEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "salon_name") val salonName: String,
    @ColumnInfo(name = "rating") val rating: Double,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "reviewer_count") val reviewerCount: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String
)
