/*
 * Copyright © 2021 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.kotlin.learn.core.ui.listener

import android.os.SystemClock
import android.view.View
import com.kotlin.learn.core.util.Constants.FIVE_HUNDRED_LONG

/**
 * [listener] will be invoke when view click
 *
 * [throttleInterval] interval between two clicks
 */
class SingleClickListener(
    private val throttleInterval: Long = THROTTLE_INTERVAL,
    private val listener: View.OnClickListener
) : View.OnClickListener {

    override fun onClick(v: View?) {
        validateClickListener(throttleInterval) { listener.onClick(v) }
    }

    companion object {
        const val THROTTLE_INTERVAL: Long = FIVE_HUNDRED_LONG
        private var globalLastClickTimestamp: Long? = null

        fun validateClickListener(
            throttleInterval: Long = THROTTLE_INTERVAL,
            onValid: () -> Unit
        ) {
            val lastClickTimestamp = globalLastClickTimestamp ?: SystemClock.elapsedRealtime()
                .also {
                    globalLastClickTimestamp = it
                    onValid()
                }

            if (SystemClock.elapsedRealtime() - lastClickTimestamp < throttleInterval) return

            globalLastClickTimestamp = SystemClock.elapsedRealtime()
            onValid()
        }
    }
}
