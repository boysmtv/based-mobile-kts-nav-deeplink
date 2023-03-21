/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.common.presentation.widget

interface CommonInvokeListener {
    fun continueProcess(): Boolean
    fun invalidRefreshToken()
    fun setRefreshTokenResultListener(listener: CommonListener)
    fun setCanRecallAPI(value: Boolean)
    fun setNullListener()
}