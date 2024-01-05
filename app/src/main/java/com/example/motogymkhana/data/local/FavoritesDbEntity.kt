package com.example.motogymkhana.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoritesDbEntity(
    @PrimaryKey @ColumnInfo(name = "stage_id") val stageId: Long,
)