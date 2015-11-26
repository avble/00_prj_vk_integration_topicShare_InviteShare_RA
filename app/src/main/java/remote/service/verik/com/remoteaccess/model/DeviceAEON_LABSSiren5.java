package remote.service.verik.com.remoteaccess.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import remote.service.verik.com.remoteaccess.R;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveConfiguration;
import remote.service.verik.com.remoteaccess.zwave.genericDevice.SwitchBinary;

public class DeviceAEON_LABSSiren5 extends SwitchBinary {


    // configuration class
    final public static String klass_SENSOR_CONFIGURATION = "CONFIGURATION";

    final public static String cmd_klass_CONFIGURATION_VOLUME = "SOUND_VOLUME";


    public DeviceAEON_LABSSiren5(String id, String name, boolean turnOn, boolean available, String device_type) {
        super(id, name, turnOn, available, device_type);

        listFragment.add(createFragmentConfiguration());

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
                        zwaveConfiguration config = new zwaveConfiguration();
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
                            zwaveConfiguration config = new zwaveConfiguration();
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
                            zwaveConfiguration config = new zwaveConfiguration();
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
}
