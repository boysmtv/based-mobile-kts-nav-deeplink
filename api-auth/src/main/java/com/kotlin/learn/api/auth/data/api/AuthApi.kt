package com.kotlin.learn.api.auth.data.api

import com.kotlin.learn.api.auth.util.AuthUrlConstants.POST_URL_CUSTOMER_LOGIN
import com.kotlin.learn.api.auth.util.AuthUrlConstants.POST_URL_FORCE_LOGOUT
import com.kotlin.learn.api.auth.util.AuthUrlConstants.POST_URL_GENERAL_BANKER_LOGIN
import com.kotlin.learn.api.auth.util.AuthUrlConstants.POST_URL_GENERAL_BANKER_LOGOUT
import com.kotlin.learn.api.auth.util.AuthUrlConstants.POST_URL_GENERAL_BANKER_REFRESH_TOKEN
import com.kotlin.learn.api.auth.util.AuthUrlConstants.POST_URL_LOGOUT_DUAL_SCREEN
import com.kotlin.learn.api.auth.util.AuthUrlConstants.POST_URL_RESEND_OTP_LOGIN
import com.kotlin.learn.api.auth.util.AuthUrlConstants.POST_URL_VERIFY_OTP_GENERAL_BANKER
import com.kotlin.learn.core.entity.auth.AuthIdentity
import com.kotlin.learn.core.entity.auth.AuthRequest
import com.kotlin.learn.core.entity.auth.AuthResponse
import com.kotlin.learn.core.entity.auth.LogoutResponse
import com.kotlin.learn.core.entity.auth.OtpAuthRequest
import com.mandiri.bds.core.entity.auth.AuthIdentityRequest
import com.kotlin.learn.core.entity.auth.EmptyResponse
import com.kotlin.learn.core.entity.auth.ForceLogoutRequest
import com.kotlin.learn.core.entity.auth.ResendOtpRequest
import com.kotlin.learn.core.entity.auth.ResendOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST(POST_URL_GENERAL_BANKER_LOGIN)
    suspend fun postLogin(@Body authRequest: AuthRequest): Response<AuthResponse>

    @POST(POST_URL_VERIFY_OTP_GENERAL_BANKER)
    suspend fun postVerifyOtp(@Body otpAuthRequest: OtpAuthRequest): Response<AuthResponse>

    @POST(POST_URL_RESEND_OTP_LOGIN)
    suspend fun postResendOtpLogin(@Body resendOtpRequest: ResendOtpRequest): Response<ResendOtpResponse>

    @POST(POST_URL_GENERAL_BANKER_LOGOUT)
    suspend fun postLogout(): Response<LogoutResponse>

    @POST(POST_URL_LOGOUT_DUAL_SCREEN)
    suspend fun postLogoutDualScreen(): Response<LogoutResponse>

    @POST(POST_URL_FORCE_LOGOUT)
    suspend fun postForceLogout(@Body request: ForceLogoutRequest): Response<EmptyResponse>

    @POST(POST_URL_GENERAL_BANKER_REFRESH_TOKEN)
    suspend fun postRefreshTokenIdentity(@Body request: AuthIdentityRequest): Response<AuthIdentity>

    @POST(POST_URL_CUSTOMER_LOGIN)
    suspend fun postLoginCustomer(@Body authRequest: AuthRequest): Response<AuthResponse>

}