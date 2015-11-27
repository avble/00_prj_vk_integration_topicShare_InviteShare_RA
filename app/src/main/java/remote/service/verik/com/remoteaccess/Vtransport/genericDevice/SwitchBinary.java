package remote.service.verik.com.remoteaccess.Vtransport.genericDevice;

import remote.service.verik.com.remoteaccess.Vtransport.zwaveBinarySwitchMsg;
import remote.service.verik.com.remoteaccess.model.Device;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdBinarySwitchReport;

public class SwitchBinary extends Device implements IcmdBinarySwitchReport {

    public SwitchBinary(String id, String friendlyName, boolean turnOn, boolean available, String device_type)
    {
        super(id, friendlyName, turnOn, available, device_type);

    }

    public void binarySwitchSetOn()
    {
        zwaveBinarySwitchMsg binaryMSG  = new zwaveBinarySwitchMsg();
        binaryMSG.setOn(id);

    }


    public void binarySwitchSetOff()
    {
        zwaveBinarySwitchMsg binaryMSG  = new zwaveBinarySwitchMsg();
        binaryMSG.setOff(id);

    }


    public void binarySwitchGet()
    {
        zwaveBinarySwitchMsg binaryMSG  = new zwaveBinarySwitchMsg();
        binaryMSG.GET(id);
    }


    @Override
    public void onBinarySwitchReport(String status, String value) {

    }
}
