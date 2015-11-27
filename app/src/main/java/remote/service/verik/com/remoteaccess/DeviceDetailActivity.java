package remote.service.verik.com.remoteaccess;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import remote.service.verik.com.remoteaccess.model.Device;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSMultilevelSensor5;
import remote.service.verik.com.remoteaccess.model.DeviceAEOTEC_Door_Window_Sensor;
import remote.service.verik.com.remoteaccess.model.DeviceGenericDimmer;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSMultilevelSensor6;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSHeavyDutySmart;
import remote.service.verik.com.remoteaccess.model.DeviceIR_SEC_SAFETYDoorLock;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSSiren5;

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

            final DeviceGenericDimmer device_dimmer = (DeviceGenericDimmer) device;

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
                    if (device.type.contentEquals("zwave"))
                        message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZWAVE, device_dimmer.getId(), dimer_progress);
                    else if (device.type.contains("zigbee"))
                        message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZIGBEE, device_dimmer.getId(), dimer_progress);
                    else if (device.type.contains("upnp"))
                        message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.UPNP, device_dimmer.getId(), dimer_progress);


                    MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);



                }
            });


            ImageView dimmer_bulb = (ImageView) findViewById(R.id.dimmer_bulb);
            dimmer_bulb.setTag(device);

            dimmer_bulb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Device device = (Device) v.getTag();
                    MqttMessage message = null;
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


                    MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);



                }
            });
        }
        if (device instanceof DeviceAEON_LABSHeavyDutySmart) {
            setContentView(R.layout.content_device_aeon_labs_heavy_duty_smart);

            final DeviceAEON_LABSHeavyDutySmart device_heavy_duty_smart = (DeviceAEON_LABSHeavyDutySmart) device;

            TextView tw_heavy_duty_smart_device = (TextView) findViewById(R.id.heavy_duty_smart_device_name);
            tw_heavy_duty_smart_device.setText(device.getName());


            device_heavy_duty_smart.viewer_tw_multilevel_humi = (TextView) findViewById(R.id.heavy_duty_smart_tw_humi);
            if (device_heavy_duty_smart.viewer_tw_multilevel_humi != null) {
                device_heavy_duty_smart.viewer_tw_multilevel_humi.setText(Double.toString(device_heavy_duty_smart.multilevel_sensor_humi));
            }

            device_heavy_duty_smart.viewer_tw_multilevel_temp = (TextView) findViewById(R.id.heavy_duty_smart_tw_temp);
            if (device_heavy_duty_smart.viewer_tw_multilevel_temp != null) {
                device_heavy_duty_smart.viewer_tw_multilevel_temp.setText(Double.toString(device_heavy_duty_smart.multilevel_sensor_humi));
            }


            device_heavy_duty_smart.viewer_tw_meter_power = (TextView) findViewById(R.id.heavy_duty_smart_tw_meter);
            if (device_heavy_duty_smart.viewer_tw_meter_power != null) {
                device_heavy_duty_smart.viewer_tw_meter_power.setText(Double.toString(device_heavy_duty_smart.meter_power));
            }


            Button button_temp = (Button) findViewById(R.id.heavy_duty_smart_button_temp);
            button_temp.setTag(device);

            button_temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Device device = (Device) v.getTag();
                    MqttMessage message = null;
                    if (device.type.contentEquals("zwave"))
                        message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSHeavyDutySmart.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");

                    MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);


                }
            });


            Button button_humi = (Button) findViewById(R.id.heavy_duty_smart_button_humi);
            button_humi.setTag(device);
            button_humi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Device device = (Device) v.getTag();
                    MqttMessage message = null;
                    if (device.type.contentEquals("zwave"))
                        message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSHeavyDutySmart.type_SENSOR_MULTILEVEL_HUMI, "", "");

                    MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);


                }
            });


            Button button_metter = (Button) findViewById(R.id.heavy_duty_smart_button_meter);
            button_metter.setTag(device);
            button_metter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Device device = (Device) v.getTag();
                    MqttMessage message = null;
                    if (device.type.contentEquals("zwave"))
                        message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(), DeviceAEON_LABSHeavyDutySmart.klass_METER, "GET", DeviceAEON_LABSHeavyDutySmart.type_METER_POWER, "", "");


                    MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);


                }
            });

        } else if (device instanceof DeviceAEON_LABSMultilevelSensor6 || device instanceof DeviceAEON_LABSMultilevelSensor5
                || device instanceof DeviceIR_SEC_SAFETYDoorLock || device instanceof DeviceAEON_LABSSiren5) {


            setContentView(R.layout.device_detail);


            // Create the adapter that will return a fragment for each of the three primary sections
            // of the app.
            mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());


            // Set up the action bar.
            final ActionBar actionBar = getActionBar();
            //actionBar.hide();

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

        } else if (device instanceof DeviceAEOTEC_Door_Window_Sensor) {


            final DeviceAEOTEC_Door_Window_Sensor device_door_window = (DeviceAEOTEC_Door_Window_Sensor) device;

            setContentView(R.layout.content_device_aeotec_door_window);

            View.OnClickListener checkbox_configuration_determine = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String klass_cmd = "";
                    String value = "00";
                    CheckBox cb_tmp = (CheckBox) v;

                    switch (v.getId()) {
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

                    if (device.type.contentEquals("zwave"))
                        message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                DeviceAEOTEC_Door_Window_Sensor.klass_SENSOR_CONFIGURATION, "SET", DeviceAEOTEC_Door_Window_Sensor.cmd_klass_CONFIGURATION_DETERMINES, value, "");


                    MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

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
                    CheckBox cb_tmp = (CheckBox) v;

                    switch (v.getId()) {
                        case R.id.sensor_ir_sec_safety_aeotec_door_window_cb_bainary_sensor:
                            klass_cmd = DeviceAEOTEC_Door_Window_Sensor.cmd_klass_CONFIGURATION_BINARY_SENSOR;
                            if (cb_tmp.isChecked()) {
                                value = "1";
                            } else {
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
                    if (device.type.contentEquals("zwave"))
                        message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                DeviceAEOTEC_Door_Window_Sensor.klass_SENSOR_CONFIGURATION, "SET", klass_cmd, value, "");


                    MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

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
                    CheckBox cb_tmp = (CheckBox) v;

                    if (cb_tmp.isChecked())
                        klass_cmd = "SET";

                    MqttMessage message = null;
                    if (device.type.contentEquals("zwave"))
                        message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, device.getId(),
                                DeviceAEOTEC_Door_Window_Sensor.klass_SENSOR_ASSOCIATION, klass_cmd, DeviceAEOTEC_Door_Window_Sensor.cmd_klass_ASSOCIATION_ONOFF_GROUP, "1", "");

                    MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

                }
            };

            CheckBox checkbox_association = (CheckBox) findViewById(R.id.sensor_ir_sec_safety_aeotec_door_window_cb_association_add_group);
            checkbox_association.setOnClickListener(association_listener);


        }


    }
}
