package remote.service.verik.com.remoteaccess;

import android.renderscript.Element;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MQTTMessageWrapper {

    // TODO:
    // this ID number should be unique
    public final static String uuid  = UUID.randomUUID().toString();

    // Zwave based services
    public final static String commandGetListDevice = "list_devices";
    public final static String commandGetListDeviceR = "list_devicesR";
    public final static String commandAddDevice = "add_devices";
    public final static String commandAddDeviceR = "add_devicesR";
    public final static String commandRemoveDevice = "remove_device";
    public final static String commandRemoveDeviceR = "remove_deviceR";

    public final static String commandSetBinary = "set_binary";
    public final static String commandSetBinaryR = "set_binaryR";
    public final static String commandGetBinary = "get_binary";
    public final static String commandGetBinaryR = "get_binaryR";

    public final static String commandGetSpecification = "get_specification";
    public final static String commandGetSpecificationR = "get_specificationR";

    public final static String commandSetSpecification = "set_specification";
    public final static String commandSetSpecificationR = "set_specificationR";

    public final static String commandSetSecureSpec = "set_secure_spec";
    public final static String commandSetSecureSpecR = "set_secure_specR";

    public final static String commandGetSecureSpec = "get_secure_spec";
    public final static String commandGetSecureSpecR = "get_secure_specR";

    public final static String commandReset = "reset";
    public final static String commandResetR = "resetR";





    // XML based library
    public static Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }

    public static final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }


    //

    public static boolean isMyMessage(String messsage)
    {
        try {
            JSONObject jason = new JSONObject(messsage);
            String uuid = (String) jason.get("uuid");
            if (uuid.equals(MQTTMessageWrapper.uuid))
                return true;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    //public static

    public static MqttMessage CreateGetListDevicesMsg(DeviceType device_type) throws JSONException {
        MqttMessage message = new MqttMessage();
        message.setId(1);
        message.setQos(0);
        message.setRetained(false);
        JSONObject jason_get_list = new JSONObject();
        jason_get_list.put("method", MQTTMessageWrapper.commandGetListDevice);
        jason_get_list.put("type", "zwave");
        if (device_type == DeviceType.ZIGBEE)
            jason_get_list.put("type", "zigbee");
        else if (device_type == DeviceType.UPNP)
            jason_get_list.put("type", "upnp");

        jason_get_list.put("uuid", uuid);

        message.setPayload(jason_get_list.toString().getBytes());
        return message;
    }

    public static MqttMessage CreateAddDeviceMsg(DeviceType device_type) throws JSONException {
        MqttMessage message = new MqttMessage();
        message.setId(1);
        message.setQos(0);
        message.setRetained(false);
        JSONObject jason_get_list = new JSONObject();
        jason_get_list.put("method", MQTTMessageWrapper.commandAddDevice);
        jason_get_list.put("type", "zwave");
        if (device_type == DeviceType.ZIGBEE)
            jason_get_list.put("type", "zigbee");
        else if (device_type == DeviceType.UPNP)
            jason_get_list.put("type", "upnp");

        jason_get_list.put("uuid", uuid);
        // TODO: add more field

        message.setPayload(jason_get_list.toString().getBytes());
        return message;
    }

    public static MqttMessage CreateRemoveDeviceMsg(DeviceType device_type) throws JSONException {
        MqttMessage message = new MqttMessage();
        message.setId(1);
        message.setQos(0);
        message.setRetained(false);
        JSONObject jason_get_list = new JSONObject();
        jason_get_list.put("method", MQTTMessageWrapper.commandRemoveDevice);
        jason_get_list.put("type", "zwave");
        if (device_type == DeviceType.ZIGBEE)
            jason_get_list.put("type", "zigbee");
        else if (device_type == DeviceType.UPNP)
            jason_get_list.put("type", "upnp");
        jason_get_list.put("uuid", uuid);
        // TODO: add more field

        message.setPayload(jason_get_list.toString().getBytes());
        return message;
    }

    public static MqttMessage CreateZwaveSetBinaryMsg(DeviceType device_type, String id, int value) throws JSONException {
        MqttMessage message = new MqttMessage();
        message.setId(1);
        message.setQos(0);
        message.setRetained(false);
        JSONObject jason_get_list = new JSONObject();
        jason_get_list.put("method", MQTTMessageWrapper.commandSetBinary);
        jason_get_list.put("type", "zwave");
        if (device_type == DeviceType.ZIGBEE)
            jason_get_list.put("type", "zigbee");
        else if (device_type == DeviceType.UPNP)
            jason_get_list.put("type", "upnp");
        jason_get_list.put("uuid", uuid);
        jason_get_list.put("id", id);
        jason_get_list.put("value", Integer.toString(value));

        message.setPayload(jason_get_list.toString().getBytes());
        return message;
    }


    public static MqttMessage CreateZwaveGetBinaryMsg(DeviceType device_type, String id) throws JSONException {
        MqttMessage message = new MqttMessage();
        message.setId(1);
        message.setQos(0);
        message.setRetained(false);
        JSONObject jason_get_list = new JSONObject();
        jason_get_list.put("method", MQTTMessageWrapper.commandGetBinary);
        jason_get_list.put("type", "zwave");
        if (device_type == DeviceType.ZIGBEE)
            jason_get_list.put("type", "zigbee");
        else if (device_type == DeviceType.UPNP)
            jason_get_list.put("type", "upnp");
        jason_get_list.put("uuid", uuid);
        jason_get_list.put("id", id);

        message.setPayload(jason_get_list.toString().getBytes());
        return message;
    }

    public static MqttMessage CreateGetSpecificationMsg() throws JSONException {
        MqttMessage message = new MqttMessage();
        message.setId(1);
        message.setQos(0);
        message.setRetained(false);
        JSONObject jason_get_list = new JSONObject();
        jason_get_list.put("method", MQTTMessageWrapper.commandGetSpecification);
        jason_get_list.put("type", "zwave");
        jason_get_list.put("uuid", uuid);
        // TODO: add more field

        message.setPayload(jason_get_list.toString().getBytes());
        return message;
    }


    public static MqttMessage CreateSetSpecificationMsg() throws JSONException {
        MqttMessage message = new MqttMessage();
        message.setId(1);
        message.setQos(0);
        message.setRetained(false);
        JSONObject jason_get_list = new JSONObject();
        jason_get_list.put("method", MQTTMessageWrapper.commandSetSpecification);
        jason_get_list.put("type", "zwave");
        jason_get_list.put("uuid", uuid);
        // TODO: add more field

        message.setPayload(jason_get_list.toString().getBytes());
        return message;
    }


    public static MqttMessage CreateSetSecureMsg() throws JSONException {
        MqttMessage message = new MqttMessage();
        message.setId(1);
        message.setQos(0);
        message.setRetained(false);
        JSONObject jason_get_list = new JSONObject();
        jason_get_list.put("method", MQTTMessageWrapper.commandSetSecureSpec);
        jason_get_list.put("type", "zwave");
        jason_get_list.put("uuid", uuid);
        // TODO: add more field

        message.setPayload(jason_get_list.toString().getBytes());
        return message;
    }


    public static MqttMessage CreateGetSecureMsg() throws JSONException {
        MqttMessage message = new MqttMessage();
        message.setId(1);
        message.setQos(0);
        message.setRetained(false);
        JSONObject jason_get_list = new JSONObject();
        jason_get_list.put("method", MQTTMessageWrapper.commandGetSecureSpec);
        jason_get_list.put("type", "zwave");
        jason_get_list.put("uuid", uuid);
        // TODO: add more field

        message.setPayload(jason_get_list.toString().getBytes());
        return message;
    }

    public static MqttMessage CreateResetMsg(DeviceType device_type) throws JSONException {
        MqttMessage message = new MqttMessage();
        message.setId(1);
        message.setQos(0);
        message.setRetained(false);
        JSONObject jason_get_list = new JSONObject();
        jason_get_list.put("method", MQTTMessageWrapper.commandReset);
        jason_get_list.put("type", "zwave");
        if (device_type == DeviceType.ZIGBEE)
            jason_get_list.put("type", "zigbee");
        jason_get_list.put("uuid", uuid);
        // TODO: add more field

        message.setPayload(jason_get_list.toString().getBytes());
        return message;
    }

    public static boolean IsCommandGetListDevice(String command)
    {
        if (getCommand(command).equals(MQTTMessageWrapper.commandGetListDevice))
            return true;
        return false;

    }


    public static String getCommand(String command) {
        try {
            JSONObject jason = new JSONObject(command);
            String method = (String) jason.get("method");
            return method;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getuuid(String command)
    {
        try {
            JSONObject jason = new JSONObject(command);
            String method = (String) jason.get("uuid");
            if (method.equals(MQTTMessageWrapper.uuid))
                return method;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    //public static
}
