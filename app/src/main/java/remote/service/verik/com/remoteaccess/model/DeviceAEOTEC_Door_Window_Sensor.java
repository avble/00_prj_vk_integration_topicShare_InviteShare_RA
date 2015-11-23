package remote.service.verik.com.remoteaccess.model;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class DeviceAEOTEC_Door_Window_Sensor extends Device {


    // configuration class
    final public static String klass_SENSOR_CONFIGURATION = "CONFIGURATION";

    final public static String cmd_klass_CONFIGURATION_DETERMINES= "DETERMINES";

    final public static String getCmd_klass_CONFIGURATION_DETERMINES_VALUES_BINARY_SENSOR_RP = "10";
    final public static String getCmd_klass_CONFIGURATION_DETERMINES_VALUES_BASIC_SET_RP = "100";
    final public static String getCmd_klass_CONFIGURATION_DETERMINES_VALUES_BATTERY_RP = "01";
    final public static String getCmd_klass_CONFIGURATION_DETERMINES_VALUES_DISABLE = "00";


    final public static String cmd_klass_CONFIGURATION_BASIC_SET = "SET_REPORT";
    final public static String cmd_klass_CONFIGURATION_BINARY_SENSOR = "BINARY_REPORT";


    // configuration class
    final public static String klass_SENSOR_ASSOCIATION = "ASSOCIATION";
    final public static String cmd_klass_ASSOCIATION_ONOFF_GROUP = "REPORT_GROUP";
    final public static String cmd_klass_ASSOCIATION_ONOFF_GROUP_CONTROLLER_ID = "1";







    public DeviceAEOTEC_Door_Window_Sensor(String id, String name, boolean turnOn, boolean available, String device_type)
    {
        super(id, name, turnOn, available, device_type);
    }


    @Override
    public void Update(String property) {
        super.Update(property);




    }
}
