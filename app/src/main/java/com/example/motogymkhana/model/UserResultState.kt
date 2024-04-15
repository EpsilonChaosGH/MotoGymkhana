package com.example.motogymkhana.model


data class UserResultState(
    val userStatus: UserStatus = UserStatus.WAITING,

    val participantID: Long,
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
    val champClass: String?,
    val placeInChampClass: Long? = null,
    val attempts: List<AttemptState>,
    val bestTimeSeconds: Long? = null,
    val bestTime: String? = null,
    val percent: Double? = null,
    val newClass: String? = null,

    val currentTime: Long = 0L,
    val openTimeMenu: Boolean = false
) {

    data class AttemptState(
        val timeSeconds: Long? = null,
        val time: String? = null,
        val fine: Long? = null,
        val resultTimeSeconds: Long? = null,
        val resultTime: String? = null,
        val attempt: Long? = null,
        val isFail: Boolean = false,
    )
}