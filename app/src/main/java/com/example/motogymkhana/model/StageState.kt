package com.example.motogymkhana.model


data class StageState(
    val stageID: Long,
    val champID: Long,
//    val classes: List<String>,
    val champTitle: String,
//    val status: String,
    val title: String,
//    val description: String,
//    val usersCount: Long,
//    val stageClass: String?,
//    val trackURL: String,
//    val city: String,
    val dateOfThe: String,
//    val referenceTimeSeconds: Long,
//    val referenceTime: String,
    val results: List<UserResultState>,
//    val attempt: Attempt = Attempt.First,
    val isFavorites: Boolean = false
)