package remote.service.verik.com.remoteaccess.model;

import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import remote.service.verik.com.remoteaccess.MQTTMessageWrapper;

/**
 * Created by huyle on 11/12/15.
 */
public class DeviceAEON_LABSHeavyDutySmart extends  Device {



    public double multilevel_sensor_temp = 0;
    public double multilevel_sensor_humi = 0;

    public double meter_power = 0;



    final public static String klass_SENSOR_MULTILEVEL = "SENSOR_MULTILEVEL";
    final public static String type_SENSOR_MULTILEVEL_TEMP = "TEMP";
    final public static String type_SENSOR_MULTILEVEL_HUMI = "HUMI";

    final public static String klass_METER = "METER";
    final public static String type_METER_POWER = "POWER";

    public DeviceAEON_LABSHeavyDutySmart(String id, String name, boolean turnOn, boolean available, String device_type)
    {
        super(id, name, turnOn, available, device_type);
    }

    // For Viewer
    public TextView viewer_tw_multilevel_humi;
    public TextView viewer_tw_multilevel_temp;
    public TextView viewer_tw_meter_power;


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

            if (method.equals(MQTTMessageWrapper.commandGetSpecificationR))
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

                            if (viewer_tw_multilevel_temp != null)
                            {
                                viewer_tw_multilevel_temp.setText(Double.toString(multilevel_sensor_temp));
                            }


                        }else if  (data0.equals(type_SENSOR_MULTILEVEL_HUMI))
                        {
                            String fahrenheit = jason.getString("fahrenheit");
                            multilevel_sensor_humi = Double.parseDouble(fahrenheit);

                            if (viewer_tw_multilevel_humi != null)
                            {
                                viewer_tw_multilevel_humi.setText(Double.toString(multilevel_sensor_humi));
                            }


                        }

                    }

                }if (klass.equals(klass_METER)) {
                if (data0.equals(type_METER_POWER)) {
                    String electricmeter = jason.getString("electricmeter");
                    String time = jason.getString("time");


                    meter_power = Double.parseDouble(electricmeter);

                    if (viewer_tw_meter_power != null) {
                        viewer_tw_meter_power.setText("Time: " + time + " Power: " + Double.toString(meter_power));
                    }


                }
            }





            }else if (method.equals(MQTTMessageWrapper.commandSetSpecificationR))
            {
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



            }




        }
        catch (JSONException e) {
        }
    }

}
