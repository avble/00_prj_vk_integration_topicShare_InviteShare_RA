package remote.service.verik.com.remoteaccess.Vtransport.genericDevice;

import remote.service.verik.com.remoteaccess.Vtransport.zwaveMultilevelMsg;
import remote.service.verik.com.remoteaccess.model.Device;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdMultilevel;

/**
 * Created by huyle on 11/27/15.
 */
public class genericMultilevelSensor extends Device implements IcmdMultilevel {


    public genericMultilevelSensor(String id, String friendlyName, boolean turnOn, boolean available, String device_type)
    {
        super(id, friendlyName, turnOn, available, device_type);

    }

    @Override
    public int multilevelGet(String id, String sensorType) {
        zwaveMultilevelMsg multilevelMsg = new zwaveMultilevelMsg();
        multilevelMsg.multilevelGet(id, sensorType);
        return 0;
    }

    public int multilevelGet(String sensorType)
    {
        return multilevelGet(id, sensorType);
    }
}
