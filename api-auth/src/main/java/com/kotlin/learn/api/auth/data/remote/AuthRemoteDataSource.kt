package com.kotlin.learn.api.auth.data.remote

import com.kotlin.learn.api.auth.data.api.AuthApi
import com.kotlin.learn.core.base.BaseDataSource
import com.kotlin.learn.core.entity.auth.AuthRequest
import com.kotlin.learn.core.entity.auth.ForceLogoutRequest
import com.kotlin.learn.core.entity.auth.OtpAuthRequest
import com.kotlin.learn.core.entity.auth.ResendOtpRequest
import com.mandiri.bds.core.entity.auth.AuthIdentityRequest
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(var api: AuthApi) : BaseDataSource() {

    suspend fun postLogin(authRequest: AuthRequest) = getResultWithSingleObject {
        api.postLogin(authRequest)
    }

    suspend fun postVerifyOtp(otpAuthRequest: OtpAuthRequest) = getResultWithSingleObject {
        api.postVerifyOtp(otpAuthRequest)
    }

    suspend fun postResendOtpLogin(resendOtpRequest: ResendOtpRequest) = getResultWithSingleObject {
        api.postResendOtpLogin(resendOtpRequest)
    }

    suspend fun postLogout() = getResultWithSingleObject {
        api.postLogout()
    }

    suspend fun postLogoutDualScreen() = getResultWithSingleObject {
        api.postLogoutDualScreen()
    }

    suspend fun postForceLogout(userId: String) = getResultWithSingleObject {
        api.postForceLogout(ForceLogoutRequest(userId, "CHANNEL"))
    }

    suspend fun getRefreshTokenIdentity(request: AuthIdentityRequest) =
        getResultWithSingleObject { api.postRefreshTokenIdentity(request) }

    suspend fun postLoginCustomer(authRequest: AuthRequest) = getResultWithSingleObject {
        api.postLoginCustomer(authRequest)
    }
}