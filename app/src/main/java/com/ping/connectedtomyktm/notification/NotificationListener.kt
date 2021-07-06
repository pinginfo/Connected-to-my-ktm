package com.ping.connectedtomyktm.notification

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.ping.connectedtomyktm.bluetooth.Communication
import com.ping.connectedtomyktm.tools.ContextObject

class NotificationListener: NotificationListenerService() {
    private var communication: Communication

    override fun onNotificationPosted(statusBarNotification: StatusBarNotification) {
        val mod = modules[statusBarNotification.packageName]
        if (mod != null && packageIsEnable(statusBarNotification.packageName)) {
            this.communication.send(mod.getDataPosted(statusBarNotification))
        }
    }

    override fun onNotificationRemoved(statusBarNotification: StatusBarNotification) {
        val mod = modules[statusBarNotification.packageName]
        if (mod != null && packageIsEnable(statusBarNotification.packageName)) {
            this.communication.send(mod.getDataRemoved(statusBarNotification))
        }
    }

    private fun packageIsEnable(packageName: String): Boolean {
        return ContextObject.getSharedPreferences()?.getBoolean(packageName, true) ?: false
    }

    init {
        modules[OsmAnd.getPackageName()] = OsmAnd()
        modules[OsmAnd.getPackageNamePlus()] = OsmAnd()
        modules[K9.namePackage] = K9()
        this.communication = Communication
    }

    companion object {
        val modules: HashMap<String, Module> = HashMap()
    }
}