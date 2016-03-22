package org.eclipse.smarthome.binding.openocean.handler;

import java.util.ArrayList;

public class ListOfConnectedDeviceId {

    String deviceId;
    static ArrayList<String> listOfAllDeviceId = new ArrayList<String>();

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public static ArrayList<String> getListOfAllDeviceId() {
        return listOfAllDeviceId;
    }

    public static void setListOfAllDeviceId(ArrayList<String> listOfAllDeviceId) {
        ListOfConnectedDeviceId.listOfAllDeviceId = listOfAllDeviceId;
    }

    public static void removeDeviceId(String deviceId) {
        listOfAllDeviceId.remove(deviceId);
    }

    public static void addDeviceId(String deviceId) {
        listOfAllDeviceId.add(deviceId);
    }

}
