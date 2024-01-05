package com.example.motogymkhana.data.model

import com.squareup.moshi.Json

data class ChampionshipInfoResponse(
    val id: Long,
    val title: String,
    val year: Long,
    val description: String,
    val stages: List<Stage>
) {

    data class Stage(
        val id: Long,
//    val status: Status,
        val title: String,
        val description: String,
        val usersCount: Long,

        @field:Json(name = "class")
        val stageClass: String? = null,

        @field:Json(name = "trackUrl")
        val trackURL: String? = null,

        val city: String,
        val dateOfThe: Long,
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

    enum class Status(val value: String) {
        ПрошедшийЭтап("Прошедший этап"),
        ЭтапОтменён("Этап отменён");

        companion object {
            public fun fromValue(value: String): Status = when (value) {
                "Прошедший этап" -> ПрошедшийЭтап
                "Этап отменён" -> ЭтапОтменён
                else -> throw IllegalArgumentException()
            }
        }
    }
}