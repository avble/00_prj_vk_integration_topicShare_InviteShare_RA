package remote.service.verik.com.remoteaccess.zwave.cmdClass;

/**
 * Created by huyle on 11/25/15.
 */
public interface IcmdConfiguration {
    int Set(String id, String data0, String data1, String data2);
    int Get(String id, String data0, String data1);
}
