package remote.service.verik.com.remoteaccess.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.R;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.Vtransport.genericDevice.genericMultilevelSwitch;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveMeterMsg;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveSensorMultilevelMsg;

/**
 * Created by huyle on 11/11/15.
 */
public class DeviceAEON_LABSSmartDimmerG2 extends genericMultilevelSwitch {

    public int dimmer_value;

    public DeviceAEON_LABSSmartDimmerG2(String id, String name, boolean turnOn, boolean available, String device_type)
    {
        super(id, name, turnOn, available, device_type);

        // Fragment initialization
        listFragment.add(createFragmentSwitchMultilevel());

    }


    private Fragment createFragmentSwitchMultilevel() {

        DeviceFragment fragment = new DeviceFragment() {

            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


                View rootView = inflater.inflate(R.layout.content_device_zwave_switch_multilevel, container, false);


                SeekBar dimmer_seek_bar = (SeekBar) rootView.findViewById(R.id.dimmer_seekbar);

                    dimmer_seek_bar.setMax(63);

                dimmer_seek_bar.setProgress(dimmer_value);


                dimmer_seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    public int dimer_progress = dimmer_value;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        dimer_progress = progress;

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        multilevelSet(dimer_progress);
                    }

                });


                return rootView;

            }

        };
        fragment.setTitle("Switch Multilevel");

        return fragment;
    }



    @Override
    public void Update(String property) {
        super.Update(property);

        JSONObject jason;

        try {
            jason = new JSONObject(property);

            String method = jason.getString("method");

            if (method.equals(RemoteAccessMsg.commandSetBinaryR))
            {

            }


        }
        catch (JSONException e) {
        }
    }

}
