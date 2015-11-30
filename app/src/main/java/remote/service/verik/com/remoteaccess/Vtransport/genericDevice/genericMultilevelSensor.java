package remote.service.verik.com.remoteaccess.Vtransport.genericDevice;

import remote.service.verik.com.remoteaccess.Vtransport.zwaveSensorMultilevelMsg;
import remote.service.verik.com.remoteaccess.model.Device;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdSensorMultilevel;

/**
 * Created by huyle on 11/27/15.
 */
public class genericMultilevelSensor extends Device implements IcmdSensorMultilevel {


    public genericMultilevelSensor(String id, String friendlyName, boolean turnOn, boolean available, String device_type)
    {
        super(id, friendlyName, turnOn, available, device_type);

    }

    @Override
    public int multilevelGet(String id, String sensorType) {
        zwaveSensorMultilevelMsg multilevelMsg = new zwaveSensorMultilevelMsg();
        multilevelMsg.multilevelGet(id, sensorType);
        return 0;
    }

    public int multilevelGet(String sensorType)
    {
        return multilevelGet(id, sensorType);
    }
}
