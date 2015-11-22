package remote.service.verik.com.remoteaccess.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MQTTWrapper;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.R;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;

public class DeviceIR_SEC_SAFETYDoorLock extends Device {


    public double configuration_beeper = 0;
    public double configuratin_vacation_mode = 0;
    public double configuration_lock_and_leave = 0;

    public int configuration_auto_lock =  0;

    public int configuration_len_of_pin = 0;



    public String battery_level =  "";




    // configuration class
    final public static String klass_SENSOR_CONFIGURATION = "CONFIGURATION";

    final public static String cmd_klass_CONFIGURATION_BEEPER= "BEEPER";
    final public static String getCmd_klass_CONFIGURATION_BEEPER_DISABLE= "0";
    final public static String getCmd_klass_CONFIGURATION_BEEPER_ENABLE= "FF";

    final public static String cmd_klass_CONFIGURATION_VACATION= "VACATION";
    final public static String cmd_klass_CONFIGURATION_VACATION_DISABLE= "00";
    final public static String cmd_klass_CONFIGURATION_VACATION_ENABLE= "FF";


    final public static String cmd_klass_CONFIGURATION_PIN_LENGTH= "PIN_LENGTH";
    final public static String getCmd_klass_CONFIGURATION_PIN_LENGTH_MAX= "10";


    final public static String cmd_klass_CONFIGURATION_AUTO_LOCK= "AUTO_LOCK";
    final public static String cmd_klass_CONFIGURATION_AUTO_LOCK_VALUE_DISABLE = "00";
    final public static String getCmd_klass_CONFIGURATION_AUTO_LOCK_ENABLE = "FF";



    final public static String cmd_klass_CONFIGURATION_LOCK_AND_LEAVE= "LOCKANDLEAVE";
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




    public DeviceIR_SEC_SAFETYDoorLock(String id, String name, boolean turnOn, boolean available, String device_type)
    {
        super(id, name, turnOn, available, device_type);
        // Fragment initialization
        listFragment.add(createFragmentConfiguration());
        listFragment.add(createFragmentMultilevel());

    }


    private DeviceFragment createFragmentMultilevel() {


        DeviceFragment fragment = new DeviceFragment() {
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


                View rootView = inflater.inflate(R.layout.content_device_ir_sec_safety_doorlock_door_lock, container, false);




                View.OnClickListener door_lock_listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String klass_cmd = DeviceIR_SEC_SAFETYDoorLock.cmd_klass_DOOR_LOCK_insecure;
                        CheckBox cb_tmp = (CheckBox) v;

                        if (cb_tmp.isChecked())
                            klass_cmd = DeviceIR_SEC_SAFETYDoorLock.cmd_klass_DOOR_LOCK_secure;

                        MqttMessage message = null;
                        if (DeviceIR_SEC_SAFETYDoorLock.this.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSecureMsg(DeviceTypeProtocol.ZWAVE, DeviceIR_SEC_SAFETYDoorLock.this.getId(),
                                    DeviceIR_SEC_SAFETYDoorLock.klass_SENSOR_DOOR_LOCK, "SET", klass_cmd, "", "");


                        MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

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
                        if (DeviceIR_SEC_SAFETYDoorLock.this.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSecureMsg(DeviceTypeProtocol.ZWAVE, DeviceIR_SEC_SAFETYDoorLock.this.getId(),
                                    DeviceIR_SEC_SAFETYDoorLock.klass_SENSOR_DOOR_LOCK, klass_cmd, DeviceIR_SEC_SAFETYDoorLock.cmd_klass_ASSOCIATION_ONOFF_GROUP, "1", "1");


                        MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

                    }
                };

                CheckBox checkbox_association = (CheckBox) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_cb_association_add_group);
                checkbox_association.setOnClickListener(association_listener);


                DeviceIR_SEC_SAFETYDoorLock.this.viewer_user_code_1_et_code = (EditText) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_et_user_code_1);
                if (DeviceIR_SEC_SAFETYDoorLock.this.viewer_user_code_1_et_code != null) {
                    DeviceIR_SEC_SAFETYDoorLock.this.viewer_user_code_1_et_code.setText("123456");
                }

                Button viewer_bt_user_code1_set = (Button) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_button_user_code_1_set);
                if (viewer_bt_user_code1_set != null) {
                    viewer_bt_user_code1_set.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MqttMessage message = null;
                            if (DeviceIR_SEC_SAFETYDoorLock.this.type.contentEquals("zwave"))
                                message = RemoteAccessMsg.CreateSetSecureMsg(DeviceTypeProtocol.ZWAVE, DeviceIR_SEC_SAFETYDoorLock.this.getId(),
                                        DeviceIR_SEC_SAFETYDoorLock.klass_SENSOR_USER_CODE, "SET", "1", DeviceIR_SEC_SAFETYDoorLock.this.getKlass_SENSOR_USER_CODE_STATE_ACCUPIED, DeviceIR_SEC_SAFETYDoorLock.this.viewer_user_code_1_et_code.getText().toString());


                            MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

                        }
                    });
                }
                return rootView;
            }
        };
        return fragment;
    }

    private DeviceFragment createFragmentConfiguration() {



        DeviceFragment fragment = new DeviceFragment() {

            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


                View rootView = inflater.inflate(R.layout.content_device_ir_sec_safety_doorlock_configuration, container, false);


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
                                klass_cmd = DeviceIR_SEC_SAFETYDoorLock.cmd_klass_CONFIGURATION_AUTO_LOCK;
                                break;
                            case R.id.sensor_ir_sec_safety_doorNlock_configuration_cb_beeper:
                                klass_cmd = DeviceIR_SEC_SAFETYDoorLock.cmd_klass_CONFIGURATION_BEEPER;
                                break;

                            case R.id.sensor_ir_sec_safety_doorNlock_configuration_cb_lock_n_leave:
                                klass_cmd = DeviceIR_SEC_SAFETYDoorLock.cmd_klass_CONFIGURATION_LOCK_AND_LEAVE;
                                break;

                            case R.id.sensor_ir_sec_safety_doorNlock_configuration_cb_vacation:
                                klass_cmd = DeviceIR_SEC_SAFETYDoorLock.cmd_klass_CONFIGURATION_VACATION;
                                break;

                        }

                        MqttMessage message = null;

                        if (DeviceIR_SEC_SAFETYDoorLock.this.type.contentEquals("zwave"))
                            message = RemoteAccessMsg.CreateSetSecureMsg(DeviceTypeProtocol.ZWAVE, DeviceIR_SEC_SAFETYDoorLock.this.getId(),
                                    DeviceIR_SEC_SAFETYDoorLock.klass_SENSOR_CONFIGURATION, "SET", klass_cmd, value, "");


                        MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

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

                DeviceIR_SEC_SAFETYDoorLock.this.viewer_et_pin_len = (EditText) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_configuration_et_pin_len);
                if (DeviceIR_SEC_SAFETYDoorLock.this.viewer_et_pin_len != null) {
                    DeviceIR_SEC_SAFETYDoorLock.this.viewer_et_pin_len.setText("4-8");
                }

                Button viewer_bt_pin_len_get = (Button) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_configuration_button_pin_len_get);
                if (viewer_bt_pin_len_get != null) {
                    viewer_bt_pin_len_get.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MqttMessage message = null;
                            try {

                                if (DeviceIR_SEC_SAFETYDoorLock.this.type.contentEquals("zwave"))
                                    message = RemoteAccessMsg.CreateGetSecureMsg(DeviceTypeProtocol.ZWAVE, DeviceIR_SEC_SAFETYDoorLock.this.getId(),
                                            DeviceIR_SEC_SAFETYDoorLock.klass_SENSOR_CONFIGURATION, "GET", DeviceIR_SEC_SAFETYDoorLock.cmd_klass_CONFIGURATION_PIN_LENGTH, "1", "");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

                        }
                    });
                }

                Button bt_pin_len_set = (Button) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_configuration_button_pin_len_set);
                if (bt_pin_len_set != null) {
                    bt_pin_len_set.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String pin_len = DeviceIR_SEC_SAFETYDoorLock.this.viewer_et_pin_len.getText().toString();

                            MqttMessage message = null;
                            if (DeviceIR_SEC_SAFETYDoorLock.this.type.contentEquals("zwave"))
                                message = RemoteAccessMsg.CreateSetSecureMsg(DeviceTypeProtocol.ZWAVE, DeviceIR_SEC_SAFETYDoorLock.this.getId(),
                                        DeviceIR_SEC_SAFETYDoorLock.klass_SENSOR_CONFIGURATION, "SET", DeviceIR_SEC_SAFETYDoorLock.cmd_klass_CONFIGURATION_PIN_LENGTH, pin_len, "");


                            MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

                        }
                    });
                }


                Button bt_battery_get = (Button) rootView.findViewById(R.id.sensor_ir_sec_safety_doorNlock_configuration_button_battery_get);
                if (bt_battery_get != null) {
                    bt_battery_get.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            MqttMessage message = null;
                            try {

                                if (DeviceIR_SEC_SAFETYDoorLock.this.type.contentEquals("zwave"))
                                    message = RemoteAccessMsg.CreateGetSecureMsg(DeviceTypeProtocol.ZWAVE, DeviceIR_SEC_SAFETYDoorLock.this.getId(),
                                            DeviceIR_SEC_SAFETYDoorLock.klass_SENSOR_BATTERY, "GET", "", "", "");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            MQTTWrapper.PublishRemoteAccessMsg(MainActivity.topic, message);

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
}
