package com.example.motogymkhana.mappers

import com.example.motogymkhana.data.model.ChampionshipResponse
import com.example.motogymkhana.model.ChampionshipState

fun ChampionshipResponse.toChampionshipsState() = ChampionshipState(
    id = id,
    title = title,
    year = year,
    description = description,
)