package remote.service.verik.com.remoteaccess.zwave.cmdClass;

/**
 * Created by huyle on 11/25/15.
 */
public interface IcmdBinarySwitch {
    int binarySwitchSetOn(String id);
    int binarySwitchSetOff(String id);
    int binarySwitchGet(String id);
}

