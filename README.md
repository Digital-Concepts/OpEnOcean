# org.eclipse.smarthome.binding.openocean
This is the new EnOcean Binding  integrated in Eclipse smartHome from Digital Concepts gmbH.

This EnOcean-binding communicates with the new Gateway (DC-GW/EO-IP) from Digital Concepts 
and reads the state of all connected devices in JSON-form and performs them.
These fonction that have been implemented are:

      -discovery a new device.
  
      -remove a device.
  
      -update the state of device.
  
      -control a device (from user interface).

the devices that have been used for testing are:

      -Eltako FSSA-230V.
  
      -Afriso CO2 Sensor.
  
      -Eltako windows/door contact FTKB
  
      -Permundo SmartPlug PSC234 EnOcean
  
      -Rocker Switch, 2 Rocker, Light and blind Control
  
  
Installation of ESH

1.This Link shows to install ESH on Windows. The Point needs to be modify like on the Picture below. http://www.eclipse.org/smarthome/documentation/development/ide.html

ESH+OH2 Core Bundles need to be choose. 
Please double click on them. 
It must appear on the case below before you click on next

2.After the Installation, you need to import the EnOcean-Project, that would be created.

2.1.You need to create the Skeleton of the EnOcean-Binding. 					
See this link to know how to create a Skeleton on ESH: https://github.com/openhab/openhab-distro/blob/master/docs/sources/development/bindings.md 
This video shows also how to create a Skeleton of a Binding on ESH: https://www.youtube.com/watch?v=30nhm0yIcvA&feature=youtu.be

2.2.After the creation of the Skeleton of the EnOcean-Binding and its imports into the workspace, you can download EnOcean-Binding in Github-Repository (https://github.com/Digital-Concepts/OpEnOcean) and import the content into EnOcean-Binding that you created.

3.After the import you get some Errors on the file “EnoceanBridge.java”. To Correct you needs to configure Eclipse. First if this Error or Warning came from “BASE64Encode ()” than you can resolve like that: On click on Eclipse->Windows->Preferences->Java->Compiler->Errors/Warnings->Deprecated and restricted API –> Forbidden reference (access rules), change the default “Error” to “Warning “. 

4.In Principe you don’t get any more Errors from the Compiler after this. You need only to configure the user-interface in order to control the devices that are connect to the Gateway.

5.You need to configure the Host into the file “openoceanBindingConstants.java” in order to connect the System (ESH) to EnOcean-Gateway.  

  
