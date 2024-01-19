package com.example.motogymkhana.data.model

data class ChampionshipResponse (
    val id: Long,
    val title: String,
    val year: Long,
    val description: String,
    val stagesCount: Long,
    val type: String
)