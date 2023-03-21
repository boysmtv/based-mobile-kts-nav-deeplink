/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.entity.auth

import com.kotlin.learn.core.util.Constants.EMPTY_STRING
import com.squareup.moshi.Json

data class ForceLogoutRequest(
    @Json(name = "userId") var userId: String = EMPTY_STRING,
    @Json(name = "channel") var channel: String = EMPTY_STRING
)
