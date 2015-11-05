package remote.service.verik.com.remoteaccess;

/**
 * Created by huyle on 11/4/15.
 */

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class VUtility {

    public static String getMAC(Context context)
    {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();

        return address;
    }

}
