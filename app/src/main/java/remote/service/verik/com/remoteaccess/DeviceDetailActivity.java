package remote.service.verik.com.remoteaccess;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import remote.service.verik.com.remoteaccess.model.Device;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSHeavyDutySmart;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSMultilevelSensor5;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSDoor_Window_Sensor;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSSmartDimmerG2;
import remote.service.verik.com.remoteaccess.model.DeviceAEON_LABSMultilevelSensor6;
import remote.service.verik.com.remoteaccess.model.DeviceSchlageSAFETYDoorLock;
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


//        if (device instanceof DeviceAEON_LABSSmartDimmerG2) {
//
//            setContentView(R.layout.content_device_zwave_switch_multilevel);
//
//            final DeviceAEON_LABSSmartDimmerG2 device_dimmer = (DeviceAEON_LABSSmartDimmerG2) device;
//
//            TextView tw_dimmer_device = (TextView) findViewById(R.id.dimmer_device_name);
//            tw_dimmer_device.setText(device.getName());
//
//            SeekBar dimmer_seek_bar = (SeekBar) findViewById(R.id.dimmer_seekbar);
//            dimmer_seek_bar.setTag(device);
//
//            if (device.type.contains("zwave"))
//                dimmer_seek_bar.setMax(63);
//            else if (device.type.contains("zigbee"))
//                dimmer_seek_bar.setMax(255);
//            dimmer_seek_bar.setProgress(device_dimmer.dimmer_value);
//
//
//            dimmer_seek_bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//
//                public int dimer_progress = device_dimmer.dimmer_value;
//
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                    dimer_progress = progress;
//
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {
//
//                }
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {
//
//
//                    MqttMessage message = null;
//                    if (device.type.contentEquals("zwave"))
//                        message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZWAVE, device_dimmer.getId(), dimer_progress);
//                    else if (device.type.contains("zigbee"))
//                        message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZIGBEE, device_dimmer.getId(), dimer_progress);
//                    else if (device.type.contains("upnp"))
//                        message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.UPNP, device_dimmer.getId(), dimer_progress);
//
//
//                    MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);
//
//
//
//                }
//            });
//
//
//            ImageView dimmer_bulb = (ImageView) findViewById(R.id.dimmer_bulb);
//            dimmer_bulb.setTag(device);
//
//            dimmer_bulb.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Device device = (Device) v.getTag();
//                    MqttMessage message = null;
//                    int value = 1;
//                    if (device.isTurnOn())
//                        value = 0;
//
//                    if (device.type.contentEquals("zwave"))
//                        message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZWAVE, device.getId(), value);
//                    else if (device.type.contains("zigbee"))
//                        message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.ZIGBEE, device.getId(), value);
//                    else if (device.type.contains("upnp"))
//                        message = RemoteAccessMsg.CreateZwaveSetBinaryMsg(DeviceTypeProtocol.UPNP, device.getId(), value);
//
//                    boolean on = true;
//                    if (value == 0)
//                        on = false;
//
//                    device.setTurnOn(on);
//
//
//                    MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);
//
//
//
//                }
//            });
//        } else
        if (device instanceof DeviceAEON_LABSMultilevelSensor6 || device instanceof DeviceAEON_LABSMultilevelSensor5
                || device instanceof DeviceSchlageSAFETYDoorLock || device instanceof DeviceAEON_LABSSiren5
                || device instanceof  DeviceAEON_LABSDoor_Window_Sensor || device instanceof DeviceAEON_LABSSmartDimmerG2
                || device instanceof DeviceAEON_LABSHeavyDutySmart) {


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

        }


    }
}
