package org.eclipse.smarthome.binding.openocean.handler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class DisplayHandler {

    private static String echtzeitEvent;

    public DisplayHandler() {

    }

    /**
     *
     * @param test
     * @param message
     */
    public static void listAnzeigen(int test, String message) {
        if (test == 0) {
            System.out.println(message);
        }
    }

    /**
     *
     * @param test
     * @param message
     * @param param
     */
    public static void listAnzeigen(int test, String message, String param) {
        if (test == 0) {
            System.out.println("ErrorHandler.debugCode(...): " + message + " " + param + ".");
        }
    }

    /**
     * This method show JSON-representation of the URL
     *
     * @param myURLCon
     * @return String
     * @throws IOException
     */
    public static String getJSONAnzeige(HttpURLConnection myURLCon) throws IOException {
        InputStream ins = new BufferedInputStream(myURLCon.getInputStream());
        int data = ins.read();
        String str = "";
        String jsonNode = "";
        int cpt1 = 0, cpt2 = 0;
        while (data != -1) {
            str = Character.toString((char) data);
            jsonNode += str;
            if (str.contains("{")) {
                cpt1 += 1;
            }
            if (str.contains("}")) {
                cpt2 += 1;
            }
            if (cpt1 > cpt2) {
                data = ins.read();
            } else {
                return jsonNode;
            }
        }
        return jsonNode;
    }

    /**
     * This method show JSON-representation of the URL
     *
     * @param myURLCon
     * @return
     * @throws IOException
     */
    public static String getJSONAnzeige2(HttpURLConnection myURLCon) throws IOException {
        InputStream ins = new BufferedInputStream(myURLCon.getInputStream());
        int data = ins.read();
        String str = "";
        String jsonNode = "";
        while (data != -1) {
            str = Character.toString((char) data);
            jsonNode += str;
            data = ins.read();
        }

        return jsonNode;
    }

    /**
     * This method show JSON-representation of the URL
     *
     * @param myURLCon
     * @return
     * @throws IOException
     */
    public static String getJSONAnzeige3(HttpURLConnection myURLCon) throws IOException {
        InputStream ins = new BufferedInputStream(myURLCon.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(ins));
        String jsonNode = null;
        while (br != null) {
            String str = br.readLine();
            jsonNode += str;
            if (str == null) {
                break;
            }
        }

        return jsonNode;
    }

    /**
     * show the content of an ArrayList
     *
     * @param deviceIdList
     */
    public static void listAnzeigen(ArrayList<String> deviceIdList) {
        for (String e : deviceIdList) {
            listAnzeigen(0, "deviceId : " + e + "\n");
        }
    }

    public static <E> void listAnzeigen(Set<E> deviceIdList) {
        for (E e : deviceIdList) {
            listAnzeigen(0, "deviceId : " + e + "\n");
        }
    }

    /**
     * show the content of an ArrayList
     *
     * @param deviceIdList
     */
    public static void listAnzeigen(HashMap<String, String> hmap) {
        for (String deviceId : hmap.keySet()) {
            listAnzeigen(0, deviceId + " : " + hmap.get(deviceId));
        }
    }
}
