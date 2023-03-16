package com.kotlin.learn.based

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.kotlin.learn.core.common.util.Constants.ZERO
import com.kotlin.learn.based.util.Constants.PROC_MOUNTS
import com.kotlin.learn.based.util.Constants.SBIN_CORE_IMG
import com.kotlin.learn.based.util.Constants.SBIN_CORE_MAGISK_DB
import com.kotlin.learn.based.util.Constants.SBIN_CORE_MIRROR
import com.kotlin.learn.based.util.Constants.SBIN_MAGISK
import java.io.File
import java.io.FileInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.IOException

class IsolatedService : Service() {

    private val blackListedMountPaths = arrayOf(
        SBIN_MAGISK, SBIN_CORE_MIRROR, SBIN_CORE_IMG, SBIN_CORE_MAGISK_DB
    )

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    val mBinder: IIsolatedService.Stub = object : IIsolatedService.Stub() {
        override fun isMagiskPresent(): Boolean {
            var isMagiskPresent = false
            val pid = android.os.Process.myPid()
            val cwd = String.format(PROC_MOUNTS, pid)
            val file = File(cwd)
            try {
                val fis = FileInputStream(file)
                val reader = BufferedReader(InputStreamReader(fis))
                val count = scanFile(reader)

                reader.close()
                fis.close()

                isMagiskPresent = count > ZERO || Native.isMagiskPresentNative()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return isMagiskPresent
        }

        fun scanFile(reader: BufferedReader): Int {
            var count = ZERO
            var str: String
            while (reader.readLine().also { str = it } != null) {
                blackListedMountPaths.forEach { path -> if (str.contains(path)) count++ }
            }
            return count
        }
    }
}