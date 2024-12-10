
import Foundation
import UIKit

@objc(BatteryModule)
class BatteryModule: NSObject {
    @objc static func requiresMainQueueSetup() -> Bool {
        return true
    }

    @objc func getBatteryLevel(_ resolve: RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock) {
        UIDevice.current.isBatteryMonitoringEnabled = true
        let batteryLevel = UIDevice.current.batteryLevel
        if batteryLevel < 0 {
            reject("E_BATTERY_LEVEL", "Unable to fetch battery level", nil)
        } else {
            resolve(batteryLevel * 100)
        }
    }
}
