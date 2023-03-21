/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.entity.auth

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ResendOtpResponse(
    @Json(name = "userId") val userId: String?,
    @Json(name = "verifyId") val verifyId: String?,
    @Json(name = "phoneNumber") val phoneNumber: String?
)