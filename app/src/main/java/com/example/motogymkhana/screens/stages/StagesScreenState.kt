package com.example.motogymkhana.screens.stages

import com.example.motogymkhana.utils.SideEffect
import com.example.motogymkhana.model.StageState

data class StagesScreenState(
    val stages: List<StageState>,
    val userMessage: SideEffect<Int?> = SideEffect(null),
    val isLoading: Boolean = false,
)