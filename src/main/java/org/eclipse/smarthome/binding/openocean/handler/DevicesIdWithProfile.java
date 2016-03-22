package org.eclipse.smarthome.binding.openocean.handler;

import java.util.HashMap;

public class DevicesIdWithProfile {

    static String deviceId;
    static HashMap<String, String> profile = new HashMap<String, String>();
    static HashMap<String, String> statesofDevice = new HashMap<String, String>();

    public void Device() {

    }

    public static HashMap<String, String> getStatesofDevice() {
        return statesofDevice;
    }

    public static HashMap<String, String> getProfile() {
        return profile;
    }

    public static void setProfile(HashMap<String, String> profile) {
        DevicesIdWithProfile.profile = profile;
    }

    public static void setStatesofDevice(HashMap<String, String> statesofDevice) {
        DevicesIdWithProfile.statesofDevice = statesofDevice;
    }

    public static String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String newDeviceId) {
        deviceId = newDeviceId;
    }

    public static void setFunctions(HashMap<String, String> deviceIdProfile) {
        profile = deviceIdProfile;

    }

}
