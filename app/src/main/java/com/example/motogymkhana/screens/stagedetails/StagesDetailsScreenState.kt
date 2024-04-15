package com.example.motogymkhana.screens.stagedetails

import com.example.motogymkhana.utils.SideEffect
import com.example.motogymkhana.model.StageInfoState

data class StagesDetailsScreenState(
    val stageInfo: StageInfoState?,
    val currentTime: String,
    val userMessage: SideEffect<Int?> = SideEffect(null),
    val isLoading: Boolean = false,
)
