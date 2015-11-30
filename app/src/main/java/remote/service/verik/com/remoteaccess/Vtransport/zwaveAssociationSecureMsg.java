package remote.service.verik.com.remoteaccess.Vtransport;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdAssociation;

/**
 * Created by huyle on 11/30/15.
 */
public class zwaveAssociationSecureMsg implements IcmdAssociation {
    @Override
    public int associationSetNode(String id, String groupID, String nodeID) {
        MqttMessage message = null;
        message = RemoteAccessMsg.CreateSetSecureMsg(DeviceTypeProtocol.ZWAVE, id, "ASSOCIATION", "SET", groupID, nodeID, "");
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;
    }

    @Override
    public int associationGetNode(String id, String groupID) {

        MqttMessage message = null;
        message = RemoteAccessMsg.CreateGetSecureMsg(DeviceTypeProtocol.ZWAVE, id, "ASSOCIATION", "GET", groupID, "", "");
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;
    }

    @Override
    public int associationRemoveNode(String id, String groupID, String nodeID) {
        MqttMessage message = null;
        message = RemoteAccessMsg.CreateSetSecureMsg(DeviceTypeProtocol.ZWAVE, id, "ASSOCIATION", "REMOVE", groupID, nodeID, "");
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;
    }
}
