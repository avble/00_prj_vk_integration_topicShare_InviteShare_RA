package remote.service.verik.com.remoteaccess.Vtransport;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdConfiguration;

/**
 * Created by huyle on 11/25/15.
 */
public class zwaveConfigurationMsg implements IcmdConfiguration {

    @Override
    public int Set(String id, String data0, String data1, String data2) {

        MqttMessage message = null;
        message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, id, "CONFIGURATION", "SET", data0, data1, data2);
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;
    }

    @Override
    public int Get(String id,String data0, String data1) {

        MqttMessage message = null;
        message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, id, "CONFIGURATION", "GET", data0, data1, "");
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;
    }
}
