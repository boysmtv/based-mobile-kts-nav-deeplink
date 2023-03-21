/*
 * Copyright © 2020 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.navigation

import androidx.navigation.NavController
import javax.inject.Inject

class NavControllerBinder @Inject constructor() {

    var navController: NavController? = null

    var needReset: Boolean = false

    fun bind(navController: NavController){
        this.navController = navController
    }

    fun unbind() {
        navController = null
    }
}