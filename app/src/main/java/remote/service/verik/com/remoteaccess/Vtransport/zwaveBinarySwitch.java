package remote.service.verik.com.remoteaccess.Vtransport;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MQTTWrapper;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.cmdBinarySwitchI;

/**
 * Created by huyle on 11/25/15.
 */
public class zwaveBinarySwitch implements cmdBinarySwitchI {

    @Override
    public int SET(String id, int value) {

        MqttMessage message = null;
        message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZWAVE, id, value);
        MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;
    }

    @Override
    public int GET(String id) {
        MqttMessage message = null;
        message = RemoteAccessMsg.CreateZwaveGetBinaryMsg(DeviceTypeProtocol.ZWAVE, id);
        MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;
    }
}
