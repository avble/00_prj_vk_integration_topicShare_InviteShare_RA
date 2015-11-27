/*******************************************************************************
 * Copyright (c) 1999, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 */

package remote.service.verik.com.remoteaccess.mqtt;


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.model.Device;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSHeavyDutySmart;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSMultilevelSensor5;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSMultilevelSensor6;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSSiren5;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSDoor_Window_Sensor;
import remote.service.verik.com.remoteaccess.model.DeviceGenericDimmer;
import remote.service.verik.com.remoteaccess.model.DeviceIR_SEC_SAFETYDoorLock;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdAssociationResp;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdBatteryResp;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdBinarySwitchResp;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdConfigurationResp;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdMultilevelResp;

/**
 * Handles call backs from the MQTT Client
 *
 */
public class MqttCallbackHandler implements MqttCallback {

    /** {@link Context} for the application used to format and import external strings**/
    private Context context;
    /** Client handle to reference the connection that this handler is attached to**/
    private String clientHandle;

    final String TAG = "MQTT-MqttCallbackHandler";

    /**
     * Creates an <code>MqttCallbackHandler</code> object
     * @param context The application's context
     * @param clientHandle The handle to a {@link Connection} object
     */
    public MqttCallbackHandler(Context context, String clientHandle)
    {
        this.context = context;
        this.clientHandle = clientHandle;
    }

    /**
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
     */
    @Override
    public void connectionLost(Throwable cause) {

        Log.d(TAG, "Connection to server has been lost, message: " + cause.getMessage());

//	  cause.printStackTrace();
        //FIXME
//
//        if (cause != null) {
//            Connection c = Connections.getInstance(context).getConnection(clientHandle);
//            c.addAction("Connection Lost");
//            c.changeConnectionStatus(ConnectionStatus.DISCONNECTED);
//
//            //format string to use a notification text
//            Object[] args = new Object[2];
//            args[0] = c.getId();
//            args[1] = c.getHostName();
//
//            String message = context.getString(R.string.connection_lost, args);
//
//            //build intent
//            Intent intent = new Intent();
//            intent.setClassName(context, "org.eclipse.paho.android.service.sample.ConnectionDetails");
//            intent.putExtra("handle", clientHandle);
//
//            //notify the user
//            Notify.notifcation(context, message, intent, R.string.notifyTitle_connectionLost);
//        }
    }

    /**
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {


        Log.d(TAG,"Received data from topic: "+topic+", Mqtt message: "+message.toString());

        if (RemoteAccessMsg.isMyMessage(message.toString()))
            return;

        String command = RemoteAccessMsg.getCommand(message.toString());
        String protocol = RemoteAccessMsg.getType(message.toString());

        JSONObject jason;



        if (command != null) {
            switch (command) {
                case RemoteAccessMsg.commandGetListDeviceR:
                    // fine-tuning the GUI
                    jason = new JSONObject(message.toString());


                    JSONArray deviceList = jason.getJSONArray("devicesList");

                    MainActivity.devices.clear();
                    //MainActivity.adapter.clear();

                    if (protocol.equals("zwave")) {

                        for (int i = 0; i < deviceList.length(); i++) {
                            JSONObject device = deviceList.getJSONObject(i);

                            String friendlyName = (String) device.get("FriendlyName");

                            String ID = (String) device.get("ID");
                            String serialNumber = (String) device.get("Serial");

                            String type = "zwave";

                            Device new_device;

                            if (Device.getDeviceTypeFromSerial(serialNumber).equals(Device.DEVICE_TYPE_Zwave_Aeotec_Smartdimmer)
                                    || Device.getDeviceTypeFromSerial(serialNumber).equals(Device.DEVICE_TYPE_Zwave_Smart_Dimmer)) {
                                new_device = new DeviceGenericDimmer(ID, friendlyName + " " + String.valueOf(i + 1), false, true, type);

                            } else if (Device.getDeviceTypeFromSerial(serialNumber).equals(Device.DEVICE_TYPE_Zwave_Heavy_Duty_Smart_Switch)) {
                                new_device = new DeviceAEON_LABSHeavyDutySmart(ID, friendlyName + " " + String.valueOf(i + 1), false, true, type);

                            } else if (Device.getDeviceTypeFromSerial(serialNumber).equals(Device.DEVICE_TYPE_Zwave_Sensor_Multilevel_6)) {

                                new_device = new DeviceAEON_LABSMultilevelSensor6(ID, friendlyName + " " + String.valueOf(i + 1), false, true, type);
                            } else if (Device.getDeviceTypeFromSerial(serialNumber).equals(Device.DEVICE_TYPE_Zwave_Sensor_Multilevel_Gen5)) {

                                new_device = new DeviceAEON_LABSMultilevelSensor5(ID, friendlyName + " " + String.valueOf(i + 1), false, true, type);
                            } else if (Device.getDeviceTypeFromSerial(serialNumber).compareToIgnoreCase(Device.DEVICE_TYPE_Zwave_Door_Lock) == 0) {

                                new_device = new DeviceIR_SEC_SAFETYDoorLock(ID, friendlyName + " " + String.valueOf(i + 1), false, true, type);
                            } else if (Device.getDeviceTypeFromSerial(serialNumber).compareToIgnoreCase(Device.DEVICE_TYPE_Zwave_AEOTEC_Door_Window_Sensor) == 0) {

                                new_device = new DeviceAEON_LABSDoor_Window_Sensor(ID, friendlyName + " " + String.valueOf(i + 1), false, true, type);
                            } else if (Device.getDeviceTypeFromSerial(serialNumber).compareToIgnoreCase(Device.DEVICE_TYPE_Zwave_Siren_Alarm_Sensor) == 0) {

                                new_device = new DeviceAEON_LABSSiren5(ID, friendlyName + " " + String.valueOf(i + 1), false, true, type);
                            }else {

                                new_device = new Device(ID, friendlyName + " " + String.valueOf(i + 1), false, true, type);

                            }
                            new_device.serialNumber = serialNumber;

                            String capabilityID = (String) device.get("Capability");
                            new_device.setCapabilityID(capabilityID);
                            MainActivity.devices.add(new_device);

                        }
                    }else if (protocol.equals("upnp"))
                    {

                        for (int i = 0; i < deviceList.length(); i++) {
                            JSONObject device = deviceList.getJSONObject(i);

                            String friendlyName = (String) device.get("FriendlyName");

                            String ID = (String) device.get("RealName");
                            String serialNumber = (String) device.get("UDN");

                            String type = "upnp";
                            Device new_device = new Device(ID, "[UPnP] " + friendlyName + " " + String.valueOf(i + 1), false, true, type);
                            new_device.serialNumber = serialNumber;

                            MainActivity.devices.add(new_device);

                        }

                        Toast.makeText(context.getApplicationContext(), "Just Received a MQTT message: " + message.toString(), Toast.LENGTH_LONG).show();
                    }

                    MainActivity.adapter.notifyDataSetChanged();


                    break;
                case RemoteAccessMsg.commandAddDeviceR:
                case RemoteAccessMsg.commandRemoveDeviceR:
                case RemoteAccessMsg.commandSetBinaryR:
                    Toast.makeText(context.getApplicationContext(), "Just Received a MQTT message: " + message.toString(), Toast.LENGTH_LONG).show();
                    break;
                case RemoteAccessMsg.commandGetBinaryR:
                    // fine-tuning the GUI
                    jason = new JSONObject(message.toString());
                    // get NodeID
                    String node_id = jason.getString("deviceid");
                    String status = jason.getString("status");
                    String value = jason.getString("reason");

                    for (int i = 0; i < MainActivity.devices.size(); i++) {
                        Device device_tmp = MainActivity.devices.get(i);
                        if (device_tmp.getId().equals(node_id)) {
                            if (device_tmp instanceof IcmdBinarySwitchResp) {
                                ((IcmdBinarySwitchResp) device_tmp).onBinarySwitchGetResp(status, value);
                                break;
                            }

                        }
                    }
                    break;


                case RemoteAccessMsg.commandGetSecureSpecR:
                case RemoteAccessMsg.commandSetSecureSpecR:
                case RemoteAccessMsg.commandSetSpecificationR:
                    Toast.makeText(context.getApplicationContext(), "Just Received a MQTT message: " + message.toString(), Toast.LENGTH_LONG).show();
                    break;
                case RemoteAccessMsg.commandGetSpecificationR:
                    // fine-tuning the GUI
                    jason = new JSONObject(message.toString());
                    // get NodeID
                    node_id = jason.getString("deviceid");

                    Device found_device = null;

                    for (int i = 0; i < MainActivity.devices.size(); i++) {
                        Device device_tmp = MainActivity.devices.get(i);
                        if (device_tmp.getId().equals(node_id))
                        {
                            found_device = device_tmp;
                            break;

                        }
                    }

                    if (found_device != null)
                    {
                        String commandinfo = jason.getString("commandinfo");
                        status = jason.getString("status");
                        //value = jason.getString("value");

                        JSONObject command_jobj = new JSONObject(commandinfo);

                        String klass = command_jobj.getString("class");
                        String cmd = command_jobj.getString("command");
                        String data0 = command_jobj.getString("data0");
                        String data1 = command_jobj.getString("data1");
                        String data2 = command_jobj.getString("data2");


                        if (klass.compareToIgnoreCase("CONFIGURATION") == 0)
                        {
                            if (found_device instanceof IcmdConfigurationResp)
                            {
                                ((IcmdConfigurationResp) found_device).onConfigurationGetResp(cmd, data0, data1, data2, status, message.toString());
                            }

                        }else if (klass.compareToIgnoreCase("ASSOCIATION") == 0)
                        {
                            if (found_device instanceof IcmdAssociationResp)
                            {
                                if (cmd.compareToIgnoreCase("GET") == 0) {
                                    String groupID = jason.getString("groupid");
                                    String maxNode = jason.getString("maxnode");
                                    JSONArray values = jason.getJSONArray("nodefollow");
                                    String nodeFlow = values.toString();

                                    ((IcmdAssociationResp) found_device).onAssociationGetResp(groupID, maxNode, nodeFlow);
                                }
                            }

                        }else if (klass.compareToIgnoreCase("SENSOR_MULTILEVEL") == 0)
                        {
                            if (found_device instanceof IcmdMultilevelResp)
                            {
                                if (cmd.compareToIgnoreCase("GET") == 0) {
                                    ((IcmdMultilevelResp) found_device).multilevelGetResp(message.toString());
                                }
                            }

                        }else if (klass.compareToIgnoreCase("BATTERY") == 0)
                        {
                            if (found_device instanceof IcmdBatteryResp)
                            {
                                if (cmd.compareToIgnoreCase("GET") == 0) {
                                    ((IcmdBatteryResp) found_device).batteryGetResp(message.toString());
                                }
                            }

                        }


                    }

                    break;


                case RemoteAccessMsg.commandResetR:
                default:
                    Toast.makeText(context.getApplicationContext(), "Just Received a MQTT message: " + message.toString(), Toast.LENGTH_LONG).show();
                    break;
            }
        }else
        {
            Toast.makeText(context.getApplicationContext(), "Just Received a MQTT message: " + message.toString(), Toast.LENGTH_LONG).show();

        }

    }

    /**
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Do nothing
    }



}
