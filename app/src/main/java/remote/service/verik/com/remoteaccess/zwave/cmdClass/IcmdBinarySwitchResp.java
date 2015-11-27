package remote.service.verik.com.remoteaccess.zwave.cmdClass;

/**
 * Created by huyle on 11/26/15.
 */
public interface IcmdBinarySwitchResp {

    void onBinarySwitchGetResp(String status, String value);

}
