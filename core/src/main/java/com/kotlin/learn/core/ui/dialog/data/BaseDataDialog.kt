package com.kotlin.learn.core.ui.dialog.data

import androidx.annotation.DrawableRes
import com.kotlin.learn.core.util.Constants.EMPTY_STRING

class BaseDataDialog(
    val title : String,
    val content: String,
    val secondaryButtonShow: Boolean,
    val secondaryButtonText: String,
    val primaryButtonShow: Boolean,
    val primaryButtonText: String,
    @DrawableRes val primaryButtonIcon: Int? = null,
    @DrawableRes val secondaryButtonIcon: Int? = null,
    @DrawableRes val icon: Int? = null,
    val buttonWithIconShow: Boolean = false,
    val buttonWithIconText: String = EMPTY_STRING,
    val isIconShow: Boolean = false
)