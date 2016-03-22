package org.eclipse.smarthome.binding.openocean.api.devices;

import java.util.ArrayList;

public class Devices {

    private String deviceId = null;

    private String friendlyId = null;

    private String physicalDevice = null;

    private LearnInProcedure learnInProcedure;

    private String eep = null;

    private String eepVariation = null;

    private String manufacturer = null;

    private String version;

    private String baseIdChannel = null;

    private String firstSeen = null;

    private String lastSeen = null;

    private boolean softSmartAck;

    private String communicationType;

    private ArrayList<TransmitModes> transmitModes = null;

    private ArrayList<States> states = null;

    private boolean operable;

    private boolean supported;

    private boolean secured;

    private String deviceType;

    private Boolean deleted;

    /**                     **/

    private String iotLabel = null;

    public String getIotLabel() {
        return iotLabel;
    }

    public void setIotLabel(String iotLabel) {
        this.iotLabel = iotLabel;
    }

    public boolean isSoftSmartAck() {
        return softSmartAck;
    }

    public String getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(String communicationType) {
        this.communicationType = communicationType;
    }

    public void setSoftSmartAck(boolean softSmartAck) {
        this.softSmartAck = softSmartAck;
    }

    public boolean isOperable() {
        return operable;
    }

    public void setOperable(boolean operable) {
        this.operable = operable;
    }

    public boolean isSecured() {
        return secured;
    }

    public void setSecured(boolean secured) {
        this.secured = secured;
    }

    public boolean isSupported() {
        return supported;
    }

    public void setSupported(boolean supported) {
        this.supported = supported;
    }

    public ArrayList<States> getStates() {
        return states;
    }

    public void setStates(ArrayList<States> states) {
        this.states = states;
    }

    public ArrayList<TransmitModes> getTransmitModes() {
        return transmitModes;
    }

    public void setTransmitModes(ArrayList<TransmitModes> transmitModes) {
        this.transmitModes = transmitModes;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public LearnInProcedure getLearnInProcedure() {
        return learnInProcedure;
    }

    public void setLearnInProcedure(LearnInProcedure learnInProcedure) {
        this.learnInProcedure = learnInProcedure;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getEepVariation() {
        return eepVariation;
    }

    public void setEepVariation(String eepVariation) {
        this.eepVariation = eepVariation;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getBaseIdChannel() {
        return baseIdChannel;
    }

    public String getPhysicalDevice() {
        return physicalDevice;
    }

    public void setPhysicalDevice(String physicalDevice) {
        this.physicalDevice = physicalDevice;
    }

    public void setBaseIdChannel(String baseIdChannel) {
        this.baseIdChannel = baseIdChannel;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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
        return "ClassPojo [baseIdChannel = " + baseIdChannel + ", manufacturer = " + manufacturer + ", friendlyId = "
                + friendlyId + ", eep = " + eep + ", deviceId = " + deviceId + ", firstSeen = " + firstSeen + "]";
    }
}
