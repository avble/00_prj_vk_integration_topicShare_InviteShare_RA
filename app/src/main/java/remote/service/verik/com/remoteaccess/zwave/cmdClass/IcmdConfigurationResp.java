package remote.service.verik.com.remoteaccess.zwave.cmdClass;

/**
 * Created by huyle on 11/26/15.
 */
public interface IcmdConfigurationResp {

    void onConfigurationGetResp(String cmd, String data0, String data1, String data2, String status);

}
