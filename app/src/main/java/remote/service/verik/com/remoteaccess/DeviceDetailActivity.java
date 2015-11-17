package remote.service.verik.com.remoteaccess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;

import remote.service.verik.com.remoteaccess.model.Device;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSMultilevelSensor5;
import remote.service.verik.com.remoteaccess.model.DeviceGenericDimmer;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSMultilevelSensor6;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSHeavyDutySmart;

public class DeviceDetailActivity extends AppCompatActivity {

    public static Device device;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (device instanceof DeviceGenericDimmer) {
            setContentView(R.layout.content_device_generic_dimmer);

            final DeviceGenericDimmer device_dimmer = (DeviceGenericDimmer)device;

            TextView tw_dimmer_device = (TextView) findViewById(R.id.dimmer_device_name);
            tw_dimmer_device.setText(device.getName());

            SeekBar dimmer_seek_bar = (SeekBar) findViewById(R.id.dimmer_seekbar);
            dimmer_seek_bar.setTag(device);

            if (device.type.contains("zwave"))
                dimmer_seek_bar.setMax(63);
            else if (device.type.contains("zigbee"))
                dimmer_seek_bar.setMax(255);
            dimmer_seek_bar.setProgress(device_dimmer.dimmer_value);


            dimmer_seek_bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                public int dimer_progress = device_dimmer.dimmer_value;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    dimer_progress = progress;

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {


                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZWAVE, device_dimmer.getId(), dimer_progress);
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZIGBEE, device_dimmer.getId(), dimer_progress);
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.UPNP, device_dimmer.getId(), dimer_progress);

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


            ImageView dimmer_bulb = (ImageView) findViewById(R.id.dimmer_bulb);
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
                            message = MQTTMessageWrapper.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZWAVE, device.getId(), value);
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), value);
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.UPNP, device.getId(), value);

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
        }if (device instanceof DeviceAEON_LABSHeavyDutySmart)
        {
            setContentView(R.layout.content_device_aeon_labs_heavy_duty_smart);

            final DeviceAEON_LABSHeavyDutySmart device_heavy_duty_smart = (DeviceAEON_LABSHeavyDutySmart)device;

            TextView tw_heavy_duty_smart_device = (TextView)findViewById(R.id.heavy_duty_smart_device_name);
            tw_heavy_duty_smart_device.setText(device.getName());


            device_heavy_duty_smart.viewer_tw_multilevel_humi = (TextView)findViewById(R.id.heavy_duty_smart_tw_humi);
            if (device_heavy_duty_smart.viewer_tw_multilevel_humi != null)
            {
                device_heavy_duty_smart.viewer_tw_multilevel_humi.setText(Double.toString(device_heavy_duty_smart.multilevel_sensor_humi));
            }

            device_heavy_duty_smart.viewer_tw_multilevel_temp = (TextView)findViewById(R.id.heavy_duty_smart_tw_temp);
            if (device_heavy_duty_smart.viewer_tw_multilevel_temp != null)
            {
                device_heavy_duty_smart.viewer_tw_multilevel_temp.setText(Double.toString(device_heavy_duty_smart.multilevel_sensor_humi));
            }


            device_heavy_duty_smart.viewer_tw_meter_power = (TextView)findViewById(R.id.heavy_duty_smart_tw_meter);
            if (device_heavy_duty_smart.viewer_tw_meter_power != null)
            {
                device_heavy_duty_smart.viewer_tw_meter_power.setText(Double.toString(device_heavy_duty_smart.meter_power));
            }





            Button button_temp = (Button)findViewById(R.id.heavy_duty_smart_button_temp);
            button_temp.setTag(device);

            button_temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Device device = (Device) v.getTag();
                    MqttMessage message = null;
                    try {
                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSHeavyDutySmart.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSHeavyDutySmart.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSHeavyDutySmart.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");

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



            Button button_humi = (Button)findViewById(R.id.heavy_duty_smart_button_humi);
            button_humi.setTag(device);
            button_humi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Device device = (Device) v.getTag();
                    MqttMessage message = null;
                    try {
                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSHeavyDutySmart.type_SENSOR_MULTILEVEL_HUMI, "", "");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSHeavyDutySmart.type_SENSOR_MULTILEVEL_HUMI, "", "");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSHeavyDutySmart.type_SENSOR_MULTILEVEL_HUMI, "", "");

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


            Button button_metter = (Button)findViewById(R.id.heavy_duty_smart_button_meter);
            button_metter.setTag(device);
            button_metter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Device device = (Device) v.getTag();
                    MqttMessage message = null;
                    try {
                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_METER, "GET", DeviceAEON_LABSHeavyDutySmart.type_METER_POWER, "", "");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_METER, "GET", DeviceAEON_LABSHeavyDutySmart.type_METER_POWER, "", "");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_METER, "GET", DeviceAEON_LABSHeavyDutySmart.type_METER_POWER, "", "");

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

        } else if (device instanceof DeviceAEON_LABSMultilevelSensor6) {


            final DeviceAEON_LABSMultilevelSensor6 device_mutilevel = (DeviceAEON_LABSMultilevelSensor6)device;

            setContentView(R.layout.content_device_generic_sensor_multilevel6);

            device_mutilevel.viewer_tw_multilevel6_humi = (TextView)findViewById(R.id.sensor_multilevel6_tw_humi);
            if (device_mutilevel.viewer_tw_multilevel6_humi != null)
            {
                device_mutilevel.viewer_tw_multilevel6_humi.setText(Double.toString(device_mutilevel.multilevel_sensor_humi));
            }

            device_mutilevel.viewer_tw_multilevel6_lumi = (TextView)findViewById(R.id.sensor_multilevel6_tw_lumi);
            if (device_mutilevel.viewer_tw_multilevel6_lumi != null)
            {
                device_mutilevel.viewer_tw_multilevel6_lumi.setText(Double.toString(device_mutilevel.multilevel_sensor_lumi));
            }

            device_mutilevel.viewer_tw_multilevel6_temp = (TextView)findViewById(R.id.sensor_multilevel6_tw_temp);
            if (device_mutilevel.viewer_tw_multilevel6_temp != null)
            {
                device_mutilevel.viewer_tw_multilevel6_temp.setText(Double.toString(device_mutilevel.multilevel_sensor_temp));
            }


            device_mutilevel.viewer_tw_multilevel6_uv = (TextView)findViewById(R.id.sensor_multilevel6_tw_ultra_violet);
            if (device_mutilevel.viewer_tw_multilevel6_uv != null)
            {
                device_mutilevel.viewer_tw_multilevel6_uv.setText(Double.toString(device_mutilevel.multilevel_sensor_uv));
            }


            // Configuration class
            device_mutilevel.viwer_cb_configuration_lock = (CheckBox)findViewById(R.id.sensor_multilevel6_cb_configuration_lock);
            if (device_mutilevel.viwer_cb_configuration_lock != null)
            {
                if (device_mutilevel.configuration_lock == 0)
                    device_mutilevel.viwer_cb_configuration_lock.setChecked(true);
                else
                    device_mutilevel.viwer_cb_configuration_lock.setChecked(false);

            }

            device_mutilevel.viewer_seekBar_configuration_timer = (SeekBar) findViewById(R.id.sensor_multilevel6_seekbar_configuration_timer);
            //device_mutilevel.viewer_seekBar_configuration_timer.setMax(Integer.parseInt(device_mutilevel.cmd_klass_CONFIGURATION_TIMER_VALUE_OFF) - 10);
            device_mutilevel.viewer_seekBar_configuration_timer.setMax(100); //FIXME: it is just for testing
            device_mutilevel.viewer_seekBar_configuration_timer.setProgress(device_mutilevel.configuration_timer - 10);





            TextView sensor_multilevel6_device_name = (TextView) findViewById(R.id.sensor_multilevel6_device_name);
            sensor_multilevel6_device_name.setText(device.getName());




            Button button_temp = (Button) findViewById(R.id.sensor_multilevel6_button_temp);

            button_temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MqttMessage message = null;
                    try {
                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor6.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor6.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor6.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");

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

            Button button_humi = (Button) findViewById(R.id.sensor_multilevel6_button_humi);

            button_humi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MqttMessage message = null;
                    try {
                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor6.type_SENSOR_MULTILEVEL_HUMI, "2A", "1/10");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor6.type_SENSOR_MULTILEVEL_HUMI, "2A", "1/10");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor6.type_SENSOR_MULTILEVEL_HUMI, "2A", "1/10");

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






            Button button_lumi = (Button) findViewById(R.id.sensor_multilevel6_button_lumi);

            button_lumi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MqttMessage message = null;
                    try {
                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor6.type_SENSOR_MULTILEVEL_LUMI, "2A", "1/10");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor6.type_SENSOR_MULTILEVEL_LUMI, "2A", "1/10");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor6.type_SENSOR_MULTILEVEL_LUMI, "2A", "1/10");

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


            Button button_violet = (Button) findViewById(R.id.sensor_multilevel6_button_ultra_violet);

            button_violet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MqttMessage message = null;
                    try {
                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor6.type_SENSOR_MULTILEVEL_UV, "2A", "1/10");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor6.type_SENSOR_MULTILEVEL_UV, "2A", "1/10");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor6.type_SENSOR_MULTILEVEL_UV, "2A", "1/10");

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


            // configuration class


            final CheckBox cb_enable_disable = (CheckBox)findViewById(R.id.sensor_multilevel6_cb_configuration_lock);
            if (cb_enable_disable != null)
            {
                cb_enable_disable.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String value = DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_LOCK_VALUE_ENABLE;
                        if (cb_enable_disable.isChecked())
                            value = DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_LOCK_VALUE_DISABLE;

                        DeviceAEON_LABSMultilevelSensor6 device_multilevel6 = (DeviceAEON_LABSMultilevelSensor6) device;
                        //device_multilevel6.configuration_timer = Integer.toString(timer_value);

                        MqttMessage message = null;
                        try {

                            if (device.type.contentEquals("zwave"))
                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_LOCK, value, "");
                            else if (device.type.contains("zigbee"))
                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_LOCK, value, "");
                            else if (device.type.contains("upnp"))
                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_LOCK, value, "");

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



            // Timer

            if (device_mutilevel.viewer_seekBar_configuration_timer != null) {
                device_mutilevel.viewer_seekBar_configuration_timer.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                    int configuration_timer_progress = device_mutilevel.configuration_timer - 10;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        configuration_timer_progress = progress;


                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                        MqttMessage message = null;
                        device_mutilevel.configuration_timer  = configuration_timer_progress;

                        try {

                            if (device.type.contentEquals("zwave"))
                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_TIMER, Integer.toHexString(device_mutilevel.configuration_timer - 10), "");
                            else if (device.type.contains("zigbee"))
                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_TIMER, Integer.toHexString(device_mutilevel.configuration_timer - 10), "");
                            else if (device.type.contains("upnp"))
                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_TIMER, Integer.toHexString(device_mutilevel.configuration_timer - 10), "");

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


            View.OnClickListener checkbox_report_configuration = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeviceAEON_LABSMultilevelSensor6 device_multilevel6 = (DeviceAEON_LABSMultilevelSensor6) device;
                    CheckBox cb_tmp = (CheckBox)v;

                    int configuration_report = device_multilevel6.configuration_report;

                    switch(v.getId()) {
                        case R.id.sensor_multilevel6_cb_temperature:
                            if (cb_tmp.isChecked()) {
                                configuration_report = configuration_report | Integer.parseInt(device_multilevel6.cmd_klass_CONFIGURATION_REPORT_VALUE_TEMP, 16);
                            }else {
                                configuration_report = configuration_report & ~Integer.parseInt(device_multilevel6.cmd_klass_CONFIGURATION_REPORT_VALUE_TEMP, 16);
                            }
                            break;

                        case R.id.sensor_multilevel6_cb_battery:
                            if (cb_tmp.isChecked()) {
                                configuration_report = configuration_report | Integer.parseInt(device_multilevel6.cmd_klass_CONFIGURATION_REPORT_VALUE_BATTERY, 16);
                            }else {
                                configuration_report = configuration_report & ~Integer.parseInt(device_multilevel6.cmd_klass_CONFIGURATION_REPORT_VALUE_BATTERY, 16);
                            }

                            break;

                        case R.id.sensor_multilevel6_cb_luminance:
                            if (cb_tmp.isChecked()) {
                                configuration_report = configuration_report | Integer.parseInt(device_multilevel6.cmd_klass_CONFIGURATION_REPORT_VALUE_LUMI, 16);
                            }else {
                                configuration_report = configuration_report & ~Integer.parseInt(device_multilevel6.cmd_klass_CONFIGURATION_REPORT_VALUE_LUMI, 16);
                            }

                            break;

                        case R.id.sensor_multilevel6_cb_ultra_violet:
                            if (cb_tmp.isChecked()) {
                                configuration_report = configuration_report | Integer.parseInt(device_multilevel6.cmd_klass_CONFIGURATION_REPORT_VALUE_ULTRA, 16);
                            }else {
                                configuration_report = configuration_report & ~Integer.parseInt(device_multilevel6.cmd_klass_CONFIGURATION_REPORT_VALUE_ULTRA, 16);
                            }
                            break;

                        case R.id.sensor_multilevel6_cb_humidity:
                            if (cb_tmp.isChecked()) {
                                configuration_report = configuration_report | Integer.parseInt(device_multilevel6.cmd_klass_CONFIGURATION_REPORT_VALUE_HUMI, 16);
                            }else {
                                configuration_report = configuration_report & ~Integer.parseInt(device_multilevel6.cmd_klass_CONFIGURATION_REPORT_VALUE_HUMI, 16);
                            }
                            break;

                    }

                    device_multilevel6.configuration_report = configuration_report;

                    String value = Integer.toHexString(configuration_report);



                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_REPORT, value, "");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        MainActivity.client.publish(MainActivity.topic, message);
                    } catch (MqttException e) {
                        Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
                    }

                }
            };


            CheckBox checkbox_report_temp = (CheckBox) findViewById(R.id.sensor_multilevel6_cb_temperature);
            checkbox_report_temp.setOnClickListener(checkbox_report_configuration);

            CheckBox checkbox_report_humi = (CheckBox) findViewById(R.id.sensor_multilevel6_cb_humidity);
            checkbox_report_humi.setOnClickListener(checkbox_report_configuration);

            CheckBox checkbox_report_uv = (CheckBox) findViewById(R.id.sensor_multilevel6_cb_ultra_violet);
            checkbox_report_uv.setOnClickListener(checkbox_report_configuration);

            CheckBox checkbox_report_battery = (CheckBox) findViewById(R.id.sensor_multilevel6_cb_battery);
            checkbox_report_battery.setOnClickListener(checkbox_report_configuration);

            CheckBox checkbox_report_lum = (CheckBox) findViewById(R.id.sensor_multilevel6_cb_luminance);
            checkbox_report_lum.setOnClickListener(checkbox_report_configuration);



            // Configuration class, Motion command class
            final RadioButton radio_button_motion_level1 = (RadioButton) findViewById(R.id.sensor_multilevel6_radio_motion_level1);
            radio_button_motion_level1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeviceAEON_LABSMultilevelSensor6 device_multilevel6 = (DeviceAEON_LABSMultilevelSensor6) device;

                    String value = "00";
                    if (radio_button_motion_level1.isChecked())
                        value = "1";

                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");

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


            final RadioButton radio_button_motion_level2 = (RadioButton) findViewById(R.id.sensor_multilevel6_radio_motion_level2);
            radio_button_motion_level2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeviceAEON_LABSMultilevelSensor6 device_multilevel6 = (DeviceAEON_LABSMultilevelSensor6) device;

                    String value = "00";
                    if (radio_button_motion_level2.isChecked())
                        value = "2";

                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");

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


            final RadioButton radio_button_motion_level3 = (RadioButton) findViewById(R.id.sensor_multilevel6_radio_motion_level3);
            radio_button_motion_level3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeviceAEON_LABSMultilevelSensor6 device_multilevel6 = (DeviceAEON_LABSMultilevelSensor6) device;

                    String value = "00";
                    if (radio_button_motion_level3.isChecked())
                        value = "3";

                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");

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


            final RadioButton radio_button_motion_level4 = (RadioButton) findViewById(R.id.sensor_multilevel6_radio_motion_level4);
            radio_button_motion_level4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeviceAEON_LABSMultilevelSensor6 device_multilevel6 = (DeviceAEON_LABSMultilevelSensor6) device;

                    String value = "00";
                    if (radio_button_motion_level4.isChecked())
                        value = "4";

                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");

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


            final RadioButton radio_button_motion_level5 = (RadioButton) findViewById(R.id.sensor_multilevel6_radio_motion_level5);
            radio_button_motion_level5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeviceAEON_LABSMultilevelSensor6 device_multilevel6 = (DeviceAEON_LABSMultilevelSensor6) device;

                    String value = "00";
                    if (radio_button_motion_level5.isChecked())
                        value = "5";

                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");

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

            RadioButton radio_button_motion_disable = (RadioButton) findViewById(R.id.sensor_multilevel6_radio_motion_disable);
            radio_button_motion_disable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeviceAEON_LABSMultilevelSensor6 device_multilevel6 = (DeviceAEON_LABSMultilevelSensor6) device;

                    RadioButton radio_button_temp = (RadioButton)v;

                    String value = "00";
                    if (radio_button_temp.isChecked())
                        value = "00";

                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_PIR, value, "");

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


            // Timer
            SeekBar seek_bar_auto_timer = (SeekBar) findViewById(R.id.sensor_multilevel6_seekbar_auto_timer);
            seek_bar_auto_timer.setMax(2678400 - 10);
            if (seek_bar_auto_timer != null) {
                seek_bar_auto_timer.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {


                    int progress_auto_timer = 0;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {



                        progress_auto_timer = progress;

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                        DeviceAEON_LABSMultilevelSensor6 device_multilevel6 = (DeviceAEON_LABSMultilevelSensor6) device;
                        progress_auto_timer = 15;

                        device_multilevel6.configuration_auto_timer = progress_auto_timer;
                        //Log.d("DEBUG", )

                        MqttMessage message = null;
                        try {

                            if (device.type.contentEquals("zwave"))
                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_AUTO_TIMER, Integer.toHexString(progress_auto_timer), "");
                            else if (device.type.contains("zigbee"))
                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_AUTO_TIMER, Integer.toHexString(progress_auto_timer), "");
                            else if (device.type.contains("upnp"))
                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_AUTO_TIMER, Integer.toHexString(progress_auto_timer), "");

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


            // association test


            final CheckBox cb_association_notification = (CheckBox)findViewById(R.id.sensor_multilevel6_cb_association_add_group);
            if (cb_association_notification != null)
            {
                cb_association_notification.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        DeviceAEON_LABSMultilevelSensor6 device_multilevel6 = (DeviceAEON_LABSMultilevelSensor6) device;

                        if (cb_association_notification.isChecked()){

                            MqttMessage message = null;
                            try {

                                if (device.type.contentEquals("zwave"))
                                    message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_ASSOCIATION,
                                            "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_ASSOCIATION_ONOFF_GROUP, DeviceAEON_LABSMultilevelSensor6.cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID , "");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                MainActivity.client.publish(MainActivity.topic, message);
                            } catch (MqttException e) {
                                Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
                            }
                        }else
                        {
                            MqttMessage message = null;
                            try {

                                if (device.type.contentEquals("zwave"))
                                    message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_ASSOCIATION,
                                            "REMOVE", DeviceAEON_LABSMultilevelSensor6.cmd_klass_ASSOCIATION_ONOFF_GROUP, DeviceAEON_LABSMultilevelSensor6.cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID , "");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                MainActivity.client.publish(MainActivity.topic, message);
                            } catch (MqttException e) {
                                Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
                            }


                        }

                    }
                });
            }



        } else if (device instanceof DeviceAEON_LABSMultilevelSensor5) {


            DeviceAEON_LABSMultilevelSensor5 device_mutilevel = (DeviceAEON_LABSMultilevelSensor5)device;

            setContentView(R.layout.content_device_generic_sensor_multilevel5);

            device_mutilevel.viewer_tw_multilevel5_humi = (TextView)findViewById(R.id.sensor_multilevel5_tw_humi);
            if (device_mutilevel.viewer_tw_multilevel5_humi != null)
            {
                device_mutilevel.viewer_tw_multilevel5_humi.setText(Double.toString(device_mutilevel.multilevel_sensor_humi));
            }

            device_mutilevel.viewer_tw_multilevel5_lumi = (TextView)findViewById(R.id.sensor_multilevel5_tw_lumi);
            if (device_mutilevel.viewer_tw_multilevel5_lumi != null)
            {
                device_mutilevel.viewer_tw_multilevel5_lumi.setText(Double.toString(device_mutilevel.multilevel_sensor_lumi));
            }

            device_mutilevel.viewer_tw_multilevel5_temp = (TextView)findViewById(R.id.sensor_multilevel5_tw_temp);
            if (device_mutilevel.viewer_tw_multilevel5_temp != null)
            {
                device_mutilevel.viewer_tw_multilevel5_temp.setText(Double.toString(device_mutilevel.multilevel_sensor_temp));
            }

            device_mutilevel.viewer_tw_sensor_battery = (TextView)findViewById(R.id.sensor_multilevel5_tw_battery);
            if (device_mutilevel.viewer_tw_sensor_battery != null)
            {
                device_mutilevel.viewer_tw_sensor_battery.setText(device_mutilevel.battery_level);
            }



            // Configuration class
//            device_mutilevel.viwer_cb_configuration_lock = (CheckBox)findViewById(R.id.sensor_multilevel5_cb_configuration_lock);
//            if (device_mutilevel.viwer_cb_configuration_lock != null)
//            {
//                if (device_mutilevel.configuration_lock == 0)
//                    device_mutilevel.viwer_cb_configuration_lock.setChecked(true);
//                else
//                    device_mutilevel.viwer_cb_configuration_lock.setChecked(false);
//
//            }


            device_mutilevel.viewer_seekBar_configuration_timer = (SeekBar) findViewById(R.id.sensor_multilevel5_seekbar_configuration_timer);
            //device_mutilevel.viewer_seekBar_configuration_timer.setMax(Integer.parseInt(device_mutilevel.cmd_klass_CONFIGURATION_TIMER_VALUE_OFF) - 10);
            device_mutilevel.viewer_seekBar_configuration_timer.setMax(100); //FIXME: it is just for testing
            device_mutilevel.viewer_seekBar_configuration_timer.setProgress(device_mutilevel.configuration_timer - 10);





            TextView sensor_multilevel5_device_name = (TextView) findViewById(R.id.sensor_multilevel5_device_name);
            sensor_multilevel5_device_name.setText(device.getName());




            Button button_temp = (Button) findViewById(R.id.sensor_multilevel5_button_temp);

            button_temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MqttMessage message = null;
                    try {
                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");

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

            Button button_humi = (Button) findViewById(R.id.sensor_multilevel5_button_humi);

            button_humi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MqttMessage message = null;
                    try {
                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_HUMI, "2A", "1/10");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_HUMI, "2A", "1/10");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_HUMI, "2A", "1/10");

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



            Button button_lumi = (Button) findViewById(R.id.sensor_multilevel5_button_lumi);

            button_lumi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MqttMessage message = null;
                    try {
                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_LUMI, "2A", "1/10");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_LUMI, "2A", "1/10");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_LUMI, "2A", "1/10");

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



            Button button_battery = (Button) findViewById(R.id.sensor_multilevel5_button_battery);

            button_battery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MqttMessage message = null;
                    try {
                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_BATTERY, "GET", "", "", "");
                       
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


//            // configuration class
//
//            final CheckBox cb_enable_disable = (CheckBox)findViewById(R.id.sensor_multilevel5_cb_configuration_lock);
//            if (cb_enable_disable != null)
//            {
//                cb_enable_disable.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        String value = DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_LOCK_VALUE_ENABLE;
//                        if (cb_enable_disable.isChecked())
//                            value = DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_LOCK_VALUE_DISABLE;
//
//                        DeviceAEON_LABSMultilevelSensor5 device_multilevel5 = (DeviceAEON_LABSMultilevelSensor5) device;
//                        //device_multilevel5.configuration_timer = Integer.toString(timer_value);
//
//                        MqttMessage message = null;
//                        try {
//
//                            if (device.type.contentEquals("zwave"))
//                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
//                                        "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_LOCK, value, "");
//                            else if (device.type.contains("zigbee"))
//                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
//                                        "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_LOCK, value, "");
//                            else if (device.type.contains("upnp"))
//                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
//                                        "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_LOCK, value, "");
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            MainActivity.client.publish(MainActivity.topic, message);
//                        } catch (MqttException e) {
//                            Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
//                        }
//
//                    }
//                });
//            }
//


            // Timer

            if (device_mutilevel.viewer_seekBar_configuration_timer != null) {
                device_mutilevel.viewer_seekBar_configuration_timer.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                    int configuration_timer_progress = 0;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        configuration_timer_progress = progress;


                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                        MqttMessage message = null;

                        //device_mutilevel.configuration_timer  = configuration_timer_progress;

                        try {

                            if (device.type.contentEquals("zwave"))
                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_TIMER, Integer.toHexString(configuration_timer_progress - 10), "");
                            else if (device.type.contains("zigbee"))
                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_TIMER, Integer.toHexString(configuration_timer_progress - 10), "");
                            else if (device.type.contains("upnp"))
                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_TIMER, Integer.toHexString(configuration_timer_progress - 10), "");

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

            final RadioButton radio_button_temp = (RadioButton) findViewById(R.id.sensor_multilevel5_radio_temperature);
            radio_button_temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeviceAEON_LABSMultilevelSensor5 device_multilevel5 = (DeviceAEON_LABSMultilevelSensor5) device;

                    String value = "00";
                    if (radio_button_temp.isChecked())
                        value = "20";

                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");

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


            RadioButton radio_button_humi = (RadioButton) findViewById(R.id.sensor_multilevel5_radio_humidity);
            radio_button_humi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeviceAEON_LABSMultilevelSensor5 device_multilevel5 = (DeviceAEON_LABSMultilevelSensor5) device;

                    RadioButton rb_tmp = (RadioButton)v;
                    String value = "00";
                    if (rb_tmp.isChecked())
                        value = "40";


                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");

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


            RadioButton radio_button_lumi = (RadioButton) findViewById(R.id.sensor_multilevel5_radio_luminance);
            radio_button_lumi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeviceAEON_LABSMultilevelSensor5 device_multilevel5 = (DeviceAEON_LABSMultilevelSensor5) device;

                    RadioButton rb_tmp = (RadioButton)v;
                    String value = "00";
                    if (rb_tmp.isChecked())
                        value = "80";

                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");

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


            RadioButton radio_button_battery = (RadioButton) findViewById(R.id.sensor_multilevel5_radio_battery);
            radio_button_battery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeviceAEON_LABSMultilevelSensor5 device_multilevel5 = (DeviceAEON_LABSMultilevelSensor5) device;


                    RadioButton rb_tmp = (RadioButton)v;

                    String value = "00";
                    if (rb_tmp.isChecked())
                        value = "01";

                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");

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



            // Configuration class, Motion command class

            CheckBox cb_motion_enable = (CheckBox) findViewById(R.id.sensor_multilevel5_cb_motion_enable);

            cb_motion_enable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeviceAEON_LABSMultilevelSensor5 device_multilevel5 = (DeviceAEON_LABSMultilevelSensor5) device;

                    CheckBox cb_tmp = (CheckBox)v;
                    String value = DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_OFF;

                    if ( cb_tmp.isChecked() )
                    {
                        value = DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_ON;
                    }


                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("zigbee"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("upnp"))
                            message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_PIR, value, "");

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


            // Timer
            SeekBar seek_bar_auto_timer = (SeekBar) findViewById(R.id.sensor_multilevel5_seekbar_auto_timer);
            seek_bar_auto_timer.setMax(2678400 - 10);
            if (seek_bar_auto_timer != null) {
                seek_bar_auto_timer.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {


                    int progress_auto_timer = 0;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {



                        progress_auto_timer = progress;

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                        DeviceAEON_LABSMultilevelSensor5 device_multilevel5 = (DeviceAEON_LABSMultilevelSensor5) device;
                        progress_auto_timer = 15;

                        device_multilevel5.configuration_auto_timer = progress_auto_timer;
                        //Log.d("DEBUG", )

                        MqttMessage message = null;
                        try {

                            if (device.type.contentEquals("zwave"))
                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_AUTO_TIMER, Integer.toHexString(progress_auto_timer), "");
                            else if (device.type.contains("zigbee"))
                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_AUTO_TIMER, Integer.toHexString(progress_auto_timer), "");
                            else if (device.type.contains("upnp"))
                                message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_AUTO_TIMER, Integer.toHexString(progress_auto_timer), "");

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


            // association test


            final CheckBox cb_association_notification = (CheckBox)findViewById(R.id.sensor_multilevel5_cb_association_add_group);
            if (cb_association_notification != null)
            {
                cb_association_notification.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        DeviceAEON_LABSMultilevelSensor5 device_multilevel5 = (DeviceAEON_LABSMultilevelSensor5) device;

                        if (cb_association_notification.isChecked()){

                            MqttMessage message = null;
                            try {

                                if (device.type.contentEquals("zwave"))
                                    message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_ASSOCIATION,
                                            "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID , "");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                MainActivity.client.publish(MainActivity.topic, message);
                            } catch (MqttException e) {
                                Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
                            }
                        }else
                        {
                            MqttMessage message = null;
                            try {

                                if (device.type.contentEquals("zwave"))
                                    message = MQTTMessageWrapper.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_ASSOCIATION,
                                            "REMOVE", DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID , "");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                MainActivity.client.publish(MainActivity.topic, message);
                            } catch (MqttException e) {
                                Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
                            }


                        }

                    }
                });
            }



        }

    }
}
