/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.kotlin.learn.core.common.presentation.events.EventListener
import com.kotlin.learn.core.common.presentation.widget.CommonListener
import com.kotlin.learn.core.common.util.Constants.ZERO
import com.kotlin.learn.core.data.CoroutineSafeJob
import com.kotlin.learn.core.ui.dialog.DialogGeneralError
import com.kotlin.learn.core.ui.dialog.DialogLogout
import com.kotlin.learn.core.ui.dialog.DialogWithAction
import com.kotlin.learn.core.ui.dialog.data.BaseDataDialog
import com.kotlin.learn.core.ui.dialog.data.BaseDataDialogGeneral

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    var switchToSelfService :(() -> Unit)? = null
    var switchToDualTablet :(() -> Unit)? = null
    var dialogGeneralError: DialogGeneralError? = null

    var initCustomOnLoadState: (() -> Unit)? = null
    var initCustomOnSuccessState: (() -> Unit)? = null
    var initCustomOnErrorState: (() -> Unit)? = null

    lateinit var safeCallJob: CoroutineSafeJob
    var listener: CommonListener? = null
    var retryCount = ZERO
    var retryReinvoke = ZERO

    abstract val eventListener: EventListener

    lateinit var binding: T
    protected abstract fun instantiateDataBinding(): T
    protected abstract fun initView()
    abstract fun backToLogin()
    abstract fun backToSplash()
    abstract fun setIdleTimeByUserType(time: Long)
    abstract fun startSessionIdleFromFragment()
    abstract fun showBackButton(isShow: Boolean)
    abstract fun showTopBar(isShow: Boolean)
    abstract fun setTopBarTitle(title: String)
    abstract fun onBackNavigation(scenario: String?)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = instantiateDataBinding()
        setContentView(binding.root)
        initCoroutineSafeJob()
        initView()
    }

    private fun initCoroutineSafeJob(
        onErrorAction: (() -> Unit)? = null,
        isSessionIdle: Boolean = false,
        onTimeoutAction: (() -> Unit)? = null,
        onTimeoutCompletion: (() -> Unit)? = null,
        delayAfterInMillis: Long? = null
    ) {
        safeCallJob = CoroutineSafeJob(this,
            onErrorAction = { onErrorAction?.let { it() } },
            isSessionIdle = { isSessionIdle },
            onTimeoutAction = { onTimeoutAction?.let { this.runOnUiThread { it() } } },
            onTimeoutCompletion = { onTimeoutCompletion?.let { this.runOnUiThread { it() } } }
        ).apply {
            continueProcess = { this@BaseActivity.eventListener.common.continueProcess() }
        }

        delayAfterInMillis?.let { safeCallJob.addDelay(it) }
    }

    fun showDialogWithActionButton(
        dataToDialog: BaseDataDialog,
        actionClickPrimary: () -> Unit,
        actionClickSecondary: () -> Unit,
        tag: String
    ) {
        DialogWithAction(
            onClickButtonPrimary = { actionClickPrimary() },
            onClickButtonSecondary = { actionClickSecondary() }
        ).apply { data = dataToDialog }.show(supportFragmentManager, tag)
    }

    fun showDialogWithThreeActionButton(
        dataToDialog: BaseDataDialog,
        actionClickPrimary: () -> Unit,
        actionClickSecondary: () -> Unit,
        tag: String
    ) {
        DialogLogout(
            onClickButtonPrimary = { actionClickPrimary() },
            onClickButtonSecondary = { actionClickSecondary() },
            onClickButtonBack = { },
            data = dataToDialog
        ).show(supportFragmentManager, tag)
    }

    open fun errorHandler(code: String?, title: String? = null, message: String? = null) = Unit

    fun showSseGeneralError(
        data: BaseDataDialogGeneral,
        actionClick: () -> Unit,
        actionClickSecondary: () -> Unit
    ) {
        dialogGeneralError = DialogGeneralError(
            data,
            actionClick,
            actionClickSecondary,
            onDismissDialogGeneralError()
        )
        dialogGeneralError = DialogGeneralError(data, actionClick, actionClickSecondary)
        dialogGeneralError?.show(supportFragmentManager, DialogGeneralError::class.simpleName)
    }

    fun onDismissDialogGeneralError(): () -> Unit = {
        dialogGeneralError?.dismiss()
        dialogGeneralError = null
    }
}