/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.ui

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_VPN

object ConnectivityUtil {

    fun isConnected(context: Context?): Boolean = context?.run {
        hasNetworkCapabilities(getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager)
    } ?: false

    fun hasNetworkCapabilities(connectivityManager: ConnectivityManager) = connectivityManager.activeNetwork?.run {
        connectivityManager.getNetworkCapabilities(this)
    }?.run {
        hasTransport(TRANSPORT_WIFI) ||
                hasTransport(TRANSPORT_CELLULAR) ||
                hasTransport(TRANSPORT_VPN)
    } ?: false
}