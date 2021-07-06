package com.ping.connectedtomyktm.notification

import android.service.notification.StatusBarNotification
import com.ping.connectedtomyktm.entities.SendingObject

class K9: Module {
    // This module must be tested :)
    private var sendingObject: SendingObject = SendingObject()

    override fun getDataPosted(sbn: StatusBarNotification): SendingObject {
        val bundle = sbn.notification.extras
        val subject: String? = bundle["android.text"] as String?
        val title: String? = bundle["android.title"] as String?
        val titleBig: String? = bundle["android.title.big"] as String?
        val email: String? = bundle["android.subText"] as String?
        email?.also { this.sendingObject.eta = it }
        titleBig?.also { this.sendingObject.dist2Target = it }
        title?.also { this.sendingObject.turnInfo = it }
        subject?.also { this.sendingObject.turnRoad = it }

        return this.sendingObject
    }

    override fun getDataRemoved(sbn: StatusBarNotification?): SendingObject {
        return SendingObject()
    }

    init {
        this.sendingObject.turnDist = "K9"
        this.sendingObject.turnIcon = SendingObject.TurnIcon.END.name
    }

    companion object {
        val namePackage = "com.fsck.k9"
    }
}