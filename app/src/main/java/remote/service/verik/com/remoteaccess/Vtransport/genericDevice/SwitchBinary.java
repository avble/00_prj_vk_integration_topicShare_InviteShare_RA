package remote.service.verik.com.remoteaccess.Vtransport.genericDevice;

import remote.service.verik.com.remoteaccess.Vtransport.zwaveBinarySwitchMsg;
import remote.service.verik.com.remoteaccess.model.Device;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdBinarySwitchResp;

public class SwitchBinary extends Device implements IcmdBinarySwitchResp {

    public SwitchBinary(String id, String friendlyName, boolean turnOn, boolean available, String device_type)
    {
        super(id, friendlyName, turnOn, available, device_type);

    }

    public void binarySwitchSetOn()
    {
        zwaveBinarySwitchMsg binaryMSG  = new zwaveBinarySwitchMsg();
        binaryMSG.binarySwitchSetOn(id);

    }


    public void binarySwitchSetOff()
    {
        zwaveBinarySwitchMsg binaryMSG  = new zwaveBinarySwitchMsg();
        binaryMSG.binarySwitchSetOff(id);

    }


    public void binarySwitchGet()
    {
        zwaveBinarySwitchMsg binaryMSG  = new zwaveBinarySwitchMsg();
        binaryMSG.binarySwitchGet(id);
    }


    @Override
    public void onBinarySwitchGetResp(String status, String value) {

    }
}
