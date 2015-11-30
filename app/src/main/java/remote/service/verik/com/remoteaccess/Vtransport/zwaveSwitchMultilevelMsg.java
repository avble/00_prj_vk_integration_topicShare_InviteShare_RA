package remote.service.verik.com.remoteaccess.Vtransport;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdSwitchMultilevel;

/**
 * Created by huyle on 11/30/15.
 */
public class zwaveSwitchMultilevelMsg implements IcmdSwitchMultilevel{


    @Override
    public int multilevelGet(String id) {

        MqttMessage message = null;
        message = RemoteAccessMsg.CreateZwaveGetBinaryMsg(DeviceTypeProtocol.ZWAVE, id);
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);
        return 0;
    }

    @Override
    public int multilevelSet(String id, int value) {

        MqttMessage message = null;
        message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZWAVE, id, value);
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);
        return 0;
    }
}
