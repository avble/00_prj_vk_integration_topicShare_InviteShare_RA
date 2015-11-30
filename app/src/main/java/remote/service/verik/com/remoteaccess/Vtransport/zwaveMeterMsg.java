package remote.service.verik.com.remoteaccess.Vtransport;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdMeter;

/**
 * Created by huyle on 11/28/15.
 */
public class zwaveMeterMsg implements IcmdMeter {
    @Override
    public int meterGet(String id) {
        MqttMessage message = null;
        message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, id, "METER", "GET", "", "", "");
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);
        return 0;
    }
}
