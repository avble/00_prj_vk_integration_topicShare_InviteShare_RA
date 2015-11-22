package remote.service.verik.com.remoteaccess;

/**
 * Created by huyle on 11/22/15.
 */
public class CmdZWaveConfiguration {
    String klass;
    String cmd;
    String paramNumber;

    // Remote Access compatible
    String remoteAPI;

    // parameter
    String remoteKlass;
    String remoteCmd;
    String data0;
    String data1;
    String data2;

    public CmdZWaveConfiguration(String klass, String cmd, String paramNumber, String remoteAPI, String remoteKlass, String remoteCmd, String data0, String data1, String data2 )
    {
        this.klass = klass;
        this.cmd = cmd;
        this.paramNumber = paramNumber;
        this.remoteAPI = remoteAPI;
        this.remoteKlass = remoteKlass;
        this.remoteCmd = remoteCmd;
        this.data0 = data0;
        this.data1 = data1;
        this.data2 = data2;
    }
}
