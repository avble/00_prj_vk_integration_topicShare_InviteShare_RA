package remote.service.verik.com.remoteaccess;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;

import remote.service.verik.com.remoteaccess.DeviceType;
import remote.service.verik.com.remoteaccess.MQTTMessageWrapper;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.R;
import remote.service.verik.com.remoteaccess.model.Device;

public class DeviceTypeDimmerActivity extends AppCompatActivity {


    public static  Device device;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_dimmer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView tw_dimmer_device = (TextView)findViewById(R.id.dimmer_device_name);
        tw_dimmer_device.setText(device.getName());

        SeekBar dimmer_seek_bar = (SeekBar)findViewById(R.id.dimmer_seekbar);
        dimmer_seek_bar.setTag(device);

        if (device.type.contains("zwave"))
            dimmer_seek_bar.setMax(63);
        else if (device.type.contains("zigbee"))
            dimmer_seek_bar.setMax(255);


        dimmer_seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Device device = (Device) seekBar.getTag();
                MqttMessage message = null;
                try {

                    if (device.type.contentEquals("zwave"))
                        message = MQTTMessageWrapper.CreateZwaveSetBinaryMsg(DeviceType.ZWAVE, device.getId(), progress);
                    else if (device.type.contains("zigbee"))
                        message = MQTTMessageWrapper.CreateZwaveSetBinaryMsg(DeviceType.ZIGBEE, device.getId(), progress);
                    else if (device.type.contains("upnp"))
                        message = MQTTMessageWrapper.CreateZwaveSetBinaryMsg(DeviceType.UPNP, device.getId(), progress);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    MainActivity.client.publish(MainActivity.topic, message);
                } catch (MqttException e) {
                    Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        ImageView dimmer_bulb = (ImageView)findViewById(R.id.dimmer_bulb);
        dimmer_bulb.setTag(device);

        dimmer_bulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Device device = (Device) v.getTag();
                MqttMessage message = null;
                try {
                    int value = 1;
                    if (device.isTurnOn())
                        value = 0;

                    if (device.type.contentEquals("zwave"))
                        message = MQTTMessageWrapper.CreateZwaveSetBinaryMsg(DeviceType.ZWAVE, device.getId(), value);
                    else if (device.type.contains("zigbee"))
                        message = MQTTMessageWrapper.CreateZwaveSetBinaryMsg(DeviceType.ZIGBEE, device.getId(), value);
                    else if (device.type.contains("upnp"))
                        message = MQTTMessageWrapper.CreateZwaveSetBinaryMsg(DeviceType.UPNP, device.getId(), value);

                    boolean on = true;
                    if (value == 0)
                        on = false;

                    device.setTurnOn(on);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    MainActivity.client.publish(MainActivity.topic, message);
                } catch (MqttException e) {
                    Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
                }



            }
        });




    }

}
