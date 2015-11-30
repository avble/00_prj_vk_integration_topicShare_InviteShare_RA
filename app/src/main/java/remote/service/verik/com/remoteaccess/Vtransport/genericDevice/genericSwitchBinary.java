package remote.service.verik.com.remoteaccess.Vtransport.genericDevice;

import remote.service.verik.com.remoteaccess.Vtransport.zwaveBinarySwitchMsg;
import remote.service.verik.com.remoteaccess.model.Device;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdBinarySwitch;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdBinarySwitchResp;

public class genericSwitchBinary extends Device implements IcmdBinarySwitch {

    public genericSwitchBinary(String id, String friendlyName, boolean turnOn, boolean available, String device_type)
    {
        super(id, friendlyName, turnOn, available, device_type);

    }

    public void binarySwitchSetOn()
    {
        binarySwitchSetOn(id);

    }


    public void binarySwitchSetOff()
    {
        binarySwitchSetOff(id);
    }


    public void binarySwitchGet()
    {
        binarySwitchGet(id);

    }


    @Override
    public int binarySwitchSetOn(String id) {
        zwaveBinarySwitchMsg binaryMSG  = new zwaveBinarySwitchMsg();
        binaryMSG.binarySwitchSetOn(id);
        return 0;
    }

    @Override
    public int binarySwitchSetOff(String id) {

        zwaveBinarySwitchMsg binaryMSG  = new zwaveBinarySwitchMsg();
        binaryMSG.binarySwitchSetOff(id);
        return 0;
    }

    @Override
    public int binarySwitchGet(String id) {
        zwaveBinarySwitchMsg binaryMSG  = new zwaveBinarySwitchMsg();
        binaryMSG.binarySwitchGet(id);
        return 0;
    }
}
