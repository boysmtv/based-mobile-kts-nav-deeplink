package com.kotlin.learn.api.auth.data.repository

import com.kotlin.learn.core.data.CoroutineDispatcherProvider
import com.kotlin.learn.api.auth.data.local.AuthLocalDataSource
import com.kotlin.learn.core.data.resultFlow
import com.kotlin.learn.core.entity.auth.AuthRequest
import com.kotlin.learn.core.entity.auth.OtpAuthRequest
import com.kotlin.learn.core.entity.auth.ResendOtpRequest
import com.kotlin.learn.api.auth.data.remote.AuthRemoteDataSource
import com.mandiri.bds.core.entity.auth.AuthIdentityRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    internal val localDataSource: AuthLocalDataSource,
    private val dispatcher: CoroutineDispatcherProvider
) {

    //region POST
    fun postLogin(authRequest: AuthRequest) = resultFlow(
        networkCall = { remoteDataSource.postLogin(authRequest) },
        saveCallResult = {
            localDataSource.saveLoginData(it)
            localDataSource.saveUserId(authRequest.userId.orEmpty())
        },
        dispatcher = dispatcher
    )

    fun postVerifyOtp(otpAuthRequest: OtpAuthRequest) = resultFlow(
        networkCall = { remoteDataSource.postVerifyOtp(otpAuthRequest) },
        saveCallResult = {
            localDataSource.saveOtpResponse(it)
            it.validity?.let { localDataSource.saveRefreshTokenTimestamp(it) }
        },
        dispatcher = dispatcher
    )

    fun postLogout() = resultFlow(
        networkCall = { remoteDataSource.postLogout() },
        dispatcher = dispatcher
    )

    fun postLogoutDualScreen() = resultFlow(
        networkCall = { remoteDataSource.postLogoutDualScreen() },
        dispatcher = dispatcher
    )

    fun postForceLogout() = resultFlow(
        networkCall = {
            remoteDataSource.postForceLogout(localDataSource.loadUserId().orEmpty())
        },
        dispatcher = dispatcher
    )

    fun postResendOtpLogin(resendOtpRequest: ResendOtpRequest) = resultFlow(
        networkCall = { remoteDataSource.postResendOtpLogin(resendOtpRequest) },
        dispatcher = dispatcher
    )

    fun postLoginCustomer(authRequest: AuthRequest) = resultFlow(
        networkCall = { remoteDataSource.postLoginCustomer(authRequest) },
        saveCallResult = {
            localDataSource.saveLoginData(it)
            localDataSource.saveUserId(authRequest.userId.orEmpty())
            it.validity?.let { localDataSource.saveRefreshTokenTimestamp(it) }
        },
        dispatcher = dispatcher
    )
    //endregion

    //region GET
    fun getRefreshTokenIdentity() = resultFlow(
        networkCall = {
            remoteDataSource.getRefreshTokenIdentity(
                AuthIdentityRequest(localDataSource.loadRefreshToken())
            )
        },
        saveCallResult = {
            localDataSource.saveAccessToken(it.accessToken, it.refreshToken)
            it.validity.let { validity ->
                localDataSource
                    .saveRefreshTokenTimestamp(validity.toLong())
            }
        },
        dispatcher = dispatcher
    )
    //endregion
}