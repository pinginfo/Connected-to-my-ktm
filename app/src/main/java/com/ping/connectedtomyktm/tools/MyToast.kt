package com.ping.connectedtomyktm.tools

import android.content.Context
import android.widget.Toast

object MyToast {
    @Volatile
    var context: Context? = null
    fun print(msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}