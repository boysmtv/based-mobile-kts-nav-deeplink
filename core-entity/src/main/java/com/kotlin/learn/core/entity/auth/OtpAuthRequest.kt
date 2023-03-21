/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.entity.auth

import androidx.annotation.Keep
import com.kotlin.learn.core.util.Constants.REQUEST_CHANNEL_TABLET
import com.squareup.moshi.Json

@Keep
data class OtpAuthRequest(
    @Json(name = "userId") var userId: String? = null,
    @Json(name = "verifyId") var verifyId: String? = null,
    @Json(name = "otp") var otp: String? = null,
    @Json(name = "channel") var channel: String = REQUEST_CHANNEL_TABLET
)
