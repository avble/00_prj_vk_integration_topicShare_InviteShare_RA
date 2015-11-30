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
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import remote.service.verik.com.remoteaccess.R;
import remote.service.verik.com.remoteaccess.Vtransport.genericDevice.NotificationSensor;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveConfigurationMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdAssociationResp;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdConfigurationResp;

public class DeviceAEON_LABSDoor_Window_Sensor extends NotificationSensor implements IcmdConfigurationResp, IcmdAssociationResp{


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







    public DeviceAEON_LABSDoor_Window_Sensor(String id, String name, boolean turnOn, boolean available, String device_type)
    {
        super(id, name, turnOn, available, device_type);

        listFragment.add(createFragmentConfiguration());
        listFragment.add(createFragmentAssociation());

    }


    private DeviceFragment createFragmentConfiguration() {

        DeviceFragment fragment = new DeviceFragment() {
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

                View rootView = inflater.inflate(R.layout.content_device_aeon_labs_door_window_configuration, container, false);


                Button button_tmp = (Button) rootView.findViewById(R.id.sensor_door_window_basic_set_get);
                button_tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Get(id, cmd_klass_CONFIGURATION_BASIC_SET, "");

                    }
                });

                button_tmp = (Button) rootView.findViewById(R.id.sensor_door_window_binary_set_get);
                button_tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Get(id, cmd_klass_CONFIGURATION_BINARY_SENSOR, "");

                    }
                });

                button_tmp = (Button) rootView.findViewById(R.id.sensor_door_window_determines_get);
                button_tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Get(id, cmd_klass_CONFIGURATION_DETERMINES, "");

                    }
                });


                CheckBox checkbox_temp = (CheckBox) rootView.findViewById(R.id.sensor_cb_basic_set);
                checkbox_temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String value = "00";
                        if ( ((CheckBox)v).isChecked())
                            value = "1";
                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, cmd_klass_CONFIGURATION_BASIC_SET, value, "");
                    }
                });


                checkbox_temp = (CheckBox) rootView.findViewById(R.id.sensor_cb_binary_set);
                checkbox_temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String value = "00";
                        if ( ((CheckBox)v).isChecked())
                            value = "1";
                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, cmd_klass_CONFIGURATION_BINARY_SENSOR, value, "");
                    }
                });

                checkbox_temp = (CheckBox) rootView.findViewById(R.id.sensor_door_window_button_determines_basic);
                checkbox_temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String value = getCmd_klass_CONFIGURATION_DETERMINES_VALUES_DISABLE;
                        if ( ((CheckBox)v).isChecked())
                            value = getCmd_klass_CONFIGURATION_DETERMINES_VALUES_BASIC_SET_RP;

                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, cmd_klass_CONFIGURATION_DETERMINES, value, "");
                    }
                });


                checkbox_temp = (CheckBox) rootView.findViewById(R.id.sensor_door_window_button_determines_binary);
                checkbox_temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String value = getCmd_klass_CONFIGURATION_DETERMINES_VALUES_DISABLE;
                        if ( ((CheckBox)v).isChecked())
                            value = getCmd_klass_CONFIGURATION_DETERMINES_VALUES_BINARY_SENSOR_RP;

                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Set(id, cmd_klass_CONFIGURATION_DETERMINES, value, "");
                    }
                });






                return rootView;

            }
        };

        fragment.setTitle("Configuration");
        return fragment;
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

                            associationGetNode(id, cmd_klass_ASSOCIATION_ONOFF_GROUP);
                        }
                    });
                }


                // association test
                CheckBox cb_association_notification = (CheckBox) rootView.findViewById(R.id.cb_association_add_group);
                if (cb_association_notification != null) {
                    cb_association_notification.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            if (((CheckBox)v).isChecked())
                                associationSetNode(id, cmd_klass_ASSOCIATION_ONOFF_GROUP, "1");
                            else
                                associationRemoveNode(id, cmd_klass_ASSOCIATION_ONOFF_GROUP, "1");

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

                            associationSetNode(id, cmd_klass_ASSOCIATION_ONOFF_GROUP, nodeID);
                        }
                    });
                }

                Button button_ass_remove = (Button) rootView.findViewById(R.id.button_association_group_node_remove1);
                if (button_ass_remove != null) {
                    button_ass_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String nodeID = ed_nodeID.getText().toString();

                            associationRemoveNode(id, cmd_klass_ASSOCIATION_ONOFF_GROUP, nodeID);
                        }
                    });
                }




                return rootView;

            }
        };
        fragment.setTitle("Association");
        return fragment;
    }


    @Override
    public void Update(String property) {
        super.Update(property);




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
