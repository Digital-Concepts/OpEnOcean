package org.eclipse.smarthome.binding.openocean.api.devices;

import org.eclipse.smarthome.binding.openocean.api.Header;
import org.eclipse.smarthome.binding.openocean.api.profiles.Profile;
import org.eclipse.smarthome.binding.openocean.api.telegram.Telegram;

/**
 *
 * @author AMambou
 */
public class EoGwDevices {

    private Devices[] devices = null;

    private Device device = null;

    private Header header = null;

    private Telegram telegram = null;

    private Profile profile;

    /**                     **/

    public Profile getProfile() {
        return profile;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Telegram getTelegram() {
        return telegram;
    }

    public void setTelegram(Telegram telegram) {
        this.telegram = telegram;
    }

    public Devices[] getDevices() {
        return devices;
    }

    public void setDevices(Devices[] devices) {
        this.devices = devices;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    // @Override
    // public String toString() {
    // return "ClassPojo [states = " + states + ", header = " + header + "]";
    // }
}
