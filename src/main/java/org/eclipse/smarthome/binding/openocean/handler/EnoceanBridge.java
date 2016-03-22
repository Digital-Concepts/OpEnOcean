package org.eclipse.smarthome.binding.openocean.handler;

import static org.eclipse.smarthome.binding.openocean.openoceanBindingConstants.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import sun.misc.BASE64Encoder;

public class EnoceanBridge {

    String host = HOST + ":" + PORT;
    String ipAdress = IPADRESS;

    public EnoceanBridge() {
        host = HOST + ":" + PORT;
    }

    public EnoceanBridge(String myHost) {
        this.host = myHost;
    }

    /**
     * This methode enables the connection to the EnOcean
     * and return an HttpURLConnection
     *
     * @param myUrl
     * @return HttpURLConnection
     */
    @SuppressWarnings("restriction")
    public void authenticate(String userName, String password) {
        // TODO Auto-generated method stub
        UrlSetting urls = new UrlSetting();
        HttpURLConnection myURLCon;
        URL myURL;
        try {
            myURL = urls.getUrl();
            myURLCon = (HttpURLConnection) myURL.openConnection();
            BASE64Encoder enc = new sun.misc.BASE64Encoder();
            String authString = userName + ":" + password;
            String encodedAuthorization = enc.encode(authString.getBytes());
            myURLCon.setRequestProperty("Authorization", "Basic " + encodedAuthorization);
        } catch (Exception e) {
            System.out.println("class: EnoceanBridge. Methode: authenticate. Error: failed to authenticate " + e);
        }

    }

    @SuppressWarnings("restriction")
    public static HttpURLConnection connection(URL myUrl) {
        try {

            HttpURLConnection myURLCon = (HttpURLConnection) myUrl.openConnection();
            BASE64Encoder enc = new sun.misc.BASE64Encoder();
            String user = USER_NAME;
            String pwd = PASSWORD;
            String authString = user + ":" + pwd;
            String encodedAuthorization = enc.encode(authString.getBytes());
            myURLCon.setRequestProperty("Authorization", "Basic " + encodedAuthorization);
            myURLCon.setRequestProperty("Connection", "Keep-Alive");
            return myURLCon;
        } catch (Exception ex) {
            System.out.println("class: EnoceanBridge. Methode: connection. Error by logging: " + ex);
        }
        return null;
    }

    /**
     *
     * @param myUrlString
     * @return
     */
    @SuppressWarnings("restriction")
    public static HttpURLConnection connection(String myUrlString) {
        try {
            UrlSetting urls = new UrlSetting();
            urls.setUrl(myUrlString);
            URL myURL = urls.getUrl();
            HttpURLConnection myURLCon = (HttpURLConnection) myURL.openConnection();
            BASE64Encoder enc = new sun.misc.BASE64Encoder();
            String user = "admin";
            String pwd = "admin";
            String authString = user + ":" + pwd;
            String encodedAuthorization = enc.encode(authString.getBytes());
            myURLCon.setRequestProperty("Authorization", "Basic " + encodedAuthorization);
            myURLCon.setRequestProperty("Connection", "Keep-Alive");
            return myURLCon;
        } catch (Exception ex) {
            System.out.println(
                    "class: EnoceanBridge. Methode: connection. Error by logging with the methode connection(): " + ex);
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
            UrlSetting myUrlSet = new UrlSetting();
            String urlString = myUrlSet.getDeviceListURL();
            myUrlSet.setUrl(urlString);
            HttpURLConnection myURLCon = connection(myUrlSet.getUrl());
            String allDevices = DisplayHandler.getJSONAnzeige(myURLCon);
            return allDevices;
        } catch (Exception ex) {
            System.out.println("class: EnoceanBridge. Methode: getAllDevice. Error: " + ex);
        }
        return null;
    }

    /**
     *
     * @param myURLCon
     */
    public static void disconnect(HttpURLConnection myURLCon) {
        myURLCon.disconnect();
    }

    /**
     * This method save all Id of device that was saved in EnOcean-Gateway
     * return a list of device ID
     *
     * @param allDevice
     * @return a List of device ID
     */
    public static ArrayList<String> getListDeviceId(String allDevice) {
        ArrayList<String> deviceIdList = new ArrayList<>();
        String jsonString = allDevice;
        try {
            JsonFactory jfactory = new JsonFactory();
            JsonParser jParser = jfactory.createJsonParser(jsonString);
            JsonToken token;

            while (!jParser.isClosed()) {
                token = jParser.nextToken();
                if ((JsonToken.FIELD_NAME.equals(token)) && ("deviceId".equals(jParser.getCurrentName()))) {
                    jParser.nextToken();
                    deviceIdList.add(jParser.getText());
                }

            }
            if (ListOfConnectedDeviceId.getListOfAllDeviceId().isEmpty()) {
                ListOfConnectedDeviceId.setListOfAllDeviceId(deviceIdList);
            }

            return deviceIdList;
        } catch (Exception ex) {
            System.out.println("Class: EnoceanBridge. Methode: getListDeviceId. Error:" + ex);
        }
        return null;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setTimeout(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException ex) {

        }
    }

    public void setDeviceState(String deviceId, String stateUpdate) {
        DeviceHandler.sendCommandToGateway(deviceId, stateUpdate);
    }

    public Object getFullConfig() {
        // TODO Auto-generated method stub
        return null;
    }

    public ArrayList<String> getDevices() {
        // TODO Auto-generated method stub
        // return DeviceHandler.getDeviceList();
        return ListOfConnectedDeviceId.getListOfAllDeviceId();
    }

    public void startSearch() {
        // TODO Auto-generated method stub

    }

    public void startSearch(List<String> serialNumbers) {
        // TODO Auto-generated method stub

    }

    public void authenticate(String string) {
        // TODO Auto-generated method stub

    }

}
