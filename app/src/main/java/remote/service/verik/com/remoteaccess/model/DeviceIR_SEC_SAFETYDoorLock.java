package remote.service.verik.com.remoteaccess.model;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONObject;

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
