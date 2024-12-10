
package com.batterymonitor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule

class BatteryModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    private var batteryLevel: Float = -1f

    override fun getName(): String {
        return "BatteryModule"
    }

    @ReactMethod
    fun getBatteryLevel(promise: Promise) {
        if (batteryLevel >= 0) {
            promise.resolve(batteryLevel)
        } else {
            promise.reject("E_BATTERY_LEVEL", "Unable to fetch battery level")
        }
    }

    override fun initialize() {
        super.initialize()
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        reactApplicationContext.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val level = intent?.getIntExtra("level", -1) ?: -1
                val scale = intent?.getIntExtra("scale", -1) ?: -1
                batteryLevel = if (level >= 0 && scale > 0) {
                    level * 100 / scale.toFloat()
                } else {
                    -1f
                }
                sendEvent("BatteryLevelChanged", batteryLevel)
            }
        }, filter)
    }

    private fun sendEvent(eventName: String, params: Any) {
        reactApplicationContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
    }
}
