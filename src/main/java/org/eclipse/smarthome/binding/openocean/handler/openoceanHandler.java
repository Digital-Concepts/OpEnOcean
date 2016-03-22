/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.smarthome.binding.openocean.handler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.smarthome.binding.openocean.openoceanBindingConstants;
import org.eclipse.smarthome.binding.openocean.api.devices.Devices;
import org.eclipse.smarthome.binding.openocean.api.devices.EoGwDevice;
import org.eclipse.smarthome.binding.openocean.api.devices.EoGwDevices;
import org.eclipse.smarthome.binding.openocean.api.devices.TransmitModes;
import org.eclipse.smarthome.binding.openocean.database.SaveThingUID;
import org.eclipse.smarthome.binding.openocean.internal.openoceanHandlerFactory;
import org.eclipse.smarthome.binding.openocean.internal.discovery.EnoceanDeviceDiscoveryService;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.OpenClosedType;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.ThingStatusInfo;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

/**
 * The {@link openoceanHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Mambou - Initial contribution
 */
public class openoceanHandler extends BaseThingHandler implements Runnable, DeviceStatusListener {

    private Logger logger = LoggerFactory.getLogger(openoceanHandler.class);

    public static final String BINDING_ID = "openocean";
    public static Set<ThingTypeUID> SUPPORTED_THING_TYPES = getSUPPORTED_THING_TYPES();
    /**
     * Variable declaration
     */
    static State state = null;

    private ChannelUID channelUID = null;
    private EnoceanBridgeHandler bridgeHandler;
    private EnoceanDeviceDiscoveryService eoDevDisco = new EnoceanDeviceDiscoveryService(50);
    private String deviceId;
    openoceanHandlerFactory op;
    Thread HandleEventFromGWEO = new Thread(this, "Handle event from DC-GW/EO-IP");

    public openoceanHandler(Thing thing) {
        super(thing);
    }

    /**
     *
     * @return
     */
    private static SaveThingUID getSaveThingUID() {
        System.out.println("Class: OpenoceanHandler. Method: getSaveThingUID. Info: Start to get save ThingUID");
        ObjectMapper mapper = new ObjectMapper();
        SaveThingUID savethin = new SaveThingUID();
        try {

            File datei = new File(openoceanBindingConstants.fileName);
            while (!datei.exists()) {
                datei = new File(openoceanBindingConstants.fileName);
            }
            if (datei.exists()) {
                savethin = mapper.readValue(datei, SaveThingUID.class);
                System.out.println("Class: OpenoceanHandler. Method: getSaveThingUID. savethin.getDevices().size(): "
                        + savethin.getDevices().size());
                return savethin;
            }

        } catch (Exception e) {
            System.out.println("Class: OpenoceanHandler. Method: getSaveThingUID. Error: " + e);
        }
        return savethin;
    }

    /**
     *
     * @return
     */
    private static Set<ThingTypeUID> getSUPPORTED_THING_TYPES() {
        Set<ThingTypeUID> arrOfthingTypeUID = Sets.newHashSet();
        SaveThingUID savethin = new SaveThingUID();
        try {
            savethin = getSaveThingUID();
            if (savethin != null) {
                for (int i = 0; i < savethin.getDevices().size(); i++) {
                    arrOfthingTypeUID.add(new ThingTypeUID(openoceanBindingConstants.BINDING_ID,
                            savethin.getDevices().get(i).getThingUID()));
                }
            }
        } catch (Exception e) {
            System.out.println("Class: OpenoceanHandler. Method: getSUPPORTED_THING_TYPES. Error: " + e);
        }
        return arrOfthingTypeUID;
    }

    /**
     *
     * @param <JSONObject>
     * @param id
     * @param thingUID
     */
    public static void addThingUIDToJsonDatei(String deviceID, String thingUID) {
        System.out.println("Class: OpenoceanHandler. Method: addThingUIDToJsonDatei. Info : start of the method ");
        ObjectMapper mapper = new ObjectMapper();
        SaveThingUID savethi = new SaveThingUID();
        ArrayList<org.eclipse.smarthome.binding.openocean.database.Devices> dev = new ArrayList<org.eclipse.smarthome.binding.openocean.database.Devices>();
        try {
            org.eclipse.smarthome.binding.openocean.database.Devices devi = new org.eclipse.smarthome.binding.openocean.database.Devices();

            savethi = getSaveThingUID();

            devi.setDeviceID(deviceID);
            devi.setThingUID(thingUID);

            if (savethi != null) {
                if (!savethi.getDevices().contains(devi)) {

                    if (!dev.contains(devi)) {
                        dev.add(devi);
                    }

                    for (int i = 0; i < savethi.getDevices().size(); i++) {

                        devi = savethi.getDevices().get(i);
                        if (!dev.contains(devi)) {
                            dev.add(devi);
                        }
                    }
                    savethi = new SaveThingUID();
                    if (dev.isEmpty() == false) {
                        savethi.setDevices(dev);
                    }
                    mapper.writeValue(new File(openoceanBindingConstants.fileName), savethi);
                } else {
                    System.out.println(
                            "Class: OpenoceanHandler. Method: addThingUIDToJsonDatei. Info : it is already in ArrayList");
                }
            } else {
                System.out.println("Class: OpenoceanHandler. Method: addThingUIDToJsonDatei. Info : savethi ist null");
            }

        } catch (Exception ex) {
            System.out.println("Class: OpenoceanHandler. Method: addThingUIDToJsonDatei. Error : " + ex);
        }
    }

    /**
     *
     */
    public void channelList() {
        System.out.println("\n show channel List \n");
        ArrayList<Channel> arrCh = new ArrayList<Channel>();
        int sizeCh = getThing().getChannels().size();
        int i = 0;
        if (sizeCh != 0) {
            Iterator<Channel> it = getThing().getChannels().iterator();
            while (it.hasNext()) {
                arrCh.add(it.next());
            }
        }
        for (Channel ch : arrCh) {
            System.out.println(ch.getUID());
        }
        System.out.println("\n End of channel List \n");
    }

    /**
     *
     */
    @Override
    public void run() {
        eventFromGateway();
    }

    /**
     *
     */
    @SuppressWarnings("null")
    private void eventFromGateway() {
        try {

            HttpURLConnection myURLCon = DeviceHandler.connectToAllDevice();
            InputStream ins = new BufferedInputStream(myURLCon.getInputStream());
            String jsonNode = "";
            String eep = null;
            int data = ins.read();
            String str = Character.toString((char) data);
            int cpt1 = 0;
            int cpt2 = 0;
            while (data != -1) {
                jsonNode += str;
                if (str.contains("{")) {
                    cpt1 += 1;
                }
                if (str.contains("}")) {
                    cpt2 += 1;
                }
                if ((cpt1 == cpt2) && (data != 13) && (data != 10)) {

                    ObjectMapper mapper = new ObjectMapper();
                    EoGwDevices eoGwDevices = mapper.readValue(jsonNode, EoGwDevices.class);
                    EoGwDevice eoGwDevice = null;

                    if (eoGwDevices != null) {
                        if (eoGwDevices.getDevice() != null) {
                            eoGwDevice = mapper.readValue(jsonNode, EoGwDevice.class);
                        }
                    }

                    if (eoGwDevice != null) {
                        if (eoGwDevice.getDevice() != null) {
                            jsonNode = "";
                            /**
                             * remove Process
                             */
                            if (eoGwDevice.getDevice().getDeleted() == true) {
                                String devId = eoGwDevice.getDevice().getDeviceId();
                                onDeviceRemoveOnOpenhabUi(devId);
                                System.out.println("Class: OpenoceanHandler. Method: EventFromGateway. The device "
                                        + devId + " has been deleted");
                            }

                            /**
                             * learn In process
                             */
                            if ((eoGwDevice.getDevice().getOperable() == false)
                                    && (eoGwDevice.getDevice().getDeleted() == false)) {
                                String eepId = eoGwDevice.getDevice().getEep();
                                if (eepId.equalsIgnoreCase("F6-??-??")) {
                                    eepId = "F6-02-01";
                                    eoGwDevice.getDevice().setEep(eepId);
                                }
                                EoGwDevices eoProfile = DeviceHandler.getProfileByEepId(eepId);
                                ArrayList<TransmitModes> transmitModes = new ArrayList<TransmitModes>();

                                for (int i = 0; i < eoProfile.getProfile().getFunctionGroups().get(0).getFunctions()
                                        .size(); i++) {
                                    TransmitModes element = new TransmitModes();

                                    element.setKey(eoProfile.getProfile().getFunctionGroups().get(0).getFunctions()
                                            .get(i).getKey());

                                    element.setTransmitOnConnect(eoProfile.getProfile().getFunctionGroups().get(0)
                                            .getFunctions().get(i).isTransmitOnConnect());

                                    element.setTransmitOnDuplicate(eoProfile.getProfile().getFunctionGroups().get(0)
                                            .getFunctions().get(i).isTransmitOnDuplicate());

                                    element.setTransmitOnEvent(eoProfile.getProfile().getFunctionGroups().get(0)
                                            .getFunctions().get(i).isTransmitOnEvent());
                                    transmitModes.add(element);
                                }
                                eoGwDevice.getDevice().setTransmitModes(transmitModes);
                                eoGwDevice.getDevice().setFriendlyId("Device_" + eoGwDevice.getDevice().getDeviceId());
                                eoGwDevice.getDevice().setOperable(true);

                                String jsonNewDevice = mapper.writeValueAsString(eoGwDevice);
                                DeviceHandler.sendDeviceToGW(eoGwDevice.getDevice().getDeviceId(), jsonNewDevice);
                                DeviceHandler.createXmlFileFromProfile(eoGwDevice);
                                /**
                                 * create the thingUID
                                 */
                                SUPPORTED_THING_TYPES.add(new ThingTypeUID(openoceanBindingConstants.BINDING_ID,
                                        eoGwDevice.getDevice().getFriendlyId()));
                                addThingUIDToJsonDatei(eoGwDevice.getDevice().getDeviceId(),
                                        eoGwDevice.getDevice().getFriendlyId());
                                        // dispose();
                                        //
                                        // initialize();

                                /**
                                 * create the channelId
                                 */
                                String channelId = null;

                                if (!eoGwDevice.getDevice().getTransmitModes().isEmpty()) {

                                    if (eoGwDevice.getDevice().getTransmitModes().get(0).getKey() == null) {
                                        channelId = "looooool";
                                    } else {
                                        channelId = eoGwDevice.getDevice().getTransmitModes().get(0).getKey();
                                        EnoceanChannelUID.channelUID.put(eoGwDevice.getDevice().getDeviceId(),
                                                channelId);
                                        String status = null;
                                        if (eoGwDevice.getDevice().getStates() != null) {
                                            status = eoGwDevice.getDevice().getStates()[0].getValue();
                                        } else {
                                            status = "0";
                                        }
                                        eepParserChoose(eepId, eoGwDevice.getDevice().getDeviceId(), status, channelId);
                                    }
                                } else {
                                    System.out.println(
                                            "Class: OpenoceanHandler. Methode: eventFromGateway. Info : No fonction found.");
                                }

                            }
                        }
                    }

                    /**
                     * update process
                     */
                    if ((eoGwDevices.getDevices() != null)) {
                        for (Devices dev : eoGwDevices.getDevices()) {
                            String deviceState;
                            if (dev.getStates().size() != 0) {
                                deviceState = dev.getStates().get(0).getValue();
                            } else {
                                deviceState = "0";
                            }
                            if (deviceState != null) {
                                DevicesIdWithProfile.statesofDevice.put(dev.getDeviceId(), deviceState);

                                if (EnoceanChannelUID.channelUID.containsKey(dev.getDeviceId())) {
                                    String channe = EnoceanChannelUID.channelUID.get(dev.getDeviceId());
                                    if (channe == null) {
                                        channe = "default";
                                    }
                                    eepParserChoose(eep, dev.getDeviceId(), deviceState, channe);
                                }

                            }
                        }
                        jsonNode = "";
                    }
                    /**
                     * update process
                     */
                    if ((eoGwDevices.getTelegram() != null)) {
                        String deviceState;
                        if (eoGwDevices.getTelegram().getFunctions().length == 0) {
                            deviceState = "0";
                        } else {
                            deviceState = eoGwDevices.getTelegram().getFunctions()[0].getValue();
                        }
                        if (deviceState != null) {
                            DevicesIdWithProfile.statesofDevice.put(eoGwDevices.getTelegram().getDeviceId(),
                                    deviceState);
                            if (EnoceanChannelUID.channelUID.containsKey(eoGwDevices.getTelegram().getDeviceId())) {
                                eepParserChoose(eep, eoGwDevices.getTelegram().getDeviceId(), deviceState,
                                        EnoceanChannelUID.channelUID.get(eoGwDevices.getTelegram().getDeviceId()));
                            }
                        }
                        jsonNode = "";
                    }
                }
                data = ins.read();
                if (data != -1) {
                    str = Character.toString((char) data);
                } else {
                    System.out.println("End of Stream");
                }
            }
        } catch (Exception ex) {
            System.out.println("Class: OpenoceanHandler. Methode: eventFromGateway: Error:" + ex);
        }
    }

    /**
     *
     * @param deviceId2
     */
    private void onDeviceRemoveOnOpenhabUi(String deviceId) {
        op = new openoceanHandlerFactory();
        ThingUID thingUID = getThing().getUID();
        if (deviceId.equalsIgnoreCase(getThing().getUID().getId())) {
            updateStatus(ThingStatus.REMOVING);
            updateStatus(ThingStatus.REMOVED);
            op.removeThing(thingUID);
            op.removeHandler(getThing().getHandler());
            dispose();
        }
    }

    private void eepParserChoose(String eep, String deviceId, String deviceState, String channelID) {
        if (deviceId.equalsIgnoreCase("0193EFFD")) {
            System.out.println("channelID: " + channelID + ". deviceState : " + deviceState);
            if (deviceState.equalsIgnoreCase("0")) {
                state = OnOffType.OFF;
            } else/* (deviceState.equalsIgnoreCase("0")) */ {
                state = OnOffType.ON;
            }
            if (state != null) {
                updateState(channelID, state);
            } else {
                System.out.println(
                        "Class: OpenoceanHandler. Method: eepParserChoose. Licht has the last state or no state: ");
            }
        } else if (deviceId.equalsIgnoreCase("01872C13")) {
            state = DecimalType.valueOf(deviceState);
            if (state != null) {
                System.out.println("Class: OpenoceanHandler. Method: eepParserChoose. AfrisoCO2 state: " + state);
                updateState(channelID, state);
            } else {
                // what can i do in this case
            }
        } else if (deviceId.equalsIgnoreCase("01852BA6")) {
            // System.out.println(deviceId + " is windows contact with state: " +
            // deviceState.toUpperCase(Locale.ENGLISH));
            if (deviceState.equalsIgnoreCase("0")) {
                deviceState = "closed";
                state = OpenClosedType.valueOf(deviceState.toUpperCase(Locale.ENGLISH));
            } else {
                deviceState = "open";
                state = OpenClosedType.valueOf(deviceState.toUpperCase(Locale.ENGLISH));
            }
            if (state != null) {
                updateState(channelID, state);
            } else {
                System.out.println("What can I do hier?");
            }
        } else {
            System.out.println("Another thing with device ID : " + deviceId + " and State: " + deviceState);
        }
    }

    /**
     *
     */
    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        System.out.println("channelUID : " + channelUID + ". Command : " + command);
        Bridge b = getBridge();
        if (b == null) {
            System.out.println("EnOcean Bridge not found");
            return;
        } else {
            EnoceanBridgeHandler enoceanBridgeHandler = getEnoceanBridgeHandler();
            if (enoceanBridgeHandler == null) {
                System.out.println("Enocean bridge handler not found. Cannot handle command without bridge.");
                return;
            } else {
                if (channelUID.getId().equals("dimValue")) {
                    if (command.toString().equals("OFF")) {
                        System.out.println("\n---------------Switch is going OFF\n");
                        try {
                            enoceanBridgeHandler.updateDeviceState(getThing().getUID().getId().toString(),
                                    command.toString());
                        } catch (IOException e) {
                            System.out.println("class: OpenoceanHandler method: Handlecommand Exception: " + e);
                        }
                    }
                    if (command.toString().equals("ON")) {
                        System.out.println("\n---------------Switch is going ON\n");
                        try {
                            enoceanBridgeHandler.updateDeviceState(getThing().getUID().getId().toString(),
                                    command.toString());
                        } catch (IOException e) {
                            System.out.println("class: OpenoceanHandler method: Handlecommand Exception: " + e);
                        }
                    }
                }
                if (channelUID.getId().equals("rollershutter")) {
                    if (command.toString().equalsIgnoreCase("UP")) {
                        DeviceHandler.sendCommandToGateway(getThing().getUID().getId().toString(), command.toString());
                    }
                    if (command.toString().equalsIgnoreCase("DOWN")) {
                        DeviceHandler.sendCommandToGateway(getThing().getUID().getId().toString(), command.toString());
                    }
                }
            }
        }
    }

    /**
     *
     * @param state
     */
    public void update(ChannelUID channelUID, State state) {
        updateState(channelUID, state);
    }

    @Override
    public void initialize() {

        System.out.println("\n Class: OpenoceanHandler. Method: initialize. Initializing of EnOcean device.\n");
        long startTime = System.currentTimeMillis();

        if (getEnoceanBridgeHandler() != null) {
            deviceInitialize(getThing());
            ThingStatusInfo statusInfo = getBridge().getStatusInfo();
            updateStatus(statusInfo.getStatus(), statusInfo.getStatusDetail(), statusInfo.getDescription());
            // deviceInitialize(getThing());
            long endTime = System.currentTimeMillis();
            System.out.println(
                    "Total elapsed time in execution of method initialize() is :" + (endTime - startTime) / 1000);

            if (HandleEventFromGWEO.isAlive() == false) {
                HandleEventFromGWEO.start();
            }
        } else {

        }
    }

    private void deviceInitialize(Thing thing) {
        HashMap<String, String> hmapState = DeviceHandler.initialize2();
        HashMap<String, String> hmapChannelUID = EnoceanChannelUID.getChannelUID();
        for (String deviceId : hmapState.keySet()) {
            if (deviceId.equalsIgnoreCase(thing.getUID().getId())) {
                String state = hmapState.get(deviceId);
                String channelId = hmapChannelUID.get(deviceId);
                eepParserChoose(null, deviceId, state, channelId);
            }
        }
    }

    @Override
    public void dispose() {
        deviceId = getThing().getUID().getId().toString();
        if (deviceId != null) {
            EnoceanBridgeHandler bridgeHandler = getEnoceanBridgeHandler();
            if (bridgeHandler != null) {
                getEnoceanBridgeHandler().unregisterDeviceStatusListener(this);
            }
            deviceId = null;
        }
    }

    private synchronized EnoceanBridgeHandler getEnoceanBridgeHandler() {
        if (this.bridgeHandler == null) {
            Bridge bridge = getBridge();
            if (bridge == null) {
                return null;
            }
            ThingHandler handler = bridge.getHandler();
            if (handler instanceof EnoceanBridgeHandler) {
                this.bridgeHandler = (EnoceanBridgeHandler) handler;
            } else {
                return null;
            }
        }
        return this.bridgeHandler;
    }

    /**
     * Cette methode permet de update un device lorsque son etat a changé
     *
     * @param eoBridge
     * @param deviceId2
     * @param device
     */
    @Override
    public void onDeviceStateChanged(EnoceanBridge eoBridge, String deviceId2, EoGwDevice device) {
        if (deviceId2.equals(device.getDevice().getDeviceId())) {
            String deviceState = null;
            if (device.getDevice().getStates()[0].getFunctions()[0].getValue() == null) {
                deviceState = "0";
            } else {
                deviceState = device.getDevice().getStates()[0].getFunctions()[0].getValue();
            }
            if (deviceState != null) {
                updateStatus(ThingStatus.ONLINE);
            } else {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.NONE, "Bridge reports device as not reachable");
            }
        }
    }

    @Override
    public void onDeviceRemove(EnoceanBridge eoBridge, String deviceId2, EoGwDevices device) {
        System.out.println("class: OpenceanHandler. Methode: onDeviceRemove. start to remove device.");
        if (deviceId2.equals(deviceId)) {
            updateStatus(ThingStatus.OFFLINE);
        }
    }

    @Override
    public void onDeviceAdded(EnoceanBridge eoBridge, String friendlyId, String deviceId2, EoGwDevice device) {
        System.out.println("class: OpenceanHandler. Methode: onDeviceAdded. start to add device : " + deviceId2);

        if (device.getDevice().getDeviceId().equals(deviceId2)) {

        }
    }
}
