package remote.service.verik.com.remoteaccess.zwave.cmdClass;

/**
 * Created by huyle on 11/25/15.
 */
public interface IcmdBinarySwitch {
    int setOn(String id);
    int setOff(String id);
    int GET(String id);

}

