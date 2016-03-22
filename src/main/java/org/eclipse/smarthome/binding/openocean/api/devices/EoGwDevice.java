package org.eclipse.smarthome.binding.openocean.api.devices;

import org.eclipse.smarthome.binding.openocean.api.Header;

public class EoGwDevice {

    private Header header = null;

    private Device device = null;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

}
