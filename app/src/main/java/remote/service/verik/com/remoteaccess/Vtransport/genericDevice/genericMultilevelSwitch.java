package remote.service.verik.com.remoteaccess.Vtransport.genericDevice;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.model.Device;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdSensorMultilevel;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdSwitchMultilevel;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdSwitchMultilevelResp;

/**
 * Created by huyle on 11/30/15.
 */
public class genericMultilevelSwitch extends Device implements IcmdSwitchMultilevel, IcmdSwitchMultilevelResp {


    public genericMultilevelSwitch(String id, String friendlyName, boolean turnOn, boolean available, String device_type)
    {
        super(id, friendlyName, turnOn, available, device_type);

    }

    public int multilevelSet(int value)
    {
        return multilevelSet(id, value);
    }

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

    @Override
    public void switchMultilevelGet(String more) {

    }

    @Override
    public void switchMultilevelSet(String more) {

    }
}
