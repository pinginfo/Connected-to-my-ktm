package com.ping.connectedtomyktm.entities

import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class SendingObject {
    var turnRoad: String? = null
    var turnDist: String? = null
    var turnDistUnit: String? = null
    var turnIcon: String? = null
    var eta: String? = null
    var dist2Target: String? = null
    var turnInfo: String? = null
    var notificationText: String? = null
    var gpsIcon: String? = null
    var uiContext: String? = null

    init {
        this.gpsIcon = "GPS";
        this.uiContext = "default"
    }

    @Throws(JSONException::class)
    private fun getIconJson(value: String?): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("Image", value ?: "UNDEFINED")
        jsonObject.put("Visibility", if (value != null) "full" else "off")
        return jsonObject
    }

    @Throws(JSONException::class)
    private fun getTextJson(value: String?): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("Text", value ?: "")
        jsonObject.put("Visibility", if (value != null) "full" else "off")
        return jsonObject
    }

    @Throws(JSONException::class)
    public fun getJson(): JSONObject {
        val jsonObject = JSONObject()
        val childObject = JSONObject()

        childObject.put("TurnRoad", getTextJson(this.turnRoad))
        childObject.put("TurnDist", getTextJson(this.turnDist))
        childObject.put("TurnDistUnit", getTextJson(this.turnDistUnit))
        childObject.put("TurnIcon", getIconJson(this.turnIcon))
        childObject.put("GpsIcon", getIconJson(this.gpsIcon))
        childObject.put("Dist2Target", getTextJson(this.dist2Target))
        childObject.put("ETA", getTextJson(this.eta))
        childObject.put("TurnInfo", getTextJson(this.turnInfo))
        childObject.put("NotificationText", getTextJson(this.notificationText))
        childObject.put("NotificationIcon", JSONObject().put("Visibility", "off"))

        jsonObject.put("UiContext", this.uiContext)
        jsonObject.put("UpdateUI", childObject)
        jsonObject.put("MsgId", if (this.uiContext.equals("guidance")) "gon" else "Restore")

        return jsonObject
    }

    fun getBytes(id: Int): ByteArray? {
        try {
            val jsonObject = this.getJson()
            val msgId = StringBuilder()
            msgId.append(jsonObject.getString("MsgId"))
            msgId.append("#$id")
            jsonObject.put("MsgId", msgId.toString())
            val dataInBytes = jsonObject.toString().replace("\\/", "/").toByteArray(StandardCharsets.UTF_8)
            val lengthData = dataInBytes.size + 1;
            val header = byteArrayOf(
                (-0x1000000 and lengthData shr 24).toByte(),
                (0xFF0000 and lengthData shr 16).toByte(),
                (0xFF00 and lengthData shr 8).toByte(),
                (0xFF and lengthData).toByte(),
                1
            )
            val result = ByteArray(dataInBytes.size + 5)
            System.arraycopy(header, 0, result, 0, 5)
            System.arraycopy(dataInBytes, 0, result, 5, dataInBytes.size)
            return result
        } catch (e: JSONException) {
            return null
        }
    }

    enum class TurnIcon {
        END,
        HEAVY_LEFT,
        HEAVY_RIGHT,
        KEEP_LEFT,
        KEEP_RIGHT,
        QUITE_LEFT,
        QUITE_RIGHT,
        LIGHT_LEFT,
        LIGHT_RIGHT,
        GO_STRAIGHT,
        UTURN_LEFT,
        UTURN_RIGHT,
        LEAVE_HIGHWAY_RIGHT_LANE,
        LEAVE_HIGHWAY_LEFT_LANE,
        FERRY,
        RAB_SECT_4_LH,
        RAB_SECT_4_RH,
        RAB_SECT_6_LH,
        RAB_SECT_6_RH,
        RAB_SECT_8_RH,
        RAB_SECT_8_LH,
        RAB_SECT_10_RH,
        RAB_SECT_10_LH,
        RAB_SECT_12_LH,
        RAB_SECT_12_RH,
        RAB_SECT_14_RH,
        RAB_SECT_14_LH,
        RAB_SECT_16_RH,
        RAB_SECT_16_LH
    }
}

