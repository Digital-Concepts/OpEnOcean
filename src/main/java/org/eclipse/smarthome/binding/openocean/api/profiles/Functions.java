package org.eclipse.smarthome.binding.openocean.api.profiles;

import java.util.ArrayList;

public class Functions /* extends Values */ {

    // private String age = null;
    //
    private String description = null;

    private boolean transmitOnConnect = false;

    private boolean testExists;

    private boolean transmitOnDuplicate = true;

    private boolean transmitOnEvent = true;

    private String value = null;

    private String key = null;

    private String unit = null;

    private String timestamp = null;

    private String ValueKey = null;

    private String meaning = null;

    private ArrayList<Values> values = null;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getValueKey() {
        return ValueKey;
    }

    public void setValueKey(String valueKey) {
        ValueKey = valueKey;
    }

    public ArrayList<Values> getValues() {
        return values;
    }

    public void setValues(ArrayList<Values> values) {
        this.values = values;
    }

    public String getUnit() {
        return unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public boolean isTransmitOnConnect() {
        return transmitOnConnect;
    }

    public void setTransmitOnConnect(boolean transmitOnConnect) {
        this.transmitOnConnect = transmitOnConnect;
    }

    public boolean isTestExists() {
        return testExists;
    }

    public void setTestExists(boolean testExists) {
        this.testExists = testExists;
    }

    public boolean isTransmitOnDuplicate() {
        return transmitOnDuplicate;
    }

    public void setTransmitOnDuplicate(boolean transmitOnDuplicate) {
        this.transmitOnDuplicate = transmitOnDuplicate;
    }

    public boolean isTransmitOnEvent() {
        return transmitOnEvent;
    }

    public void setTransmitOnEvent(boolean transmitOnEvent) {
        this.transmitOnEvent = transmitOnEvent;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    // @Override
    // public String toString() {
    // return "ClassPojo [timestamp = " + timestamp + ", valueKey = " + valueKey + ", age = " + age + ", value = "
    // + value + ", key = " + key + "]";
    // }
}
