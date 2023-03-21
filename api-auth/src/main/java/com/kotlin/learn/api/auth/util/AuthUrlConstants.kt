package com.kotlin.learn.api.auth.util

object AuthUrlConstants {
    const val POST_URL_CUSTOMER_LOGIN = "authentication/v1/login/customer-tablet"
    const val POST_URL_FORCE_LOGOUT = "authentication/v1/user/force-logout"
    const val POST_URL_GENERAL_BANKER_LOGIN = "authentication/v1/login"
    const val POST_URL_GENERAL_BANKER_LOGOUT = "authentication/v1/logout"
    const val POST_URL_GENERAL_BANKER_REFRESH_TOKEN = "authentication/v1/refresh-token"
    const val POST_URL_LOGOUT_DUAL_SCREEN = "authentication/v1/logout/dual-tablet"
    const val POST_URL_RESEND_OTP_LOGIN = "authentication/v1/resend-otp"
    const val POST_URL_VERIFY_OTP_GENERAL_BANKER = "authentication/v1/verify"
}