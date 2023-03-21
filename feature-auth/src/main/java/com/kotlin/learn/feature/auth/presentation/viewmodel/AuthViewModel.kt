package com.kotlin.learn.feature.auth.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.learn.core.util.Constants.EMPTY_STRING
import com.kotlin.learn.core.util.Constants.ONE
import com.kotlin.learn.feature.auth.navigation.AuthNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val postLoginCustomerUseCase: PostLoginCustomerUseCase,
    private val postLoginUseCase: PostLoginUseCase,
    private val postReservationStateUseCase: PostReservationStateUseCase,
    private val removeScenarioUseCase: ClearScenarioUseCase,
    private val saveReservationIdUseCase: SaveReservationIdUseCase,
    private val saveUserTypeUseCase: SaveUserTypeUseCase,
    private val saveBranchNumberUseCase: SaveBranchNumberUseCase,
    internal val saveDialogStateUseCase: SaveDialogStateUseCase,
    internal val saveLastSelectedTransactionUseCase: SaveLastSelectedTransactionUseCase,
    internal val saveInsuficientBalanceDialogUseCase: SaveInsuficientBalanceDialogUseCase,
    internal val clearScenarioSubmitUseCase: ClearTransactionScenarioSubmitUseCase,
    internal val loadAppConfigUseCase: LoadAppConfigUseCase,
    internal val clearScenarioSubmit: ClearTransactionScenarioSubmitUseCase,
    internal val saveGbLoginType: SaveGbLoginType,
    val nav: AuthNavigation
) : ViewModel() {

    //region Local
    val loginData by lazy { MutableLiveData<AuthResponse>() }
    val loginCustomerData by lazy { MutableLiveData<AuthResponse>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    val loadErrorMessage by lazy { MutableLiveData<String>() }

    var dataAuthResponse = AuthResponse()
    var dataCustomerAuthResponse = AuthResponse()
    var errorMessage: String = EMPTY_STRING
    var userId: String = EMPTY_STRING
    var password: String = EMPTY_STRING
    var userType: String = GB.value

    private val minimumPass: Int = ONE
    private val minimumUserId: Int = ONE
    //endregion

    //region Live Data
    private val _login = MutableLiveData<Result<AuthResponse>>()
    val login: LiveData<Result<AuthResponse>> = _login

    private val _loginCustomer = MutableLiveData<Result<AuthResponse>>()
    val loginCustomer: LiveData<Result<AuthResponse>> = _loginCustomer

    private val _postReservationState = MutableLiveData<Result<EmptyResponse>>()
    val postReservationState: LiveData<Result<EmptyResponse>> = _postReservationState
    //endregion

    //region Invoker
    suspend fun getLogin() =
        postLoginUseCase(
            AuthRequest(
                userId = userId,
                password = password
            )
        ).collect {
            _login.postValue(it)
        }

    suspend fun getLoginCustomer() =
        postLoginCustomerUseCase(
            AuthRequest(
                userId = userId,
                password = password
            )
        ).collect {
            _loginCustomer.postValue(it)
        }

    suspend fun postReservationState(request: ReservationStateRequest) =
        postReservationStateUseCase(request)
            .collect { _postReservationState.postValue(it) }
    //endregion

    //region post
    suspend fun postReservationConfirmationSupervisor() {
        postReservationState(
            ReservationStateRequest(
                customerReservationState = SseState.ADVERTISEMENT_WAIT_SUPERVISOR_ALLOCATION_PAGE,
                generalBankerReservationState = SseState.CHOOSE_SUPERVISOR_ALLOCATION_PAGE
            )
        )
    }

    suspend fun postReservationConfirmation() {
        postReservationState(
            ReservationStateRequest(
                customerReservationState = SseState.DEPOSIT_SUBMIT_WAIT_PAGE,
                generalBankerReservationState = SseState.DEPOSIT_SUBMIT_PAGE,
            )
        )
    }
    //endregion

    //region Save
    fun saveUserType(type: String = EMPTY_STRING) = saveUserTypeUseCase(type)

    fun saveReservationId(id: String) = saveReservationIdUseCase.invoke(id)

    fun saveBranchNumber(branchNumber: String = EMPTY_STRING) =
        saveBranchNumberUseCase.invoke(branchNumber)
    //endregion

    //region Remove
    fun clearScenarioCache() = removeScenarioUseCase()
    //endregion

    fun isLoginButton(username: String, password: String): Boolean =
        username.length >= minimumUserId && password.length >= minimumPass
}
