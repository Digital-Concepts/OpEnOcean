package org.eclipse.smarthome.binding.openocean.handler;

import java.util.HashMap;

import org.eclipse.smarthome.binding.openocean.api.devices.EoGwDevices;

public class DevicesIdWithState {

    static String deviceId = null;
    static HashMap<String, EoGwDevices> stateOfDeviceId = new HashMap<String, EoGwDevices>();

    public DevicesIdWithState() {
        deviceId = null;
        stateOfDeviceId = new HashMap<String, EoGwDevices>();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        DevicesIdWithState.deviceId = deviceId;
    }

    public HashMap<String, EoGwDevices> getStateOfDeviceId() {
        return stateOfDeviceId;
    }

    public void setStateOfDeviceId(HashMap<String, EoGwDevices> stateOfDeviceId) {
        DevicesIdWithState.stateOfDeviceId = stateOfDeviceId;
    }

}
