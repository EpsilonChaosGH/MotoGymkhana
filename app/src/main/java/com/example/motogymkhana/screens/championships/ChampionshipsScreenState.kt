package com.example.motogymkhana.screens.championships

import com.example.motogymkhana.model.ChampionshipState
import com.example.motogymkhana.utils.SideEffect

data class ChampionshipsScreenState(
    val championships: List<ChampionshipState>,
    val userMessage: SideEffect<Int?> = SideEffect(null),
    val isLoading: Boolean = false,
)