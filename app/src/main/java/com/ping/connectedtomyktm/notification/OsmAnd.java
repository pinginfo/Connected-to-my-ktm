package com.ping.connectedtomyktm.notification;

import android.os.Bundle;
import android.service.notification.StatusBarNotification;

import com.ping.connectedtomyktm.R;
import com.ping.connectedtomyktm.entities.SendingObject;
import com.ping.connectedtomyktm.tools.ContextObject;

public class OsmAnd implements Module {
    private ContextObject contextObject;

    public OsmAnd() {
        this.contextObject = ContextObject.INSTANCE;
    }

    public SendingObject.TurnIcon getIcons(String str) {
        if (str.equals(this.contextObject.getContext().getString(R.string.route_tr))) {
            return SendingObject.TurnIcon.QUITE_RIGHT;
        } else if (str.equals(this.contextObject.getContext().getString(R.string.route_tshr))) {
            return SendingObject.TurnIcon.HEAVY_RIGHT;
        } else if (str.equals(this.contextObject.getContext().getString(R.string.route_tslr))) {
            return SendingObject.TurnIcon.LIGHT_RIGHT;
        } else if (str.equals(this.contextObject.getContext().getString(R.string.route_kr))) {
            return SendingObject.TurnIcon.KEEP_RIGHT;
        } else if (str.equals(this.contextObject.getContext().getString(R.string.route_tl))) {
            return SendingObject.TurnIcon.QUITE_LEFT;
        } else if (str.equals(this.contextObject.getContext().getString(R.string.route_tshl))) {
            return SendingObject.TurnIcon.HEAVY_LEFT;
        } else if (str.equals(this.contextObject.getContext().getString(R.string.route_tsll))) {
            return SendingObject.TurnIcon.LIGHT_LEFT;
        } else if (str.equals(this.contextObject.getContext().getString(R.string.route_kl))) {
            return SendingObject.TurnIcon.KEEP_LEFT;
        } else if (str.equals(this.contextObject.getContext().getString(R.string.route_tu))) {
            return SendingObject.TurnIcon.UTURN_LEFT;
        } else if (str.equals(this.contextObject.getContext().getString(R.string.route_head))) {
            return SendingObject.TurnIcon.GO_STRAIGHT;
        } else {
            String roundabout = this.contextObject.getContext().getString(R.string.route_roundabout_short);
            if (str.equals(String.format(roundabout, 1))) {
                return SendingObject.TurnIcon.RAB_SECT_4_RH;
            } else if (str.equals(String.format(roundabout, 2))) {
                return SendingObject.TurnIcon.RAB_SECT_6_RH;
            } else if (str.equals(String.format(roundabout, 3))) {
                return SendingObject.TurnIcon.RAB_SECT_8_RH;
            } else if (str.equals(String.format(roundabout, 4))) {
                return SendingObject.TurnIcon.RAB_SECT_10_RH;
            } else if (str.equals(String.format(roundabout, 5))) {
                return SendingObject.TurnIcon.RAB_SECT_12_RH;
            } else if (str.equals(String.format(roundabout, 6))) {
                return SendingObject.TurnIcon.RAB_SECT_14_RH;
            } else if (str.equals(String.format(roundabout, 7))) {
                return SendingObject.TurnIcon.RAB_SECT_16_RH;
            }
        }
        return SendingObject.TurnIcon.FERRY;
    }

    public static String getPackageName() {
        return "net.osmand";
    }

    public static String getPackageNamePlus() {
        return "net.osmand.plus";
    }

    public SendingObject getDataPosted(StatusBarNotification sbn) {
        Bundle bundle = sbn.getNotification().extras;
        String[] title = bundle.getString("android.title").split("•");
        String[] bigText;
        if (title.length > 1) {
            int offset = bundle.getString("android.bigText").indexOf(title[1]) + title[1].length();
            bigText = bundle.getString("android.bigText").substring(offset).split("\n");
        } else {
            bigText = bundle.getString("android.bigText").split("\n");
        }
        String[] otherValues = bigText.length > 1 ? bigText[1].split("•") : bigText[0].split("•");
        SendingObject result = new SendingObject();

        String dist2Target = title[0].trim();
        String turnInfo = title.length > 1 ? title[1].trim() : "";
        String turnRoad = bigText[0].trim();
        String turnIcon = getIcons(turnInfo).name();
        String dist = otherValues[0].trim();
        String remainingTime = otherValues.length > 1 ? otherValues[1].trim() : "";
        String arrivalTime = otherValues.length > 2 ? otherValues[2].trim() : "";
        String actualSpeed = otherValues.length > 3 ? otherValues[3].trim() : "";
        turnInfo = actualSpeed;

        if (turnIcon.startsWith("RAB_SECT_")) {
            int exit = (Integer.parseInt(turnIcon.split("_")[2]) - 2) / 2;
            turnInfo = String.format(this.contextObject.getContext().getString(R.string.roundabout_exit), exit);
        }

        result.setEta(arrivalTime);
        result.setDist2Target(dist + ", " + remainingTime);
        result.setTurnDist(dist2Target.split(" ")[0]);
        result.setTurnDistUnit(dist2Target.split(" ").length > 1 ? dist2Target.split(" ")[1] : "");
        result.setTurnRoad(turnRoad);
        result.setTurnIcon(turnIcon);
        result.setTurnInfo(turnInfo);
        result.setUiContext("guidance");
        return result;
    }

    public SendingObject getDataRemoved(StatusBarNotification sbn) {
        SendingObject result = new SendingObject();

        result.setTurnDist("0");
        result.setTurnDistUnit("m");
        result.setTurnRoad(this.contextObject.getContext().getString(R.string.arrived_at_destination));
        result.setTurnIcon(SendingObject.TurnIcon.END.name());

        return result;
    }
}
