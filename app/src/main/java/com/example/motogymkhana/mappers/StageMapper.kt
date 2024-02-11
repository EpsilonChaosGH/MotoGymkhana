package com.example.motogymkhana.mappers

import com.example.motogymkhana.utils.FORMAT_dd_MM_yyyy
import com.example.motogymkhana.data.model.StageInfoResponse
import com.example.motogymkhana.data.model.StageResponse
import com.example.motogymkhana.model.FirebaseStatus
import com.example.motogymkhana.utils.format
import com.example.motogymkhana.model.StageInfoState
import com.example.motogymkhana.model.StageState
import com.example.motogymkhana.model.UserResultState
import com.example.motogymkhana.model.UserStatus

fun StageInfoResponse.toStageInfoState(firebaseStatusList: List<FirebaseStatus>) = StageInfoState(
    stageID = stageId,
    champID = champID,
//    classes = classes,
    champTitle = champTitle,
//    status = status,
    title = title,
//    description = description,
//    usersCount = usersCount,
//    stageClass = stageClass,
//    trackURL = trackUrl,
//    city = city,
    dateOfThe = dateOfThe,
//    referenceTimeSeconds = referenceTimeSeconds,
//    referenceTime = referenceTime,
    results = results.map { result ->
        var status = UserStatus.WAITING
        firebaseStatusList.forEach {
            if (it.userId == result.userID) status = it.userStatus
        }
        result.toUserResultState(status)
    }
)

fun StageResponse.toStageState(favoritesList: List<Long>) = StageState(
    stageID = stageId,
    title = title,
    dateOfThe = dateOfThe.format(FORMAT_dd_MM_yyyy),
    isFavorites = favoritesList.contains(stageId)
)

private fun StageInfoResponse.UserResult.toUserResultState(userStatus: UserStatus) = UserResultState(
    userStatus = userStatus,
    userID = userID,
    userFullName = userFullName,
    userLastName = userLastName,
    userFirstName = userFirstName,
    userCity = userCity,
    motorcycle = motorcycle,
    athleteClass = athleteClass,
    placeInAthleteClass = placeInAthleteClass,
    champClass = champClass ?: "@@@",
    placeInChampClass = placeInChampClass,
    attemtps = attemtps.map { it.toUserAttemptState() },
    bestTimeSeconds = bestTimeSeconds,
    bestTime = bestTime,
    percent = percent,
    newClass = newClass,
)

private fun StageInfoResponse.Attemtp.toUserAttemptState() = UserResultState.AttemtpState(
    timeSeconds = timeSeconds ?: 0,
    time = time?: "@@@",
    fine = fine?: 0,
    resultTimeSeconds = resultTimeSeconds?: 0,
    resultTime = resultTime?: "@@@",
    attempt = attempt?: 0,
    isFail = isFail?: false,
    video = video,
)