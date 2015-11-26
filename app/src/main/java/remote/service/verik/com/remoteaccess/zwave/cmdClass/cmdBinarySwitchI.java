package remote.service.verik.com.remoteaccess.zwave.cmdClass;

/**
 * Created by huyle on 11/25/15.
 */
public interface cmdBinarySwitchI {
    int SET(String id, int value);
    int GET(String id);

}

