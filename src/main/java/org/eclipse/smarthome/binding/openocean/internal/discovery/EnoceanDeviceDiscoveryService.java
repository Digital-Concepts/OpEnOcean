package org.eclipse.smarthome.binding.openocean.internal.discovery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.smarthome.binding.openocean.openoceanBindingConstants;
import org.eclipse.smarthome.binding.openocean.api.devices.EoGwDevice;
import org.eclipse.smarthome.binding.openocean.api.devices.EoGwDevices;
import org.eclipse.smarthome.binding.openocean.handler.DeviceHandler;
import org.eclipse.smarthome.binding.openocean.handler.DeviceStatusListener;
import org.eclipse.smarthome.binding.openocean.handler.EnoceanBridge;
import org.eclipse.smarthome.binding.openocean.handler.EnoceanBridgeHandler;
import org.eclipse.smarthome.binding.openocean.handler.ListOfConnectedDeviceId;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;

public class EnoceanDeviceDiscoveryService extends AbstractDiscoveryService implements DeviceStatusListener {

    private EnoceanBridgeHandler enoceanBridgeHandler;

    /**
     *
     * @param timeout
     * @throws IllegalArgumentException
     */
    public EnoceanDeviceDiscoveryService(int timeout) throws IllegalArgumentException {
        super(timeout);
    }

    /**
     *
     * @param enoceanBridgeHandler
     */
    public EnoceanDeviceDiscoveryService(EnoceanBridgeHandler enoceanBridgeHandler) {
        super(500);
        this.enoceanBridgeHandler = enoceanBridgeHandler;
    }

    /**
     *
     */
    public void activate() {
        enoceanBridgeHandler.registerDeviceStatusListener(this);
    }

    /**
     *
     */
    @Override
    public Set<ThingTypeUID> getSupportedThingTypes() {
        return EnoceanBridgeHandler.SUPPORTED_THING_TYPES;
    }

    /**
     *
     */
    @Override
    public void deactivate() {
        removeOlderResults(new Date().getTime());
        enoceanBridgeHandler.unregisterDeviceStatusListener(this);
    }

    /**
     *
     */
    @Override
    public void startScan() {
        System.out.println("Class: EnoceanDiscoveryService. Methode: startScan. learn in process actived ... \n");
        DeviceHandler.learnInProcess();
        System.out.println("++++++++++++ Start scan of EnOcean-bridge for devices +++++++++++++++\n");
        ArrayList<String> listOfConnectedDeviceId = enoceanBridgeHandler.geListDeviceId();
        ArrayList<String> lastListOfConnectedDeviceId = ListOfConnectedDeviceId.getListOfAllDeviceId();

        if (!listOfConnectedDeviceId.isEmpty()) {
            for (String devId : listOfConnectedDeviceId) {
                String profileDevById = enoceanBridgeHandler.getProfileByDeviceId(devId);
                String friendlyId = enoceanBridgeHandler.getFriendlyIdByDeviceId(devId);
                thingCreator(friendlyId, devId, profileDevById);
                lastListOfConnectedDeviceId.add(devId);
                ListOfConnectedDeviceId.setListOfAllDeviceId(lastListOfConnectedDeviceId);
            }
        } else {
            System.out.println(
                    "class: EnoceanDeviceDiscoveryService. Methode startScan(): listDeviceId is empty. cannot create a device");
        }

    }

    /**
     *
     */
    @Override
    protected synchronized void stopScan() {
        super.stopScan();
        removeOlderResults(getTimestampOfLastScan());
    }

    /**
     *
     */
    @Override
    public void onDeviceAdded(EnoceanBridge eoBridge, String friendlyId, String deviceId, EoGwDevice device) {
        onDeviceAddedInternal(friendlyId, deviceId, device);
    }

    /**
     * @param friendlyId
     * @param deviceId
     * @param device
     */
    private void onDeviceAddedInternal(String friendlyId, String deviceId, EoGwDevice device) {

        ThingUID thingUID = getThingUID(friendlyId, deviceId);
        if (thingUID != null) {
            ThingUID bridgeUID = enoceanBridgeHandler.getThing().getUID();
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(openoceanBindingConstants.Device_ID, deviceId);
            DiscoveryResult discoveryResult = DiscoveryResultBuilder.create(thingUID).withProperties(properties)
                    .withBridge(bridgeUID).withLabel(friendlyId).build();
            thingDiscovered(discoveryResult);
        } else {
            System.out.println("Error with thingUID:" + thingUID
                    + ". class EnoceanDeviceDiscoveryService methode: onDeviceAddedInternal");
        }
    }

    /**
     *
     */
    @Override
    public void onDeviceRemove(EnoceanBridge eoBridge, String deviceId, EoGwDevices device) {
        ThingUID thingUID = getThingUID(null, deviceId);
        if (thingUID != null) {
            ArrayList<String> listDeviceId = enoceanBridgeHandler.geListDeviceId();
            if ((!listDeviceId.isEmpty())) {
                if (listDeviceId.contains(deviceId)) {
                    enoceanBridgeHandler.removeDeviceIdFromGateway(deviceId);
                } else {
                    System.out.println(
                            "Class: EnoceanDeviceDiscoveryService. Method: onDeviceRemove. This device Id is not connected to the gateway");
                }
            } else {
                System.out.println(
                        "Class: EnoceanDeviceDiscoveryService. Method: onDeviceRemove. List of device Id is empty");
            }
            thingRemoved(thingUID);
        }
    }

    /**
     *
     */
    @Override
    public void onDeviceStateChanged(EnoceanBridge eoBridge, String deviceId, EoGwDevice device) {
        // TODO Auto-generated method stub

    }

    /**
     *
     * @param friendlyId
     * @param deviceId
     * @param device
     * @return
     */
    private ThingUID getThingUID(String friendlyId, String deviceId) {
        ThingUID bridgeUID = enoceanBridgeHandler.getThing().getUID();
        ThingTypeUID thingTypeUID = new ThingTypeUID(openoceanBindingConstants.BINDING_ID,
                friendlyId.replaceAll("[^a-zA-Z0-9_]", "_"));
        if (!getSupportedThingTypes().contains(thingTypeUID)) {
            String thingDeviceId = deviceId;
            ThingUID thingUID = new ThingUID(thingTypeUID, bridgeUID, thingDeviceId);
            return thingUID;
        } else {
            return null;
        }
    }

    /**
     *
     * @param friendlyId
     * @param deviceId
     * @param strJsonDevice
     */
    public void thingCreator(String friendlyId, String deviceId, String profileDevId) {
        try {
            ThingUID thingUID = getThingUID(friendlyId, deviceId);
            if (thingUID != null) {
                ThingUID bridgeUID = enoceanBridgeHandler.getThing().getUID();
                Map<String, Object> properties = new HashMap<>(1);
                properties.put(openoceanBindingConstants.Device_ID, deviceId);
                DiscoveryResult discoveryResult = DiscoveryResultBuilder.create(thingUID).withProperties(properties)
                        .withBridge(bridgeUID).withLabel(friendlyId).build();
                thingDiscovered(discoveryResult);

            } else {
                System.out.println("discovered unsupported device of type {} with id {" + deviceId + "}");
            }
        } catch (Exception ex) {
            System.out.println("Class EnoceanDeviceDiscoveryService. Methode: thingCreator. Error: " + ex);
        }
    }

    @Override
    protected void thingRemoved(ThingUID thingUID) {
        System.out.println("\n j'ai trouvé \n");
        super.thingRemoved(thingUID);
    }

}
