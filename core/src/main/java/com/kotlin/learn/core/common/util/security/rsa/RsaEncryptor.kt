/*
 *
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.kotlin.learn.core.common.util.security.rsa

import android.util.Base64
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import com.kotlin.learn.core.common.util.PreferenceConstants
import com.kotlin.learn.core.util.Constants.EMPTY_STRING
import com.kotlin.learn.core.common.util.security.SecurePrefManager
import timber.log.Timber
import java.nio.charset.StandardCharsets.UTF_8
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.Cipher.ENCRYPT_MODE
import javax.inject.Inject

class RsaEncryptor @Inject constructor(private val securePrefManager: SecurePrefManager) {

    companion object {
        const val ENCRYPT_ALGORITHM = "RSA/ECB/OAEPPadding"
        const val KEY_ALGORITHM = "RSA"
        const val REPLACE_CHAR = "\n"
    }

    private fun generatePublicKey(): PublicKey? {
        val publicKey = securePrefManager.getString(PreferenceConstants.AppConfig.PREF_KEY_RSA_KEY)
        val spec = X509EncodedKeySpec(Base64.decode(publicKey, DEFAULT))
        return KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(spec)
    }

    fun encryptString(text: String): String {
        var encrypted: String = EMPTY_STRING
        try {
            val cipher = Cipher.getInstance(ENCRYPT_ALGORITHM)
            cipher.init(ENCRYPT_MODE, generatePublicKey())
            encrypted = encodeToString(cipher.doFinal(text.toByteArray(UTF_8)), DEFAULT)
        } catch (e: Exception) {
            Timber.e(e)
        } finally {
            return encrypted.replace(REPLACE_CHAR, EMPTY_STRING)
        }
    }
}