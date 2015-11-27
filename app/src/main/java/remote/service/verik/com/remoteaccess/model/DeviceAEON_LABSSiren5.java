package remote.service.verik.com.remoteaccess.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import remote.service.verik.com.remoteaccess.R;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveConfigurationMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdAssociationReport;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdConfigurationReport;
import remote.service.verik.com.remoteaccess.Vtransport.genericDevice.SwitchBinary;

public class DeviceAEON_LABSSiren5 extends SwitchBinary implements IcmdConfigurationReport, IcmdAssociationReport {


    // configuration class
    final public static String klass_SENSOR_CONFIGURATION = "CONFIGURATION";

    final public static String cmd_klass_CONFIGURATION_VOLUME = "SOUND_VOLUME";


    public DeviceAEON_LABSSiren5(String id, String name, boolean turnOn, boolean available, String device_type) {
        super(id, name, turnOn, available, device_type);

        listFragment.add(createFragmentBinarySwitch());
        listFragment.add(createFragmentConfiguration());

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
                        Button button = (Button)v;
                        if (button.getText().toString().compareToIgnoreCase("on") == 0)
                        {
                            button.setText("off");
                            DeviceAEON_LABSSiren5.this.binarySwitchSetOn();
                        }else
                        {
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



                RadioGroup radio_group_siren = (RadioGroup)rootView.findViewById(R.id.radioGroupSiren);
                if (radio_group_siren != null)
                {
                    radio_group_siren.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                    {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            int value = 0;
                            switch (checkedId)
                            {
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



                RadioGroup radio_group_volume = (RadioGroup)rootView.findViewById(R.id.radioGroupVolume);
                if (radio_group_volume != null)
                {
                    radio_group_volume.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                    {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            int value = 0;
                            switch (checkedId)
                            {
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
    public void onBinarySwitchReport(String status, String value) {
        Log.d("Report Debug", "Just received a Binary Report. Status " + status + " Value is  " + value);

    }

    @Override
    public void onConfigurationReport(String cmd, String data0, String data1, String data2, String status) {
        Log.d("Report Debug", "Just received a Configuration Report. cmd " + cmd + " data0  " + data0 + " data1 " + data1 +  " data2 " + data2 + " status " + status);

    }

    @Override
    public void onAssociationReport(String groupIdentifier, String maxNode, String nodeFlow) {
        Log.d("Report Debug", "Just received a Configuration Report. groupID " + groupIdentifier +  maxNode + nodeFlow);
    }
}
