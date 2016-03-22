/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.smarthome.binding.openocean;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link openoceanBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Mambou - Initial contribution
 */
public class openoceanBindingConstants {

    public static final String BINDING_ID = "openocean";

    // List of all Thing Type UIDs
    // public final static ThingTypeUID THING_TYPE_EltakoLight = new ThingTypeUID(BINDING_ID, "Switch_Actuator");
    // public final static ThingTypeUID THING_TYPE_AfrisoCO2 = new ThingTypeUID(BINDING_ID, "AfrisoCO2");
    // public final static ThingTypeUID THING_TYPE_Fenster = new ThingTypeUID(BINDING_ID, "Fenster_KiZi");
    public final static ThingTypeUID THING_TYPE_BRIDGE = new ThingTypeUID(BINDING_ID, "enoceanBridge");
    public static String fileName = "../../../smarthome/extensions/binding/org.eclipse.smarthome.binding.openocean/ESH-INF/database/database.json";

    // List of all Channel ids
    // public final static String CHANNEL_1 = "brightness";
    // public final static String CHANNEL_2 = "Number";
    // public final static String CHANNEL_3 = "Contact";
    // public final static String CHANNEL_4 = "Rollershutter";
    public final static String CHANNEL_1 = "switch";
    public final static String CHANNEL_2 = "co2";
    public final static String CHANNEL_3 = "contact";
    public final static String CHANNEL_4 = "dimValue";
    // public final static String CHANNEL_5 = "newDeviceCh";

    // Bridge config properties
    // public static final String HOST = "http://172.28.28.150";
    public static final String HOST = "http://172.28.28.103";
    // public static final String HOST = "http://192.168.178.29";
    public static final String PORT = "8080";
    // public static final String IPADRESS = "192.168.178.29:8080";
    public static final String IPADRESS = "172.28.28.103:8080";
    // public static final String IPADRESS = "172.28.28.150:8080";
    public static final String USER_NAME = "admin";
    public static final String PASSWORD = "admin";
    public static final String SERIAL_NUMBER = "123456789";

    public static final String Device_ID = "deviceId";
}
