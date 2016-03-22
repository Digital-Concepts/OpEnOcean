package org.eclipse.smarthome.binding.openocean.database;

public class Devices {

    private String deviceID;

    private String thingUID;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getThingUID() {
        return thingUID;
    }

    public void setThingUID(String thingUID) {
        this.thingUID = thingUID;
    }
}
