package org.eclipse.smarthome.binding.openocean.api.devices;

import org.eclipse.smarthome.binding.openocean.api.profiles.Functions;

public class States /* extends Functions */ {

    private Functions[] functions = null;

    // private String physicalDevice = null;
    //
    // private String friendlyId = null;
    //
    // private String deviceId = null;

    private String meaning = null;

    private String valueKey = null;

    /**                         **/

    private String key = null;

    private String value = null;

    private String unit = null;

    private String timestamp = null;

    private String age = null;

    /**                     **/

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getValueKey() {
        return valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Functions[] getFunctions() {
        return functions;
    }

    public void setFunctions(Functions[] functions) {
        this.functions = functions;
    }

}
