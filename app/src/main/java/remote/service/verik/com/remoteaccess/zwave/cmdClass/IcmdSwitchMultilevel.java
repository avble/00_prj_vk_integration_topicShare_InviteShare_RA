package remote.service.verik.com.remoteaccess.zwave.cmdClass;

/**
 * Created by huyle on 11/30/15.
 */
public interface IcmdSwitchMultilevel {
    int multilevelGet(String id);
    int multilevelSet(String id, int value);
}
