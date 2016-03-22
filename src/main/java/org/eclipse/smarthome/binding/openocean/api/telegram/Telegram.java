package org.eclipse.smarthome.binding.openocean.api.telegram;

import org.eclipse.smarthome.binding.openocean.api.profiles.Functions;

public class Telegram /* extends Functions */ {

    private String timestamp = null;

    private Functions[] functions = null;

    private String direction = null;

    private String physicalDevice = null;

    private String friendlyId = null;

    private TelegramInfo telegramInfo = null;

    private String deviceId = null;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Functions[] getFunctions() {
        return functions;
    }

    public void setFunctions(Functions[] functions) {
        this.functions = functions;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getPhysicalDevice() {
        return physicalDevice;
    }

    public void setPhysicalDevice(String physicalDevice) {
        this.physicalDevice = physicalDevice;
    }

    public String getFriendlyId() {
        return friendlyId;
    }

    public void setFriendlyId(String friendlyId) {
        this.friendlyId = friendlyId;
    }

    public TelegramInfo getTelegramInfo() {
        return telegramInfo;
    }

    public void setTelegramInfo(TelegramInfo telegramInfo) {
        this.telegramInfo = telegramInfo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "ClassPojo [timestamp = " + timestamp + ", functions = " + functions + ", direction = " + direction
                + ", physicalDevice = " + physicalDevice + ", friendlyId = " + friendlyId + ", telegramInfo = "
                + telegramInfo + ", deviceId = " + deviceId + "]";
    }
}
