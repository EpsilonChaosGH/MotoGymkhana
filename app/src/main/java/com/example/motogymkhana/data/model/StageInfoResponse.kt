package com.example.motogymkhana.data.model

import com.squareup.moshi.Json


data class StageInfoResponse(

    @field:Json(name = "id")
    val stageId: Long,

    @field:Json(name = "champId")
    val champID: Long,

//    val classes: List<String>,
    val champTitle: String,
//    val status: String,
    val title: String,
//    val description: String,
//    val usersCount: Long,

//    @field:Json(name = "class")
//    val stageClass: String?,
//    val trackUrl: String,
//    val city: String,
    val dateOfThe: Long,
//    val referenceTimeSeconds: Long,
//    val referenceTime: String,
    val results: List<UserResult>
) {

    data class UserResult(
        @field:Json(name = "userId")
        val userID: Long,

        val userFullName: String,
        val userLastName: String,
        val userFirstName: String,
        val userCity: String,
       // val userCountry: UserCountry,
        val motorcycle: String,
        val athleteClass: String,
        val placeInAthleteClass: Long? = null,
       // val champClass: ChampClass,
        val champClass: String? = null,
        val placeInChampClass: Long? = null,
        val attemtps: List<Attemtp>,
        val bestTimeSeconds: Long? = null,
        val bestTime: String? = null,
        val percent: Double? = null,
        val newClass: String? = null
    )

    data class Attemtp(
        val timeSeconds: Long? = null,
        val time: String? = null,
        val fine: Long? = null,
        val resultTimeSeconds: Long? = null,
        val resultTime: String? = null,
        val attempt: Long? = null,
        val isFail: Boolean? = null,
        val video: Any? = null
    )

    enum class ChampClass(val value: String) {
        BC2("B-C2"),
        D1C3("D1-C3"),
        D3D2("D3-D2"),
        ND4("N-D4");

        companion object {
            public fun fromValue(value: String): ChampClass = when (value) {
                "B-C2" -> BC2
                "D1-C3" -> D1C3
                "D3-D2" -> D3D2
                "N-D4" -> ND4
                else -> throw IllegalArgumentException()
            }
        }
    }

    enum class UserCountry(val value: String) {
        Беларусь("Беларусь"),
        Россия("Россия");

        companion object {
            public fun fromValue(value: String): UserCountry = when (value) {
                "Беларусь" -> Беларусь
                "Россия" -> Россия
                else -> throw IllegalArgumentException()
            }
        }
    }
}
