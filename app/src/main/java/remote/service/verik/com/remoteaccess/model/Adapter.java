package remote.service.verik.com.remoteaccess.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;

import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.R;

/**
 * Created by congngale on 7/16/15.
 */
public class Adapter extends ArrayAdapter<Device> {

    public Adapter(Context context, ArrayList<Device> devices) {
        super(context, 0, devices);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get device
        final Device device = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.device, parent, false);
        }


        ImageView bulb = (ImageView) convertView.findViewById(R.id.bulb);
        TextView tw = (TextView)convertView.findViewById(R.id.device_name);
        if (tw != null)
            tw.setText(device.getName());

        // FIXME
        //Button button = (Button) convertView.findViewById(R.id.button);
        // FIXME: Do not support onClick event
        /*
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (device.isTurnOn()) {
                    device.setTurnOn(false);
                    if (MainActivity.client != null && MainActivity.client.isConnected()) {
                        //publish message
                        MqttMessage message = new MqttMessage();
                        message.setId(1);
                        message.setPayload("off".getBytes());
                        message.setQos(0);
                        message.setRetained(false);
                        try {
                            MainActivity.client.publish(MainActivity.getTopic(), message);
                        } catch (MqttException e) {
                            Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
                        }
                    }
                } else {
                    device.setTurnOn(true);
                    if (MainActivity.client != null && MainActivity.client.isConnected()) {
                        //publish message
                        MqttMessage message = new MqttMessage();
                        message.setId(1);
                        message.setPayload("on".getBytes());
                        message.setQos(0);
                        message.setRetained(false);
                        try {
                            MainActivity.client.publish(MainActivity.getTopic(), message);
                        } catch (MqttException e) {
                            Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });


 */

        if (device.isAvailable()) {
            if (device.isTurnOn()) {
                bulb.setImageResource(R.drawable.bulb_on);
                // FIXME
                // button.setText("Turn Off");
            } else {
                bulb.setImageResource(R.drawable.bulb_off);
                // FIXME
                // button.setText("Turn On");
            }
            return convertView;
        } else {
            return super.getView(position, convertView, parent);
        }



    }

}
