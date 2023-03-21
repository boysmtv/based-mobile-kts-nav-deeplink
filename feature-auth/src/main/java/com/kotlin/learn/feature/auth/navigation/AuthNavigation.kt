/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.feature.auth.navigation

interface AuthNavigation {
    fun navigateToDashboard(scenario: String)
    fun navigateToTransaction(state: String)
    fun navigateToCustomerLanding()
    fun navigateToCustomerVerification()
    fun navigateToRating()
    fun navigateToWaiting(scenario: String)
    fun navigateToCustomerInformation()
    fun navigateToRecapture()
    fun navigateToNumericAuthorization(scenario: String)
    fun navigateToDebitCardMaintenance()
}