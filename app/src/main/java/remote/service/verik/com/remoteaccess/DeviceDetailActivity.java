package remote.service.verik.com.remoteaccess;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import remote.service.verik.com.remoteaccess.model.DeviceAEOTEC_Door_Window_Sensor;
import remote.service.verik.com.remoteaccess.model.DeviceGenericDimmer;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSMultilevelSensor6;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSHeavyDutySmart;
import remote.service.verik.com.remoteaccess.model.DeviceIR_SEC_SAFETYDoorLock;

public class DeviceDetailActivity extends FragmentActivity implements ActionBar.TabListener {

    public static Device device;

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    ViewPager mViewPager;




    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            return device.getFragment(i);
        }

        @Override
        public int getCount() {
            return device.getFragmentCount();

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return device.getFragmentTitle(position);
            //return "Section " + (position + 1);
        }
    }






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
                            message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZWAVE, device_dimmer.getId(), dimer_progress);
                        else if (device.type.contains("zigbee"))
                            message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZIGBEE, device_dimmer.getId(), dimer_progress);
                        else if (device.type.contains("upnp"))
                            message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.UPNP, device_dimmer.getId(), dimer_progress);

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
                            message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZWAVE, device.getId(), value);
                        else if (device.type.contains("zigbee"))
                            message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), value);
                        else if (device.type.contains("upnp"))
                            message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.UPNP, device.getId(), value);

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
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSHeavyDutySmart.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");
                        else if (device.type.contains("zigbee"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSHeavyDutySmart.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");
                        else if (device.type.contains("upnp"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSHeavyDutySmart.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");

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
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSHeavyDutySmart.type_SENSOR_MULTILEVEL_HUMI, "", "");
                        else if (device.type.contains("zigbee"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSHeavyDutySmart.type_SENSOR_MULTILEVEL_HUMI, "", "");
                        else if (device.type.contains("upnp"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSHeavyDutySmart.type_SENSOR_MULTILEVEL_HUMI, "", "");

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
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_METER, "GET", DeviceAEON_LABSHeavyDutySmart.type_METER_POWER, "", "");
                        else if (device.type.contains("zigbee"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_METER, "GET", DeviceAEON_LABSHeavyDutySmart.type_METER_POWER, "", "");
                        else if (device.type.contains("upnp"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_METER, "GET", DeviceAEON_LABSHeavyDutySmart.type_METER_POWER, "", "");

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


            setContentView(R.layout.device_detail);


            // Create the adapter that will return a fragment for each of the three primary sections
            // of the app.
            mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());


            // Set up the action bar.
            final ActionBar actionBar = getActionBar();

            // Specify that the Home/Up button should not be enabled, since there is no hierarchical
            // parent.
            actionBar.setHomeButtonEnabled(false);

            // Specify that we will be displaying tabs in the action bar.
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            // Set up the ViewPager, attaching the adapter and setting up a listener for when the
            // user swipes between sections.
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mAppSectionsPagerAdapter);
            mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    // When swiping between different app sections, select the corresponding tab.
                    // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                    // Tab.
                    actionBar.setSelectedNavigationItem(position);
                }
            });



            // For each of the sections in the app, add a tab to the action bar.
            for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
                // Create a tab with text corresponding to the page title defined by the adapter.
                // Also specify this Activity object, which implements the TabListener interface, as the
                // listener for when this tab is selected.
                actionBar.addTab(
                        actionBar.newTab()
                                .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                                .setTabListener(this));
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
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");
                        else if (device.type.contains("zigbee"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");
                        else if (device.type.contains("upnp"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");

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
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_HUMI, "2A", "1/10");
                        else if (device.type.contains("zigbee"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_HUMI, "2A", "1/10");
                        else if (device.type.contains("upnp"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_HUMI, "2A", "1/10");

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
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_LUMI, "2A", "1/10");
                        else if (device.type.contains("zigbee"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_LUMI, "2A", "1/10");
                        else if (device.type.contains("upnp"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_LUMI, "2A", "1/10");

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
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_BATTERY, "GET", "", "", "");
                       
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
                                message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_TIMER, Integer.toHexString(configuration_timer_progress - 10), "");
                            else if (device.type.contains("zigbee"))
                                message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_TIMER, Integer.toHexString(configuration_timer_progress - 10), "");
                            else if (device.type.contains("upnp"))
                                message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
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
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("zigbee"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("upnp"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
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
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("zigbee"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("upnp"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
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
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("zigbee"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("upnp"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
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
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("zigbee"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                        else if (device.type.contains("upnp"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
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
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("zigbee"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_PIR, value, "");
                        else if (device.type.contains("upnp"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(),
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
                                message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_AUTO_TIMER, Integer.toHexString(progress_auto_timer), "");
                            else if (device.type.contains("zigbee"))
                                message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_AUTO_TIMER, Integer.toHexString(progress_auto_timer), "");
                            else if (device.type.contains("upnp"))
                                message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.UPNP, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
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
                                    message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_ASSOCIATION,
                                            "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID, "");

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
                                    message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_ASSOCIATION,
                                            "REMOVE", DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID, "");

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



        } else if(device instanceof DeviceIR_SEC_SAFETYDoorLock) {


            final DeviceIR_SEC_SAFETYDoorLock device_ir_doorlock = (DeviceIR_SEC_SAFETYDoorLock)device;

            setContentView(R.layout.content_device_ir_sec_safety_doorlock);


            View.OnClickListener checkbox_configuration = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String klass_cmd = "";
                    String value = "00";
                    CheckBox cb_tmp = (CheckBox)v;

                    if (cb_tmp.isChecked())
                        value = "FF";

                    switch(v.getId()) {
                        case R.id.sensor_ir_sec_safety_doorNlock_cb_auto_lock:
                            klass_cmd = DeviceIR_SEC_SAFETYDoorLock.cmd_klass_CONFIGURATION_AUTO_LOCK;
                            break;
                        case R.id.sensor_ir_sec_safety_doorNlock_cb_beeper:
                            klass_cmd = DeviceIR_SEC_SAFETYDoorLock.cmd_klass_CONFIGURATION_BEEPER;
                            break;

                        case R.id.sensor_ir_sec_safety_doorNlock_cb_lock_n_leave:
                            klass_cmd = DeviceIR_SEC_SAFETYDoorLock.cmd_klass_CONFIGURATION_LOCK_AND_LEAVE;
                            break;

                        case R.id.sensor_ir_sec_safety_doorNlock_cb_vacation:
                            klass_cmd = DeviceIR_SEC_SAFETYDoorLock.cmd_klass_CONFIGURATION_VACATION;
                            break;

                    }

                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSecureMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceIR_SEC_SAFETYDoorLock.klass_SENSOR_CONFIGURATION, "SET", klass_cmd, value, "");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

                }
            };

            CheckBox checkbox_door_and_lock = (CheckBox) findViewById(R.id.sensor_ir_sec_safety_doorNlock_cb_auto_lock);
            checkbox_door_and_lock.setOnClickListener(checkbox_configuration);

            CheckBox checkbox_beeper = (CheckBox) findViewById(R.id.sensor_ir_sec_safety_doorNlock_cb_beeper);
            checkbox_beeper.setOnClickListener(checkbox_configuration);

            CheckBox checkbox_lock_n_leave = (CheckBox) findViewById(R.id.sensor_ir_sec_safety_doorNlock_cb_lock_n_leave);
            checkbox_lock_n_leave.setOnClickListener(checkbox_configuration);

            CheckBox checkbox_vacation = (CheckBox) findViewById(R.id.sensor_ir_sec_safety_doorNlock_cb_vacation);
            checkbox_vacation.setOnClickListener(checkbox_configuration);

            device_ir_doorlock.viewer_et_pin_len = (EditText)findViewById(R.id.sensor_ir_sec_safety_doorNlock_et_pin_len);
            if (device_ir_doorlock.viewer_et_pin_len != null){
                device_ir_doorlock.viewer_et_pin_len.setText("4-8");
            }

            Button viewer_bt_pin_len_get = (Button)findViewById(R.id.sensor_ir_sec_safety_doorNlock_button_pin_len_get);
            if (viewer_bt_pin_len_get != null)
            {
                viewer_bt_pin_len_get.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MqttMessage message = null;
                        try {

                            if (device.type.contentEquals("zwave"))
                                message = RemoteAccessMsg.CreateGetSecureMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                        DeviceIR_SEC_SAFETYDoorLock.klass_SENSOR_CONFIGURATION, "GET", DeviceIR_SEC_SAFETYDoorLock.cmd_klass_CONFIGURATION_PIN_LENGTH, "1", "");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

                    }
                });
            }

            Button bt_pin_len_set = (Button)findViewById(R.id.sensor_ir_sec_safety_doorNlock_button_pin_len_set);
            if (bt_pin_len_set != null)
            {
                bt_pin_len_set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String pin_len = device_ir_doorlock.viewer_et_pin_len.getText().toString();

                        MqttMessage message = null;
                        try {

                            if (device.type.contentEquals("zwave"))
                                message = RemoteAccessMsg.CreateSetSecureMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                        DeviceIR_SEC_SAFETYDoorLock.klass_SENSOR_CONFIGURATION, "SET", DeviceIR_SEC_SAFETYDoorLock.cmd_klass_CONFIGURATION_PIN_LENGTH,  pin_len, "");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

                    }
                });
            }


            Button bt_battery_get = (Button)findViewById(R.id.sensor_ir_sec_safety_doorNlock_button_battery_get);
            if (bt_battery_get != null)
            {
                bt_battery_get.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MqttMessage message = null;
                        try {

                            if (device.type.contentEquals("zwave"))
                                message = RemoteAccessMsg.CreateGetSecureMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                        DeviceIR_SEC_SAFETYDoorLock.klass_SENSOR_BATTERY, "GET", "", "", "");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

                    }
                });
            }




            View.OnClickListener door_lock_listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String klass_cmd = DeviceIR_SEC_SAFETYDoorLock.cmd_klass_DOOR_LOCK_insecure;
                    CheckBox cb_tmp = (CheckBox)v;

                    if (cb_tmp.isChecked())
                        klass_cmd = DeviceIR_SEC_SAFETYDoorLock.cmd_klass_DOOR_LOCK_secure;

                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSecureMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceIR_SEC_SAFETYDoorLock.klass_SENSOR_DOOR_LOCK, "SET", klass_cmd, "", "");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

                }
            };

            CheckBox checkbox_door_lock = (CheckBox) findViewById(R.id.sensor_ir_sec_safety_doorNlock_cb_door_lock);
            checkbox_door_lock.setOnClickListener(door_lock_listener);




            View.OnClickListener association_listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String klass_cmd = "REMOVE";
                    CheckBox cb_tmp = (CheckBox)v;

                    if (cb_tmp.isChecked())
                        klass_cmd = "SET";

                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSecureMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceIR_SEC_SAFETYDoorLock.klass_SENSOR_DOOR_LOCK, klass_cmd, DeviceIR_SEC_SAFETYDoorLock.cmd_klass_ASSOCIATION_ONOFF_GROUP, "1", "1");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

                }
            };

            CheckBox checkbox_association = (CheckBox) findViewById(R.id.sensor_ir_sec_safety_doorNlock_cb_association_add_group);
            checkbox_association.setOnClickListener(association_listener);


            device_ir_doorlock.viewer_user_code_1_et_code = (EditText)findViewById(R.id.sensor_ir_sec_safety_doorNlock_et_user_code_1);
            if (device_ir_doorlock.viewer_user_code_1_et_code != null){
                device_ir_doorlock.viewer_user_code_1_et_code.setText("123456");
            }

            Button viewer_bt_user_code1_set = (Button)findViewById(R.id.sensor_ir_sec_safety_doorNlock_button_user_code_1_set);
            if (viewer_bt_user_code1_set != null)
            {
                viewer_bt_user_code1_set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MqttMessage message = null;
                        try {

                            if (device.type.contentEquals("zwave"))
                                message = RemoteAccessMsg.CreateSetSecureMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                        DeviceIR_SEC_SAFETYDoorLock.klass_SENSOR_USER_CODE, "SET", "1", device_ir_doorlock.getKlass_SENSOR_USER_CODE_STATE_ACCUPIED, device_ir_doorlock.viewer_user_code_1_et_code.getText().toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

                    }
                });
            }


        }else if(device instanceof DeviceAEOTEC_Door_Window_Sensor) {


            final DeviceAEOTEC_Door_Window_Sensor device_door_window = (DeviceAEOTEC_Door_Window_Sensor)device;

            setContentView(R.layout.content_device_aeotec_door_window);

            View.OnClickListener checkbox_configuration_determine = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String klass_cmd = "";
                    String value = "00";
                    CheckBox cb_tmp = (CheckBox)v;

                    switch(v.getId()) {
                        case R.id.sensor_ir_sec_safety_aeotec_door_window_cb_determines_basic_set:
                            if (cb_tmp.isChecked())
                                value = DeviceAEOTEC_Door_Window_Sensor.getCmd_klass_CONFIGURATION_DETERMINES_VALUES_BASIC_SET_RP;
                            else
                                value = DeviceAEOTEC_Door_Window_Sensor.getCmd_klass_CONFIGURATION_DETERMINES_VALUES_DISABLE;

                            break;
                        case R.id.sensor_ir_sec_safety_aeotec_door_window_cb_determines_binary_sensor:
                            if (cb_tmp.isChecked())
                                value = DeviceAEOTEC_Door_Window_Sensor.getCmd_klass_CONFIGURATION_DETERMINES_VALUES_BINARY_SENSOR_RP;
                            else
                                value = DeviceAEOTEC_Door_Window_Sensor.getCmd_klass_CONFIGURATION_DETERMINES_VALUES_DISABLE;
                            break;

                    }

                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEOTEC_Door_Window_Sensor.klass_SENSOR_CONFIGURATION, "SET", DeviceAEOTEC_Door_Window_Sensor.cmd_klass_CONFIGURATION_DETERMINES, value, "");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

                }
            };


            CheckBox checkbox_binary_sensor = (CheckBox) findViewById(R.id.sensor_ir_sec_safety_aeotec_door_window_cb_determines_binary_sensor);
            checkbox_binary_sensor.setOnClickListener(checkbox_configuration_determine);

            CheckBox checkbox_basic_set = (CheckBox) findViewById(R.id.sensor_ir_sec_safety_aeotec_door_window_cb_determines_basic_set);
            checkbox_basic_set.setOnClickListener(checkbox_configuration_determine);



            View.OnClickListener checkbox_configuration_set_report = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String klass_cmd = "";
                    String value = "00";
                    CheckBox cb_tmp = (CheckBox)v;

                    switch(v.getId()) {
                        case R.id.sensor_ir_sec_safety_aeotec_door_window_cb_bainary_sensor:
                            klass_cmd = DeviceAEOTEC_Door_Window_Sensor.cmd_klass_CONFIGURATION_BINARY_SENSOR;
                            if (cb_tmp.isChecked()) {
                                value = "1";
                            }
                            else {
                                value = "0";
                            }
                            break;
                        case R.id.sensor_ir_sec_safety_aeotec_door_window_cb_basic_set:
                            klass_cmd = DeviceAEOTEC_Door_Window_Sensor.cmd_klass_CONFIGURATION_BASIC_SET;
                            if (cb_tmp.isChecked())
                                value = "1";
                            else
                                value = "0";
                            break;

                    }

                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEOTEC_Door_Window_Sensor.klass_SENSOR_CONFIGURATION, "SET", klass_cmd, value, "");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

                }
            };


            CheckBox checkbox_binary_sensor_set = (CheckBox) findViewById(R.id.sensor_ir_sec_safety_aeotec_door_window_cb_basic_set);
            checkbox_binary_sensor_set.setOnClickListener(checkbox_configuration_set_report);

            CheckBox checkbox_basic_set_set = (CheckBox) findViewById(R.id.sensor_ir_sec_safety_aeotec_door_window_cb_bainary_sensor);
            checkbox_basic_set_set.setOnClickListener(checkbox_configuration_set_report);




            View.OnClickListener association_listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String klass_cmd = "REMOVE";
                    CheckBox cb_tmp = (CheckBox)v;

                    if (cb_tmp.isChecked())
                        klass_cmd = "SET";

                    MqttMessage message = null;
                    try {

                        if (device.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                    DeviceAEOTEC_Door_Window_Sensor.klass_SENSOR_ASSOCIATION, klass_cmd, DeviceAEOTEC_Door_Window_Sensor.cmd_klass_ASSOCIATION_ONOFF_GROUP, "1", "");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

                }
            };

            CheckBox checkbox_association = (CheckBox) findViewById(R.id.sensor_ir_sec_safety_aeotec_door_window_cb_association_add_group);
            checkbox_association.setOnClickListener(association_listener);


        }


    }
}
