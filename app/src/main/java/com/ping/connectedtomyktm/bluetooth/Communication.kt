package com.ping.connectedtomyktm.bluetooth

import android.content.Context
import android.os.Handler
import android.os.Message

import com.ping.connectedtomyktm.MainActivity
import com.ping.connectedtomyktm.entities.SendingObject

object Communication {
    private lateinit var myHandler: Handler

    fun setContext(context: Context) {
        this.myHandler = MyHandler("MyAwesomeHandle").getHandler(context)
    }

    fun open() {
        val message = Message()
        message.what = MyHandler.OPEN
        this.myHandler.removeMessages(MyHandler.CLOSE)
        this.myHandler.sendMessage(message)
    }

    fun send(obj: SendingObject) {
        MainActivity.dataModel.select(obj)
        val message = Message()
        message.what = MyHandler.SENDING
        message.obj = obj
        this.myHandler.removeMessages(MyHandler.OPEN)
        this.myHandler.removeMessages(MyHandler.SENDING)
        this.myHandler.sendMessage(message)
    }

    fun close() {
        val message = Message()
        message.what = MyHandler.CLOSE
        this.myHandler.removeMessages(MyHandler.OPEN)
        this.myHandler.removeMessages(MyHandler.SENDING)
        this.myHandler.sendMessage(message)
    }
}