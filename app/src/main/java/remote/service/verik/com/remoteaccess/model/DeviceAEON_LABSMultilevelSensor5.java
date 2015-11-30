package remote.service.verik.com.remoteaccess.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;

import org.json.JSONObject;

import remote.service.verik.com.remoteaccess.R;
import remote.service.verik.com.remoteaccess.Vtransport.genericDevice.NotificationSensor;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveBatteryMsg;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveConfigurationMsg;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveMultilevelMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdAssociationResp;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdBatteryResp;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdConfigurationResp;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdMultilevelResp;

public class DeviceAEON_LABSMultilevelSensor5 extends NotificationSensor implements IcmdAssociationResp, IcmdConfigurationResp, IcmdMultilevelResp, IcmdBatteryResp {


    public double multilevel_sensor_temp = 0;
    public double multilevel_sensor_humi = 0;
    public double multilevel_sensor_lumi = 0;

    public int configuration_lock = 0;
    public int configuration_timer = 10; // minimun value
    public int configuration_auto_timer = Integer.parseInt(cmd_klass_CONFIGURATION_AUTO_TIMER_MIN, 16);


    public String battery_level = "00";


    // multilevel class
    final public static String klass_SENSOR_MULTILEVEL = "SENSOR_MULTILEVEL";
    final public static String type_SENSOR_MULTILEVEL_TEMP = "TEMP";
    final public static String type_SENSOR_MULTILEVEL_HUMI = "HUMI";
    final public static String type_SENSOR_MULTILEVEL_LUMI = "LUMI";


    // configuration class
    final public static String klass_SENSOR_CONFIGURATION = "CONFIGURATION";

    final public static String cmd_klass_CONFIGURATION_LOCK = "LOCK_CONFIGURATION";
    final public static String cmd_klass_CONFIGURATION_LOCK_VALUE_DISABLE = "0";
    final public static String cmd_klass_CONFIGURATION_LOCK_VALUE_ENABLE = "1";

    final public static String cmd_klass_CONFIGURATION_TIMER = "TIME";
    final public static String cmd_klass_CONFIGURATION_TIMER_VALUE_MIN = "1";
    final public static String cmd_klass_CONFIGURATION_TIMER_VALUE_MAX = "15300";

    final public static String cmd_klass_CONFIGURATION_REPORT = "REPORT_SENSOR";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_TEMP = "20";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_HUMI = "40";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_LUMI = "80";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_ULTRA = "02";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_BATTERY = "01";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_DISABLE = "00";


    final public static String cmd_klass_CONFIGURATION_PIR = "ENABLE_MOTION";
    final public static String cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_ON = "1";
    final public static String cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_OFF = "0";


    final public static String cmd_klass_CONFIGURATION_AUTO_TIMER = "TIME_AUTO_REPORT";
    final public static String cmd_klass_CONFIGURATION_AUTO_TIMER_MIN = "10";
    final public static String cmd_klass_CONFIGURATION_AUTO_TIMER_MAX = "2678400";


    // configuration class
    final public static String klass_SENSOR_ASSOCIATION = "ASSOCIATION";
    final public static String cmd_klass_ASSOCIATION_ONOFF_GROUP = "ONOFF_GROUP";
    final public static String cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID = "1";


    // Battery class
    final public static String klass_SENSOR_BATTERY = "BATTERY";


    public CheckBox viwer_cb_configuration_lock;
    public SeekBar viewer_seekBar_configuration_timer;


    public DeviceAEON_LABSMultilevelSensor5(String id, String name, boolean turnOn, boolean available, String device_type) {
        super(id, name, turnOn, available, device_type);

        // Fragment initialization
        listFragment.add(createFragmentMultilevel());
        listFragment.add(createFragmentConfiguration());
        listFragment.add(createFragmentAssociation());

    }

    private DeviceFragment createFragmentAssociation() {

        DeviceFragment fragment = new DeviceFragment() {
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

                View rootView = inflater.inflate(R.layout.content_device_aeon_labs_multilevel5_association, container, false);


                // association test
                Button button_ass_get = (Button) rootView.findViewById(R.id.button_association_group_get);
                if (button_ass_get != null) {
                    button_ass_get.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            associationGetNode(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP);
                        }
                    });
                }

                final EditText ed_nodeID = (EditText) rootView.findViewById(R.id.et_node_id1);


                Button button_ass_add = (Button) rootView.findViewById(R.id.button_association_group_node_add1);
                if (button_ass_add != null) {
                    button_ass_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String nodeID = ed_nodeID.getText().toString();

                            associationSetNode(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP, nodeID);
                        }
                    });
                }

                Button button_ass_remove = (Button) rootView.findViewById(R.id.button_association_group_node_remove1);
                if (button_ass_remove != null) {
                    button_ass_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String nodeID = ed_nodeID.getText().toString();

                            associationRemoveNode(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP, nodeID);
                        }
                    });
                }


                // association test
                CheckBox cb_association_notification = (CheckBox) rootView.findViewById(R.id.sensor_multilevel5_association_cb_association_add_group);
                if (cb_association_notification != null) {
                    cb_association_notification.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            if (((CheckBox) v).isChecked())
                                associationSetNode(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID);
                            else
                                associationRemoveNode(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID);

                        }
                    });
                }


                return rootView;

            }
        };
        fragment.setTitle("Association");
        return fragment;
    }

    private DeviceFragment createFragmentMultilevel() {

        DeviceFragment fragment = new DeviceFragment() {
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

                View rootView = inflater.inflate(R.layout.content_device_aeon_labs_multilevel5_multilevel, container, false);


                Button button_temp = (Button) rootView.findViewById(R.id.sensor_multilevel5_multilevel_button_temp);

                button_temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        zwaveMultilevelMsg multielvel = new zwaveMultilevelMsg();
                        multielvel.multilevelGet(id, type_SENSOR_MULTILEVEL_TEMP);
                    }
                });

                Button button_humi = (Button) rootView.findViewById(R.id.sensor_multilevel5_multilevel_button_humi);

                button_humi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        zwaveMultilevelMsg multielvel = new zwaveMultilevelMsg();
                        multielvel.multilevelGet(id, type_SENSOR_MULTILEVEL_HUMI);

                    }
                });


                Button button_lumi = (Button) rootView.findViewById(R.id.sensor_multilevel5_multilevel_button_lumi);

                button_lumi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        zwaveMultilevelMsg multielvel = new zwaveMultilevelMsg();
                        multielvel.multilevelGet(id, type_SENSOR_MULTILEVEL_LUMI);

                    }
                });


                Button button_battery = (Button) rootView.findViewById(R.id.sensor_multilevel5_multilevel_button_battery);

                button_battery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        zwaveBatteryMsg battery = new zwaveBatteryMsg();
                        battery.batteryGet(id);

                    }
                });
                return rootView;
            }

        };
        fragment.setTitle("MultilevelNBattery");

        return fragment;
    }

    private DeviceFragment createFragmentConfiguration() {

        DeviceFragment fragment = new DeviceFragment() {
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

                View rootView = inflater.inflate(R.layout.content_device_aeon_labs_multilevel5_configuration, container, false);

                // Timer

                DeviceAEON_LABSMultilevelSensor5.this.viewer_seekBar_configuration_timer = (SeekBar) rootView.findViewById(R.id.sensor_multilevel5_configuration_seekbar_configuration_timer);

                if (DeviceAEON_LABSMultilevelSensor5.this.viewer_seekBar_configuration_timer != null) {

                    // FIXME: scale for development
                    DeviceAEON_LABSMultilevelSensor5.this.viewer_seekBar_configuration_timer.setMax((Integer.parseInt(DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_TIMER_VALUE_MAX) - Integer.parseInt(DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_TIMER_VALUE_MIN)) / 10);

                    DeviceAEON_LABSMultilevelSensor5.this.viewer_seekBar_configuration_timer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            DeviceAEON_LABSMultilevelSensor5.this.configuration_timer = progress;

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Set(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_TIMER, Integer.toHexString(DeviceAEON_LABSMultilevelSensor5.this.configuration_timer - 10), "");


                        }
                    });
                }

                final RadioButton radio_button_temp = (RadioButton) rootView.findViewById(R.id.sensor_multilevel5_configuration_radio_temperature);
                radio_button_temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String value = "00";
                        if (radio_button_temp.isChecked())
                            value = "20";

                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");
                    }
                });


                RadioButton radio_button_humi = (RadioButton) rootView.findViewById(R.id.sensor_multilevel5_configuration_radio_humidity);
                radio_button_humi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        RadioButton rb_tmp = (RadioButton) v;
                        String value = "00";
                        if (rb_tmp.isChecked())
                            value = "40";

                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");

                    }

                });


                RadioButton radio_button_lumi = (RadioButton) rootView.findViewById(R.id.sensor_multilevel5_configuration_radio_luminance);
                radio_button_lumi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        RadioButton rb_tmp = (RadioButton) v;
                        String value = "00";
                        if (rb_tmp.isChecked())
                            value = "80";

                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");

                    }


                });


                RadioButton radio_button_battery = (RadioButton) rootView.findViewById(R.id.sensor_multilevel5_configuration_radio_battery);
                radio_button_battery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        RadioButton rb_tmp = (RadioButton) v;

                        String value = "00";
                        if (rb_tmp.isChecked())
                            value = "01";

                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");


                    }
                });

                CheckBox cb_motion_enable = (CheckBox) rootView.findViewById(R.id.sensor_multilevel5_configuration_cb_motion_enable);

                cb_motion_enable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CheckBox cb_tmp = (CheckBox) v;
                        String value = DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_OFF;

                        if (cb_tmp.isChecked()) {
                            value = DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_ON;
                        }

                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_PIR, value, "");


                    }
                });


                // Timer
                SeekBar seek_bar_auto_timer = (SeekBar) rootView.findViewById(R.id.sensor_multilevel5_configuration_seekbar_auto_timer);

                if (seek_bar_auto_timer != null) {

                    // FIXME: scale for development
                    DeviceAEON_LABSMultilevelSensor5.this.viewer_seekBar_configuration_timer.setMax((Integer.parseInt(DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_AUTO_TIMER_MAX) - Integer.parseInt(DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_AUTO_TIMER_MIN)) / 10000);


                    seek_bar_auto_timer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                            DeviceAEON_LABSMultilevelSensor5.this.configuration_auto_timer = progress + Integer.parseInt(DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_AUTO_TIMER_MIN);

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            //Log.d("DEBUG", )
                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Set(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_AUTO_TIMER, Integer.toHexString(DeviceAEON_LABSMultilevelSensor5.this.configuration_auto_timer), "");

                        }
                    });
                }


                // GET
                Button button_get_motion = (Button) rootView.findViewById(R.id.sensor_multilevel5_configuration_button_motion_enable_get);
                if (button_get_motion != null) {
                    button_get_motion.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Get(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_PIR, "");

                        }
                    });
                }
                Button button_get_report_configuration = (Button) rootView.findViewById(R.id.sensor_multilevel5_configuration_button_report_config_get);
                if (button_get_report_configuration != null) {
                    button_get_report_configuration.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Get(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, "");
                        }
                    });
                }

                Button button_get_report_auto_timer = (Button) rootView.findViewById(R.id.sensor_multilevel5_configuration_button_auto_timer_get);
                if (button_get_report_auto_timer != null) {
                    button_get_report_auto_timer.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Get(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_AUTO_TIMER, "");

                        }
                    });
                }


                Button button_get_report_timer = (Button) rootView.findViewById(R.id.sensor_multilevel5_configuration_button_timer_get);
                if (button_get_report_timer != null) {
                    button_get_report_timer.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Get(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_TIMER, "");
                        }
                    });
                }


                return rootView;

            }
        };
        fragment.setTitle("Configuration");
        return fragment;
    }


    @Override
    public void Update(String property) {
        super.Update(property);

    }

    @Override
    public void onAssociationGetResp(String groupIdentifier, String maxNode, String nodeFlow) {
        AddLogToHistory("[ASSOCIATION][GET] groupIdentifier: " + groupIdentifier + " maxNode: " + maxNode + " nodeFlow: " + nodeFlow);
    }

    @Override
    public void onConfigurationGetResp(String cmd, String data0, String data1, String data2, String status, String more) {
        //AddLogToHistory("[CONFIGURATION] data0: " + data0 + " data1: " + data1 + " data2: " + data2 + " status: " + status);
        AddLogToHistory("[CONFIGURATION][GET] " + more);

    }


    @Override
    public int multilevelGetResp(String more) {
        AddLogToHistory("[MULTILEVEL][GET] " + more);
        return 0;
    }

    @Override
    public int batteryGetResp(String more) {
        AddLogToHistory("[BATTERY][GET] " + more);
        return 0;
    }
}
