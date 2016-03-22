/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.smarthome.binding.openocean.internal;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.eclipse.smarthome.binding.openocean.openoceanBindingConstants;
import org.eclipse.smarthome.binding.openocean.handler.DeviceHandler;
import org.eclipse.smarthome.binding.openocean.handler.EnoceanBridgeHandler;
import org.eclipse.smarthome.binding.openocean.handler.openoceanHandler;
import org.eclipse.smarthome.binding.openocean.internal.discovery.EnoceanDeviceDiscoveryService;
import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.osgi.framework.ServiceRegistration;

import com.google.common.collect.Sets;

/**
 * The {@link openoceanHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Mambou - Initial contribution
 */
public class openoceanHandlerFactory extends BaseThingHandlerFactory {

    public final static Set<ThingTypeUID> SUPPORTED_THING_TYPES = Sets.union(EnoceanBridgeHandler.SUPPORTED_THING_TYPES,
            openoceanHandler.SUPPORTED_THING_TYPES);

    private Map<ThingUID, ServiceRegistration<?>> discoveryServiceRegs = new HashMap<>();

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES.contains(thingTypeUID);
    }

    @Override
    public Thing createThing(ThingTypeUID thingTypeUID, Configuration configuration, ThingUID thingUID,
            ThingUID bridgeUID) {
        if (EnoceanBridgeHandler.SUPPORTED_THING_TYPES.contains(thingTypeUID)) {
            ThingUID enoceanBridgeUID = getBridgeThingUID(thingTypeUID, thingUID, configuration);
            return super.createThing(thingTypeUID, configuration, enoceanBridgeUID, null);
        } else if (openoceanHandler.SUPPORTED_THING_TYPES.contains(thingTypeUID)) {
            ThingUID enoceanDeviceUID = getDeviceUID(thingTypeUID, thingUID, configuration, bridgeUID);
            Thing toto = super.createThing(thingTypeUID, configuration, enoceanDeviceUID, bridgeUID);

            return toto;
        } else {
            System.out.println("class: openoceanHandlerFactory. Methode: createThing. info: something is new");
        }
        throw new IllegalArgumentException(
                "The thing type " + thingTypeUID + " is not supported by the Enocean binding.");
    }

    /**
     *
     * @param thingTypeUID
     * @param thingUID
     * @param configuration
     * @param bridgeUID
     * @return
     */
    private ThingUID getDeviceUID(ThingTypeUID thingTypeUID, ThingUID thingUID, Configuration configuration,
            ThingUID bridgeUID) {
        String deviceId = (String) configuration.get(openoceanBindingConstants.Device_ID);
        if (thingUID == null) {
            thingUID = new ThingUID(thingTypeUID, deviceId, bridgeUID.getId());
        }
        return thingUID;
    }

    /**
     *
     * @param thingTypeUID
     * @param thingUID
     * @param configuration
     * @return
     */
    private ThingUID getBridgeThingUID(ThingTypeUID thingTypeUID, ThingUID thingUID, Configuration configuration) {
        if (thingUID == null) {
            String serialNumber = (String) configuration.get(openoceanBindingConstants.SERIAL_NUMBER);
            thingUID = new ThingUID(thingTypeUID, serialNumber);
        }
        return thingUID;
    }

    @Override
    protected ThingHandler createHandler(Thing thing) {

        if (EnoceanBridgeHandler.SUPPORTED_THING_TYPES.contains(thing.getThingTypeUID())) {
            EnoceanBridgeHandler handler = new EnoceanBridgeHandler((Bridge) thing);
            registerDeviceDiscoveryService(handler);
            return handler;
        } else if (openoceanHandler.SUPPORTED_THING_TYPES.contains(thing.getThingTypeUID())) {
            return new openoceanHandler(thing);
        } else {
            return null;
        }
    }

    private void registerDeviceDiscoveryService(EnoceanBridgeHandler bridgeHandler) {
        EnoceanDeviceDiscoveryService discoveryService = new EnoceanDeviceDiscoveryService(bridgeHandler);
        discoveryService.activate();
        this.discoveryServiceRegs.put(bridgeHandler.getThing().getUID(), bundleContext
                .registerService(DiscoveryService.class.getName(), discoveryService, new Hashtable<String, Object>()));
    }

    @Override
    public synchronized void removeHandler(ThingHandler thingHandler) {
        DeviceHandler.removeDeviceOnGW(thingHandler.getThing().getUID().getId());
        if (thingHandler instanceof EnoceanBridgeHandler) {
            ServiceRegistration<?> serviceReg = this.discoveryServiceRegs.get(thingHandler.getThing().getUID());
            if (serviceReg != null) {
                // remove discovery service, if bridge handler is removed
                EnoceanDeviceDiscoveryService service = (EnoceanDeviceDiscoveryService) bundleContext
                        .getService(serviceReg.getReference());
                service.deactivate();
                serviceReg.unregister();
                discoveryServiceRegs.remove(thingHandler.getThing().getUID());
            }

        }
    }

}
