package remote.service.verik.com.remoteaccess.model;

import android.content.Intent;
import android.view.View;

import remote.service.verik.com.remoteaccess.DeviceDetailActivity;

/**
 * Created by huyle on 11/11/15.
 */
public class DeviceGenericDimmer extends  Device{

    int dimmer_value;

    public DeviceGenericDimmer(String id, String name, boolean turnOn, boolean available, String device_type)
    {
        super(id, name, turnOn, available, device_type);
    }

}
