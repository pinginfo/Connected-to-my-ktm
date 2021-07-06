package com.ping.connectedtomyktm.tools

import android.content.Context
import android.content.SharedPreferences

object ContextObject {
    private var context: Context? = null

    fun setContext(context: Context) {
        this.context = context.applicationContext
    }

    fun getContext(): Context? {
        return this.context
    }

    fun getSharedPreferences(): SharedPreferences? {
        return this.context?.getSharedPreferences("Settings", Context.MODE_PRIVATE)
    }
}