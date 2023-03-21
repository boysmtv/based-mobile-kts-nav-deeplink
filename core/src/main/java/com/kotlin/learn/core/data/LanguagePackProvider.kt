/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.kotlin.learn.core.data

import com.i18next.android.Operation

interface LanguagePackProvider {

    fun get(key: String, operation: Operation? = null): String?

    fun get(key: String, language: SupportedLanguage, operation: Operation? = null): String?

    fun setLanguage(lang: String)

    fun load(defaultLanguage: SupportedLanguage)
}