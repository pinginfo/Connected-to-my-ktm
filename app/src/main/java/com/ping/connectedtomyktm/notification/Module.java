package com.ping.connectedtomyktm.notification;

import android.service.notification.StatusBarNotification;

import com.ping.connectedtomyktm.entities.SendingObject;

public interface Module {
    SendingObject getDataPosted(StatusBarNotification sbn);
    SendingObject getDataRemoved(StatusBarNotification sbn);
}
