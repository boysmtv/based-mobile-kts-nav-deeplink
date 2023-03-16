package com.kotlin.learn.based

import com.kotlin.learn.based.util.Constants.NATIVE_LIB

class Native {
    companion object {
        init {
            System.loadLibrary(NATIVE_LIB)
        }
        external fun isMagiskPresentNative(): Boolean
    }
}