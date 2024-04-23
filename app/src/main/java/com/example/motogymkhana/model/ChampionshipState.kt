package com.example.motogymkhana.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ChampionshipState(
    val id: Long,
    val title: String,
    val year: Long,
    val description: String,
//    val stagesCount: Long,
//    val type: String
): Parcelable