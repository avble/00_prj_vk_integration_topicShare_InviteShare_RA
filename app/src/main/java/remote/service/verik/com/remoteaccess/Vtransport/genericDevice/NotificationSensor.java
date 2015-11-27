package remote.service.verik.com.remoteaccess.Vtransport.genericDevice;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.model.Device;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdAssociation;

/**
 * Created by huyle on 11/26/15.
 */
public class NotificationSensor extends Device implements IcmdAssociation{

    public NotificationSensor(String id, String friendlyName, boolean turnOn, boolean available, String device_type)
    {
        super(id, friendlyName, turnOn, available, device_type);

    }

    public int associationSetNode(String id, String groupID, String nodeID) {
        MqttMessage message = null;
        message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, id, "ASSOCIATION", "SET", groupID, nodeID, "");
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;
    }

    @Override
    public int associationGetNode(String id, String groupID) {

        MqttMessage message = null;
        message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, id, "ASSOCIATION", "GET", groupID, "", "");
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;
    }

    @Override
    public int associationRemoveNode(String id, String groupID, String nodeID) {
        MqttMessage message = null;
        message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, id, "ASSOCIATION", "REMOVE", groupID, nodeID, "");
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;
    }
}
