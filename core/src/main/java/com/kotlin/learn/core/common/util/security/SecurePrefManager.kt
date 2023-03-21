/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.common.util.security

import com.mandiri.bds.core.common.util.security.CorePrefManager
import javax.inject.Inject

class SecurePrefManager @Inject constructor(private val corePrefManager: CorePrefManager) {

    fun setString(key: String, value: String?) = corePrefManager.setString(key, value)

    fun setStringSet(key: String, value: Set<String>) = corePrefManager.setStringSet(key, value )

    fun setInt(key: String, value: Int) = corePrefManager.setInt(key, value)

    fun setLong(key: String, value: Long) = corePrefManager.setLong(key, value)

    fun setFloat(key: String, value: Float) = corePrefManager.setFloat(key, value)

    fun setBoolean(key: String, value: Boolean) = corePrefManager.setBoolean(key, value)

    fun getString(key: String): String? = corePrefManager.getString(key)

    fun getStringSet(key: String) = corePrefManager.getStringSet(key)

    fun getInt(key: String): Int = corePrefManager.getInt(key)

    fun getLong(key: String): Long = corePrefManager.getLong(key)

    fun getFloat(key: String): Float = corePrefManager.getFloat(key)

    fun getBoolean(key: String): Boolean = corePrefManager.getBoolean(key)

    fun isContain(key: String): Boolean = corePrefManager.isContain(key)

    fun remove(key: String) = corePrefManager.remove(key)

    fun clear() = corePrefManager.clear()
}