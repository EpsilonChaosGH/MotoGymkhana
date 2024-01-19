package com.example.motogymkhana.model


data class UserResultState(
    val userID: Long,
    val userFullName: String,
    val userLastName: String,
    val userFirstName: String,
    val userCity: String,
    // val userCountry: UserCountry,
    val motorcycle: String,
    val athleteClass: String,
    val placeInAthleteClass: Long? = null,
//      val champClass: ChampClass,
    val champClass: String,
    val placeInChampClass: Long? = null,
    val attemtps: List<AttemtpState>,
    val bestTimeSeconds: Long? = null,
    val bestTime: String? = null,
    val percent: Double? = null,
    val newClass: String? = null
) {

    data class AttemtpState(
        val timeSeconds: Long,
        val time: String,
        val fine: Long,
        val resultTimeSeconds: Long,
        val resultTime: String,
        val attempt: Long,
        val isFail: Boolean,
        val video: Any? = null
    )
}