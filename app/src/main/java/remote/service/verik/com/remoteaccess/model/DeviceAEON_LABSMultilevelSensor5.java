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
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.R;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.Vtransport.genericDevice.NotificationSensor;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdAssociationReport;

public class DeviceAEON_LABSMultilevelSensor5 extends NotificationSensor implements IcmdAssociationReport {


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


    // For Viewer
    public TextView viewer_tw_multilevel5_humi;
    public TextView viewer_tw_multilevel5_temp;
    public TextView viewer_tw_multilevel5_lumi;
    public TextView viewer_tw_sensor_battery;

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

                View rootView = inflater.inflate(R.layout.content_device_generic_sensor_multilevel5_association, container, false);


                // association test
                Button button_ass_get  = (Button) rootView.findViewById(R.id.button_association_group_get);
                if (button_ass_get != null) {
                    button_ass_get.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            getNode(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP);
                        }
                    });
                }

                final EditText ed_nodeID   = (EditText) rootView.findViewById(R.id.et_node_id);




                Button button_ass_add  = (Button) rootView.findViewById(R.id.button_association_group_node_add);
                if (button_ass_add != null) {
                    button_ass_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String nodeID = ed_nodeID.getText().toString();

                            setNode(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP, nodeID);
                        }
                    });
                }

                Button button_ass_remove  = (Button) rootView.findViewById(R.id.button_association_group_node_remove);
                if (button_ass_remove != null) {
                    button_ass_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String nodeID = ed_nodeID.getText().toString();

                            associationNodeRemove(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP, nodeID);
                        }
                    });
                }




                // association test
                CheckBox cb_association_notification = (CheckBox) rootView.findViewById(R.id.sensor_multilevel5_association_cb_association_add_group);
                if (cb_association_notification != null) {
                    cb_association_notification.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            if (((CheckBox)v).isChecked())
                                setNode(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID);
                             else
                                associationNodeRemove(id, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP, DeviceAEON_LABSMultilevelSensor5.cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID);

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

                View rootView = inflater.inflate(R.layout.content_device_generic_sensor_multilevel5_multilevel, container, false);

                DeviceAEON_LABSMultilevelSensor5.this.viewer_tw_multilevel5_humi = (TextView) rootView.findViewById(R.id.sensor_multilevel5_multilevel_tw_humi);
                if (DeviceAEON_LABSMultilevelSensor5.this.viewer_tw_multilevel5_humi != null) {
                    DeviceAEON_LABSMultilevelSensor5.this.viewer_tw_multilevel5_humi.setText(Double.toString(DeviceAEON_LABSMultilevelSensor5.this.multilevel_sensor_humi));
                }

                DeviceAEON_LABSMultilevelSensor5.this.viewer_tw_multilevel5_lumi = (TextView) rootView.findViewById(R.id.sensor_multilevel5_multilevel_tw_lumi);
                if (DeviceAEON_LABSMultilevelSensor5.this.viewer_tw_multilevel5_lumi != null) {
                    DeviceAEON_LABSMultilevelSensor5.this.viewer_tw_multilevel5_lumi.setText(Double.toString(DeviceAEON_LABSMultilevelSensor5.this.multilevel_sensor_lumi));
                }

                DeviceAEON_LABSMultilevelSensor5.this.viewer_tw_multilevel5_temp = (TextView) rootView.findViewById(R.id.sensor_multilevel5_multilevel_tw_temp);
                if (DeviceAEON_LABSMultilevelSensor5.this.viewer_tw_multilevel5_temp != null) {
                    DeviceAEON_LABSMultilevelSensor5.this.viewer_tw_multilevel5_temp.setText(Double.toString(DeviceAEON_LABSMultilevelSensor5.this.multilevel_sensor_temp));
                }

                DeviceAEON_LABSMultilevelSensor5.this.viewer_tw_sensor_battery = (TextView) rootView.findViewById(R.id.sensor_multilevel5_multilevel_tw_battery);
                if (DeviceAEON_LABSMultilevelSensor5.this.viewer_tw_sensor_battery != null) {
                    DeviceAEON_LABSMultilevelSensor5.this.viewer_tw_sensor_battery.setText(DeviceAEON_LABSMultilevelSensor5.this.battery_level);
                }


                Button button_temp = (Button) rootView.findViewById(R.id.sensor_multilevel5_multilevel_button_temp);

                button_temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MqttMessage message = null;
                        if (DeviceAEON_LABSMultilevelSensor5.this.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, DeviceAEON_LABSMultilevelSensor5.this.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_TEMP, "2A", "1/10");

                        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);


                    }
                });

                Button button_humi = (Button) rootView.findViewById(R.id.sensor_multilevel5_multilevel_button_humi);

                button_humi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MqttMessage message = null;
                        if (DeviceAEON_LABSMultilevelSensor5.this.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, DeviceAEON_LABSMultilevelSensor5.this.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_HUMI, "2A", "1/10");

                        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

                    }
                });


                Button button_lumi = (Button) rootView.findViewById(R.id.sensor_multilevel5_multilevel_button_lumi);

                button_lumi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MqttMessage message = null;
                        if (DeviceAEON_LABSMultilevelSensor5.this.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, DeviceAEON_LABSMultilevelSensor5.this.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_MULTILEVEL, "GET", DeviceAEON_LABSMultilevelSensor5.type_SENSOR_MULTILEVEL_LUMI, "2A", "1/10");

                        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

                    }
                });


                Button button_battery = (Button) rootView.findViewById(R.id.sensor_multilevel5_multilevel_button_battery);

                button_battery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MqttMessage message = null;
                        if (DeviceAEON_LABSMultilevelSensor5.this.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, DeviceAEON_LABSMultilevelSensor5.this.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_BATTERY, "GET", "", "", "");

                        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);


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
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

                View rootView = inflater.inflate(R.layout.content_device_generic_sensor_multilevel5_configuration, container, false);

                // Timer

                DeviceAEON_LABSMultilevelSensor5.this.viewer_seekBar_configuration_timer = (SeekBar)rootView.findViewById(R.id.sensor_multilevel5_configuration_seekbar_configuration_timer);

                if (DeviceAEON_LABSMultilevelSensor5.this.viewer_seekBar_configuration_timer != null) {

                    // FIXME: scale for development
                    DeviceAEON_LABSMultilevelSensor5.this.viewer_seekBar_configuration_timer.setMax((Integer.parseInt(DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_TIMER_VALUE_MAX) - Integer.parseInt(DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_TIMER_VALUE_MIN))/10) ;

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

                            MqttMessage message = null;

                            if (DeviceAEON_LABSMultilevelSensor5.this.type.contentEquals("zwave"))
                                message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, DeviceAEON_LABSMultilevelSensor5.this.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_TIMER, Integer.toHexString(DeviceAEON_LABSMultilevelSensor5.this.configuration_timer - 10), "");

                            MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);


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

                        MqttMessage message = null;

                        if (DeviceAEON_LABSMultilevelSensor5.this.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, DeviceAEON_LABSMultilevelSensor5.this.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");

                        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);


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


                        MqttMessage message = null;

                        if (DeviceAEON_LABSMultilevelSensor5.this.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, DeviceAEON_LABSMultilevelSensor5.this.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");

                        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);
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

                        MqttMessage message = null;

                        if (DeviceAEON_LABSMultilevelSensor5.this.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, DeviceAEON_LABSMultilevelSensor5.this.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");

                        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);
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

                        MqttMessage message = null;
                        if (DeviceAEON_LABSMultilevelSensor5.this.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, DeviceAEON_LABSMultilevelSensor5.this.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_REPORT, value, "");

                        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);


                    }
                });


                // Configuration class, Motion command class

                CheckBox cb_motion_enable = (CheckBox) rootView.findViewById(R.id.sensor_multilevel5_configuration_cb_motion_enable);

                cb_motion_enable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CheckBox cb_tmp = (CheckBox) v;
                        String value = DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_OFF;

                        if (cb_tmp.isChecked()) {
                            value = DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_ON;
                        }


                        MqttMessage message = null;

                        if (DeviceAEON_LABSMultilevelSensor5.this.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, DeviceAEON_LABSMultilevelSensor5.this.getId(),
                                    DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION, "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_PIR, value, "");
                        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);


                    }
                });


                // Timer
                SeekBar seek_bar_auto_timer = (SeekBar) rootView.findViewById(R.id.sensor_multilevel5_configuration_seekbar_auto_timer);

                if (seek_bar_auto_timer != null) {

                    // FIXME: scale for development
                    DeviceAEON_LABSMultilevelSensor5.this.viewer_seekBar_configuration_timer.setMax((Integer.parseInt(DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_AUTO_TIMER_MAX) - Integer.parseInt(DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_AUTO_TIMER_MIN))/10000) ;


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

                            MqttMessage message = null;

                            if (DeviceAEON_LABSMultilevelSensor5.this.type.contentEquals("zwave"))
                                message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, DeviceAEON_LABSMultilevelSensor5.this.getId(), DeviceAEON_LABSMultilevelSensor5.klass_SENSOR_CONFIGURATION,
                                        "SET", DeviceAEON_LABSMultilevelSensor5.cmd_klass_CONFIGURATION_AUTO_TIMER, Integer.toHexString(DeviceAEON_LABSMultilevelSensor5.this.configuration_auto_timer), "");
                            MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);
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

        JSONObject jason;

        try {
            jason = new JSONObject(property);

            String method = jason.getString("method");

            String device_id = jason.getString("deviceid");

            String commandinfo = jason.getString("commandinfo");
            if (method == null || commandinfo == null)
                return; // do nothing

            if (method.equals(RemoteAccessMsg.commandGetSpecificationR)) {
                JSONObject jason_command_info = new JSONObject(commandinfo);
                String klass = jason_command_info.getString("class");
                String command = jason_command_info.getString("command");
                String data0 = jason_command_info.getString("data0");

                if (klass == null || command == null || data0 == null)
                    return;

                if (klass.equals(klass_SENSOR_MULTILEVEL)) {
                    if (command.equals("GET")) {
                        if (data0.equals(type_SENSOR_MULTILEVEL_TEMP)) {
                            String fahrenheit = jason.getString("fahrenheit");
                            multilevel_sensor_temp = Double.parseDouble(fahrenheit);

                            if (viewer_tw_multilevel5_temp != null) {
                                viewer_tw_multilevel5_temp.setText(Double.toString(multilevel_sensor_temp));
                            }


                        } else if (data0.equals(type_SENSOR_MULTILEVEL_LUMI)) {

                            String luminance = jason.getString("luminance");
                            multilevel_sensor_lumi = Double.parseDouble(luminance);

                            if (viewer_tw_multilevel5_lumi != null) {
                                viewer_tw_multilevel5_lumi.setText(Double.toString(multilevel_sensor_lumi));
                            }

                        } else if (data0.equals(type_SENSOR_MULTILEVEL_HUMI)) {
                            String absolute_humidity = jason.getString("absolute_humidity");
                            multilevel_sensor_humi = Double.parseDouble(absolute_humidity);

                            if (viewer_tw_multilevel5_humi != null) {
                                viewer_tw_multilevel5_humi.setText(Double.toString(multilevel_sensor_humi));
                            }


                        }

                    }

                } else if (klass.equals(klass_SENSOR_ASSOCIATION)) {

                    // TODO

                } else if (klass.equals(klass_SENSOR_CONFIGURATION)) {
                    // TODO

                } else if (klass.equals(klass_SENSOR_BATTERY)) {
                    if (command.equals("GET")) {
                        battery_level = jason.getString("batterylevel");

                        if (viewer_tw_sensor_battery != null) {
                            viewer_tw_sensor_battery.setText(battery_level);
                        }


                    }
                }
            } else if (method.equals(RemoteAccessMsg.commandSetSpecificationR)) {
                JSONObject jason_command_info = new JSONObject(commandinfo);
                String klass = jason_command_info.getString("class");
                String command = jason_command_info.getString("command");
                String data0 = jason_command_info.getString("data0");

                if (klass == null || command == null || data0 == null)
                    return;
                if (klass.equals(klass_SENSOR_MULTILEVEL)) {


                } else if (klass.equals(klass_SENSOR_ASSOCIATION)) {

                    // TODO

                } else if (klass.equals(klass_SENSOR_CONFIGURATION)) {
                    if (command.equals("GET")) {
                        if (data0.equals(cmd_klass_CONFIGURATION_LOCK)) {
                            String data1 = jason_command_info.getString("data1");
                            configuration_lock = Integer.parseInt(data1);
                            if (configuration_lock == 0)
                                viwer_cb_configuration_lock.setChecked(true);
                            else
                                viwer_cb_configuration_lock.setChecked(false);
                        }
                    }

                }
            }

        } catch (JSONException e) {
        }
    }

    @Override
    public void onAssociationReport(String groupIdentifier, String maxNode, String nodeFlow) {
        AddLogToHistory("[ASSOCIATION] groupIdentifier: " + groupIdentifier + " maxNode: " + maxNode + " nodeFlow: " + nodeFlow);
    }
}
