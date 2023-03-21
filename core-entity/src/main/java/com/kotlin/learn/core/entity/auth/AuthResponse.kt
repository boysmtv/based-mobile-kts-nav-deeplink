package com.kotlin.learn.core.entity.auth

import android.os.Parcelable
import androidx.annotation.Keep
import com.kotlin.learn.core.util.Constants.ZERO_LONG
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@JsonClass(generateAdapter = true)
data class AuthResponse(
    @Json(name = "accessToken") var accessToken: String? = null,
    @Json(name = "isReserved") var isReserved: Boolean = false,
    @Json(name = "role") var role: String? = null,
    @Json(name = "fullName") var fullName: String? = null,
    @Json(name = "tellerGroup") var tellerGroup: String? = null,
    @Json(name = "tellerId") var tellerId: String? = null,
    @Json(name = "tellerUserId") var tellerUserId: String? = null,
    @Json(name = "branchNumber") var branchNumber: String? = null,
    @Json(name = "phoneNumber") var phoneNumber: String? = null,
    @Json(name = "email") var email: String? = null,
    @Json(name = "branchName") var branchName: String? = null,
    @Json(name = "verifyId") var verifyId: String? = null,
    @Json(name = "userId") var userId: String? = null,
    @Json(name = "refreshToken") var refreshToken: String? = null,
    @Json(name = "validity") var validity: Long? = ZERO_LONG,
    @Json(name = "accessMenu") val accessMenu: List<String> = listOf(),
    @Json(name = "tellerCounter") val tellerCounter: String? = null,
    @Json(name = "isDirectSelfService") val isDirectSelfService: Boolean = false,
    @Json(name = "currentStateResponse") val currentStateResponse: CurrentStateResponse? = null
) : Parcelable

@Keep
@Parcelize
data class CurrentStateResponse(
    @Json(name = "reservationId") val reservationId: String? = null,
    @Json(name = "currentState") val currentState: String? = null,
    @Json(name = "durationSeconds") val durationSeconds: Long? = ZERO_LONG,
    @Json(name = "isWaitingConfirmationSupervisor") val isWaitingConfirmationSupervisor: Boolean? = null,
    @Json(name = "isWaitingConfirmation") val isWaitingConfirmation: Boolean? = null
) : Parcelable
