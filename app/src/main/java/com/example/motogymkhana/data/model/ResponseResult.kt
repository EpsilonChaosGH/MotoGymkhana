package com.example.motogymkhana.data.model

import android.content.res.Resources.NotFoundException
import retrofit2.Response

fun<T> Response<T>.getResult(): T{
    if (this.isSuccessful){
         this.body()?.let {
        return it
        } ?: throw NotFoundException()
    } else{
        throw Exception()
    }
}