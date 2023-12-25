package com.example.motogymkhana.data.network

import com.example.motogymkhana.model.Type

data class ChampionshipResponse (
    val id: Long,
    val title: String,
    val year: Long,
    val description: String,
    val stagesCount: Long,
    val type: Type
)