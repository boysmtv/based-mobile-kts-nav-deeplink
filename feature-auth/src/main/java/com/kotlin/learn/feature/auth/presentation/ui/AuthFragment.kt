/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.feature.auth.presentation.ui

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kotlin.learn.feature.auth.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    internal val viewModel by viewModels<AuthViewModel>()

    override val injectedFragment: Fragment = this

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAuthBinding =
        FragmentAuthBinding.inflate(inflater, container, false)

    override fun initView() {
        softInputMode(SOFT_INPUT_ADJUST_RESIZE)
        observeLogin()
        observeLoginCustomer()
        observeReservationState()
        observeLoad()
        setupContent()
    }

    private fun observeReservationState() = with(viewModel) {
        observeDataFlow(
            postReservationState,
            onLoad = { showHideProgress(true) },
            onError = { showHideProgress(false) }
        ) {
            showHideProgress(false)
            navigateToWaiting()
        }
    }

    private fun setupContent() = with(binding) {
        setSwitchButton()
        btnLoginAuth.disable()
        tilPasswordAuth.clearFocus()
        btnLoginAuth.setOnSingleClickListener { loginOnClickListener() }
        etUserNameAuth.addTextChangedListener {
            loginEnableData()
            if (tilUserNameAuth.isErrorEnabled) tilUserNameAuth.isErrorEnabled = false
        }
        etPasswordAuth.addTextChangedListener {
            loginEnableData()
            if (tilPasswordAuth.isErrorEnabled) tilPasswordAuth.isErrorEnabled = false
        }
    }

    private fun setSwitchButton() = with(binding.swButton) {
        setTextSelectFirst(translate(R.string.auth_segment_label_login_gb))
        setTextSelectSecond(translate(R.string.auth_segment_label_login_customer))
        setSwitchButtonListenerInterface(object : MandiriSwitchButtonListener {
            override fun onClickButtonFirst() {
                viewModel.userType = GB.value
            }

            override fun onClickButtonSecond() {
                viewModel.userType = CUSTOMER.value
            }

        })
    }

    private fun observeLoad() = with(viewModel) {
        loginData.observe(viewLifecycleOwner, loginDataObserver)
        loginCustomerData.observe(viewLifecycleOwner, loginCustomerDataObserver)
        loading.observe(viewLifecycleOwner, loadingLiveDataObserver)
        loadError.observe(viewLifecycleOwner, errorLiveDataObserver)
        loadErrorMessage.observe(viewLifecycleOwner, errorMessageObserver)
    }

    private val loginDataObserver = Observer<AuthResponse> {
        Handler().postDelayed({
            viewModel.nav.navigateToDashboard(EMPTY_STRING)
        }, DELAY)
    }

    private val loginCustomerDataObserver = Observer<AuthResponse> {
        Handler().postDelayed({
            viewModel.nav.navigateToCustomerLanding()
        }, DELAY)
    }

    private val loadingLiveDataObserver = Observer<Boolean> { isLoading ->
        showHideProgress(isLoading)
        binding.tilPasswordAuth.isEnabled = !isLoading
        binding.tilUserNameAuth.isEnabled = !isLoading
        binding.btnLoginAuth.isEnabled = !isLoading
    }

    private val errorLiveDataObserver = Observer<Boolean> { isError ->
        if (isError == true) {
            val loginErrorWrong = viewModel.errorMessage

            Toast(context).showCustomToastFailed(
                loginErrorWrong,
                requireActivity()
            )

            binding.tilUserNameAuth.apply {
                isErrorEnabled = false
                setErrorIconDrawable(R.drawable.ic_tailing)
                error = loginErrorWrong
            }

            binding.tilPasswordAuth.apply {
                setErrorIconDrawable(R.drawable.ic_tailing)
                error = loginErrorWrong
            }
        }
    }

    private val errorMessageObserver = Observer<String> { viewModel.errorMessage = it }

    private fun observeLogin() = observeDataFlow(
        viewModel.login,
        onLoad = { showHideProgress(true) },
        onError = { observeOnErrorLogin(it) },
        onSuccess = {
            viewModel.dataAuthResponse = it
            viewModel.clearScenarioSubmit()
            observeOnSuccessLogin()
        }
    )

    private fun observeLoginCustomer() = observeDataFlow(
        viewModel.loginCustomer,
        onLoad = { showHideProgress(true) },
        onError = { observeOnErrorLogin(it) },
        onSuccess = {
            viewModel.dataCustomerAuthResponse = it
            viewModel.clearScenarioSubmit()
            observeOnSuccessLoginCustomer(it)
        }
    )

    private fun observeOnErrorLogin(it: Result<AuthResponse>) {
        showHideProgress(false)
        when {
            it.httpStatus == CODE_409 && it.code in setOf(
                CODE_40011, CODE_40323, CODE_40014, CODE_40017, CODE_40047,
                CODE_40018, CODE_40288, CODE_40221, CODE_40081, CODE_40036,
                CODE_40342, CODE_40210, CODE_40347, CODE_40241, CODE_40386,
                CODE_40414, CODE_40564, CODE_40529
            ) -> showDialogError(
                icon = ic_error,
                title = it.title.toString(),
                message = it.message.toString()
            )
            it.httpStatus == CODE_409 && it.code == CODE_40242 -> showDialogError(
                icon = ic_error_multiple_device,
                title = it.title.toString(),
                message = it.message.toString()
            )
            else -> errorHandler(it.code, it.title, it.message, it.httpStatus)
        }
    }

    private fun observeOnSuccessLogin() = with(viewModel) {
        showHideProgress(false)
        setIdleTimeByUserType(
            loadAppConfigUseCase(PREF_KEY_APP_IDLE_TIME)?.parameterValue.orReplaceWith(
                DEFAULT_APPLICATION_IDLE_TIME
            ).toLong()
        )
        clearScenarioSubmitUseCase()
        dataAuthResponse.accessToken?.let { it1 -> validateNeedOtp(it1) }
            ?: navigateToOtp()
        (activity as? BaseActivity<*>)?.startSessionIdleFromFragment()
    }

    private fun observeOnSuccessLoginCustomer(authResponse: AuthResponse) = with(viewModel) {
        setIdleTimeByUserType(
            loadAppConfigUseCase(PREF_KEY_APP_IDLE_TIME_CUSTOMER)?.parameterValue.orReplaceWith(
                DEFAULT_APPLICATION_IDLE_TIME
            ).toLong()
        )
        saveLastSelectedTransactionUseCase(ZERO)
        showHideProgress(false)
        saveUserType(userType)
        saveDialogStateUseCase(false)
        authResponse.currentStateResponse?.reservationId?.let { id -> saveReservationId(id) }
        authResponse.branchNumber?.let { branchNumber -> saveBranchNumber(branchNumber) }
        ResumeState.apply {
            if (authResponse.currentStateResponse != null)
                setLastState(authResponse.currentStateResponse?.currentState.toString())
            else setLastState(EMPTY_STRING)

            if (authResponse.currentStateResponse != null) setStateIsRetry(true)
            else setStateIsRetry(false)
        }
        if (authResponse.currentStateResponse != null) navigateBasedOnCurrentState(authResponse.currentStateResponse)
        else viewModel.nav.navigateToCustomerLanding()
        (activity as? BaseActivity<*>)?.startSessionIdleFromFragment()
    }

    private fun navigateBasedOnCurrentState(currentStateResponse: CurrentStateResponse?) = with(viewModel) {
        when {
            currentStateResponse?.isWaitingConfirmationSupervisor == true -> safeCallJob.run {
                postReservationConfirmationSupervisor()
            }
            currentStateResponse?.currentState == DEPOSIT_SUBMIT_WAIT_PAGE ||
                    currentStateResponse?.isWaitingConfirmation == true -> safeCallJob.run {
                postReservationConfirmation()
            }
            currentStateResponse?.currentState == TRANSACTION_OVERVIEW_WAIT_PAGE -> {
                nav.navigateToTransaction(TRANSACTION_OVERVIEW_PAGE_CUSTOMER)
            }
            currentStateResponse?.currentState == TRANSACTION_CONFIRMATION_PAGE -> {
                nav.navigateToTransaction(CUSTOMER_TRANSACTION_CONFIRMATION)
            }
            currentStateResponse?.currentState == TRANSACTION_CONFIRMATION_OLD_WAIT_PAGE -> {
                nav.navigateToWaiting(TRANSACTION_CONFIRMATION_OLD_WAIT_PAGE)
            }
            currentStateResponse?.currentState in setOf(
                CONFIRM_FINISH_TRANSACTION_PAGE,
                REVIEW_PAGE
            ) -> nav.navigateToRating()
            currentStateResponse?.currentState == RECEIPT_WAIT_PAGE -> nav.navigateToTransaction(RECEIPT_WAIT_PAGE)
            else -> viewModel.nav.navigateToCustomerLanding()
        }
    }

    private fun validateNeedOtp(token: String) {
        if (token.isEmpty()) navigateToOtp()
        else viewModel.nav.navigateToDashboard(EMPTY_STRING)
    }

    private fun navigateToWaiting() = with(viewModel) {
        when {
            dataCustomerAuthResponse.currentStateResponse?.isWaitingConfirmation == true ->
                nav.navigateToWaiting(SCENARIO_TRANSACTION_SUBMIT_DEPOSIT)
            dataCustomerAuthResponse.currentStateResponse?.isWaitingConfirmationSupervisor == true ->
                nav.navigateToWaiting(ADVERTISEMENT_WAIT_SUPERVISOR_ALLOCATION_PAGE)
            else -> Unit
        }
    }

    private fun loginOnClickListener() = with(binding) {
        viewModel.saveInsuficientBalanceDialogUseCase(false)
        viewModel.userId = etUserNameAuth.text.toString()
        viewModel.password = etPasswordAuth.text.toString()

        if (viewModel.userType == GB.value) safeCallJob.run { viewModel.getLogin() }
        else safeCallJob.run { viewModel.getLoginCustomer() }
        viewModel.clearScenarioCache()
        viewModel.saveGbLoginType(LoginType.DUAL_SCREEN.name)
    }

    private fun loginEnableData() = with(binding) {
        btnLoginAuth.isEnabled = viewModel.isLoginButton(
            etUserNameAuth.text.toString(),
            etPasswordAuth.text.toString()
        )
    }

    private fun navigateToOtp() = with(viewModel) {
        nav.navigateToNumericAuthorization(AUTH)
    }

    private fun showDialogError(
        icon: Int?,
        title: String?,
        message: String?
    ) {
        val content = BaseDataDialog(
            title.toString(),
            message.toString(),
            secondaryButtonShow = false,
            primaryButtonShow = true,
            primaryButtonText = BUTTON_BACK,
            secondaryButtonText = EMPTY_STRING,
            icon = icon,
            isIconShow = true
        )

        showDialogWithActionButton(
            dataToDialog = content,
            actionClickPrimary = {},
            actionClickSecondary = {},
            AuthFragment::class.simpleName.orEmpty()
        )
    }

    fun setIdleTimeByUserType(time: Long) {
        (activity as? BaseActivity<*>)?.setIdleTimeByUserType(time)
    }
}


