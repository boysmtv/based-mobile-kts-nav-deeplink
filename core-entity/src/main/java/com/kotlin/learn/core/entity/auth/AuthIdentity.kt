/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.entity.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthIdentity(
    @Json(name = "accessToken") val accessToken: String = EMPTY_STRING,
    @Json(name = "refreshToken") val refreshToken: String = EMPTY_STRING,
    @Json(name = "validity") val validity: String = EMPTY_STRING
)