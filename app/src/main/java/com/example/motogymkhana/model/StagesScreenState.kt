package com.example.motogymkhana.model

data class StagesScreenState(
    val stages: List<StageState>,
    val userMessage: SideEffect<Int?> = SideEffect(null),
    val isLoading: Boolean = false,
)
