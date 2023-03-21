/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */

package com.kotlin.learn.core.common.util

import com.kotlin.learn.core.common.entity.Quadruple

fun <T, A> pairOf(first: T, second: A? = null): Pair<T, A?> = Pair(first, second)

fun <T, A, B> tripleOf(first: T, second: A, third: B? = null): Triple<T, A, B?> = Triple(first, second, third)

fun <T, A, B, C> quadrupleOf(first: T, second: A, third: B? = null, fourth: C? = null): Quadruple<T, A, B?, C?> =
    Quadruple(first, second, third, fourth)

fun <T> Quadruple<T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth)
