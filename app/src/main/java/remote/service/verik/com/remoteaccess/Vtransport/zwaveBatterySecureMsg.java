package remote.service.verik.com.remoteaccess.Vtransport;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdBattery;

/**
 * Created by huyle on 11/30/15.
 */
public class zwaveBatterySecureMsg implements IcmdBattery {

    public int batteryGet(String id) {
        MqttMessage message = null;
        message = RemoteAccessMsg.CreateGetSecureMsg(DeviceTypeProtocol.ZWAVE, id, "BATTERY", "GET", "", "", "");
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);
        return 0;
    }
}
