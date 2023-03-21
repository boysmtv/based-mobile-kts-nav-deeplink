/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.common.entity

import androidx.annotation.Keep

@Keep
data class Result<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val title: String?,
    val code: String?,
    val httpStatus: String?
) {
    private var loadingHasBeenHandled = false
    private var successHasBeenHandled = false
    private var errorHasBeenHandled = false

    fun getLoadingStateIfNotHandled() = if (loadingHasBeenHandled) {
        null
    } else {
        loadingHasBeenHandled = true
    }

    fun getSuccessStateIfNotHandled(): T? = if (successHasBeenHandled) {
        null
    } else {
        successHasBeenHandled = true
        data
    }

    fun getErrorStateIfNotHandled() = if (errorHasBeenHandled) {
        null
    } else {
        errorHasBeenHandled = true
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Result<T> = Result(Status.SUCCESS, data, null, null, null, null)

        fun <T> error(
            message: String,
            data: T? = null,
            title: String? = null,
            code: String? = null,
            httpStatus: String? = null
        ): Result<T> =
            Result(Status.ERROR, data, message, title, code, httpStatus)

        fun <T> loading(data: T? = null): Result<T> =
            Result(Status.LOADING, data, null, null, null, null)
    }
}