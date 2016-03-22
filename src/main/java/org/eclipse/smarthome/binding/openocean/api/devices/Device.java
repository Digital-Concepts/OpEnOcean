package org.eclipse.smarthome.binding.openocean.api.devices;

import java.util.ArrayList;

public class Device {

    private String deviceId;

    private String friendlyId;

    private String physicalDevice;

    private LearnInProcedure learnInProcedure;

    private String eep;

    private String eepVariation;

    private String manufacturer;

    private String version;

    private String baseIdChannel;

    private String firstSeen;

    private String lastSeen;

    private boolean softSmartAck;

    private ArrayList<TransmitModes> transmitModes = null;

    private boolean operable;

    private boolean supported;

    private boolean deleted;

    private States[] states;

    private String deviceType;

    private CommunicationType communicationType;

    private boolean secured;

    /**                             **/
    private String iotLabel = null;

    public String getIotLabel() {
        return iotLabel;
    }

    public void setIotLabel(String iotLabel) {
        this.iotLabel = iotLabel;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public CommunicationType getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(CommunicationType communicationType) {
        this.communicationType = communicationType;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getBaseIdChannel() {
        return baseIdChannel;
    }

    public void setBaseIdChannel(String baseIdChannel) {
        this.baseIdChannel = baseIdChannel;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEepVariation() {
        return eepVariation;
    }

    public void setEepVariation(String eepVariation) {
        this.eepVariation = eepVariation;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public boolean getSoftSmartAck() {
        return softSmartAck;
    }

    public void setSoftSmartAck(boolean softSmartAck) {
        this.softSmartAck = softSmartAck;
    }

    public boolean getOperable() {
        return operable;
    }

    public void setOperable(boolean operable) {
        this.operable = operable;
    }

    public boolean getSecured() {
        return secured;
    }

    public void setSecured(boolean secured) {
        this.secured = secured;
    }

    public ArrayList<TransmitModes> getTransmitModes() {
        return transmitModes;
    }

    public void setTransmitModes(ArrayList<TransmitModes> transmitModes) {
        this.transmitModes = transmitModes;
    }

    public States[] getStates() {
        return states;
    }

    public void setStates(States[] states) {
        this.states = states;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public boolean getSupported() {
        return supported;
    }

    public void setSupported(boolean supported) {
        this.supported = supported;
    }

    public String getPhysicalDevice() {
        return physicalDevice;
    }

    public void setPhysicalDevice(String physicalDevice) {
        this.physicalDevice = physicalDevice;
    }

    public LearnInProcedure getLearnInProcedure() {
        return learnInProcedure;
    }

    public void setLearnInProcedure(LearnInProcedure learnInProcedure) {
        this.learnInProcedure = learnInProcedure;
    }

    public String getFriendlyId() {
        return friendlyId;
    }

    public void setFriendlyId(String friendlyId) {
        this.friendlyId = friendlyId;
    }

    public String getEep() {
        return eep;
    }

    public void setEep(String eep) {
        this.eep = eep;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(String firstSeen) {
        this.firstSeen = firstSeen;
    }

    @Override
    public String toString() {
        return "ClassPojo [lastSeen = " + lastSeen + ", softSmartAck = " + softSmartAck + ", operable = " + operable
                + ", secured = " + secured + ", transmitModes = " + transmitModes + ", states = " + states
                + ", deviceType = " + deviceType + ", supported = " + supported + ", physicalDevice = " + physicalDevice
                + ", learnInProcedure = " + learnInProcedure + ", friendlyId = " + friendlyId + ", eep = " + eep
                + ", deviceId = " + deviceId + ", firstSeen = " + firstSeen + "]";
    }
}
