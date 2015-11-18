package remote.service.verik.com.remoteaccess.model;

import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import remote.service.verik.com.remoteaccess.RemoteAccessMsg;

public class DeviceAEON_LABSMultilevelSensor5 extends Device {


    public double multilevel_sensor_temp = 0;
    public double multilevel_sensor_humi = 0;
    public double multilevel_sensor_lumi = 0;

    public int configuration_lock =  0;
    public int configuration_timer = 10; // minimun value
    public int configuration_auto_timer = Integer.parseInt(cmd_klass_CONFIGURATION_AUTO_TIMER_MIN, 16);


    public String battery_level =  "";


    // multilevel class
    final public static String klass_SENSOR_MULTILEVEL = "SENSOR_MULTILEVEL";
    final public static String type_SENSOR_MULTILEVEL_TEMP = "TEMP";
    final public static String type_SENSOR_MULTILEVEL_HUMI = "HUMI";
    final public static String type_SENSOR_MULTILEVEL_LUMI = "LUMI";





    // configuration class
    final public static String klass_SENSOR_CONFIGURATION = "CONFIGURATION";

    final public static String cmd_klass_CONFIGURATION_LOCK= "LOCK_CONFIGURATION";
    final public static String cmd_klass_CONFIGURATION_LOCK_VALUE_DISABLE= "0";
    final public static String cmd_klass_CONFIGURATION_LOCK_VALUE_ENABLE= "1";

    final public static String cmd_klass_CONFIGURATION_TIMER= "TIME";
    final public static String cmd_klass_CONFIGURATION_TIMER_VALUE_MIN= "10";
    final public static String cmd_klass_CONFIGURATION_TIMER_VALUE_OFF= "3600";

    final public static String cmd_klass_CONFIGURATION_REPORT= "REPORT_SENSOR";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_TEMP= "20";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_HUMI= "40";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_LUMI= "80";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_ULTRA= "02";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_BATTERY= "01";
    final public static String cmd_klass_CONFIGURATION_REPORT_VALUE_DISABLE= "00";



    final public static String cmd_klass_CONFIGURATION_PIR= "ENABLE_MOTION";
    final public static String cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_ON = "1";
    final public static String cmd_klass_CONFIGURATION_PIR_VALUE_SENSE_OFF = "0";



    final public static String cmd_klass_CONFIGURATION_AUTO_TIMER= "TIME_AUTO_REPORT";
    final public static String cmd_klass_CONFIGURATION_AUTO_TIMER_MIN = "0A";
    final public static String cmd_klass_CONFIGURATION_AUTO_TIMER_MAX = "28DE80";



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




    public DeviceAEON_LABSMultilevelSensor5(String id, String name, boolean turnOn, boolean available, String device_type)
    {
        super(id, name, turnOn, available, device_type);
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

            if (method.equals(RemoteAccessMsg.commandGetSpecificationR))
            {
                JSONObject jason_command_info = new JSONObject(commandinfo);
                String klass = jason_command_info.getString("class");
                String command = jason_command_info.getString("command");
                String data0 = jason_command_info.getString("data0");

                if (klass == null || command == null || data0 == null)
                    return;

                if (klass.equals(klass_SENSOR_MULTILEVEL))
                {
                    if (command.equals("GET"))
                    {
                        if (data0.equals(type_SENSOR_MULTILEVEL_TEMP))
                        {
                            String fahrenheit = jason.getString("fahrenheit");
                            multilevel_sensor_temp = Double.parseDouble(fahrenheit);

                            if (viewer_tw_multilevel5_temp != null)
                            {
                                viewer_tw_multilevel5_temp.setText(Double.toString(multilevel_sensor_temp));
                            }


                        }else if  (data0.equals(type_SENSOR_MULTILEVEL_LUMI))
                        {

                            String luminance = jason.getString("luminance");
                            multilevel_sensor_lumi = Double.parseDouble(luminance);

                            if (viewer_tw_multilevel5_lumi != null)
                            {
                                viewer_tw_multilevel5_lumi.setText(Double.toString(multilevel_sensor_lumi));
                            }

                        }else if  (data0.equals(type_SENSOR_MULTILEVEL_HUMI))
                        {
                            String absolute_humidity = jason.getString("absolute_humidity");
                            multilevel_sensor_humi = Double.parseDouble(absolute_humidity);

                            if (viewer_tw_multilevel5_humi != null)
                            {
                                viewer_tw_multilevel5_humi.setText(Double.toString(multilevel_sensor_humi));
                            }


                        }

                    }

                }else if (klass.equals(klass_SENSOR_ASSOCIATION)) {

                    // TODO

                }else if (klass.equals(klass_SENSOR_CONFIGURATION)) {
                    // TODO

                }else if (klass.equals(klass_SENSOR_BATTERY)) {
                    if (command.equals("GET")) {
                        battery_level = jason.getString("batterylevel");

                        if (viewer_tw_sensor_battery != null) {
                            viewer_tw_sensor_battery.setText(battery_level);
                        }


                    }
                }
            }else if (method.equals(RemoteAccessMsg.commandSetSpecificationR))
            {
                JSONObject jason_command_info = new JSONObject(commandinfo);
                String klass = jason_command_info.getString("class");
                String command = jason_command_info.getString("command");
                String data0 = jason_command_info.getString("data0");

                if (klass == null || command == null || data0 == null)
                    return;
                if (klass.equals(klass_SENSOR_MULTILEVEL))
                {


                }else if (klass.equals(klass_SENSOR_ASSOCIATION)) {

                    // TODO

                }else if (klass.equals(klass_SENSOR_CONFIGURATION)) {
                    if (command.equals("GET"))
                    {
                        if (data0.equals(cmd_klass_CONFIGURATION_LOCK))
                        {
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




        }
        catch (JSONException e) {
        }
    }
}
