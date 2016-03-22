package org.eclipse.smarthome.binding.openocean.api.devices;

public class TransmitModes {

    private boolean transmitOnConnect;

    private boolean transmitOnDuplicate;

    private boolean transmitOnEvent;

    private String key;

    public boolean getTransmitOnConnect() {
        return transmitOnConnect;
    }

    public void setTransmitOnConnect(boolean transmitOnConnect) {
        this.transmitOnConnect = transmitOnConnect;
    }

    public boolean getTransmitOnDuplicate() {
        return transmitOnDuplicate;
    }

    public void setTransmitOnDuplicate(boolean transmitOnDuplicate) {
        this.transmitOnDuplicate = transmitOnDuplicate;
    }

    public boolean getTransmitOnEvent() {
        return transmitOnEvent;
    }

    public void setTransmitOnEvent(boolean transmitOnEvent) {
        this.transmitOnEvent = transmitOnEvent;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
