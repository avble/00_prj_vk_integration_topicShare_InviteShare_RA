package remote.service.verik.com.remoteaccess.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.R;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveAssociationSecureMsg;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveBatterySecureMsg;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveConfigurationSecureMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdAssociationResp;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdBatteryResp;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdConfigurationResp;

public class DeviceSchlageSAFETYDoorLock extends Device implements IcmdBatteryResp, IcmdConfigurationResp, IcmdAssociationResp{


    public double configuration_beeper = 0;
    public double configuratin_vacation_mode = 0;
    public double configuration_lock_and_leave = 0;

    public int configuration_auto_lock = 0;

    public int configuration_len_of_pin = 0;


    public String battery_level = "";


    // configuration class
    final public static String klass_SENSOR_CONFIGURATION = "CONFIGURATION";

    final public static String cmd_klass_CONFIGURATION_BEEPER = "BEEPER";
    final public static String getCmd_klass_CONFIGURATION_BEEPER_DISABLE = "0";
    final public static String getCmd_klass_CONFIGURATION_BEEPER_ENABLE = "FF";

    final public static String cmd_klass_CONFIGURATION_VACATION = "VACATION";
    final public static String cmd_klass_CONFIGURATION_VACATION_DISABLE = "00";
    final public static String cmd_klass_CONFIGURATION_VACATION_ENABLE = "FF";


    final public static String cmd_klass_CONFIGURATION_PIN_LENGTH = "PIN_LENGTH";
    final public static String getCmd_klass_CONFIGURATION_PIN_LENGTH_MAX = "10";


    final public static String cmd_klass_CONFIGURATION_AUTO_LOCK = "AUTO_LOCK";
    final public static String cmd_klass_CONFIGURATION_AUTO_LOCK_VALUE_DISABLE = "00";
    final public static String getCmd_klass_CONFIGURATION_AUTO_LOCK_ENABLE = "FF";


    final public static String cmd_klass_CONFIGURATION_LOCK_AND_LEAVE = "LOCKANDLEAVE";
    final public static String cmd_klass_CONFIGURATION_LOCK_AND_LEAVE_DISABLE = "00";
    final public static String cmd_klass_CONFIGURATION_LOCK_AND_LEAVE_ENABLE = "FF";


    // configuration class
    final public static String klass_SENSOR_ASSOCIATION = "ASSOCIATION";
    final public static String cmd_klass_ASSOCIATION_ONOFF_GROUP = "ONOFF_GROUP";
    final public static String cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID = "1";


    // Battery class
    final public static String klass_SENSOR_DOOR_LOCK = "DOOR_LOCK";
    final public static String cmd_klass_DOOR_LOCK_secure = "close";
    final public static String cmd_klass_DOOR_LOCK_insecure = "open";


    // Battery class
    final public static String klass_SENSOR_BATTERY = "BATTERY";


    // Battery class
    final public static String klass_SENSOR_USER_CODE = "USER_CODE";
    final public static String getKlass_SENSOR_USER_CODE_STATE_AVAILABLE = "0";
    final public static String getKlass_SENSOR_USER_CODE_STATE_ACCUPIED = "1";
    final public static String getKlass_SENSOR_USER_CODE_STATE_RESERVED = "2";
    final public static String getKlass_SENSOR_USER_CODE_STATE_UNKNOWN = "EF";


    // For Viewer
    public EditText viewer_et_pin_len;
    public EditText viewer_user_code_1_et_code;


    public DeviceSchlageSAFETYDoorLock(String id, String name, boolean turnOn, boolean available, String device_type) {
        super(id, name, turnOn, available, device_type);
        // Fragment initialization
        listFragment.add(createFragmentConfiguration());
        listFragment.add(createFragmentDoorLockUserCode());
        listFragment.add(createFragmentAssociation());


    }



    private DeviceFragment createFragmentAssociation() {

        DeviceFragment fragment = new DeviceFragment() {
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

                View rootView = inflater.inflate(R.layout.content_device_generic_zwave_association, container, false);


                // association test
                Button button_ass_get = (Button) rootView.findViewById(R.id.button_association_group_get);
                if (button_ass_get != null) {
                    button_ass_get.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            zwaveAssociationSecureMsg associate = new zwaveAssociationSecureMsg();
                            associate.associationGetNode(id, cmd_klass_ASSOCIATION_ONOFF_GROUP);

                        }
                    });
                }


                // association test
                CheckBox cb_association_notification = (CheckBox) rootView.findViewById(R.id.cb_association_add_group);
                if (cb_association_notification != null) {
                    cb_association_notification.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            zwaveAssociationSecureMsg associate = new zwaveAssociationSecureMsg();

                            if (((CheckBox)v).isChecked())
                                associate.associationSetNode(id, cmd_klass_ASSOCIATION_ONOFF_GROUP, "1");
                            else
                                associate.associationRemoveNode(id, cmd_klass_ASSOCIATION_ONOFF_GROUP, "1");

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
                            zwaveAssociationSecureMsg associate = new zwaveAssociationSecureMsg();

                            associate.associationSetNode(id, cmd_klass_ASSOCIATION_ONOFF_GROUP, nodeID);
                        }
                    });
                }

                Button button_ass_remove = (Button) rootView.findViewById(R.id.button_association_group_node_remove1);
                if (button_ass_remove != null) {
                    button_ass_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String nodeID = ed_nodeID.getText().toString();
                            zwaveAssociationSecureMsg associate = new zwaveAssociationSecureMsg();

                            associate.associationRemoveNode(id, cmd_klass_ASSOCIATION_ONOFF_GROUP, nodeID);

                        }
                    });
                }




                return rootView;

            }
        };
        fragment.setTitle("Association");
        return fragment;
    }


    private DeviceFragment createFragmentDoorLockUserCode() {


        DeviceFragment fragment = new DeviceFragment() {
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


                View rootView = inflater.inflate(R.layout.content_device_schlage_doorlock_door_lock, container, false);


                View.OnClickListener door_lock_listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String klass_cmd = DeviceSchlageSAFETYDoorLock.cmd_klass_DOOR_LOCK_insecure;
                        CheckBox cb_tmp = (CheckBox) v;

                        if (cb_tmp.isChecked())
                            klass_cmd = DeviceSchlageSAFETYDoorLock.cmd_klass_DOOR_LOCK_secure;

                        MqttMessage message = null;
                        if (DeviceSchlageSAFETYDoorLock.this.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSecureMsg(DeviceTypeProtocol.ZWAVE, DeviceSchlageSAFETYDoorLock.this.getId(),
                                    DeviceSchlageSAFETYDoorLock.klass_SENSOR_DOOR_LOCK, "SET", klass_cmd, "", "");


                        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

                    }
                };

                CheckBox checkbox_door_lock = (CheckBox) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_cb_door_lock);
                checkbox_door_lock.setOnClickListener(door_lock_listener);


                View.OnClickListener association_listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String klass_cmd = "REMOVE";
                        CheckBox cb_tmp = (CheckBox) v;

                        if (cb_tmp.isChecked())
                            klass_cmd = "SET";

                        MqttMessage message = null;
                        if (DeviceSchlageSAFETYDoorLock.this.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSecureMsg(DeviceTypeProtocol.ZWAVE, DeviceSchlageSAFETYDoorLock.this.getId(),
                                    DeviceSchlageSAFETYDoorLock.klass_SENSOR_DOOR_LOCK, klass_cmd, DeviceSchlageSAFETYDoorLock.cmd_klass_ASSOCIATION_ONOFF_GROUP, "1", "1");


                        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

                    }
                };

                CheckBox checkbox_association = (CheckBox) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_cb_association_add_group);
                checkbox_association.setOnClickListener(association_listener);


                DeviceSchlageSAFETYDoorLock.this.viewer_user_code_1_et_code = (EditText) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_et_user_code_1);
                if (DeviceSchlageSAFETYDoorLock.this.viewer_user_code_1_et_code != null) {
                    DeviceSchlageSAFETYDoorLock.this.viewer_user_code_1_et_code.setText("123456");
                }

                Button viewer_bt_user_code1_set = (Button) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_button_user_code_1_set);
                if (viewer_bt_user_code1_set != null) {
                    viewer_bt_user_code1_set.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MqttMessage message = null;
                            if (DeviceSchlageSAFETYDoorLock.this.type.contentEquals("zwave"))
                                message = RemoteAccessMsg.CreateSetSecureMsg(DeviceTypeProtocol.ZWAVE, DeviceSchlageSAFETYDoorLock.this.getId(),
                                        DeviceSchlageSAFETYDoorLock.klass_SENSOR_USER_CODE, "SET", "1", DeviceSchlageSAFETYDoorLock.this.getKlass_SENSOR_USER_CODE_STATE_ACCUPIED, DeviceSchlageSAFETYDoorLock.this.viewer_user_code_1_et_code.getText().toString());


                            MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

                        }
                    });
                }
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


                View rootView = inflater.inflate(R.layout.content_device_schlage_doorlock_configuration, container, false);


                View.OnClickListener checkbox_configuration = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String klass_cmd = "";
                        String value = "00";
                        CheckBox cb_tmp = (CheckBox) v;

                        if (cb_tmp.isChecked())
                            value = "FF";

                        switch (v.getId()) {
                            case R.id.sensor_ir_sec_safety_doorNlock_configuration_cb_auto_lock:
                                klass_cmd = DeviceSchlageSAFETYDoorLock.cmd_klass_CONFIGURATION_AUTO_LOCK;
                                break;
                            case R.id.sensor_ir_sec_safety_doorNlock_configuration_cb_beeper:
                                klass_cmd = DeviceSchlageSAFETYDoorLock.cmd_klass_CONFIGURATION_BEEPER;
                                break;

                            case R.id.sensor_ir_sec_safety_doorNlock_configuration_cb_lock_n_leave:
                                klass_cmd = DeviceSchlageSAFETYDoorLock.cmd_klass_CONFIGURATION_LOCK_AND_LEAVE;
                                break;

                            case R.id.sensor_ir_sec_safety_doorNlock_configuration_cb_vacation:
                                klass_cmd = DeviceSchlageSAFETYDoorLock.cmd_klass_CONFIGURATION_VACATION;
                                break;

                        }

                        zwaveConfigurationSecureMsg config = new zwaveConfigurationSecureMsg();
                        config.Set(id, klass_cmd, value, "");

                    }
                };

                CheckBox checkbox_door_and_lock = (CheckBox) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_configuration_cb_auto_lock);
                checkbox_door_and_lock.setOnClickListener(checkbox_configuration);

                CheckBox checkbox_beeper = (CheckBox) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_configuration_cb_beeper);
                checkbox_beeper.setOnClickListener(checkbox_configuration);

                CheckBox checkbox_lock_n_leave = (CheckBox) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_configuration_cb_lock_n_leave);
                checkbox_lock_n_leave.setOnClickListener(checkbox_configuration);

                CheckBox checkbox_vacation = (CheckBox) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_configuration_cb_vacation);
                checkbox_vacation.setOnClickListener(checkbox_configuration);

                DeviceSchlageSAFETYDoorLock.this.viewer_et_pin_len = (EditText) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_configuration_et_pin_len);
                if (DeviceSchlageSAFETYDoorLock.this.viewer_et_pin_len != null) {
                    DeviceSchlageSAFETYDoorLock.this.viewer_et_pin_len.setText("4-8");
                }

                Button viewer_bt_pin_len_get = (Button) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_configuration_button_pin_len_get);
                if (viewer_bt_pin_len_get != null) {
                    viewer_bt_pin_len_get.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            zwaveConfigurationSecureMsg config = new zwaveConfigurationSecureMsg();
                            config.Get(id, cmd_klass_CONFIGURATION_PIN_LENGTH, "1");

                        }
                    });
                }

                Button bt_pin_len_set = (Button) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_configuration_button_pin_len_set);
                if (bt_pin_len_set != null) {
                    bt_pin_len_set.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String pin_len = DeviceSchlageSAFETYDoorLock.this.viewer_et_pin_len.getText().toString();


                            zwaveConfigurationSecureMsg config = new zwaveConfigurationSecureMsg();
                            config.Set(id, cmd_klass_CONFIGURATION_PIN_LENGTH, pin_len, "");

                        }
                    });
                }


                Button bt_battery_get = (Button) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_configuration_button_battery_get);
                if (bt_battery_get != null) {
                    bt_battery_get.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            zwaveBatterySecureMsg batter = new zwaveBatterySecureMsg();
                            batter.batteryGet(id);

                        }
                    });
                }


                return rootView;
            }
        };
        fragment.setTitle("Configuration & Battery");
        return fragment;
    }


    @Override
    public void Update(String property) {
        super.Update(property);

        JSONObject jason;
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
//            if (method.equals(RemoteAccessMsg.commandGetSpecificationR))
//            {
//                JSONObject jason_command_info = new JSONObject(commandinfo);
//                String klass = jason_command_info.getString("class");
//                String command = jason_command_info.getString("command");
//                String data0 = jason_command_info.getString("data0");
//
//                if (klass == null || command == null || data0 == null)
//                    return;
//
//                if (klass.equals(klass_SENSOR_MULTILEVEL))
//                {
//                    if (command.equals("GET"))
//                    {
//                        if (data0.equals(type_SENSOR_MULTILEVEL_TEMP))
//                        {
//                            String fahrenheit = jason.getString("fahrenheit");
//                            multilevel_sensor_temp = Double.parseDouble(fahrenheit);
//
//                            if (viewer_tw_multilevel5_temp != null)
//                            {
//                                viewer_tw_multilevel5_temp.setText(Double.toString(multilevel_sensor_temp));
//                            }
//
//
//                        }else if  (data0.equals(type_SENSOR_MULTILEVEL_LUMI))
//                        {
//
//                            String luminance = jason.getString("luminance");
//                            multilevel_sensor_lumi = Double.parseDouble(luminance);
//
//                            if (viewer_tw_multilevel5_lumi != null)
//                            {
//                                viewer_tw_multilevel5_lumi.setText(Double.toString(multilevel_sensor_lumi));
//                            }
//
//                        }else if  (data0.equals(type_SENSOR_MULTILEVEL_HUMI))
//                        {
//                            String absolute_humidity = jason.getString("absolute_humidity");
//                            multilevel_sensor_humi = Double.parseDouble(absolute_humidity);
//
//                            if (viewer_tw_multilevel5_humi != null)
//                            {
//                                viewer_tw_multilevel5_humi.setText(Double.toString(multilevel_sensor_humi));
//                            }
//
//
//                        }
//
//                    }
//
//                }else if (klass.equals(klass_SENSOR_ASSOCIATION)) {
//
//                    // TODO
//
//                }else if (klass.equals(klass_SENSOR_CONFIGURATION)) {
//                    // TODO
//
//                }else if (klass.equals(klass_SENSOR_BATTERY)) {
//                    if (command.equals("GET")) {
//                        battery_level = jason.getString("batterylevel");
//
//                        if (viewer_tw_sensor_battery != null) {
//                            viewer_tw_sensor_battery.setText(battery_level);
//                        }
//
//
//                    }
//                }
//            }else if (method.equals(RemoteAccessMsg.commandSetSpecificationR))
//            {
//                JSONObject jason_command_info = new JSONObject(commandinfo);
//                String klass = jason_command_info.getString("class");
//                String command = jason_command_info.getString("command");
//                String data0 = jason_command_info.getString("data0");
//
//                if (klass == null || command == null || data0 == null)
//                    return;
//                if (klass.equals(klass_SENSOR_MULTILEVEL))
//                {
//
//
//                }else if (klass.equals(klass_SENSOR_ASSOCIATION)) {
//
//                    // TODO
//
//                }else if (klass.equals(klass_SENSOR_CONFIGURATION)) {
//                    if (command.equals("GET"))
//                    {
//                        if (data0.equals(cmd_klass_CONFIGURATION_LOCK))
//                        {
//                            String data1 = jason_command_info.getString("data1");
//                            configuration_lock = Integer.parseInt(data1);
//                            if (configuration_lock == 0)
//                                viwer_cb_configuration_lock.setChecked(true);
//                            else
//                                viwer_cb_configuration_lock.setChecked(false);
//                        }
//                    }
//
//                }
//
//
//
//            }
//
//
//
//
//        }
//        catch (JSONException e) {
//        }
    }

    @Override
    public int batteryGetResp(String more) {
        AddLogToHistory("[BATTERY][GET] " + more);
        return 0;
    }

    @Override
    public void onConfigurationGetResp(String cmd, String data0, String data1, String data2, String status, String more) {
        AddLogToHistory("[CONFIGURATION][GET] " + more);
    }

    @Override
    public void onAssociationGetResp(String groupIdentifier, String maxNode, String nodeFlow) {
        AddLogToHistory("[ASSOCIATION] groupIdentifier: " + groupIdentifier + " maxNode: " + maxNode + " nodeFlow: " + nodeFlow);
    }
}
