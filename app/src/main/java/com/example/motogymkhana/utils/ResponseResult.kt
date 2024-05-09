package com.example.motogymkhana.utils

import android.content.res.Resources.NotFoundException
import retrofit2.Response

fun <T> Response<T>.getResult(): T {
    if (this.isSuccessful) {
        this.body()?.let {
            return it
        } ?: throw NotFoundException()
    } else {
        throw Exception(this.body().toString())
    }
}

fun okhttp3.Response.getResult(): String {
    if (this.isSuccessful) {
        this.body?.let {
            return it.string()
        } ?: throw NotFoundException()
    } else {
        throw Exception()
    }
}