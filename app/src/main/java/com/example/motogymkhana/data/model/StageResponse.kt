package com.example.motogymkhana.data.model

import com.squareup.moshi.Json


data class StageResponse(

    @field:Json(name = "id")
    val stageId: Long,

    @field:Json(name = "champId")
    val champID: Long,

    val classes: List<String>,
    val champTitle: String,
    val status: String? = null,
    val title: String,
    val description: String,
    val usersCount: Long,

    @field:Json(name = "class")
    val stageClass: String?,
    val trackUrl: String? = null,
    val city: String,
    val dateOfThe: Long? = null,
    val referenceTimeSeconds: Long? = null,
    val referenceTime: String? = null,
    val results: List<UserResult>
) {

    data class UserResult(
        @field:Json(name = "participantId")
        val participantID: Long,

        @field:Json(name = "userId")
        val userID: Long,

        val userFullName: String,
        val userLastName: String,
        val userFirstName: String,
        val userCity: String,
        val userCountry: String? = null,
        val motorcycle: String,
        val athleteClass: String,
        val placeInAthleteClass: Long? = null,
        val champClass: String? = null,
        val placeInChampClass: Long? = null,

        @field:Json(name = "attemtps")
        val attempts: List<Attempt>,
        val bestTimeSeconds: Long? = null,
        val bestTime: String? = null,
        val percent: Double? = null,
        val newClass: String? = null,
        val number: String? = null,
        val order: String? = null
    )

    data class Attempt(
        val timeSeconds: Long? = null,
        val time: String? = null,
        val fine: Long? = null,
        val resultTimeSeconds: Long? = null,
        val resultTime: String? = null,
        val attempt: Long? = null,
        val isFail: Boolean = false,
        val video: Any? = null
    )
}