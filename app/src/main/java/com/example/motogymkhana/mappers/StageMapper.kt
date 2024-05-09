package com.example.motogymkhana.mappers

import com.example.motogymkhana.data.model.StageResponse
import com.example.motogymkhana.model.StageState
import com.example.motogymkhana.model.UserResultState
import com.example.motogymkhana.model.UserStatus
import com.example.motogymkhana.utils.FORMAT_dd_MM_yyyy
import com.example.motogymkhana.utils.format

fun StageResponse.toStageState(favoritesList: List<Long>? = null) = StageState(
    stageID = stageId,
    champID = champID,
    classes = classes,
    champTitle = champTitle,
    status = status,
    title = title,
    description = description,
    usersCount = usersCount,
    stageClass = stageClass,
    trackURL = trackUrl,
    city = city,
    dateOfThe = dateOfThe?.format(FORMAT_dd_MM_yyyy) ?: "0",
    referenceTimeSeconds = referenceTimeSeconds,
    referenceTime = referenceTime,
    results = results.map { it.toUserResultState() }.sortedBy { it.userStatus.ordinal },
    isFavorites = favoritesList?.contains(stageId) ?: false
)

private fun StageResponse.UserResult.toUserResultState() =
    UserResultState(
        participantID = participantID,
        userStatus = if (attempts.getOrNull(0) != null && attempts.getOrNull(1) != null) {
            UserStatus.FINISHED
        } else {
            UserStatus.WAITING
        },
        userID = userID,
        userFullName = userFullName,
        userLastName = userLastName,
        userFirstName = userFirstName,
        userCity = userCity,
        motorcycle = motorcycle,
        athleteClass = athleteClass,
        placeInAthleteClass = placeInAthleteClass,
        champClass = champClass,
        placeInChampClass = placeInChampClass,
        attempts = attempts.map { it.toUserAttemptState() },
        bestTimeSeconds = bestTimeSeconds,
        bestTime = bestTime,
        percent = percent,
        newClass = newClass,
        number = number,
        order = order
    )

private fun StageResponse.Attempt.toUserAttemptState() = UserResultState.AttemptState(
    timeSeconds = timeSeconds,
    time = time,
    fine = fine,
    resultTimeSeconds = resultTimeSeconds,
    resultTime = resultTime,
    attempt = attempt,
    isFail = isFail,
)