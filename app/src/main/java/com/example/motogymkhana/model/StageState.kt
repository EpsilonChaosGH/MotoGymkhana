package com.example.motogymkhana.model


data class StageState(
    val stageID: Long,
    val title: String,
    val dateOfThe: String,
    val isFavorites: Boolean = false
)