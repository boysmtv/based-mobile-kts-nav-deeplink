/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.base

import com.kotlin.learn.core.common.util.RespondConstants.HttpCode.CODE_504
import com.kotlin.learn.core.util.Constants.EMPTY_STRING
import com.kotlin.learn.core.util.Constants.ZERO
import com.kotlin.learn.core.common.entity.Result
import com.kotlin.learn.core.entity.common.ErrorResponse
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import java.lang.NullPointerException
import java.net.SocketTimeoutException

abstract class BaseDataSource {

    /**
     * Use this method if the response returned from the service is in form of a collection but,
     * it's wrapped inside a single named object and you just want to get its collection inside. For example:
     * ```
     * {
     *     "aSingleObjectThatContainsTheDataCollection": [
     *          { A collection of data },
     *          {...},
     *          {...},
     *          .
     *          .
     *          .
     *      ]
     * }
     * ```
     * This function will automatically get the value contained in the current function
     *
     */
    suspend fun <T> getResult(call: suspend () -> Response<Map<String, T>>): Result<T> = try {
        val response = call()

        if (response.isSuccessful) response.body()?.run {
            Result.success(values.first())
        } ?: errorHandler(response.code(), null, " ${response.code()} ${response.message()}")
        else errorHandler(response.code(), response.errorBody(), response.message())
    } catch (e: Exception) {
        Timber.e(e)
        errorHandler(ZERO, null, e.message ?: e.toString())
    }

    /**
     * Similar use with [getResult] but, the response will be the whole thing and delegate the returned value to the
     * caller of this function to be processed further.
     */
    suspend fun <T> getResultWithSingleObject(call: suspend () -> Response<T>): Result<T> = try {
        val response = call()
        if (response.isSuccessful) response.body()?.run {
            Result.success(this)
        } ?: errorHandler(
            response.code(),
            response.errorBody(),
            "returns NULL. ${response.message()}"
        )
        else errorHandler(response.code(), response.errorBody(), "Error. ${response.message()}")
    } catch (ex: SocketTimeoutException) {
        errorHandler(CODE_504.toInt(), null, ex.message ?: ex.toString())
    } catch (e: Exception) {
        Timber.e(e)
        errorHandler(0, null, e.message ?: e.toString())
    }

    suspend fun <T> getLocalResult(load: suspend () -> T?): Result<T> = with(load.invoke()) {
        return@with if (this != null) Result.success(this)
        else Result.error(NullPointerException().message.toString())
    }

    private fun <T> errorHandler(
        httpCode: Int,
        errorBody: ResponseBody?,
        extraMessage: String? = EMPTY_STRING
    ): Result<T> {
        try {
            val errorDetail = errorBody?.string()?.run {
                Moshi.Builder().build().adapter(ErrorResponse::class.java).fromJson(this)
            }
            return Result.error(
                message = errorDetail?.message
                    ?: "Network call has failed for the following reason(s): $extraMessage",
                data = null,
                title = errorDetail?.title,
                code = errorDetail?.code,
                httpStatus = httpCode.toString()
            )
        } catch (e: Exception) {
            return Result.error(
                message = EMPTY_STRING,
                data = null,
                title = EMPTY_STRING,
                code = EMPTY_STRING,
                httpStatus = httpCode.toString()
            )
        }
    }
}