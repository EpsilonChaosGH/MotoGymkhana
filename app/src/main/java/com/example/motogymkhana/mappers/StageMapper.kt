package com.example.motogymkhana.mappers

import com.example.motogymkhana.FORMAT_dd_MM_yyyy
import com.example.motogymkhana.data.model.StageInfoResponse
import com.example.motogymkhana.data.model.StageResponse
import com.example.motogymkhana.format
import com.example.motogymkhana.model.StageInfoState
import com.example.motogymkhana.model.StageState
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

fun StageResponse.toStageState() = StageState(
    stageID = stageID,
    title = title,
    dateOfThe = dateOfThe.format(FORMAT_dd_MM_yyyy)
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