package com.example.motogymkhana.model

data class PostTimeRequestBody(
    val stageId: String,
    val participantID: String,
    val attempt: String,
    val time: String,
    val fine: String = "0",
    val isFail: String = "0"
)