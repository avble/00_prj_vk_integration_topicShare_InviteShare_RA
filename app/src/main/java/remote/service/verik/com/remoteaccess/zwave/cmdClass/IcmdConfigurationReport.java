package remote.service.verik.com.remoteaccess.zwave.cmdClass;

/**
 * Created by huyle on 11/26/15.
 */
public interface IcmdConfigurationReport {

    void onConfigurationReport(String cmd, String data0, String data1, String data2, String status);

}
