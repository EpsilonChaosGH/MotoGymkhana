package com.example.motogymkhana.model

data class FirebaseStatus(
    val userId: Long = 0,
    val userStatus: UserStatus = UserStatus.WAITING
)
