/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.kotlin.learn.core.ui.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebResourceRequest
import android.webkit.WebResourceError
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.webkit.SslErrorHandler
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.learn.core.R
import com.kotlin.learn.core.ui.listener.SingleClickListener
import com.kotlin.learn.core.ui.listener.SingleClickListener.Companion.THROTTLE_INTERVAL
import com.kotlin.learn.core.util.Constants
import com.kotlin.learn.core.util.Constants.ALPHA.OPACITY_NORMAL
import com.kotlin.learn.core.util.Constants.ALPHA.OPACITY_ZERO
import com.kotlin.learn.core.util.Constants.CountDown.ONE_THOUSAND
import com.kotlin.learn.core.util.Constants.FIVE_HUNDRED

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.animateShow() {
    this.apply {
        alpha = OPACITY_ZERO
        visibility = View.VISIBLE
    }
    this.animate()
        .alpha(OPACITY_NORMAL)
        .setDuration(ONE_THOUSAND.toLong())
        .setListener(null)
}

fun View.animateHide() {
    this.animate()
        .alpha(OPACITY_ZERO)
        .setDuration(FIVE_HUNDRED.toLong())
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                this@animateHide.visibility = View.GONE
            }
        })
}

fun View.animateSlide(translationValue: Float, view: Int) {
    this.animate()
        .translationY(translationValue)
        .setDuration(FIVE_HUNDRED.toLong())
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                this@animateSlide.visibility = view
            }
        })
}

fun View.animateSlideHorizontal(translationValue: Float, view: Int) {
    this.animate()
        .translationX(translationValue)
        .setDuration(FIVE_HUNDRED.toLong())
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                this@animateSlideHorizontal.visibility = view
            }
        })
}

fun CheckBox.checked() {
    this.isChecked = true
}

fun CheckBox.unChecked() {
    this.isChecked = false
}

@Deprecated(
    message = "Use Android-KTX built-in function instead",
    replaceWith = ReplaceWith("androidx.core.view.isVisible")
)
fun View.showOrHide(condition: Boolean) {
    if (condition) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@Deprecated(
    message = "Use Android-KTX built-in function instead",
    replaceWith = ReplaceWith("androidx.core.view.isInvisible")
)
fun View.showOrInvisible(condition: Boolean) {
    if (condition) this.visibility = View.VISIBLE
    else this.visibility = View.INVISIBLE
}

/**
 * Context is needed here to obtain the correct value that has been configured for the current screen.
 * You can obtain the same result by using Resources.getSystem().displayMetrics.density though you cannot be 100% sure
 * that the result would be consistent from time to time.
 *
 * Also, Google define the same calculation to convert dp unit to pixel in the link below
 * https://developer.android.com/training/multiscreen/screendensities#dips-pels
 */
fun Float.convertDpToPx(ctx: Context): Float =
    this * (ctx.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT.toFloat())

/**
 * This is the reverse of Float.convertDpToPx
 */
fun Float.convertPxToDp(ctx: Context): Float =
    this / (ctx.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT.toFloat())

/**
 * The same condition applies here. It will be adjusted for both the screen density and the user's preference.
 *
 */
fun Float.convertSpToPx(ctx: Context): Float = this * ctx.resources.displayMetrics.scaledDensity

/**
 * This is the reverse of Float.convertSpToPx
 */
fun Float.convertPxToSp(ctx: Context): Float = this / ctx.resources.displayMetrics.scaledDensity

/**
 * Set TextView visibility to [View.VISIBLE] when the input text is not empty, otherwise visibility will be [View.GONE]
 */
fun TextView.setTextHideIfEmpty(content: CharSequence?, bufferType: TextView.BufferType? = null) {
    if (content.isNullOrBlank()) {
        visibility = View.GONE
        text = ""
    } else {
        visibility = View.VISIBLE
        if (bufferType != null) setText(content, bufferType)
        else text = content
    }
}

/**
 * Converts Int value to Px(Int)
 */
fun Int.convertIntToPx(ctx: Context): Int =
    (this * ctx.resources.displayMetrics.density).toInt()

fun View.showForceKeyboard() {
    val inputMethodManager =
        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

fun View.hideKeyboard() {
    val inputMethodManager =
        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (inputMethodManager.isAcceptingText) inputMethodManager.hideSoftInputFromWindow(
        windowToken,
        0
    )
}

fun withRecycle(attributes: TypedArray, consumeAttributes: TypedArray.() -> Unit) {
    try {
        consumeAttributes(attributes)
    } finally {
        attributes.recycle()
    }
}

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    ContextCompat.getDrawable(context, drawableRes)?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}

@SuppressLint("SetJavaScriptEnabled")
fun WebView.setupWebView(
    onUrlIntercepted: ((uri: Uri?) -> Boolean)? = null,
    onLoadError: ((WebResourceRequest?, WebResourceError?) -> Unit)? = null,
    onLoadCompleted: () -> Unit = {}
) {
    val chromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress == Constants.Tnc.WEB_MAX_PROGRESS) onLoadCompleted()
        }
    }

    webViewClient = object : WebViewClient() {
        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            handler?.proceed()
        }

        @RequiresApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) =
            onUrlIntercepted?.let { it(request?.url) } ?: false

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?) =
            onUrlIntercepted?.let {
                it(Uri.parse(url))
            } ?: false

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            onLoadError?.invoke(request, error)
        }
    }

    webChromeClient = chromeClient
    settings.javaScriptEnabled = true
    settings.loadsImagesAutomatically = true
    isHorizontalScrollBarEnabled = false
    isScrollbarFadingEnabled = false
    isVerticalScrollBarEnabled = false
}

fun View.getString(@StringRes stringId: Int): String = context.getString(stringId)

fun View.setOnSingleClickListener(
    throttleInterval: Long = THROTTLE_INTERVAL,
    click: View.OnClickListener?
) {
    click?.let { setOnSingleClick(throttleInterval, click) } ?: setOnClickListener(null)
}

fun View.setOnSingleClickListener(throttleInterval: Long, listener: (View) -> Unit) {
    this.setOnSingleClickListener(throttleInterval, View.OnClickListener { v -> listener(v) })
}

fun View.setOnSingleClick(throttleInterval: Long, click: View.OnClickListener) =
    setOnClickListener(SingleClickListener(throttleInterval, click))


fun FrameLayout.alreadyScrolled(listener: (Boolean) -> Unit) {
    post { listener(!canScrollVertically(Constants.ONE)) }
    setOnScrollChangeListener { _, _, _, _, _ ->
        canScrollVertically(Constants.ONE).run { if (!this) listener(true) }
    }
}