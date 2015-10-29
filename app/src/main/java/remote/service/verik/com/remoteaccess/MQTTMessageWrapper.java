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

/**
 * Created by huyle on 10/23/15.
 */
public class MQTTMessageWrapper {

    // TODO:
    // this ID number should be unique
    public final static String uuid  = UUID.randomUUID().toString();

    public final static String commandGetListDevice = "list_devices";
    public final static String RcommandGetListDevice = "GetListDeviceR";
    public final static String commandAddDevice = "AddDevice";
    public final static String RcommandAddDevice = "AddDeviceR";


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
        Document doc = getDomElement(messsage);
        NodeList nodeList = doc.getElementsByTagName("uuid");
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node e = nodeList.item(i);
            if (getElementValue(e).equals(MQTTMessageWrapper.uuid))
                return true;
        }

        return false;
    }
    //public static

    public static MqttMessage CreateGetListMsg() throws JSONException {
        MqttMessage message = new MqttMessage();
        message.setId(1);
        message.setQos(0);
        message.setRetained(false);
        JSONObject jason_get_list = new JSONObject();
        jason_get_list.put("method", MQTTMessageWrapper.commandGetListDevice);
        jason_get_list.put("type", "zwave");
        jason_get_list.put("uuid", uuid);

        message.setPayload(jason_get_list.toString().getBytes());
        return message;
    }


    public static boolean IsCommandGetListDevice(String command)
    {
        if (getCommand(command).equals(MQTTMessageWrapper.commandGetListDevice))
            return true;
        return false;

    }


    public static String getCommand(String command)
    {
        try {
            JSONObject jason = new JSONObject(command);
            String method = (String) jason.get("method");
            if (method.equals(MQTTMessageWrapper.commandGetListDevice))
                return method;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return null;
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
