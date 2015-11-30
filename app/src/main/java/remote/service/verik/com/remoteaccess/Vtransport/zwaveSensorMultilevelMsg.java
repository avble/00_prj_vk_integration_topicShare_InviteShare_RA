package remote.service.verik.com.remoteaccess.Vtransport;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdSensorMultilevel;

/**
 * Created by huyle on 11/27/15.
 */
public class zwaveSensorMultilevelMsg implements IcmdSensorMultilevel {
    @Override
    public int multilevelGet(String id, String sensorType) {
        MqttMessage message = null;
        message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, id, "SENSOR_MULTILEVEL", "GET", sensorType, "", "");
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);
        return 0;
    }
}
