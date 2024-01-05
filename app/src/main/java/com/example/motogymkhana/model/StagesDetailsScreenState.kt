package com.example.motogymkhana.model

data class StagesDetailsScreenState(
    val stageInfo: StageInfoState?,
    val userMessage: SideEffect<Int?> = SideEffect(null),
    val isLoading: Boolean = false,
)
