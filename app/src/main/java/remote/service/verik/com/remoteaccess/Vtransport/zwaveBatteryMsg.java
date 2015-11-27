package remote.service.verik.com.remoteaccess.Vtransport;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdBattery;

/**
 * Created by huyle on 11/27/15.
 */
public class zwaveBatteryMsg implements IcmdBattery {
    @Override
    public int batteryGet(String id) {
        MqttMessage message = null;
        message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, id, "BATTERY", "GET", "", "", "");
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);
        return 0;
    }
}
