package remote.service.verik.com.remoteaccess.Vtransport;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdBinarySwitch;

/**
 * Created by huyle on 11/25/15.
 */
public class zwaveBinarySwitchMsg implements IcmdBinarySwitch {

    @Override
    public int binarySwitchSetOn(String id)
    {
        MqttMessage message = null;
        message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZWAVE, id, 1);
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;

    }
    @Override
    public int binarySwitchSetOff(String id)
    {
        MqttMessage message = null;
        message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZWAVE, id, 0);
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;

    }


    @Override
    public int binarySwitchGet(String id) {
        MqttMessage message = null;
        message = RemoteAccessMsg.CreateZwaveGetBinaryMsg(DeviceTypeProtocol.ZWAVE, id);
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;
    }
}
