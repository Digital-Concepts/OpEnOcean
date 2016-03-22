package org.eclipse.smarthome.binding.openocean.handler;

import org.eclipse.smarthome.binding.openocean.api.devices.EoGwDevice;
import org.eclipse.smarthome.binding.openocean.api.devices.EoGwDevices;

public interface DeviceStatusListener {

    public void onDeviceStateChanged(EnoceanBridge eoBridge, String deviceId, EoGwDevice device);

    public void onDeviceRemove(EnoceanBridge eoBridge, String deviceId, EoGwDevices device);

    public void onDeviceAdded(EnoceanBridge eoBridge, String friendlyId, String deviceId, EoGwDevice device);

}
