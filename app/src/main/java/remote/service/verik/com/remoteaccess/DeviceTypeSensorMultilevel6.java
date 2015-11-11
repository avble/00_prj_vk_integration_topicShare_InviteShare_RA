package remote.service.verik.com.remoteaccess;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;

import remote.service.verik.com.remoteaccess.model.Device;

public class DeviceTypeSensorMultilevel6 extends AppCompatActivity {

    public static Device device;

    final public static String klass_SENSOR_MULTILEVEL = "SENSOR_MULTILEVEL";
    final public static String type_SENSOR_MULTILEVEL_TEMP = "TEMP";
    final public static String type_SENSOR_MULTILEVEL_HUMI = "HUMI";
    final public static String type_SENSOR_MULTILEVEL_UV = "UV";
    final public static String type_SENSOR_MULTILEVEL_LUMI = "LUMI";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_type_sensor_multilevel6);
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


        TextView tw_heavy_duty_smart_device = (TextView)findViewById(R.id.sensor_multilevel_device_name);
        tw_heavy_duty_smart_device.setText(device.getName());


        Button button_temp = (Button)findViewById(R.id.sensor_multilevel_button_temp);

        button_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MqttMessage message = null;
                try {
                    if (device.type.contentEquals("zwave"))
                        message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceType.ZWAVE, device.getId(), DeviceTypeSensorMultilevel6.klass_SENSOR_MULTILEVEL, "GET", DeviceTypeSensorMultilevel6.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");
                    else if (device.type.contains("zigbee"))
                        message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceType.ZIGBEE, device.getId(), DeviceTypeSensorMultilevel6.klass_SENSOR_MULTILEVEL, "GET", DeviceTypeSensorMultilevel6.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");
                    else if (device.type.contains("upnp"))
                        message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceType.UPNP, device.getId(), DeviceTypeSensorMultilevel6.klass_SENSOR_MULTILEVEL, "GET", DeviceTypeSensorMultilevel6.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");

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

        Button button_humi = (Button)findViewById(R.id.sensor_multilevel_button_humi);

        button_humi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MqttMessage message = null;
                try {
                    if (device.type.contentEquals("zwave"))
                        message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceType.ZWAVE, device.getId(), DeviceTypeSensorMultilevel6.klass_SENSOR_MULTILEVEL, "GET", DeviceTypeSensorMultilevel6.type_SENSOR_MULTILEVEL_HUMI, "2A", "1/10");
                    else if (device.type.contains("zigbee"))
                        message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceType.ZIGBEE, device.getId(), DeviceTypeSensorMultilevel6.klass_SENSOR_MULTILEVEL, "GET", DeviceTypeSensorMultilevel6.type_SENSOR_MULTILEVEL_HUMI, "2A", "1/10");
                    else if (device.type.contains("upnp"))
                        message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceType.UPNP, device.getId(), DeviceTypeSensorMultilevel6.klass_SENSOR_MULTILEVEL, "GET", DeviceTypeSensorMultilevel6.type_SENSOR_MULTILEVEL_HUMI, "2A", "1/10");

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


        Button button_lumi = (Button)findViewById(R.id.sensor_multilevel_button_lumi);

        button_lumi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MqttMessage message = null;
                try {
                    if (device.type.contentEquals("zwave"))
                        message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceType.ZWAVE, device.getId(), DeviceTypeSensorMultilevel6.klass_SENSOR_MULTILEVEL, "GET", DeviceTypeSensorMultilevel6.type_SENSOR_MULTILEVEL_LUMI, "2A", "1/10");
                    else if (device.type.contains("zigbee"))
                        message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceType.ZIGBEE, device.getId(), DeviceTypeSensorMultilevel6.klass_SENSOR_MULTILEVEL, "GET", DeviceTypeSensorMultilevel6.type_SENSOR_MULTILEVEL_LUMI, "2A", "1/10");
                    else if (device.type.contains("upnp"))
                        message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceType.UPNP, device.getId(), DeviceTypeSensorMultilevel6.klass_SENSOR_MULTILEVEL, "GET", DeviceTypeSensorMultilevel6.type_SENSOR_MULTILEVEL_LUMI, "2A", "1/10");

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




        Button button_violet = (Button)findViewById(R.id.sensor_multilevel_button_ultra_violet);

        button_violet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MqttMessage message = null;
                try {
                    if (device.type.contentEquals("zwave"))
                        message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceType.ZWAVE, device.getId(), DeviceTypeSensorMultilevel6.klass_SENSOR_MULTILEVEL, "GET", DeviceTypeSensorMultilevel6.type_SENSOR_MULTILEVEL_UV, "2A", "1/10");
                    else if (device.type.contains("zigbee"))
                        message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceType.ZIGBEE, device.getId(), DeviceTypeSensorMultilevel6.klass_SENSOR_MULTILEVEL, "GET", DeviceTypeSensorMultilevel6.type_SENSOR_MULTILEVEL_UV, "2A", "1/10");
                    else if (device.type.contains("upnp"))
                        message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceType.UPNP, device.getId(), DeviceTypeSensorMultilevel6.klass_SENSOR_MULTILEVEL, "GET", DeviceTypeSensorMultilevel6.type_SENSOR_MULTILEVEL_UV, "2A", "1/10");

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
