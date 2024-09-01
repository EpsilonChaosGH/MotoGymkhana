package com.example.motogymkhana.screens.stagedetails

import com.example.motogymkhana.utils.SideEffect
import com.example.motogymkhana.model.StageState

data class StageDetailsScreenState(
    val stageState: StageState?,
    val userMessage: SideEffect<Int?> = SideEffect(null),
    val isLoading: Boolean = false,
)