package remote.service.verik.com.remoteaccess.model;

import remote.service.verik.com.remoteaccess.Vtransport.genericDevice.genericSwitchBinary;

/**
 * Created by huyle on 11/30/15.
 */
public class DeviceEverspringScrewinLampSocket extends genericSwitchBinary {

    public DeviceEverspringScrewinLampSocket(String id, String friendlyName, boolean turnOn, boolean available, String device_type)
    {
        super(id, friendlyName, turnOn, available, device_type);

    }

}
