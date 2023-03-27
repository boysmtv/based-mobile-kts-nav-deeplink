package com.kotlin.learn.based.viewmodel

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.learn.api.auth.domain.get.GetAuthRefreshTokenIdentityUseCase
import com.kotlin.learn.based.IIsolatedService
import com.kotlin.learn.based.util.Constants
import com.kotlin.learn.core.common.entity.Result
import com.kotlin.learn.core.common.util.PreferenceConstants.Auth.PREF_KEY_APP_IDLE_TIME
import com.kotlin.learn.core.entity.auth.AuthIdentity
import com.kotlin.learn.core.entity.auth.EmptyResponse
import com.kotlin.learn.core.entity.auth.LogoutResponse
import com.kotlin.learn.core.util.Constants.DEFAULT_APPLICATION_IDLE_TIME
import com.kotlin.learn.core.util.Constants.ZERO_LONG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getAuthRefreshTokenIdentityUseCase: GetAuthRefreshTokenIdentityUseCase,
    private val postForceLogoutUseCase: PostForceLogoutUseCase,
    private val loginDataUseCase: LoadLoginDataUseCase,
    private val postLogoutUseCase: PostLogoutUseCase,
    private val loadUseridUseCase: LoadUserIdUseCase,
    internal val loadRefreshTokenTimestampUseCase: LoadRefreshTokenTimestampUseCase,
    private val saveRefreshTokenTimestampUseCase: SaveRefreshTokenTimestampUseCase,
    private val loadFullnameUseCase: LoadFullnameUseCase,
    private val loadUserTypeUseCase: LoadUserTypeUseCase,
    internal val loadUserType: LoadUserTypeUseCase
) : ViewModel() {

    private val _refreshToken = MutableLiveData<Result<AuthIdentity>>()
    val refreshToken: LiveData<Result<AuthIdentity>> = _refreshToken

    private val _logout = MutableLiveData<Result<LogoutResponse>>()
    val logout: LiveData<Result<LogoutResponse>> = _logout

    private val _forceLogout = MutableLiveData<Result<EmptyResponse>>()
    val forceLogout: LiveData<Result<EmptyResponse>> = _forceLogout

    //region Local
    val isAppInForeground = AtomicBoolean(false)
    private val isRefreshTokenCalling = AtomicBoolean(false)
    private val isCanRecallAPI = AtomicBoolean(true)
    var stateParam = mutableMapOf<String, String>()
    val isLogoutDialogShow = AtomicBoolean(false)

    private var serviceBinder: IIsolatedService? = null
    private var bServiceBound = false
    private var runOnes = true

    internal val mIsolatedServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            serviceBinder = IIsolatedService.Stub.asInterface(iBinder)
            bServiceBound = true
            if (runOnes) {
                runOnes = false
                runRootDevice()
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            bServiceBound = false
        }
    }

    //endregion

    //region INVOKER
    suspend fun getBalance() =
        getBalanceUseCase().collect { _balance.postValue(it) }

    suspend fun postLogout() =
        postLogoutUseCase().collect { _logout.postValue(it) }

    suspend fun postLogoutDualScreen() =
        postLogoutDualScreenUseCase().collect { _logoutDualScreen.postValue(it) }

    suspend fun postForceLogout() =
        postForceLogoutUseCase().collect { _forceLogout.postValue(it) }

    suspend fun getAuthRefreshTokenIdentity(sseCall: (suspend () -> Unit)? = null) =
        getAuthRefreshTokenIdentityUseCase().collect {
            sseCall?.let { sseAction ->
                if (it.status == Result.Status.SUCCESS) {
                    setCanRecallAPI(true)
                    setRefreshTokenCalling(false)
                    sseAction()
                } else _refreshToken.postValue(it)
            } ?: run {
                _refreshToken.postValue(it)
            }
        }

    suspend fun getReservationState(params: Map<String, String>) =
        getReservationStateEventUseCase(mutableMapOf<String, String>().apply {
            putAll(params)
        }).collect { _reservationState.postValue(it) }
    //endregion

    //region HANDLER
    fun runRootDevice() {
        if (bServiceBound) {
            var bIsMagisk = false
            try {
                serviceBinder?.isMagiskPresent()?.let { bIsMagisk = it }
                if (bIsMagisk) throw RuntimeException(Constants.MAGISK_FOUND)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    fun loadApplicationIdleTime(): Long =
        loadAppConfigUseCase(PREF_KEY_APP_IDLE_TIME)?.parameterValue.orReplaceWith(
            DEFAULT_APPLICATION_IDLE_TIME
        ).toLong()

    fun getLoginData() = loginDataUseCase()

    fun getFullname() = loadFullnameUseCase()

    fun getUserType() = loadUserTypeUseCase()

    fun getUserId() = loadUseridUseCase()

    fun isCanRecallAPI() = isCanRecallAPI.get()

    fun setCanRecallAPI(value: Boolean) = isCanRecallAPI.set(value)

    fun isRefreshTokenCalling() = isRefreshTokenCalling.get()

    fun setRefreshTokenCalling(value: Boolean) = isRefreshTokenCalling.set(value)

    fun saveRefreshToken(timestamp: Long = ZERO_LONG) = saveRefreshTokenTimestampUseCase(timestamp)
    //endregion
}