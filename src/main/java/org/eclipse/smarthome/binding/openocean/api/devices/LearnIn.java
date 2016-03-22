package org.eclipse.smarthome.binding.openocean.api.devices;

public class LearnIn {

    private String deviceId;

    private LearnInMode mode;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public LearnInMode getMode() {
        return mode;
    }

    public void setMode(LearnInMode mode) {
        this.mode = mode;
    }

}
