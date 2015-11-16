package remote.service.verik.com.remoteaccess.model;

import android.content.Intent;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import remote.service.verik.com.remoteaccess.DeviceDetailActivity;
import remote.service.verik.com.remoteaccess.MQTTMessageWrapper;

/**
 * Created by huyle on 11/11/15.
 */
public class DeviceGenericDimmer extends  Device{

    public int dimmer_value;

    public DeviceGenericDimmer(String id, String name, boolean turnOn, boolean available, String device_type)
    {
        super(id, name, turnOn, available, device_type);
    }



    @Override
    public void Update(String property) {
        super.Update(property);

        JSONObject jason;

        try {
            jason = new JSONObject(property);

            String method = jason.getString("method");

            if (method.equals(MQTTMessageWrapper.commandSetBinaryR))
            {

            }


        }
        catch (JSONException e) {
        }
    }

}
