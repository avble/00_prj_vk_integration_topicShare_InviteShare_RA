package remote.service.verik.com.remoteaccess.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
            convertView.setLongClickable(true);
        }


        ImageView bulb = (ImageView) convertView.findViewById(R.id.bulb);

        bulb.setOnClickListener(MainActivity.BinarySwitchButton);
        bulb.setTag(device);

        TextView tw = (TextView)convertView.findViewById(R.id.device_name);
        tw.setTag(device);
        tw.setOnClickListener(MainActivity.deviceOnClick);

//        tw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Device device = (Device) v.getTag();
//
//                if (device.getCapabilityID().contains("SWITCH_MULTILEVEL"))
//                {
//                    Intent intent1 = new Intent(v.getContext(), DeviceTypeDimmerActivity.class);
//                    //Intent intent1 = new Intent(this, DeviceTypeDimmerActivity.this);
//
//                    v.getContext().startActivity(intent1);
//
//                    //startActivity(intent1);
//
//
//                    //startActivityForResult(intent1, null);
//                }
//
//
//            }
//        });

        //tw.setOnClickListener(MainActivity.deviceOnClick);

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
            convertView.setTag("ID_01");
            return convertView;
        } else {
            return super.getView(position, convertView, parent);
        }



    }

}
