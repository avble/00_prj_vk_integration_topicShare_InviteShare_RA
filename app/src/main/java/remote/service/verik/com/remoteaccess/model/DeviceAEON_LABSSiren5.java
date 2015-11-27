package remote.service.verik.com.remoteaccess.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import remote.service.verik.com.remoteaccess.DeviceTypeProtocol;
import remote.service.verik.com.remoteaccess.MainActivity;
import remote.service.verik.com.remoteaccess.R;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveConfigurationMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdAssociation;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdAssociationResp;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdConfigurationResp;
import remote.service.verik.com.remoteaccess.Vtransport.genericDevice.SwitchBinary;

public class DeviceAEON_LABSSiren5 extends SwitchBinary implements IcmdConfigurationResp, IcmdAssociationResp, IcmdAssociation {


    // configuration class
    final public static String klass_SENSOR_CONFIGURATION = "CONFIGURATION";

    final public static String cmd_klass_CONFIGURATION_VOLUME = "SOUND_VOLUME";
    final public static String cmd_klass_CONFIGURATION_NOTIFICATION = "NOTIFICATION";

    // association class
    final public static String cmd_klass_ASSOCIATION_ONOFF_GROUP = "REPORT_GROUP";


    public DeviceAEON_LABSSiren5(String id, String name, boolean turnOn, boolean available, String device_type) {
        super(id, name, turnOn, available, device_type);

        listFragment.add(createFragmentBinarySwitch());
        listFragment.add(createFragmentConfiguration());
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

                            associationGetNode(id, DeviceAEON_LABSSiren5.cmd_klass_ASSOCIATION_ONOFF_GROUP);
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
                                associationSetNode(id, DeviceAEON_LABSSiren5.cmd_klass_ASSOCIATION_ONOFF_GROUP, "1");
                            else
                                associationRemoveNode(id, DeviceAEON_LABSSiren5.cmd_klass_ASSOCIATION_ONOFF_GROUP, "1");

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

                            associationSetNode(id, DeviceAEON_LABSSiren5.cmd_klass_ASSOCIATION_ONOFF_GROUP, nodeID);
                        }
                    });
                }

                Button button_ass_remove = (Button) rootView.findViewById(R.id.button_association_group_node_remove1);
                if (button_ass_remove != null) {
                    button_ass_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String nodeID = ed_nodeID.getText().toString();

                            associationRemoveNode(id, DeviceAEON_LABSSiren5.cmd_klass_ASSOCIATION_ONOFF_GROUP, nodeID);
                        }
                    });
                }




                return rootView;

            }
        };
        fragment.setTitle("Association");
        return fragment;
    }

    private DeviceFragment createFragmentBinarySwitch() {

        DeviceFragment fragment = new DeviceFragment() {
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

                View rootView = inflater.inflate(R.layout.content_device_zwave_switch_binary, container, false);

                Button button_set = (Button) rootView.findViewById(R.id.switch_binary_button_binary_switch);
                button_set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button button = (Button) v;
                        if (button.getText().toString().compareToIgnoreCase("on") == 0) {
                            button.setText("off");
                            DeviceAEON_LABSSiren5.this.binarySwitchSetOn();
                        } else {
                            button.setText("on");
                            DeviceAEON_LABSSiren5.this.binarySwitchSetOff();

                        }

                    }
                });


                Button button_get = (Button) rootView.findViewById(R.id.switch_binary_button_binary_switch_get);
                button_get.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeviceAEON_LABSSiren5.this.binarySwitchGet();

                    }
                });


                return rootView;

            }
        };

        fragment.setTitle("BinarySwitch");
        return fragment;
    }


    private DeviceFragment createFragmentConfiguration() {

        DeviceFragment fragment = new DeviceFragment() {
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

                View rootView = inflater.inflate(R.layout.content_device_zwave_aeon_labs_siren5_configuration, container, false);

                Button button_volume = (Button) rootView.findViewById(R.id.sensor_siren_configuration_button_volume_get);
                button_volume.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Get(id, cmd_klass_CONFIGURATION_VOLUME, "");


                    }
                });


                RadioGroup radio_group_siren = (RadioGroup) rootView.findViewById(R.id.radioGroupSiren);
                if (radio_group_siren != null) {
                    radio_group_siren.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            int value = 0;
                            switch (checkedId) {
                                case R.id.radioButtonSirenType1:
                                    value = 1;
                                    break;
                                case R.id.radioButtonSirenType2:
                                    value = 2;
                                    break;
                                case R.id.radioButtonSirenType3:
                                    value = 3;
                                    break;
                                case R.id.radioButtonSirenType4:
                                    value = 4;
                                    break;
                                case R.id.radioButtonSirenType5:
                                    value = 5;
                                    break;


                            }
                            String value_s = String.format("%d00", value);
                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Set(id, cmd_klass_CONFIGURATION_VOLUME, value_s, "");

                            // checkedId is the RadioButton selected
                        }
                    });
                }


                RadioGroup radio_group_volume = (RadioGroup) rootView.findViewById(R.id.radioGroupVolume);
                if (radio_group_volume != null) {
                    radio_group_volume.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            int value = 0;
                            switch (checkedId) {
                                case R.id.radioButtonVolumeType1:
                                    value = 1;
                                    break;
                                case R.id.radioButtonVolumeType2:
                                    value = 2;
                                    break;
                                case R.id.radioButtonVolumeType3:
                                    value = 3;
                                    break;


                            }
                            String value_s = String.format("00%d", value);
                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Set(id, cmd_klass_CONFIGURATION_VOLUME, Integer.toString(value), "");

                            // checkedId is the RadioButton selected
                        }
                    });
                }

                Button button_notification = (Button) rootView.findViewById(R.id.sensor_siren_configuration_button_notification_get);
                button_notification.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                        config.Get(id, cmd_klass_CONFIGURATION_NOTIFICATION, "");

                    }
                });



                RadioGroup radio_group_notification = (RadioGroup) rootView.findViewById(R.id.radioGroupNotificationType);
                if (radio_group_notification != null) {
                    radio_group_notification.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            int value = 0;
                            switch (checkedId) {
                                case R.id.radioButtonNotificationType1:
                                    value = 0;
                                    break;
                                case R.id.radioButtonNotificationType2:
                                    value = 1;
                                    break;
                                case R.id.radioButtonNotificationType3:
                                    value = 2;
                                    break;


                            }
                            String value_s = String.format("%d", value);
                            zwaveConfigurationMsg config = new zwaveConfigurationMsg();
                            config.Set(id, cmd_klass_CONFIGURATION_NOTIFICATION, Integer.toString(value), "");

                            // checkedId is the RadioButton selected
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
    public void onBinarySwitchGetResp(String status, String value) {
        Log.d("Report Debug", "Just received a Binary Report. Status " + status + " Value is  " + value);
        AddLogToHistory("BinarySwitch][GET] Status " + status + " Value is  " + value);


    }

    @Override
    public void onConfigurationGetResp(String cmd, String data0, String data1, String data2, String status, String more) {
        //AddLogToHistory("[CONFIGURATION][GET] data0: " + data0 + " data1: " + data1 + " data2: " + data2 + " status: " + status);
        AddLogToHistory("[CONFIGURATION][GET]  " + more);
        Log.d("Report Debug", "Just received a Configuration Report. cmd " + cmd + " data0  " + data0 + " data1 " + data1 + " data2 " + data2 + " status " + status);

    }

    public int associationSetNode(String id, String groupID, String nodeID) {
        MqttMessage message = null;
        message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, id, "ASSOCIATION", "SET", groupID, nodeID, "");
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;
    }

    @Override
    public int associationGetNode(String id, String groupID) {

        MqttMessage message = null;
        message = RemoteAccessMsg.CreateGetSpecificationMsg(DeviceTypeProtocol.ZWAVE, id, "ASSOCIATION", "GET", groupID, "", "");
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;
    }

    @Override
    public int associationRemoveNode(String id, String groupID, String nodeID) {
        MqttMessage message = null;
        message = RemoteAccessMsg.CreateSetSpecificationMsg(DeviceTypeProtocol.ZWAVE, id, "ASSOCIATION", "REMOVE", groupID, nodeID, "");
        MainActivity.mqtt_client.PublishRemoteAccessMsg(MainActivity.topic, message);

        return 0;
    }


    @Override
    public void onAssociationGetResp(String groupIdentifier, String maxNode, String nodeFlow) {
        AddLogToHistory("[ASSOCIATION] groupIdentifier: " + groupIdentifier + " maxNode: " + maxNode + " nodeFlow: " + nodeFlow);
    }
}
