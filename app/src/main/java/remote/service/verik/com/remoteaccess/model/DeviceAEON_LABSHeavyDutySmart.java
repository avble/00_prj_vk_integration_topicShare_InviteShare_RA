package remote.service.verik.com.remoteaccess.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import remote.service.verik.com.remoteaccess.R;
import remote.service.verik.com.remoteaccess.RemoteAccessMsg;
import remote.service.verik.com.remoteaccess.Vtransport.genericDevice.genericSwitchBinary;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveAssociationMsg;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveBatteryMsg;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveMeterMsg;
import remote.service.verik.com.remoteaccess.Vtransport.zwaveMultilevelMsg;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdAssociationResp;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdMeterResp;
import remote.service.verik.com.remoteaccess.zwave.cmdClass.IcmdMultilevelResp;

/**
 * Created by huyle on 11/12/15.
 */
public class DeviceAEON_LABSHeavyDutySmart extends genericSwitchBinary implements IcmdMultilevelResp, IcmdMeterResp, IcmdAssociationResp {



    final public static String klass_SENSOR_MULTILEVEL = "SENSOR_MULTILEVEL";
    final public static String type_SENSOR_MULTILEVEL_TEMP = "TEMP";

    final public static String klass_METER = "METER";
    final public static String type_METER_POWER = "POWER";

    // Association
    final public static String cmd_klass_association_group1 = "REPORT_GROUP";
    final public static String cmd_klass_association_group2 = "ONOFF_GROUP";



    // For Viewer
    public TextView viewer_tw_multilevel_humi;
    public TextView viewer_tw_multilevel_temp;
    public TextView viewer_tw_meter_power;


    public DeviceAEON_LABSHeavyDutySmart(String id, String name, boolean turnOn, boolean available, String device_type)
    {
        super(id, name, turnOn, available, device_type);

        // Fragment initialization
        listFragment.add(createFragmentMultilevel());
        listFragment.add(createFragmentAssociation());


    }

    private Fragment createFragmentMultilevel() {

        DeviceFragment fragment = new DeviceFragment() {
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


                View rootView = inflater.inflate(R.layout.content_device_aeon_labs_heavy_duty_smart_multilevel, container, false);


                Button button_temp = (Button) rootView.findViewById(R.id.heavy_duty_smart_button_temp);

                button_temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        zwaveMultilevelMsg multielvel = new zwaveMultilevelMsg();
                        multielvel.multilevelGet(id, type_SENSOR_MULTILEVEL_TEMP);
                    }
                });


                Button button_meter = (Button) rootView.findViewById(R.id.heavy_duty_smart_button_meter);

                button_meter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        zwaveMeterMsg meter = new zwaveMeterMsg();
                        meter.meterGet(id);
                    }
                });

                return rootView;

            }

        };
        fragment.setTitle("MultilevelNPowerLevel");

        return fragment;
    }


    private DeviceFragment createFragmentAssociation() {
        DeviceFragment fragment = new DeviceFragment() {

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


                View rootView = inflater.inflate(R.layout.content_device_generic_zwave_association, container, false);


                // association test
                Button button_ass_get = (Button) rootView.findViewById(R.id.button_association_group_get);
                if (button_ass_get != null) {
                    button_ass_get.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            zwaveAssociationMsg associationMsg = new zwaveAssociationMsg();
                            associationMsg.associationGetNode(id, cmd_klass_association_group1);

                        }
                    });
                }


                // association test
                CheckBox cb_association_notification = (CheckBox) rootView.findViewById(R.id.cb_association_add_group);
                if (cb_association_notification != null) {
                    cb_association_notification.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            zwaveAssociationMsg associationMsg = new zwaveAssociationMsg();

                            if (((CheckBox)v).isChecked())
                                associationMsg.associationSetNode(id, cmd_klass_association_group1, "1");
                            else
                                associationMsg.associationRemoveNode(id, cmd_klass_association_group1, "1");

                        }
                    });
                }



                final EditText ed_nodeID = (EditText) rootView.findViewById(R.id.et_node_id1);

                final EditText ed_nodeID2 = (EditText) rootView.findViewById(R.id.et_node_id2);


                Button button_ass_add = (Button) rootView.findViewById(R.id.button_association_group_node_add1);
                if (button_ass_add != null) {
                    button_ass_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String nodeID = ed_nodeID.getText().toString();
                            zwaveAssociationMsg associationMsg = new zwaveAssociationMsg();
                            associationMsg.associationSetNode(id, cmd_klass_association_group1, nodeID);

                        }
                    });
                }

                Button button_ass_remove = (Button) rootView.findViewById(R.id.button_association_group_node_remove1);
                if (button_ass_remove != null) {
                    button_ass_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String nodeID = ed_nodeID.getText().toString();
                            zwaveAssociationMsg associationMsg = new zwaveAssociationMsg();
                            associationMsg.associationRemoveNode(id, cmd_klass_association_group1, nodeID);

                        }
                    });
                }


                Button button_ass_add2 = (Button) rootView.findViewById(R.id.button_association_group_node_add2);
                if (button_ass_add2 != null) {
                    button_ass_add2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String nodeID = ed_nodeID2.getText().toString();
                            zwaveAssociationMsg associationMsg = new zwaveAssociationMsg();
                            associationMsg.associationSetNode(id, cmd_klass_association_group2, nodeID);

                        }
                    });
                }

                Button button_ass_remove2 = (Button) rootView.findViewById(R.id.button_association_group_node_remove2);
                if (button_ass_remove2 != null) {
                    button_ass_remove2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String nodeID = ed_nodeID2.getText().toString();
                            zwaveAssociationMsg associationMsg = new zwaveAssociationMsg();
                            associationMsg.associationRemoveNode(id, cmd_klass_association_group2, nodeID);

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
    public int multilevelGetResp(String more) {
        AddLogToHistory("[MULTILEVEL][GET] " + more);
        return 0;
    }

    @Override
    public int meterGetResp(String more) {
        AddLogToHistory("[METER][GET] " + more);
        return 0;
    }

    @Override
    public void onAssociationGetResp(String groupIdentifier, String maxNode, String nodeFlow) {
        AddLogToHistory("[ASSOCIATION][GET] groupIdentifier: " + groupIdentifier + " maxNode: " + maxNode + " nodeFlow: " + nodeFlow);
    }
}
