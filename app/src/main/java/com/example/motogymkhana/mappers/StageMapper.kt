package com.example.motogymkhana.mappers

import com.example.motogymkhana.data.network.StageInfoResponse
import com.example.motogymkhana.model.StageInfoState
import com.example.motogymkhana.model.UserResultState

fun StageInfoResponse.toStageInfoState() = StageInfoState(
    stageID = stageID,
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
    dateOfThe = dateOfThe,
    referenceTimeSeconds = referenceTimeSeconds,
    referenceTime = referenceTime,
    results = results.map { it.toUserResultState() }
)

private fun StageInfoResponse.UserResult.toUserResultState() = UserResultState(
    userID = userID,
    userFullName = userFullName,
    userLastName = userLastName,
    userFirstName = userFirstName,
    userCity = userCity,
    motorcycle = motorcycle,
    athleteClass = athleteClass,
    placeInAthleteClass = placeInAthleteClass,
    placeInChampClass = placeInChampClass,
    attemtps = attemtps.map { it.toUserAttemptState() },
    bestTimeSeconds = bestTimeSeconds,
    bestTime = bestTime,
    percent = percent,
    newClass = newClass,
)

private fun StageInfoResponse.Attemtp.toUserAttemptState() = UserResultState.AttemtpState(
    timeSeconds = timeSeconds,
    time = time,
    fine = fine,
    resultTimeSeconds = resultTimeSeconds,
    resultTime = resultTime,
    attempt = attempt,
    isFail = isFail,
    video = video,
)