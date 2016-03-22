package org.eclipse.smarthome.binding.openocean.handler;

import java.util.HashMap;

public class EnoceanChannelUID {

    static HashMap<String, String> channelUID = new HashMap<String, String>();
    static String key;
    static String value;

    public static HashMap<String, String> getChannelUID() {
        return channelUID;
    }

    public static void setChannelUID(HashMap<String, String> channelUID) {
        EnoceanChannelUID.channelUID = channelUID;
    }

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        EnoceanChannelUID.key = key;
    }

    public static String getValue() {
        return value;
    }

    public static void setValue(String value) {
        EnoceanChannelUID.value = value;
    }

}
