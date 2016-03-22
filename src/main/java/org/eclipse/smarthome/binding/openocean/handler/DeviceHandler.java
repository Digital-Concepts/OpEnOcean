package org.eclipse.smarthome.binding.openocean.handler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.smarthome.binding.openocean.api.devices.Devices;
import org.eclipse.smarthome.binding.openocean.api.devices.EoGwDevice;
import org.eclipse.smarthome.binding.openocean.api.devices.EoGwDevices;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DeviceHandler {

    static String stateDevice = null;
    static EoGwDevices device = null;

    public static HttpURLConnection connectToDeviceID(String deviceID) throws MalformedURLException {
        UrlSetting myUrlSet = new UrlSetting();
        HttpURLConnection myURLCon = EnoceanBridge.connection(new URL(myUrlSet.getDeviceIdStreamURL(deviceID)));
        return myURLCon;
    }

    /**
     *
     * @return
     */
    public static HttpURLConnection connectToAllDevice() {
        UrlSetting myUrlSet = new UrlSetting();
        HttpURLConnection myURLCon = null;
        try {
            myURLCon = EnoceanBridge.connection(new URL(myUrlSet.getDeviceListStreamURL()));
        } catch (Exception ex) {
            System.out.println("Class: DeviceHandler. Method: connectToDeviceID. Error: " + ex);
        }

        return myURLCon;
    }

    /**
     * return the eep (profile) of device
     *
     * @param deviceid
     * @return
     */
    public static String getEepProfile(String jsonString) {
        try {
            JsonFactory jfactory = new JsonFactory();
            JsonParser jParser = jfactory.createJsonParser(jsonString);
            JsonToken token;

            while (!jParser.isClosed()) {
                token = jParser.nextToken();
                if ((JsonToken.FIELD_NAME.equals(token)) && ("profile".equals(jParser.getCurrentName()))) {
                    while (true) {
                        token = jParser.nextToken();
                        if (token == null) {
                            break;
                        }
                        if (JsonToken.FIELD_NAME.equals(token) && "eep".equals(jParser.getCurrentName())) {
                            token = jParser.nextToken();
                            jsonString = jParser.getText();
                        }
                    }
                }
            }
            return jsonString;
        } catch (Exception ex) {
            System.out.println("DeviceHandler_getEepProfile-Methode :" + ex);
        }
        return null;
    }

    /**
     *
     * This Methode return the JSON representation of all Device
     * that are in the EnOcean-Gateway
     *
     * @return String
     * @throws MalformedURLException
     * @throws IOException
     */

    public static String getAllDevice() throws MalformedURLException, IOException {
        try {
            UrlSetting urls = new UrlSetting();
            String urlString = urls.getDeviceListURL();
            urls.setUrl(urlString);
            HttpURLConnection myURLCon = EnoceanBridge.connection(urls.getUrl());
            String allDevices = DisplayHandler.getJSONAnzeige(myURLCon);
            return allDevices;
        } catch (Exception ex) {
            System.out.println("DeviceHandler.getAllDevice(): " + ex);
        }
        return null;
    }

    /**
     * This method return the JSON-representation of device ID
     *
     * @param deviceId
     * @return String
     */
    @SuppressWarnings("unused")
    private static String getDeviceId(String deviceId) {
        try {
            UrlSetting myUrlSet = new UrlSetting();
            HttpURLConnection myURLCon = EnoceanBridge.connection(new URL(myUrlSet.getDeviceIdURL(deviceId)));
            String devId = DisplayHandler.getJSONAnzeige(myURLCon);
            myURLCon.disconnect();
            return devId;
        } catch (Exception ex) {
            System.out.println("DeviceHandler_getDeviceId " + ex);
        }
        return null;
    }

    /**
     *
     * @param deviceId
     * @return
     */
    public static String getDeviceIdProfileJson(String deviceId) {
        try {
            UrlSetting urls = new UrlSetting();
            urls.setUrl(urls.getDeviceIdURL(deviceId));
            HttpURLConnection myURLCon = EnoceanBridge.connection(urls.getUrl());
            String jsonString = DisplayHandler.getJSONAnzeige(myURLCon);

            return jsonString;
        } catch (Exception ex) {
            System.out.println("Class: DeviceHandler. Method: getDeviceIdProfileJson. Error: " + ex);
        }
        return null;
    }

    /**
     *
     * @param deviceId
     * @return
     */
    public static EoGwDevices getProfileByEepId(String EeId) {
        String eep = "DefaufltDevice";
        EoGwDevices eoGwDevices = null;
        try {
            UrlSetting urls = new UrlSetting();
            urls.setUrl(urls.getProfileByEepId(EeId));
            HttpURLConnection myURLCon = EnoceanBridge.connection(urls.getUrl());
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = DisplayHandler.getJSONAnzeige(myURLCon);
            eoGwDevices = mapper.readValue(jsonString, EoGwDevices.class);
        } catch (Exception ex) {
            System.out.println("Class: DeviceHandler. Method: getProfileByDeviceId. Error: " + ex);
        }
        return eoGwDevices;
    }

    /**
     *
     * @param deviceId
     * @return
     */
    public static String getProfileByDeviceId(String deviceId) {
        try {
            System.out.println("device Id : " + deviceId);
            String jsonDevice = getDeviceIdProfileJson(deviceId);
            ObjectMapper mapper = new ObjectMapper();
            EoGwDevice eoGwDevice = mapper.readValue(jsonDevice, EoGwDevice.class);
            System.out.println("device.getProfile : " + eoGwDevice.getDevice().getEep());
            String eep = "DefaufltDevice";
            if (eoGwDevice.getDevice().getEep() != null) {
                eep = eoGwDevice.getDevice().getEep();
            }

        } catch (Exception ex) {
            System.out.println("Class: DeviceHandler. Method: getProfileByDeviceId. Error: " + ex);
        }
        return null;
    }

    /**
     *
     * @param deviceId
     * @return
     */
    public static String getDeviceIdStreamJson(String deviceId) {
        try {
            UrlSetting myUrlSet = new UrlSetting();
            myUrlSet.setUrl(myUrlSet.getDeviceIdStreamURL(deviceId));
            HttpURLConnection myURLCon = EnoceanBridge.connection(myUrlSet.getUrl());
            String devIdStr = getFromJsonStateLight(myURLCon);
            return devIdStr;
        } catch (Exception ex) {
            System.out.println("DeviceHandler getDeviceIdStream-methode: " + ex);
        }
        return null;
    }

    /**
     *
     * @param myURLCon
     * @return
     * @throws IOException
     */
    public static String getFromJsonStateLight(HttpURLConnection myURLCon) throws IOException {
        InputStream ins = new BufferedInputStream(myURLCon.getInputStream());
        String jsonNode = "";
        int data = ins.read();
        String str = Character.toString((char) data);
        int cpt1 = 0;
        int cpt2 = 0;
        int i = 0;
        while (data != -1) {
            jsonNode += str;
            if (str.contains("{")) {
                cpt1 += 1;
            }
            if (str.contains("}")) {
                cpt2 += 1;
            }
            if ((cpt1 == cpt2)) {
                i++;
                if (jsonNode != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    device = mapper.readValue(jsonNode, EoGwDevices.class);
                    stateDevice = device.getDevices()[0].getStates().get(0).getFunctions()[0].getValue();
                    // if (i <= 1) {
                    // stateDevice = device.getDevices()[0].getStates()[0].getFunctions()[0].getValue();
                    // jsonNode = null;
                    // }
                    // if (i > 1) {
                    // stateDevice = device.getDevices()[0].getStates()[0].getFunctions()[0].getValue();
                    // jsonNode = null;
                    // }
                }
            }
            data = ins.read();
            str = Character.toString((char) data);
        }
        return jsonNode;
    }

    /**
     * this method save the device eep and return a List of key value with key
     * = id of device and the value = eep of device
     *
     * @param deviceIdList
     * @return
     */
    public static HashMap<String, String> getDeviceIdProfile(ArrayList<String> deviceIdList) {
        try {
            HashMap<String, String> hmap1 = new HashMap<>();

            for (String deviceId : deviceIdList) {
                String eepJson = getDeviceIdProfileJson(deviceId);
                String eep = getEepProfile(eepJson);
                hmap1.put(deviceId, eep);
                // DevicesIdWithProfile.profile = hmap1;
            }
            DevicesIdWithProfile.profile.putAll(hmap1);
        } catch (Exception ex) {
            System.out.println("Class: DeviceHandler. Methode: getDeviceIdProfile. Error: " + ex);
        }
        return DevicesIdWithProfile.profile;
    }

    /**
     *
     * @param devId
     * @return
     */
    public static String getFriendlyIdByDeviceId(String devId) {
        UrlSetting urls = new UrlSetting();
        urls.setUrl(urls.getDeviceIdURL(devId));
        HttpURLConnection myURLCon;
        try {
            myURLCon = EnoceanBridge.connection(urls.getUrl());
            String jsonString = DisplayHandler.getJSONAnzeige(myURLCon);
            ObjectMapper mapper = new ObjectMapper();
            EoGwDevice eoGwDevice = mapper.readValue(jsonString, EoGwDevice.class);
            System.out.println("device Id : " + devId);
            System.out.println("device.getFriendlyId() : " + eoGwDevice.getDevice().getFriendlyId());
            String friendlyId = "DefaufltDevice";
            if (eoGwDevice.getDevice().getFriendlyId() != null) {
                friendlyId = eoGwDevice.getDevice().getFriendlyId();
            }
            return friendlyId;
        } catch (Exception e) {
            System.out.println("class: DeviceHandler. Method: getFriendlyIdByDeviceId. Error: " + e);
        }

        return null;
    }

    /**
     *
     * @param command
     * @return
     */
    public static int sendCommandToGateway(String deviceId, String command) {
        int status = 0;
        try {
            String jsonCommand = null;
            UrlSetting myUrlSet = new UrlSetting();
            myUrlSet.setUrl(myUrlSet.getDeviceIdStateURL(deviceId));
            HttpURLConnection myURLCon = EnoceanBridge.connection(myUrlSet.getUrl());
            myURLCon.setRequestMethod("PUT");
            myURLCon.setDoOutput(true);
            myURLCon.setRequestProperty("Connection", "Keep-Alive");
            myURLCon.setRequestProperty("Content-Type", "application/json");
            myURLCon.setRequestProperty("Accept", "application/json");
            OutputStreamWriter out = new OutputStreamWriter(myURLCon.getOutputStream());
            if (command.equalsIgnoreCase("OFF")) {
                System.out.println("sendCommandToGateway.command: " + deviceId + " is " + command);
                jsonCommand = "{\"state\" : {\"functions\" : [ {\"key\" :" + " \"dimValue\",\"value\" : \"0\" } ] } }";
            }
            if (command.equalsIgnoreCase("ON")) {
                System.out.println("sendCommandToGateway.command: " + deviceId + " is " + command);
                jsonCommand = "{\"state\" : {\"functions\" : [ {\"key\" :" + " \"dimValue\",\"value\" : \"1\" } ] } }";
            }
            out.write(jsonCommand);
            out.flush();
            out.close();
            status = myURLCon.getResponseCode();
        } catch (Exception ex) {
            System.out.println(
                    "class: DeviceHandler. Methode: sendCommandToGateway. Error with the DeviceHandler.sendCommandtoGateway-methode : "
                            + ex);
        }
        return status;
    }

    /**
     *
     */
    public static HashMap<String, String> initialize2() {

        System.out.println("\n Class: DeviceHandler. Method: initialize2(). Start to initialize all device\n");

        String allDevice = "";
        HashMap<String, String> hmap = new HashMap<>();
        ArrayList<String> listOfDeviceId = new ArrayList<String>();
        String value = null;
        HttpURLConnection resultURLListOfDeviceId = null;
        String strId = null;

        try {
            if (ListOfConnectedDeviceId.getListOfAllDeviceId().isEmpty()) {
                allDevice = getAllDevice();
                listOfDeviceId = EnoceanBridge.getListDeviceId(allDevice);
            } else {
                listOfDeviceId = ListOfConnectedDeviceId.getListOfAllDeviceId();
            }
            DevicesIdWithProfile.profile = getDeviceIdProfile(listOfDeviceId);

            resultURLListOfDeviceId = connectToAllDevice();
            String jsonDeviceId = DisplayHandler.getJSONAnzeige(resultURLListOfDeviceId);
            ObjectMapper mapper = new ObjectMapper();
            EoGwDevices device = mapper.readValue(jsonDeviceId, EoGwDevices.class);

            for (Devices dev : device.getDevices()) {

                if (dev.getStates().size() != 0) {

                    String channel = dev.getStates().get(0).getKey();
                    strId = dev.getDeviceId();

                    if (dev.getStates().size() == 0) {
                        value = "0";
                    } else {
                        value = dev.getStates().get(0).getValue();
                    }
                    EnoceanChannelUID.channelUID.put(strId, channel);
                    hmap.put(strId, value);

                }
            }
            DevicesIdWithProfile.statesofDevice = hmap;
            System.out.println("\n Class: DeviceHandler. Method: initialize2(). End to initialize all device\n");
            return DevicesIdWithProfile.statesofDevice;

        } catch (Exception ex) {
            System.out.println("Class: DeviceHandler. Method: initialize2. Error: " + ex);
        }

        return null;

    }

    /**
     *
     * @return
     */
    public static List<EoGwDevices> getDeviceList() {
        String allDevice;
        try {
            allDevice = getAllDevice();
            ArrayList<String> listOfDeviceId = EnoceanBridge.getListDeviceId(allDevice);
            List<EoGwDevices> deviceList = null;
            for (String devId : listOfDeviceId) {
                // String jsonString =
                // je prends le device id, apres j'appelle sonjson-datei
                // ensuite je ...
            }
        } catch (Exception e) {
            System.out.println("Class: DeviceHandler. Methode: getDeviceList. Error:" + e);
        }
        return null;
    }

    /**
     *
     */
    public static void removeDeviceOnGW(String deviceId) {
        try {
            System.out.println(
                    "\n Class: DeviceHandler. Method: removeDeviceOnGW. start to remove the device id: " + deviceId);
            String jsonCommand = null;
            UrlSetting myUrlSet = new UrlSetting();
            myUrlSet.setUrl(myUrlSet.getDeviceIdURL(deviceId));
            HttpURLConnection myURLCon = EnoceanBridge.connection(myUrlSet.getUrl());
            myURLCon.setDoOutput(true);
            myURLCon.setRequestProperty("Content-Type", "application/json");
            myURLCon.setRequestMethod("DELETE");
            System.out.println(
                    "\n Class: DeviceHandler. Method: removeDeviceOnGW. Info: " + myURLCon.getResponseMessage());

        } catch (Exception ex) {
            System.out.println("Class: DeviceHandler. Method: removeDeviceOnGW. Error: " + ex);
        }
    }

    public static void learnInProcess() {
        System.out.println("Class: DeviceHandler. Method: learnInProcess. Start the Learn-In mode");

        UrlSetting myUrlSet = new UrlSetting();
        myUrlSet.setUrl(myUrlSet.getLearnInURL());
        HttpURLConnection myURLCon;
        try {
            myURLCon = EnoceanBridge.connection(myUrlSet.getUrl());
            myURLCon.setDoOutput(true);
            myURLCon.setRequestProperty("Content-Type", "application/json");
            myURLCon.setRequestMethod("POST");
            OutputStreamWriter out = new OutputStreamWriter(myURLCon.getOutputStream());
            String jsonlearnInMode = "{\"receiveMode\" : \"learnMode\"}";
            out.write(jsonlearnInMode);
            out.flush();
            out.close();
            int status = myURLCon.getResponseCode();
            System.out.println(status);
        } catch (Exception ex) {
            System.out.println("Class: DeviceHandler. Method: learnInProcess. Error: " + ex);
        }
    }

    /**
     *
     */
    public static void learnInProcessClose() {
        System.out.println("Class: DeviceHandler. Method: learnInProcess.Learn-In mode is close");

        UrlSetting myUrlSet = new UrlSetting();
        myUrlSet.setUrl(myUrlSet.getLearnInURL());
        HttpURLConnection myURLCon;
        try {
            myURLCon = EnoceanBridge.connection(myUrlSet.getUrl());
            myURLCon.setDoOutput(true);
            myURLCon.setRequestProperty("Content-Type", "application/json");
            myURLCon.setRequestMethod("POST");
            OutputStreamWriter out = new OutputStreamWriter(myURLCon.getOutputStream());
            String jsonlearnInMode = "{\"receiveMode\" : \"normalMode\"}";
            out.write(jsonlearnInMode);
            out.flush();
            out.close();
            int status = myURLCon.getResponseCode();
            System.out.println(status);
            if (status == 200) {
                System.out.println("Class: DeviceHandler. Method: learnInProcess.Learn-In mode is close");
            }

        } catch (Exception ex) {
            System.out.println("Class: DeviceHandler. Method: learnInProcess. Error: " + ex);
        }
    }

    public static void createXmlFileFromProfile(EoGwDevice eoGwDevice) {

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.newDocument();
            String thingID = eoGwDevice.getDevice().getFriendlyId();
            final Element racine = document.createElement("thing:thing-descriptions");
            racine.setAttribute("bindingId", "openocean");
            racine.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            racine.setAttribute("xmlns:thing", "http://eclipse.org/smarthome/schemas/thing-description/v1.0.0");
            racine.setAttribute("xsi:schemaLocation",
                    "http://eclipse.org/smarthome/schemas/thing-description/v1.0.0 http://eclipse.org/smarthome/schemas/thing-description-1.0.0.xsd");
            document.appendChild(racine);

            final Comment commentaire = document.createComment("Sample Thing Type");
            racine.appendChild(commentaire);

            final Element thing_type = document.createElement("thing-type");
            thing_type.setAttribute("id", thingID);
            racine.appendChild(thing_type);

            final Element things = document.createElement("supported-bridge-type-refs");
            final Element things2 = document.createElement("bridge-type-ref");
            things2.setAttribute("id", "enoceanBridge");
            things.appendChild(things2);
            thing_type.appendChild(things);

            final Element label = document.createElement("label");
            label.appendChild(document.createTextNode("New Thing"));

            final Element description = document.createElement("description");
            description.appendChild(document.createTextNode("This is a new Thing."));
            thing_type.appendChild(label);
            thing_type.appendChild(description);

            // channel generation
            final Element channels = document.createElement("channels");

            int taille = eoGwDevice.getDevice().getTransmitModes().size();
            System.out.println("Class: DeviceHandler. Method: createXmlFileFromProfile. taille : " + taille);
            for (int i = 0; i < taille; i++) {
                final Element channel = document.createElement("channel");
                channel.setAttribute("id", eoGwDevice.getDevice().getTransmitModes().get(i).getKey());
                channel.setAttribute("typeId", eoGwDevice.getDevice().getTransmitModes().get(i).getKey());
                channels.appendChild(channel);
            }
            thing_type.appendChild(channels);

            final Comment commentaire2 = document.createComment("Sample channel Type");
            racine.appendChild(commentaire2);
            System.out.println("start to create itemType");
            String itemType = "Number";
            for (int i = 0; i < taille; i++) {

                final Element channel_type = document.createElement("channel-type");
                channel_type.setAttribute("id", eoGwDevice.getDevice().getTransmitModes().get(i).getKey());
                racine.appendChild(channel_type);

                final Element item_type = document.createElement("item-type");

                if (eoGwDevice.getDevice().getTransmitModes().get(i).getKey().equalsIgnoreCase("Switch")) {
                    itemType = "Switch";
                } else if (eoGwDevice.getDevice().getTransmitModes().get(i).getKey().equalsIgnoreCase("Contact")) {
                    itemType = "Contact";
                } else {
                    itemType = "String";
                }

                item_type.appendChild(document.createTextNode(itemType));
                final Element label2 = document.createElement("label");
                label2.appendChild(document.createTextNode(itemType));
                final Element description2 = document.createElement("description");
                description2.appendChild(document.createTextNode("this is a desciption"));
                final Element category = document.createElement("category");
                category.appendChild(document.createTextNode("this is a category"));
                channel_type.appendChild(item_type);
                channel_type.appendChild(label2);
                channel_type.appendChild(description2);
                channel_type.appendChild(category);
            }
            System.out.println("ende to create itemType");
            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
            final DOMSource source = new DOMSource(document);
            String fileName = eoGwDevice.getDevice().getFriendlyId();
            File toto = new File(
                    "../../../smarthome/extensions/binding/org.eclipse.smarthome.binding.openocean/ESH-INF/thing/"
                            + fileName + ".xml");
            final StreamResult sortie = new StreamResult(toto);
            System.out.println(toto.getAbsolutePath());

            transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            // transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            transformer.transform(source, sortie);
        } catch (Exception ex) {
            System.out.println("Class: DeviceHandler. Method: createXmlFileFromProfile. Error: " + ex);
        }

    }

    public static int sendDeviceToGW(String deviceId, String jsonNewDevice) {
        int status = 0;
        try {
            System.out.println(
                    "class: DeviceHandler. Methode: sendDeviceToGW. Info : Prepare to send new device on the Gateway");
            UrlSetting myUrlSet = new UrlSetting();
            myUrlSet.setUrl(myUrlSet.getDeviceIdURL(deviceId));
            HttpURLConnection myURLCon = EnoceanBridge.connection(myUrlSet.getUrl());
            myURLCon.setRequestMethod("POST");
            myURLCon.setDoOutput(true);
            myURLCon.setRequestProperty("Content-Type", "application/json");
            myURLCon.setRequestProperty("Accept", "application/json");
            OutputStreamWriter out = new OutputStreamWriter(myURLCon.getOutputStream());
            out.write(jsonNewDevice);
            out.flush();
            out.close();
            status = myURLCon.getResponseCode();

            if (status == 200) {
                System.out.println(
                        "class: DeviceHandler. Methode: sendDeviceToGW. Status POST : " + status + ". Success");
                // ListOfConnectedDeviceId.listOfAllDeviceId.add(deviceId);
                // EnoceanBridge.getListDeviceId(EnoceanBridge.getAllDevice());
                // System.out.println("class: DeviceHandler. Methode: sendDeviceToGW. Status POST : "
                // + EnoceanBridge.getListDeviceId(EnoceanBridge.getAllDevice()) + ". Success");
            } else {
                System.out.println(
                        "class: DeviceHandler. Methode: sendDeviceToGW. Status POST : " + status + ". Failure");
            }
        } catch (Exception ex) {
            System.out.println("class: DeviceHandler. Methode: sendDeviceToGW. Error: " + ex);
        }
        return status;
    }
}
