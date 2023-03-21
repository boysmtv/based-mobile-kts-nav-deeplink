package com.kotlin.learn.based.util.presentation

import android.app.Activity
import com.kotlin.learn.based.activity.MainActivity
import com.kotlin.learn.based.util.events.CommonEvent
import com.kotlin.learn.core.common.presentation.events.EventListener
import com.kotlin.learn.core.common.presentation.widget.CommonInvokeListener
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LearnEvent @Inject constructor() : EventListener {
    private lateinit var activity: MainActivity

    override lateinit var common: CommonInvokeListener

    override val isSessionIdle = AtomicBoolean(false)

    override fun bindActivity(activity: Activity) {
        this.activity = activity as MainActivity
        this.common = CommonEvent(activity)
    }
}