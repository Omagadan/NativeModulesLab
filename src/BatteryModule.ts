
import { NativeEventEmitter, NativeModules } from 'react-native';

interface BatteryModule {
    getBatteryLevel(): Promise<number>;
}

const { BatteryModule } = NativeModules;
export default BatteryModule as BatteryModule;
