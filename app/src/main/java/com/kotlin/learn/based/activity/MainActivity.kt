package com.kotlin.learn.based.activity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.appdynamics.eumagent.runtime.AgentConfiguration
import com.kotlin.learn.based.R
import com.kotlin.learn.based.databinding.ActivityMainBinding
import com.kotlin.learn.based.viewmodel.MainActivityViewModel
import com.kotlin.learn.core.common.util.Constants.DEFAULT_APPLICATION_IDLE_TIME
import com.kotlin.learn.core.navigation.NavControllerBinder
import com.kotlin.learn.core.base.BaseActivity
import com.kotlin.learn.core.ui.widget.dialog.ProgressBarDialog
import dagger.hilt.android.AndroidEntryPoint
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import com.kotlin.learn.core.common.entity.Result.Status.ERROR
import com.kotlin.learn.core.common.entity.Result.Status.LOADING
import com.kotlin.learn.core.common.entity.Result.Status.SUCCESS
import com.kotlin.learn.core.common.entity.Result
import com.kotlin.learn.core.util.Constants.EMPTY_STRING
import javax.inject.Inject
import BuildConfig as AppBuildConfig

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var binder: NavControllerBinder

    @Inject
    lateinit var agentConfiguration: AgentConfiguration
    lateinit var menu : Menu

    internal lateinit var systemDialogsReceiver: BroadcastReceiver

    private lateinit var appBarConfiguration: AppBarConfiguration

    internal val viewModel: MainActivityViewModel by viewModels()

    internal var applicationIdleTime = DEFAULT_APPLICATION_IDLE_TIME.toLong()

    internal val dialog by lazy { ProgressBarDialog() }

    internal var activeFragment = R.id.fragment_auth

    internal val nonAppBarFragments = setOf(
        R.id.fragment_numeric_authorization_verification_landing,
        R.id.fragment_numeric_authorization_entry_point,
        R.id.fragment_numeric_authorization_otp,
        R.id.fragment_numeric_authorization_pin,
        R.id.fragment_waiting_transition,
        R.id.fragment_receipt,
        R.id.fragment_waiting_customer_landing,
        R.id.fragment_transaction_entry_point,
        R.id.fragment_edit_transaction,
        R.id.fragment_choose_edit_transaction_account,
        R.id.fragment_recapture_camera_ktp,
        R.id.fragment_customer_ads_receipt,
        R.id.fragment_customer_screen_status,
        R.id.fragment_customer_rating_service,
        R.id.fragment_transaction_gb_confirmation,
        R.id.fragment_customer_waiting_supervisor_confirmation,
        R.id.fragment_transaction_confirmation_cs,
        R.id.fragment_edit_script,
        R.id.fragment_numeric_authorization_selected_card,
        R.id.fragment_rating_finish,
        R.id.fragment_numeric_authorization_landing_pin,
        R.id.fragment_overview_transaction_customer,
        R.id.fragment_choose_phone_number,
        R.id.fragment_overview_transaction_customer,
        R.id.fragment_numeric_authorization_auth,
        R.id.fragment_numeric_authorization_auth,
        R.id.fragment_debit_card_maintenance_guideline_send_debit_card,
        R.id.fragment_transaction_change_debit_card,
        R.id.fragment_numeric_authorization_verification_landing,
        R.id.fragment_confirmation_debit_card_maintenance,
        R.id.fragment_transaction_preview_skp,
        R.id.fragment_camera_document,
        R.id.fragment_customer_data_maintenance_update_data,
        R.id.fragment_account_maintenance_edit_account_activation,
        R.id.fragment_account_maintenance_edit_account_complaint
    )

    internal val nonUserTypeFragments = setOf(
        R.id.fragment_splash,
        R.id.fragment_numeric_authorization_auth,
        R.id.fragment_auth,
        R.id.fragment_numeric_authorization_entry_point
    )

    private val stateObserver = Observer<Result<ReservationState>> {
        when (it.status) {
            SUCCESS -> onSuccessSse(it)
            LOADING -> responseCallback(it)
            ERROR -> onErrorSse(it.httpStatus)
        }
    }

    internal val navController by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()
    }

    override val eventListener by lazy { (application as BdsApplication).eventListener }

    internal var handler: Handler? = Handler(Looper.getMainLooper())

    private var scenarioOnBack: String? = EMPTY_STRING

    internal val onUserIdle = Runnable {
        lifecycleScope.launchWhenResumed {
            eventListener.isSessionIdle.set(true)
            showDialogIdleOrNavigate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        eventListener.bindActivity(this)
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        eventListener.common.setCanRecallAPI(true)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setSupportActionBar(binding.toolbar)
        registerSystemDialogsReceiver()
        if (BuildConfig.IS_ENABLE_APPD) Instrumentation.start(agentConfiguration)
        startSession()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_graph_auth,
                R.id.nav_graph_dashboard,
                R.id.nav_graph_transaction,
                R.id.nav_graph_supervisor,
                R.id.nav_graph_customer_information,
                R.id.nav_graph_numeric_authorization,
                R.id.nav_graph_rating,
                R.id.nav_graph_waiting,
                R.id.nav_graph_upload_document,
                R.id.fragment_recapture_verification_ktp,
                R.id.nav_graph_debit_card_maintenance
            )
        )
        navController?.let { setupActionBarWithNavController(it, appBarConfiguration) }
        observeBalance()
        observeLogout()
        observeLogoutDualScreen()
        observeForceLogout()
        observerRefreshToken()
        eventListener()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) binding.hideScreenBlocker()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        setIdleSavedInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        setIdleSavedInstanceState(outPersistentState)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setIdleRestoreInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?,
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        setIdleRestoreInstanceState(persistentState)
    }

    override fun onResume() {
        super.onResume()
        navController?.let { binder.bind(it) }
        viewModel.isAppInForeground.set(true)
    }

    override fun onPause() {
        super.onPause()
        viewModel.isAppInForeground.set(false)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        startSession()
    }

    override fun onStart() {
        binding.hideScreenBlocker()
        if (AppBuildConfig.IS_ENABLE_ISOLATED_SERVICE) {
            val intent = Intent(this, IsolatedService::class.java)
            applicationContext.bindService(
                intent,
                viewModel.mIsolatedServiceConnection,
                BIND_AUTO_CREATE
            )
        }
        super.onStart()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { ViewPumpContextWrapper.wrap(it) })
    }

    override fun onBackPressed() = Unit

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.greeter_menu, menu)
        return viewModel.getUserType() == GB.value || viewModel.getUserType() == GB_CS.value
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                showLogoutDialog()
                return true
            }
        }
        return false
    }

    override fun instantiateDataBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun observeState(response: (Result<ReservationState>) -> Unit) {
        responseCallback = response
        viewModel.reservationState.observe(this, stateObserver)
    }

    override fun invokeState(
        deviceType: String,
        currentState: String,
        reservationId: String,
    ) {
        lifecycleSafeScope {
            launchSse {
                viewModel.getReservationState(mutableMapOf<String, String>().apply {
                    put(KEY_DEVICE_TYPE, deviceType)
                    put(KEY_CURRENT_STATE, currentState)
                    if (reservationId.isNotEmpty()) put(KEY_RESERVATION_ID, reservationId)
                    viewModel.stateParam = this
                })
            }
        }
    }

    override fun backToLogin() {
        viewModel.removeContextCacheUseCase
        supportActionBar?.hide()
        navController?.popBackStack(R.id.fragment_auth, false)
    }

    override fun backToSplash() {
        viewModel.removeContextCacheUseCase
        supportActionBar?.hide()
        navigateToSplash()
    }

    override fun onSupportNavigateUp(): Boolean {
        when (activeFragment) {
            R.id.fragment_customer_verification_detail -> navController?.popBackStack()
            R.id.fragment_customer_portfolio_detail -> navController?.popBackStack()
            R.id.fragment_customer_detail -> navController?.popBackStack()
            R.id.fragment_customer_information_history ->
                navController?.popBackStack(R.id.nav_graph_customer_information, true)
            R.id.fragment_overview_transaction -> navController?.popBackStack(
                R.id.nav_graph_transaction,
                true
            )
            R.id.fragment_numeric_authorization_otp -> {
                if (scenarioOnBack.isNullOrEmpty()) {
                    navController?.popBackStack()
                } else {
                    if (scenarioOnBack == SUPERVISOR_CUSTOMER_DATA_CONFIRMATION)
                        navController?.navigate(
                            R.id.nav_graph_supervisor, bundleOf(
                                ARGS_KEY_SCENARIO to scenarioOnBack
                            )
                        )
                    else navController?.navigate(
                        R.id.nav_graph_transaction, bundleOf(
                            ARGS_KEY_SCENARIO to scenarioOnBack
                        )
                    )
                }
            }
            R.id.fragment_recapture_camera_ktp -> navController?.popBackStack()
            R.id.fragment_transaction_preview_skp -> navController?.popBackStack()
        }
        return true
    }

    override fun setIdleTimeByUserType(time: Long) {
        applicationIdleTime = time
    }

    override fun startSessionIdleFromFragment() {
        startSession()
    }

    override fun showTopBar(isShow: Boolean) {
        if (isShow) supportActionBar?.show()
        else supportActionBar?.hide()
    }

    override fun setTopBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onBackNavigation(scenario: String?) {
        scenarioOnBack = scenario
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun showBackButton(isShow: Boolean) {
        if (isShow) supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.navigationIcon = getDrawable(ic_back_button_small)
    }

    private fun observeBalance() = observeDataFlow(
        viewModel.balance,
        onLoad = { showLoading() },
        onError = {
            when (it.httpStatus) {
                CODE_500 -> binding.onLoadBalanceError(getString(R.string.dialog_input_connection_error))
                CODE_404 -> binding.onLoadBalanceError(getString(R.string.dialog_detail_data_not_found))
                else -> binding.onLoadBalanceError(it.message.toString())
            }
            binding.btnRefreshTopBar.clearAnimation()
        }
    ) {
        it.balance?.let { balance ->
            binding.btnRefreshTopBar.showIfAllowed(CASH_DETAILS)
            binding.tvTotalRemainingCashTopBar.showIfAllowed(CASH_DETAILS)
            binding.tvTotalRemainingCashTopBar.text = CurrencyUtil.showCurrency(balance, it.currency, true)
        }
        binding.apply {
            btnRefreshTopBar.clearAnimation()
            tvRemainingCashNameTopBar.showIfAllowed(CASH_DETAILS)
            tvMessageErrorTopBar.hide()
            tvMessageErrorTopBar.text = EMPTY_STRING
        }
    }

    private fun observeLogoutDualScreen() = observeDataFlow(
        viewModel.logoutDualScreen,
        onLoad = {
            showLoading()
            showHideProgress(true)
        },
        onError = { onErrorLogout(result = it, isLogoutDualScreen = true) }
    ) {
        showHideProgress(false)
        binding.btnRefreshTopBar.clearAnimation()
        backToSplash()
    }

    companion object {
        init {
            System.loadLibrary(NATIVE_LIB)
        }
    }
}