package com.example.motogymkhana.model

data class PostTimeRequestBody(
    val stageId: String = "66",
    val participantID: String = "792",
    val attempt: String = "1",
    val time: String = "02:11.11",
    val fine: String = "0",
    val isFail: String = "0"
)