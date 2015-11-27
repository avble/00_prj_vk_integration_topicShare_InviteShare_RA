package remote.service.verik.com.remoteaccess.model;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;


import java.util.ArrayList;

import remote.service.verik.com.remoteaccess.CmdZWaveConfiguration;
import remote.service.verik.com.remoteaccess.R;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.Vtransport.genericDevice.genericMultilevelSensor;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveAssociationMsg;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveConfigurationMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdAssociationResp;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdConfigurationResp;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdMultilevelResp;


public class DeviceAEON_LABSMultilevelSensor6 extends genericMultilevelSensor implements  View.OnClickListener, IcmdMultilevelResp, IcmdConfigurationResp, IcmdAssociationResp {


    //CmdZWaveConfiguration cmdConfiguration[] =

    ArrayList <CmdZWaveConfiguration> listZwaveCmd = new ArrayList<CmdZWaveConfiguration>();


    public int configuration_lock = 0;
    public int configuration_timer = 10; // minimun value



    public int configuration_report = Integer.parseInt(cmd_klass_CONFIGURATION_REPORT_VALUE_DISABLE, 16);
    public int configuration_PIR_sense = Integer.parseInt(cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_LEVEL1, 16);
    public int configuration_auto_timer = Integer.parseInt(cmd_klass_CONFIGURATION_AUTO_TIMER_MIN, 16);


    // multilevel class
    final public static String klass_SENSOR_MULTILEVEL = "SENSOR_MULTILEVEL";
    final public static String type_SENSOR_MULTILEVEL_TEMP = "TEMP";
    final public static String type_SENSOR_MULTILEVEL_HUMI = "HUMI";
    final public static String type_SENSOR_MULTILEVEL_UV = "UV";
    final public static String type_SENSOR_MULTILEVEL_LUMI = "LUMI";


    // configuration class
    final public static String klass_SENSOR_CONFIGURATION = "CONFIGURATION";

    final public static String cmd_klass_CONFIGURATION_LOCK = "LOCK_CONFIGURATION";
    final public static String cmd_klass_CONFIGURATION_LOCK_VALUE_DISABLE = "0";
    final public static String cmd_klass_CONFIGURATION_LOCK_VALUE_ENABLE = "1";

    final public static String cmd_klass_CONFIGURATION_TIMER = "TIME";
    final public static String cmd_klass_CONFIGURATION_TIMER_VALUE_MIN = "10";
    final public static String cmd_klass_CONFIGURATION_TIMER_VALUE_OFF = "3600";

    final public static String cmd_klass_CONFIGURATION_REPORT = "REPORT_SENSOR";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_TEMP = "20";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_HUMI = "40";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_LUMI = "80";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_ULTRA = "10";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_BATTERY = "01";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_DISABLE = "00";

    final public static String cmd_klass_CONFIGURATION_PIR = "ENABLE_MOTION";
    final public static String cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_LEVEL1 = "1";
    final public static String cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_LEVEL2 = "2";
    final public static String cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_LEVEL3 = "3";
    final public static String cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_LEVEL4 = "4";
    final public static String cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_LEVEL5 = "5";


    final public static String cmd_klass_CONFIGURATION_AUTO_TIMER = "TIME_AUTO_REPORT";
    final public static String cmd_klass_CONFIGURATION_AUTO_TIMER_MIN = "0A";
    final public static String cmd_klass_CONFIGURATION_AUTO_TIMER_MAX = "28DE80";


    // configuration class
    final public static String klass_SENSOR_ASSOCIATION = "ASSOCIATION";
    final public static String cmd_klass_ASSOCIATION_ONOFF_GROUP = "ONOFF_GROUP";
    final public static String cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID = "1";


    public CheckBox viewer_cb_configuration_lock;
    public SeekBar viewer_seekBar_configuration_timer;


    public DeviceAEON_LABSMultilevelSensor6(String id, String name, boolean turnOn, boolean available, String device_type) {
        super(id, name, turnOn, available, device_type);


        listZwaveCmd.add(new CmdZWaveConfiguration("CONFIGURATION", "SET", "FC", RemoteAccessMsg.commandSetSpecification, "CONFIGURATION", "SET", "LOCK_CONFIGURATION", null, null));


        // Fragment initialization 
        listFragment.add(createFragmentConfiguration());
        listFragment.add(createFragmentMultilevel());
        listFragment.add(createFragmentAssociation());

    }


    @Override
    public void Update(String property) {
        super.Update(property);
//
//        JSONObject jason;
//
//        try {
//            jason = new JSONObject(property);
//
//            String method = jason.getString("method");
//
//            String device_id = jason.getString("deviceid");
//
//            String commandinfo = jason.getString("commandinfo");
//            if (method == null || commandinfo == null)
//                return; // do nothing
//
//            if (method.equals(RemoteAccessMsg.commandGetSpecificationR)) {
//                JSONObject jason_command_info = new JSONObject(commandinfo);
//                String klass = jason_command_info.getString("class");
//                String command = jason_command_info.getString("command");
//                String data0 = jason_command_info.getString("data0");
//
//                if (klass == null || command == null || data0 == null)
//                    return;
//
//                if (klass.equals(klass_SENSOR_MULTILEVEL)) {
//                    if (command.equals("GET")) {
//                        if (data0.equals(type_SENSOR_MULTILEVEL_TEMP)) {
//                            String fahrenheit = jason.getString("fahrenheit");
//                            multilevel_sensor_temp = Double.parseDouble(fahrenheit);
//
//                            if (viewer_tw_multilevel6_temp != null) {
//                                viewer_tw_multilevel6_temp.setText(Double.toString(multilevel_sensor_temp));
//                            }
//
//                        } else if (data0.equals(type_SENSOR_MULTILEVEL_LUMI)) {
//
//                            String luminance = jason.getString("luminance");
//                            multilevel_sensor_lumi = Double.parseDouble(luminance);
//
//                            if (viewer_tw_multilevel6_lumi != null) {
//                                viewer_tw_multilevel6_lumi.setText(Double.toString(multilevel_sensor_lumi));
//                            }
//
//                        } else if (data0.equals(type_SENSOR_MULTILEVEL_HUMI)) {
//                            String absolute_humidity = jason.getString("absolute_humidity");
//                            multilevel_sensor_humi = Double.parseDouble(absolute_humidity);
//
//                            if (viewer_tw_multilevel6_humi != null) {
//                                viewer_tw_multilevel6_humi.setText(Double.toString(multilevel_sensor_humi));
//                            }
//
//
//                        } else if (data0.equals(type_SENSOR_MULTILEVEL_UV)) {
//
//                            String ultraviolet = jason.getString("ultraviolet");
//                            multilevel_sensor_uv = Double.parseDouble(ultraviolet);
//
//                            if (viewer_tw_multilevel6_uv != null) {
//                                viewer_tw_multilevel6_uv.setText(Double.toString(multilevel_sensor_uv));
//                            }
//
//                        }
//                    }
//
//                } else if (klass.equals(klass_SENSOR_ASSOCIATION)) {
//
//                    // TODO
//
//                } else if (klass.equals(klass_SENSOR_CONFIGURATION)) {
//                    // TODO
//
//                }
//            } else if (method.equals(RemoteAccessMsg.commandSetSpecificationR)) {
//                JSONObject jason_command_info = new JSONObject(commandinfo);
//                String klass = jason_command_info.getString("class");
//                String command = jason_command_info.getString("command");
//                String data0 = jason_command_info.getString("data0");
//
//                if (klass == null || command == null || data0 == null)
//                    return;
//                if (klass.equals(klass_SENSOR_MULTILEVEL)) {
//
//
//                } else if (klass.equals(klass_SENSOR_ASSOCIATION)) {
//
//                    // TODO
//
//                } else if (klass.equals(klass_SENSOR_CONFIGURATION)) {
//                    if (command.equals("GET")) {
//                        if (data0.equals(cmd_klass_CONFIGURATION_LOCK)) {
//                            String data1 = jason_command_info.getString("data1");
//                            configuration_lock = Integer.parseInt(data1);
//                            if (configuration_lock == 0)
//                                viewer_cb_configuration_lock.setChecked(true);
//                            else
//                                viewer_cb_configuration_lock.setChecked(false);
//                        }
//                    }
//
//                }
//
//            }
//
//
//        } catch (JSONException e) {
//        }
    }



    @Override
    public void onClick(View v) {

    }




    private DeviceFragment createFragmentMultilevel() {
        DeviceFragment fragment = new DeviceFragment() {

            public static final String ARG_SECTION_NUMBER = "section_number";

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

                View rootView = inflater.inflate(R.layout.content_device_aeon_labs_multilevel6_multilevel, container, false);


                Button button_temp = (Button) rootView.findViewById(R.id.sensor_multilevel6_multilevel_button_temp);

                button_temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        multilevelGet(type_SENSOR_MULTILEVEL_TEMP);
                    }
                });

                Button button_humi = (Button) rootView.findViewById(R.id.sensor_multilevel6_multilevel_button_humi);

                button_humi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        multilevelGet(type_SENSOR_MULTILEVEL_HUMI);

                    }
                });


                Button button_lumi = (Button) rootView.findViewById(R.id.sensor_multilevel6_multilevel_button_lumi);

                button_lumi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        multilevelGet(type_SENSOR_MULTILEVEL_LUMI);

                    }
                });


                Button button_violet = (Button) rootView.findViewById(R.id.sensor_multilevel6_multilevel_button_ultra_violet);

                button_violet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        multilevelGet(type_SENSOR_MULTILEVEL_UV);

                    }
                });


                return rootView;

            }
        };

        fragment.setTitle("Multilevel");

        return fragment;

    }


    private DeviceFragment createFragmentConfiguration() {

        DeviceFragment fragment = new DeviceFragment() {

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

                View rootView = inflater.inflate(R.layout.content_device_aeon_labs_multilevel6_configuration, container, false);

                DeviceAEON_LABSMultilevelSensor6.this.viewer_cb_configuration_lock = (CheckBox)rootView.findViewById(R.id.sensor_multilevel6_cb_configuration_lock);
                DeviceAEON_LABSMultilevelSensor6.this.viewer_seekBar_configuration_timer = (SeekBar)rootView.findViewById(R.id.sensor_multilevel6_seekbar_configuration_timer);



                final CheckBox cb_enable_disable = (CheckBox) rootView.findViewById(R.id.sensor_multilevel6_cb_configuration_lock);

                if (cb_enable_disable != null) {
                    cb_enable_disable.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            String value = DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_LOCK_VALUE_ENABLE;
                            if (cb_enable_disable.isChecked())
                                value = DeviceAEON_LABSMultilevelSensor6.cmd_klass_CONFIGURATION_LOCK_VALUE_DISABLE;

                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Set(id, cmd_klass_CONFIGURATION_LOCK, value, "");

                        }
                    });
                }


                // Timer
                DeviceAEON_LABSMultilevelSensor6.this.viewer_seekBar_configuration_timer = (SeekBar) rootView.findViewById(R.id.sensor_multilevel6_seekbar_configuration_timer);

                // Scale for debugging
                DeviceAEON_LABSMultilevelSensor6.this.viewer_seekBar_configuration_timer.setMax((3600-10)/10);

                if (DeviceAEON_LABSMultilevelSensor6.this.viewer_seekBar_configuration_timer != null) {
                    DeviceAEON_LABSMultilevelSensor6.this.viewer_seekBar_configuration_timer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                            DeviceAEON_LABSMultilevelSensor6.this.configuration_timer  = progress;


                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Set(id, cmd_klass_CONFIGURATION_TIMER, Integer.toHexString(DeviceAEON_LABSMultilevelSensor6.this.configuration_timer - 10), "");

                        }
                    });
                }


                View.OnClickListener checkbox_report_configuration = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CheckBox cb_tmp = (CheckBox) v;

                        int configuration_report = DeviceAEON_LABSMultilevelSensor6.this.configuration_report;

                        switch (v.getId()) {
                            case R.id.sensor_multilevel6_cb_temperature:
                                if (cb_tmp.isChecked()) {
                                    configuration_report = configuration_report | Integer.parseInt(DeviceAEON_LABSMultilevelSensor6.this.cmd_klass_CONFIGURATION_REPORT_VALUE_TEMP, 16);
                                } else {
                                    configuration_report = configuration_report & ~Integer.parseInt(DeviceAEON_LABSMultilevelSensor6.this.cmd_klass_CONFIGURATION_REPORT_VALUE_TEMP, 16);
                                }
                                break;

                            case R.id.sensor_multilevel6_cb_battery:
                                if (cb_tmp.isChecked()) {
                                    configuration_report = configuration_report | Integer.parseInt(DeviceAEON_LABSMultilevelSensor6.this.cmd_klass_CONFIGURATION_REPORT_VALUE_BATTERY, 16);
                                } else {
                                    configuration_report = configuration_report & ~Integer.parseInt(DeviceAEON_LABSMultilevelSensor6.this.cmd_klass_CONFIGURATION_REPORT_VALUE_BATTERY, 16);
                                }

                                break;

                            case R.id.sensor_multilevel6_cb_luminance:
                                if (cb_tmp.isChecked()) {
                                    configuration_report = configuration_report | Integer.parseInt(DeviceAEON_LABSMultilevelSensor6.this.cmd_klass_CONFIGURATION_REPORT_VALUE_LUMI, 16);
                                } else {
                                    configuration_report = configuration_report & ~Integer.parseInt(DeviceAEON_LABSMultilevelSensor6.this.cmd_klass_CONFIGURATION_REPORT_VALUE_LUMI, 16);
                                }

                                break;

                            case R.id.sensor_multilevel6_cb_ultra_violet:
                                if (cb_tmp.isChecked()) {
                                    configuration_report = configuration_report | Integer.parseInt(DeviceAEON_LABSMultilevelSensor6.this.cmd_klass_CONFIGURATION_REPORT_VALUE_ULTRA, 16);
                                } else {
                                    configuration_report = configuration_report & ~Integer.parseInt(DeviceAEON_LABSMultilevelSensor6.this.cmd_klass_CONFIGURATION_REPORT_VALUE_ULTRA, 16);
                                }
                                break;

                            case R.id.sensor_multilevel6_cb_humidity:
                                if (cb_tmp.isChecked()) {
                                    configuration_report = configuration_report | Integer.parseInt(DeviceAEON_LABSMultilevelSensor6.this.cmd_klass_CONFIGURATION_REPORT_VALUE_HUMI, 16);
                                } else {
                                    configuration_report = configuration_report & ~Integer.parseInt(DeviceAEON_LABSMultilevelSensor6.this.cmd_klass_CONFIGURATION_REPORT_VALUE_HUMI, 16);
                                }
                                break;

                        }

                        DeviceAEON_LABSMultilevelSensor6.this.configuration_report = configuration_report;

                        String value = Integer.toHexString(configuration_report);

                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, cmd_klass_CONFIGURATION_REPORT, value, "");
                    }
                };


                CheckBox checkbox_report_temp = (CheckBox) rootView.findViewById(R.id.sensor_multilevel6_cb_temperature);
                checkbox_report_temp.setOnClickListener(checkbox_report_configuration);

                CheckBox checkbox_report_humi = (CheckBox) rootView.findViewById(R.id.sensor_multilevel6_cb_humidity);
                checkbox_report_humi.setOnClickListener(checkbox_report_configuration);

                CheckBox checkbox_report_uv = (CheckBox) rootView.findViewById(R.id.sensor_multilevel6_cb_ultra_violet);
                checkbox_report_uv.setOnClickListener(checkbox_report_configuration);

                CheckBox checkbox_report_battery = (CheckBox) rootView.findViewById(R.id.sensor_multilevel6_cb_battery);
                checkbox_report_battery.setOnClickListener(checkbox_report_configuration);

                CheckBox checkbox_report_lum = (CheckBox) rootView.findViewById(R.id.sensor_multilevel6_cb_luminance);
                checkbox_report_lum.setOnClickListener(checkbox_report_configuration);


                // Configuration class, Motion command class
                final RadioButton radio_button_motion_level1 = (RadioButton) rootView.findViewById(R.id.sensor_multilevel6_radio_motion_level1);
                radio_button_motion_level1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String value = "00";
                        if (radio_button_motion_level1.isChecked())
                            value = "1";

                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, cmd_klass_CONFIGURATION_PIR, value, "");

                    }
                });


                final RadioButton radio_button_motion_level2 = (RadioButton) rootView.findViewById(R.id.sensor_multilevel6_radio_motion_level2);
                radio_button_motion_level2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String value = "00";
                        if (radio_button_motion_level2.isChecked())
                            value = "2";

                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, cmd_klass_CONFIGURATION_PIR, value, "");

                    }
                });


                final RadioButton radio_button_motion_level3 = (RadioButton) rootView.findViewById(R.id.sensor_multilevel6_radio_motion_level3);
                radio_button_motion_level3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String value = "00";
                        if (radio_button_motion_level3.isChecked())
                            value = "3";

                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, cmd_klass_CONFIGURATION_PIR, value, "");


                    }
                });


                final RadioButton radio_button_motion_level4 = (RadioButton) rootView.findViewById(R.id.sensor_multilevel6_radio_motion_level4);
                radio_button_motion_level4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String value = "00";
                        if (radio_button_motion_level4.isChecked())
                            value = "4";
                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, cmd_klass_CONFIGURATION_PIR, value, "");

                    }
                });


                final RadioButton radio_button_motion_level5 = (RadioButton) rootView.findViewById(R.id.sensor_multilevel6_radio_motion_level5);
                radio_button_motion_level5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String value = "00";
                        if (radio_button_motion_level5.isChecked())
                            value = "5";

                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, cmd_klass_CONFIGURATION_PIR, value, "");

                    }
                });

                RadioButton radio_button_motion_disable = (RadioButton) rootView.findViewById(R.id.sensor_multilevel6_radio_motion_disable);
                radio_button_motion_disable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        RadioButton radio_button_temp = (RadioButton) v;

                        String value = "00";
                        if (radio_button_temp.isChecked())
                            value = "00";

                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, cmd_klass_CONFIGURATION_PIR, value, "");



                    }
                });


                // Timer
                SeekBar seek_bar_auto_timer = (SeekBar) rootView.findViewById(R.id.sensor_multilevel6_seekbar_auto_timer);
                // Scale for debugging
                seek_bar_auto_timer.setMax((2678400 - 10)/10000);
                if (seek_bar_auto_timer != null) {
                    seek_bar_auto_timer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


  //                      int progress_auto_timer = 0;

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                            DeviceAEON_LABSMultilevelSensor6.this.configuration_auto_timer = progress;


                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Set(id, cmd_klass_CONFIGURATION_AUTO_TIMER, Integer.toHexString( DeviceAEON_LABSMultilevelSensor6.this.configuration_auto_timer + 10), "");

                        }
                    });
                }



                // GET
                Button button_get_lock_config = (Button) rootView.findViewById(R.id.sensor_multilevel_button_lock_config_get);
                if (button_get_lock_config != null) {
                    button_get_lock_config.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Get(id, cmd_klass_CONFIGURATION_LOCK, "");

                        }
                    });
                }

                Button button_get_timer_config = (Button) rootView.findViewById(R.id.sensor_multilevel_button_timer_config_get);
                if (button_get_timer_config != null) {
                    button_get_timer_config.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Get(id, cmd_klass_CONFIGURATION_TIMER, "");

                        }
                    });
                }


                Button button_get_report_config = (Button) rootView.findViewById(R.id.sensor_multilevel_button_report_config_get);
                if (button_get_report_config != null) {
                    button_get_report_config.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Get(id, cmd_klass_CONFIGURATION_REPORT, "");

                        }
                    });
                }

                Button button_get_motion_config = (Button) rootView.findViewById(R.id.sensor_multilevel_button_motion_config_get);
                if (button_get_motion_config != null) {
                    button_get_motion_config.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Get(id, cmd_klass_CONFIGURATION_PIR, "");

                        }
                    });
                }

                Button button_get_auto_timer = (Button) rootView.findViewById(R.id.sensor_multilevel_button_auto_timer_get);
                if (button_get_auto_timer != null) {
                    button_get_auto_timer.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Get(id, cmd_klass_CONFIGURATION_PIR, "");

                        }
                    });
                }

                return rootView;

            }
        };

        fragment.setTitle("Configuration");

        return fragment;

    }


    private DeviceFragment createFragmentAssociation() {
        DeviceFragment fragment = new DeviceFragment() {

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



                View rootView = inflater.inflate(R.layout.content_device_generic_zwave_association, container, false);


                // association test
                Button button_ass_get = (Button) rootView.findViewById(R.id.button_association_group_get);
                if (button_ass_get != null) {
                    button_ass_get.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            zwaveAssociationMsg associationMsg = new zwaveAssociationMsg();
                            associationMsg.associationGetNode(id, cmd_klass_ASSOCIATION_ONOFF_GROUP);

                        }
                    });
                }


                // association test
                CheckBox cb_association_notification = (CheckBox) rootView.findViewById(R.id.cb_association_add_group);
                if (cb_association_notification != null) {
                    cb_association_notification.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            zwaveAssociationMsg associationMsg = new zwaveAssociationMsg();

                            if (((CheckBox)v).isChecked())
                                associationMsg.associationSetNode(id, cmd_klass_ASSOCIATION_ONOFF_GROUP, "1");
                            else
                                associationMsg.associationRemoveNode(id, cmd_klass_ASSOCIATION_ONOFF_GROUP, "1");

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
                            zwaveAssociationMsg associationMsg = new zwaveAssociationMsg();
                            associationMsg.associationSetNode(id, cmd_klass_ASSOCIATION_ONOFF_GROUP, nodeID);

                        }
                    });
                }

                Button button_ass_remove = (Button) rootView.findViewById(R.id.button_association_group_node_remove1);
                if (button_ass_remove != null) {
                    button_ass_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String nodeID = ed_nodeID.getText().toString();
                            zwaveAssociationMsg associationMsg = new zwaveAssociationMsg();
                            associationMsg.associationRemoveNode(id, cmd_klass_ASSOCIATION_ONOFF_GROUP, nodeID);

                        }
                    });
                }

//
//
//
//                View rootView = inflater.inflate(R.layout.content_device_aeon_labs_multilevel6_association, container, false);
//
//
//                // association test
//
//                final CheckBox cb_association_notification = (CheckBox) rootView.findViewById(R.id.sensor_multilevel6_cb_association_add_group);
//                if (cb_association_notification != null)
// {
//                    cb_association_notification.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//
//
//                            if (cb_association_notification.isChecked()) {
//
//                                MqttMessage message = null;
//
//                                if (DeviceAEON_LABSMultilevelSensor6.this.type.contentEquals("zwave"))
//                                    message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, DeviceAEON_LABSMultilevelSensor6.this.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_ASSOCIATION,
//                                            "SET", DeviceAEON_LABSMultilevelSensor6.cmd_klass_ASSOCIATION_ONOFF_GROUP, DeviceAEON_LABSMultilevelSensor6.cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID, "");
//
//
//                                MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);
//
//                            } else {
//                                MqttMessage message = null;
//
//                                if (DeviceAEON_LABSMultilevelSensor6.this.type.contentEquals("zwave"))
//                                    message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, DeviceAEON_LABSMultilevelSensor6.this.getId(), DeviceAEON_LABSMultilevelSensor6.klass_SENSOR_ASSOCIATION,
//                                            "REMOVE", DeviceAEON_LABSMultilevelSensor6.cmd_klass_ASSOCIATION_ONOFF_GROUP, DeviceAEON_LABSMultilevelSensor6.cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID, "");
//
//
//                                MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);
//
//
//                            }
//
//                        }
//                    });
//                }
                return rootView;

            }
        };

        fragment.setTitle("Association");


        return fragment;

    }

    @Override
    public int multilevelGetResp(String more) {
        AddLogToHistory("[MULTILEVEL][GET] " + more);
        return 0;
    }

    @Override
    public void onConfigurationGetResp(String cmd, String data0, String data1, String data2, String status, String more) {
        AddLogToHistory("[CONFIGURATION][GET] " + more);

    }

    @Override
    public void onAssociationGetResp(String groupIdentifier, String maxNode, String nodeFlow) {
        AddLogToHistory("[ASSOCIATION][GET] groupIdentifier: " + groupIdentifier + " maxNode: " + maxNode + " nodeFlow: " + nodeFlow);

    }
}
