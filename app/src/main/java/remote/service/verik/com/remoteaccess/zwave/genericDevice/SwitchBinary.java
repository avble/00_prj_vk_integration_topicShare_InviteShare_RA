package remote.service.verik.com.remoteaccess.zwave.genericDevice;

import remote.service.verik.com.remoteaccess.Vtransport.zwaveBinarySwitch;
import remote.service.verik.com.remoteaccess.model.Device;

public class SwitchBinary extends Device{

    public SwitchBinary(String id, String friendlyName, boolean turnOn, boolean available, String device_type)
    {
        super(id, friendlyName, turnOn, available, device_type);

    }

    public void binarySwitchSet(int value)
    {
        zwaveBinarySwitch binaryMSG  = new zwaveBinarySwitch();
        binaryMSG.SET(id, value);

    }


    public void binarySwitchGet()
    {
        zwaveBinarySwitch binaryMSG  = new zwaveBinarySwitch();
        binaryMSG.GET(id);
    }




}
