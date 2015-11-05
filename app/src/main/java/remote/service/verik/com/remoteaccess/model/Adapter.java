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
