package com.example.motogymkhana.data.model

import com.squareup.moshi.Json


data class StageResponse(

    @Json(name = "id")
    val stageID: Long,

    val title: String,
    val dateOfThe: Long,
)
