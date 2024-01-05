package com.example.motogymkhana.data.model

import com.squareup.moshi.Json


data class StageResponse(

    @field:Json(name = "id")
    val stageId: Long,

    val title: String,
    val dateOfThe: Long,
)
