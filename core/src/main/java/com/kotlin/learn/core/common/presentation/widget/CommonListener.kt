/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.common.presentation.widget

interface CommonListener {
    fun restartTask()
    fun mainErrorHandler(
        code: String?,
        title: String? = null,
        message: String? = null,
        httpCode: String? = null
    )
    fun showProgressDialog(isShow: Boolean)
}