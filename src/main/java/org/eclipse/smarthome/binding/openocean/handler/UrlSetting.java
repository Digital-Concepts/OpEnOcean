package org.eclipse.smarthome.binding.openocean.handler;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.smarthome.binding.openocean.openoceanBindingConstants;

public class UrlSetting {

    String url = openoceanBindingConstants.HOST + ":" + openoceanBindingConstants.PORT;

    public void URLSetting() {
        url = openoceanBindingConstants.HOST + ":" + openoceanBindingConstants.PORT;
    }

    public void URLSetting(String myURL) {
        url = myURL;
    }

    public URL getUrl() throws MalformedURLException {
        return new URL(url);
    }

    public void setUrl(String urle) {
        url = urle;
    }

    /**
     *
     * @return
     */
    public String getDeviceGWURL() {
        return url + "/systemInfo";
    }

    public String getLearnInURL() {
        return url + "/system/receiveMode";
    }

    public String getProfileListURL() {
        return url + "/profiles";
    }

    public String getDeviceListURL() {
        return url + "/devices";
    }

    public String getDeviceListStateURL() {
        return url + "/devices/states";
    }

    public String getDeviceListStreamURL() {
        return url + "/devices/stream";
    }

    public String getDeviceListTelegramnURL() {
        return url + "/devices/telegrams";
    }

    public String getDeviceIdURL(String deviceId) {
        deviceId = url + "/devices/" + deviceId;
        return deviceId;
    }

    public String getDeviceIdProfileURL(String deviceId) {
        return url + "/devices/" + deviceId + "/profile";
    }

    public String getDeviceIdStateURL(String deviceId) {
        return url + "/devices/" + deviceId + "/state";
    }

    public String getDeviceIdStreamURL(String deviceId) {
        return url + "/devices/" + deviceId + "/stream";
    }

    public String getDeviceIdTelegramsURL(String deviceId) {
        return url + "/devices/" + deviceId + "/telegrams";
    }

    public String getProfileByEepId(String EepId) {
        return url + "/profiles/" + EepId;
    }

}
