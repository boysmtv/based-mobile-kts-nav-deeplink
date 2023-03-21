/*
 * Copyright © 2021 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.kotlin.learn.core.common.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types.newParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

class JsonUtil @Inject constructor(val moshi: Moshi) {

    inline fun <reified InputType> toJson(originalData: InputType, newType: Type? = null): String =
        getJSONAdapter<InputType>(newType).toJson(originalData)

    inline fun <reified OutputType> fromJson(originalData: String, newType: Type? = null): OutputType? =
        getJSONAdapter<OutputType>(newType).fromJson(originalData)

    inline fun <reified AdapterType> getJSONAdapter(newType: Type?): JsonAdapter<AdapterType> =
        moshi.adapter(newType ?: AdapterType::class.java)

    inline fun <K, V> objectsMapToJson(key: Class<K>, value: Class<V>, json: Any): String? {
        return moshi
            .adapter<Any>(newParameterizedType(MutableMap::class.java, key, value))
            .toJson(json)
    }
}