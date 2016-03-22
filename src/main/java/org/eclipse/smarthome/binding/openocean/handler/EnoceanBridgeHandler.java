package org.eclipse.smarthome.binding.openocean.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.smarthome.binding.openocean.openoceanBindingConstants;
import org.eclipse.smarthome.binding.openocean.api.devices.EoGwDevice;
import org.eclipse.smarthome.binding.openocean.api.devices.EoGwDevices;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseBridgeHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.types.Command;

public class EnoceanBridgeHandler extends BaseBridgeHandler {

    public final static Set<ThingTypeUID> SUPPORTED_THING_TYPES = Collections
            .singleton(openoceanBindingConstants.THING_TYPE_BRIDGE);

    private static final String DEFAULT_USERNAME = "user";
    private static final int POLLING_FREQUENCY = 10; // in seconds

    private boolean lastEnoceanBridgeConnectionState = false;

    private List<DeviceStatusListener> deviceStatusListeners = new CopyOnWriteArrayList<>();
    private Map<String, EoGwDevice> lastDeviceStates = new HashMap<>();

    private ScheduledFuture<?> pollingJob;
    private Runnable pollingRunnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (!lastEnoceanBridgeConnectionState) {
                System.out
                        .println("Class: EnoceanBridgeHandler. Method: pollingRunnable. Connection to Enocean-Bridge {"
                                + eoBridge.getIpAdress() + "} established.");
                lastEnoceanBridgeConnectionState = true;
                onConnectionResumed(eoBridge);
            }
            if (lastEnoceanBridgeConnectionState) {
                // verifie ob they have new device or ob a device will been removed
            }
        }
    };

    private EnoceanBridge eoBridge = null;

    public EnoceanBridgeHandler(Bridge enoceanBridge) {
        super(enoceanBridge);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialize() {
        System.out.println("Initializing Enocean bridge handler.");

        if (getConfig().get(openoceanBindingConstants.USER_NAME) == null) {
            getConfig().put(openoceanBindingConstants.USER_NAME, DEFAULT_USERNAME);
            System.out.println("USER_NAME: " + openoceanBindingConstants.USER_NAME);
        }

        if (getConfig().get(openoceanBindingConstants.HOST) == null) {
            getConfig().put(openoceanBindingConstants.HOST, "172.28.28.150:8080");
            System.out.println("getConfig().get(HOST): " + getConfig().get(openoceanBindingConstants.HOST));
        }

        if (getConfig().get(openoceanBindingConstants.PASSWORD) == null) {
            getConfig().put(openoceanBindingConstants.PASSWORD, openoceanBindingConstants.PASSWORD);
            System.out.println("getConfig().get(PASSWORD): " + getConfig().get(openoceanBindingConstants.PASSWORD));
        }

        if (getConfig().get(openoceanBindingConstants.HOST) != null) {
            if (eoBridge == null) {
                eoBridge = new EnoceanBridge((String) getConfig().get(openoceanBindingConstants.HOST));
            }
            onUpdate();
        } else {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.OFFLINE.CONFIGURATION_ERROR,
                    "Class: EnoceanBridgeHandler. Method: initialize.Cannot connect to Enocean bridge. IP address or user name not set.");
        }
    }

    @Override
    public void dispose() {
        if (pollingJob != null && !pollingJob.isCancelled()) {
            pollingJob.cancel(true);
            pollingJob = null;
        }
        if (eoBridge != null) {
            eoBridge = null;
        }
    }

    private synchronized void onUpdate() {
        if (eoBridge != null) {
            if (pollingJob == null || pollingJob.isCancelled()) {
                pollingJob = scheduler.scheduleAtFixedRate(pollingRunnable, 1, POLLING_FREQUENCY, TimeUnit.SECONDS);
            }
        }
    }

    public void onConnectionLost(EnoceanBridge bridge) {
        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.OFFLINE.BRIDGE_OFFLINE);
    }

    public void onConnectionResumed(EnoceanBridge bridge) {
        updateStatus(ThingStatus.ONLINE);
        // now also re-initialize all device handlers
        for (Thing thing : getThing().getThings()) {
            ThingHandler handler = thing.getHandler();
            if (handler != null) {
                handler.initialize();
            }
        }
    }

    public void onNotAuthenticated(EnoceanBridge bridge) {
        String userName = (String) getConfig().get(openoceanBindingConstants.USER_NAME);
        String password = (String) getConfig().get(openoceanBindingConstants.PASSWORD);
        if (userName != null) {
            try {
                bridge.authenticate(userName, password);
            } catch (Exception e) {
                System.out.println("\n Class: EnoceanBridgeHandler. Method: onNotAuthenticated. Enocean-bridge {"
                        + getConfig().get(openoceanBindingConstants.HOST)
                        + "} is not authenticated - please verifie your bridge (Host,username and password).");
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.OFFLINE.CONFIGURATION_ERROR,
                        "Not authenticated - change username.");
            }
        }
    }

    public boolean registerDeviceStatusListener(DeviceStatusListener deviceStatusListener) {
        if (deviceStatusListeners == null) {
            throw new NullPointerException(
                    "Class: EnoceanBridgeHandler. Method: registerDeviceStatusListener. It's not allowed to pass a null DeviceStatusListener.");
        }
        boolean result = deviceStatusListeners.add(deviceStatusListener);
        if (result) {
            onUpdate();
            for (EoGwDevice device : lastDeviceStates.values()) {
                deviceStatusListener.onDeviceAdded(eoBridge, null, null, device);
            }
        }
        return result;
    }

    public boolean unregisterDeviceStatusListener(DeviceStatusListener deviceStatusListener) {
        boolean result = deviceStatusListeners.remove(deviceStatusListener);
        if (result) {
            onUpdate();
        }
        return result;

    }

    public void startSearch() {
        if (eoBridge != null) {
            try {
                eoBridge.startSearch();
            } catch (Exception e) {
                System.out.println(
                        "Class: EnoceanBridgeHandler. Method: startSearch. Bridge cannot start search mode: " + e);
            }
        }

    }

    public void startSearch(List<String> serialNumbers) {
        if (eoBridge != null) {
            try {
                eoBridge.startSearch(serialNumbers);
            } catch (Exception e) {
                System.out.println(
                        "Class: EnoceanBridgeHandler. Method: startSearch. Bridge cannot start search mode: " + e);
            }
        }
    }

    public List<EoGwDevices> getDevices() {
        List<EoGwDevices> device = null;
        if (eoBridge != null) {
            try {
                try {
                    device = ((EnoceanBridgeHandler) eoBridge.getFullConfig()).getDevices();
                } catch (IllegalStateException e) {
                    lastEnoceanBridgeConnectionState = false;
                    onNotAuthenticated(eoBridge);
                    device = ((EnoceanBridgeHandler) eoBridge.getFullConfig()).getDevices();
                }
            } catch (Exception e) {
                System.out.println(
                        "Class: EnoceanBridgeHandler. Method: getDevices. Bridge cannot search for new device." + e);
            }
        }
        return device;
    }

    public void updateDeviceState(String deviceId, String stateUpdate) throws IOException {
        if (eoBridge != null) {
            try {
                eoBridge.setDeviceState(deviceId, stateUpdate);
            } catch (IllegalStateException e) {
                System.out.println(
                        "class: EnoceanBridgeHandler. Methode: updateDeviceState. Error while accessing device: {"
                                + e.getMessage() + "}");
            }
        } else {
            System.out.println(
                    "class: EnoceanBridgeHandler. Methode: updateDeviceState. No bridge connected or selected. Cannot set device state.");
        }

    }

    public EoGwDevice getDeviceById(String deviceId) {
        return lastDeviceStates.get(deviceId);
    }

    public List<EoGwDevices> getAllDevices() {
        ArrayList<EoGwDevices> jsonStringDevice = new ArrayList<EoGwDevices>();

        return null;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> geListDeviceId() {
        try {
            return EnoceanBridge.getListDeviceId(EnoceanBridge.getAllDevice());
        } catch (Exception e) {
            System.out.println("class: EnoceanBridgeHandler. Methode:geListDeviceId. Error: " + e);
        }
        return null;
    }

    /**
     *
     * @param devId
     * @return
     */
    public String getProfileByDeviceId(String devId) {
        return DeviceHandler.getProfileByDeviceId(devId);
    }

    public String getFriendlyIdByDeviceId(String devId) {
        return DeviceHandler.getFriendlyIdByDeviceId(devId);
    }

    public void removeDeviceIdFromGateway(String deviceId) {
        if (!geListDeviceId().isEmpty()) {
            if (geListDeviceId().contains(deviceId)) {
                geListDeviceId().remove(deviceId);
                /**
                 * code to remove direct into the Gateway
                 */
            }
        }
    }
}
