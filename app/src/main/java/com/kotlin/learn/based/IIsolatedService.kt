package com.kotlin.learn.based

import android.os.IInterface
import android.os.Binder
import android.os.IBinder
import android.os.RemoteException
import android.os.Parcel
import com.kotlin.learn.core.common.util.Constants.ZERO

interface IIsolatedService: IInterface {
    /** Local-side IPC implementation stub class.  */

    abstract class Stub : Binder(), IIsolatedService {
        open fun stub() {
            attachInterface(this, descriptor)
        }

        override fun asBinder(): IBinder? {
            return this
        }

        @Throws(RemoteException::class)
        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            return when (code) {
                INTERFACE_TRANSACTION -> {
                    reply?.writeString(descriptor)
                    true
                }
                TRANSACTION_isMagiskPresent -> {
                    data.enforceInterface(descriptor)
                    val result = this.isMagiskPresent()
                    reply?.writeNoException()
                    reply?.writeInt(if (result) 1 else 0)
                    true
                }
                else -> {
                    super.onTransact(code, data, reply, flags)
                }
            }
        }

        companion object {
            private const val descriptor = "com.kotlin.learn.based.IIsolatedService"
            const val TRANSACTION_isMagiskPresent = FIRST_CALL_TRANSACTION + 0

            fun asInterface(obj: IBinder?): IIsolatedService? {
                if (obj == null) {
                    return null
                }
                val iin = obj.queryLocalInterface(descriptor)
                return if (iin != null && iin is IIsolatedService) iin else Proxy(obj)
            }

            private class Proxy(var mRemote: IBinder) : IIsolatedService {
                override fun asBinder(): IBinder {
                    return mRemote
                }

                // Check if Magisk is present
                @Throws(RemoteException::class)
                override fun isMagiskPresent(): Boolean {
                    val data = Parcel.obtain()
                    val reply = Parcel.obtain()
                    return try {
                        data.writeInterfaceToken(descriptor)
                        mRemote.transact(
                            TRANSACTION_isMagiskPresent,
                            data,
                            reply,
                            ZERO
                        )
                        reply.readException()
                        reply.readInt() != ZERO
                    } finally {
                        reply.recycle()
                        data.recycle()
                    }
                }
            }
        }
    }

    // Check if Magisk is present
    @Throws(RemoteException::class)
    fun isMagiskPresent(): Boolean
}