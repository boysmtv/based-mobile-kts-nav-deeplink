/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.common.presentation.events

import android.app.Activity
import com.kotlin.learn.core.common.presentation.widget.CommonInvokeListener
import java.util.concurrent.atomic.AtomicBoolean

interface EventListener {
    var common: CommonInvokeListener
    val isSessionIdle: AtomicBoolean
    fun bindActivity(activity: Activity)
}