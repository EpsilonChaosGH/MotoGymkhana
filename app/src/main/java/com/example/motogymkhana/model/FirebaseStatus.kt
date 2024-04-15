package com.example.motogymkhana.model

data class FirebaseStatus(
    val participantID: Long = 0,
    val userStatus: UserStatus = UserStatus.WAITING
)
