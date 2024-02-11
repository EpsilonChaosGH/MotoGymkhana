package com.example.motogymkhana.model

import com.example.motogymkhana.R

enum class UserStatus(val colorResId: Int) {
    RIDES(R.color.green),
    NEXT(R.color.red),
    HEATING(R.color.yellow),
    WAITING(R.color.white),
}