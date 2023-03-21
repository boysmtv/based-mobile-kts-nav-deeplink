/*
 *
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.kotlin.learn.core.common.navigation

import android.content.Context

interface DeeplinkNavigation {
    fun navigate(state: String, context: Context, vararg arguments: Pair<String, Any>)
    fun navigate(state: String, vararg arguments: Pair<String, Any>)
}