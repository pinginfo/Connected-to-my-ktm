package com.ping.connectedtomyktm.bluetooth

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import com.ping.connectedtomyktm.entities.SendingObject
import java.lang.RuntimeException

class MyHandler(name: String): HandlerThread(name) {
    private lateinit var myHandler: Handler
    private lateinit var bluetooth: BluetoothManager

    fun getHandler(context: Context): Handler {
        this.start()
        this.bluetooth = BluetoothManager(context)

        this.myHandler = object : Handler(this.looper) {
            override fun handleMessage(message: Message) {
                if (bluetooth == null) { throw RuntimeException("Bluetooth manager is null") }
                if (message.what == OPEN) {
                    bluetooth.connect()
                } else if (message.what == CLOSE) {
                    bluetooth.close()
                } else if (message.what == SENDING) {
                    bluetooth.send((message.obj as SendingObject).getBytes(BluetoothManager.idMessage++))
                }
            }
        }

        return this.myHandler
    }


    companion object {
        const val OPEN = 0
        const val CLOSE = 1
        const val SENDING = 2
    }
}