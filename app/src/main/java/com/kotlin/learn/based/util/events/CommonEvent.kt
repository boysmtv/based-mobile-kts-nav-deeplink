package com.kotlin.learn.based.util.events

import com.kotlin.learn.based.activity.MainActivity
import com.kotlin.learn.based.R
import com.kotlin.learn.core.common.presentation.widget.CommonInvokeListener
import com.kotlin.learn.core.common.presentation.widget.CommonListener
import org.threeten.bp.Instant

class CommonEvent(private var activity: MainActivity) : CommonInvokeListener {

    override fun continueProcess() = with(activity) {
        when {
            activeFragment in setOf(
                R.id.fragment_auth, R.id.fragment_splash) -> true
            Instant.now().epochSecond > viewModel.loadRefreshTokenTimestampUseCase() -> {
                getToken()
                false
            }
            else -> true
        }
    }

    override fun invalidRefreshToken() {
        with(activity) {
            viewModel.saveRefreshToken()
        }
    }

    override fun setRefreshTokenResultListener(listener: CommonListener) {
        activity.listener = listener
    }

    override fun setCanRecallAPI(value: Boolean) {
        activity.viewModel.setCanRecallAPI(value)
    }

    override fun setNullListener() {
        activity.listener = null
    }
}