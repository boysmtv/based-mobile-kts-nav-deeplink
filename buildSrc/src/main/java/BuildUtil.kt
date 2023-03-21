/*
 * Copyright © 2020 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

import org.gradle.kotlin.dsl.support.delegates.ProjectDelegate

fun ProjectDelegate.getPropertiesValue(
    keyName: String,
    propertiesFileName: String = "api.properties"
): String {
    val items = HashMap<String, String>()
    val fl = rootProject.file(propertiesFileName)
    (fl.exists()).let {
        fl.forEachLine {
            items[it.split("=")[0]] = it.split("=")[1]
        }
    }

    return items[keyName]!!
}

fun ProjectDelegate.getPropertiesIntValue(
    keyName: String,
    propertiesFileName: String
): Int = getPropertiesValue(keyName, propertiesFileName).toInt()

fun ProjectDelegate.getPropertiesBooleanValue(
    keyName: String,
    propertiesFileName: String
): Boolean = getPropertiesValue(keyName, propertiesFileName).toBoolean()
