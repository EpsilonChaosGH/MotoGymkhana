package com.example.motogymkhana.data.model

import com.squareup.moshi.Json

data class ChampionshipResponse(
    val id: Long,
    val title: String,
    val year: Long,
    val description: String,
    val stages: List<Stage>
) {

    data class Stage(
        val id: Long,
        val status: String? = null,
        val title: String? = null,
        val description: String? = null,
        val usersCount: Long? = null,

        @field:Json(name = "class")
        val stageClass: String? = null,

        @field:Json(name = "trackUrl")
        val trackURL: String? = null,

        val city: String? = null,
        val dateOfThe: Long? = null,
        val referenceTimeSeconds: Long? = null,
        val referenceTime: String? = null,
        val bestTimeSeconds: Long? = null,
        val bestTime: String? = null,

        @field:Json(name = "bestUserId")
        val bestUserID: Long? = null,

        val bestUserFirstName: String? = null,
        val bestUserLastname: String? = null,
        val bestUserFullName: String? = null,
        val bestUserCity: String? = null
    )
}