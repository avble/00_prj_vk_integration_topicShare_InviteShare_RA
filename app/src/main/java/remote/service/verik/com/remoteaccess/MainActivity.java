package remote.service.verik.com.remoteaccess;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusListener;
import org.alljoyn.bus.Mutable;
import org.alljoyn.bus.ProxyBusObject;
import org.alljoyn.bus.SessionListener;
import org.alljoyn.bus.SessionOpts;
import org.alljoyn.bus.Status;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import remote.service.verik.com.remoteaccess.model.Adapter;
import remote.service.verik.com.remoteaccess.model.Device;


public class MainActivity extends ActionBarActivity implements View.OnCreateContextMenuListener, MqttCallback, IMqttActionListener, httpWrapperInterface {


    /* Load the native alljoyn_java library. */
    static {
        System.loadLibrary("alljoyn_java");
    }

    private final int INTENT_SETTING_RESULT_CODE = 1;

    public ArrayList<Device> devices;

    public static Handler handler;

    public static Adapter adapter;

    public static String share_invite_srv = "share_invite_srv";
    public static String share_mqtt_srv = "share_mqtt_srv";
    public static String share_topic = "topic";
    public static String share_pin = "pincode";
    public static String share_key = "key";



    // MQTT related data member
    // FIXME: there are 2 variables mqttSRV + URI.
    // Should remove either of them

    public static MqttAndroidClient client;
    private String mqttSRV = "52.88.81.183:1883";
    private static String topic = "";
    public static final String TAG = "MQTT";
    public static final String URI = "tcp://52.88.81.183:1883";
    public static final String CLIENT_ID = "02";

    public static View.OnClickListener bulbOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Device device = (Device) v.getTag();
            MqttMessage message = null;
            try {
                int value = 1;
                if (device.isTurnOn())
                    value = 0;
                message = MQTTMessageWrapper.CreateZwaveSetBinaryMsg(device.getId(), value);

                boolean on = true;
                if (value == 0)
                    on = false;

                device.setTurnOn(on);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                MainActivity.client.publish(topic, message);
            } catch (MqttException e) {
                Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
            }

        }
    };


    // InviteShare related data member
    private String pincode = "";
    private String inviteSRV = "52.88.81.183:8100";


    // AllJoyn related data member
    AlljoynWrapper alljoyn_wrapper;
    private ProgressDialog mDialog;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AlljoynWrapper.MESSAGE_ALLJOYN_GETTOPIC_REPLY:
                    String ret = (String) msg.obj;
                    if (ret.length() > 0) {
                        Toast.makeText(getApplicationContext(), "Just received a topic with key " + alljoyn_wrapper.keyValue + ": " + (String) msg.obj, Toast.LENGTH_LONG).show();
                        setTopic((String)msg.obj);
                    }else
                        Toast.makeText(getApplicationContext(), "Can not find the topic for key " + alljoyn_wrapper.keyValue , Toast.LENGTH_LONG).show();

                    break;

                case AlljoynWrapper.MESSAGE_POST_TOAST:
                    Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;
                case AlljoynWrapper.MESSAGE_ALLJOYN_START_PROGRESS_DIALOG:
                    mDialog = ProgressDialog.show(MainActivity.this,
                            "",
                            "Finding GetTopic Service.\nPlease wait...",
                            true,
                            true);
                    break;
                case AlljoynWrapper.MESSAGE_ALLJOYN_STOP_PROGRESS_DIALOG:
                    mDialog.dismiss();
                    Toast.makeText(getApplicationContext(), (String) "Successfully connect to board", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };


    public void setTopic(String new_topic) {
        if (!new_topic.equals(topic)) {
            topic = new_topic;

            if (topic.length() > 0) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (client.isConnected()) {
                                client.subscribe(topic, 0);
                            }
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        }
    }

    public static String getTopic()
    {
        return topic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(Looper.getMainLooper());


        devices = new ArrayList<>();
        //devices.add(new Device(1, "Z-Wave Light Bulb", false, true));

        // List view
        ListView list = (ListView) findViewById(R.id.list);
        adapter = new Adapter(this, devices);
        list.setAdapter(adapter);
        // Context menu
        registerForContextMenu(list);

        // MQTT initialization
        Log.d(TAG,"Start to connect to MQTT server.");
        //connect to server
        try {
            client  = new MqttAndroidClient(this,URI,CLIENT_ID);
            client.setCallback(this);
            //connect to server
            client.connect(null,this);
        } catch (MqttException e) {
            Log.d(TAG, "Error when connect to server " + URI + ", error code:  " + e.getReasonCode());
        }

        // Alljoyn initialzation
        alljoyn_wrapper = new AlljoynWrapper();

        //InviteShare Initialzation
        HttpWrapper.disableSSLCertificateChecking();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MqttMessage message;
        switch (item.getItemId()) {
            case R.id.option_menu_shareTopic_searching:
                alljoyn_wrapper.mBusHandler.sendEmptyMessage(alljoyn_wrapper.mBusHandler.CONNECT);
                mHandler.sendEmptyMessage(alljoyn_wrapper.MESSAGE_ALLJOYN_START_PROGRESS_DIALOG);
                return true;

            case R.id.option_menu_shareTopic_get_topic:
                Message msg = alljoyn_wrapper.mBusHandler.obtainMessage(AlljoynWrapper.BusHandler.GET_TOPIC,
                        alljoyn_wrapper.keyValue);
                alljoyn_wrapper.mBusHandler.sendMessage(msg);


                return true;

            case R.id.option_menu_inviteShare_genTopic: {
                HttpWrapper http_request = new HttpWrapper();
                http_request.topic = topic;
                http_request.setOutputListener(this);
                SettingActivity.cur_command = SettingActivity.COMMAND_INVITESHARE_GEN_PINCODE;
                String url = new String("https://");
                url += inviteSRV + "/generateInvite";
                http_request.execute(url);
            }
                return true;

            case R.id.option_menu_inviteShare_getTopic: {
                HttpWrapper http_request = new HttpWrapper();
                http_request.setOutputListener(this);
                SettingActivity.cur_command = SettingActivity.COMMAND_INVITESHARE_GET_TOPIC;

                String url = new String("https://");
                url += inviteSRV + "/getTopic/" + pincode;
                http_request.execute(url);
            }
                return true;

            case R.id.option_menu_setting:
                Intent intent1 = new Intent(this, SettingActivity.class);
                intent1.putExtra(share_invite_srv, inviteSRV);
                intent1.putExtra(share_mqtt_srv, mqttSRV);
                intent1.putExtra(share_topic, topic);
                intent1.putExtra(share_pin, pincode);
                intent1.putExtra(share_key, alljoyn_wrapper.keyValue);
                startActivityForResult(intent1, INTENT_SETTING_RESULT_CODE);
                return true;

            case R.id.option_menu_remoteAccess_zwave_get_list: //Get list of menu item
                message = null;
                try {
                    message = MQTTMessageWrapper.CreateGetListDevicesMsg();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    MainActivity.client.publish(topic, message);
                } catch (MqttException e) {
                    Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
                }

                return true;

            case R.id.option_menu_remoteAccess_zwave_add_device:
                message = null;
                try {
                    message = MQTTMessageWrapper.CreateAddDeviceMsg();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    MainActivity.client.publish(topic, message);
                } catch (MqttException e) {
                    Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
                }

                return true;


            case R.id.option_menu_remoteAccess_zwave_remove_device:
                message = null;
                try {
                    message = MQTTMessageWrapper.CreateRemoveDeviceMsg();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    MainActivity.client.publish(topic, message);
                } catch (MqttException e) {
                    Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
                }

                return true;


            case R.id.option_menu_help:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //    Context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.device_context_menu, menu);

        ListView list = (ListView)v;

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        Device device = (Device)list.getItemAtPosition(info.position);
        menu.setHeaderTitle(device.getId());

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MqttMessage message;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Device device = (Device)adapter.getItem(info.position);

        switch (item.getItemId())
        {
            case R.id.context_menu_get_binary:
                message = null;
                try {
                    message = MQTTMessageWrapper.CreateZwaveGetBinaryMsg(device.getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    MainActivity.client.publish(topic, message);
                } catch (MqttException e) {
                    Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
                }

                return true;
            case R.id.context_menu_get_specification:
                return true;
            case R.id.context_menu_get_secure_spec:
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public void connectionLost(Throwable throwable) {
        Log.d(TAG, "Connection to server has been lost, message: " + throwable.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {


        Log.d(TAG,"Received data from topic: "+topic+", Mqtt message: "+mqttMessage.toString());


        if (MQTTMessageWrapper.isMyMessage(mqttMessage.toString()))
            return;

        String command = MQTTMessageWrapper.getCommand(mqttMessage.toString());
        switch (command)
        {
            case MQTTMessageWrapper.commandGetListDeviceR:
                // fine-tuning the GUI
                JSONObject jason = new JSONObject(mqttMessage.toString());

                JSONArray deviceList = jason.getJSONArray("devicesList");

                devices = new ArrayList<>();
                adapter.clear();

                for(int i = 0; i < deviceList.length(); i++)
                {
                    JSONObject device = deviceList.getJSONObject(i);

                    String friendlyName = (String) device.get("FriendlyName");
                    String ID =  (String) device.get("ID");
                    devices.add(new Device(ID, friendlyName + " " + String.valueOf( i + 1) , false, true));

                }

                ListView list = (ListView) findViewById(R.id.list);
                adapter = new Adapter(this, devices);
                list.setAdapter(adapter);

                break;
            case MQTTMessageWrapper.commandAddDeviceR:
                // Add new device
                break;

            case MQTTMessageWrapper.commandRemoveDeviceR:
                break;

            case MQTTMessageWrapper.commandSetBinaryR:
                break;

            case MQTTMessageWrapper.commandGetBinaryR:
                break;

            case MQTTMessageWrapper.commandGetSecureSpecR:
                break;

            case MQTTMessageWrapper.commandSetSecureSpecR:
                break;

            case MQTTMessageWrapper.commandSetSpecification:
                break;

            case MQTTMessageWrapper.commandGetSpecification:
                break;

            case MQTTMessageWrapper.commandResetR:
                break;

            default:
                break;
        }


//        if(adapter != null){
//            Device device = adapter.getItem(0);
//            if(mqttMessage.toString().equals("on")){
//                device.setTurnOn(true);
//            }else if(mqttMessage.toString().equals("off")){
//                device.setTurnOn(false);
//            }
//            adapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        Log.d(TAG,"Delivered message successful");
    }

    @Override
    public void onSuccess(IMqttToken iMqttToken) {
        Log.d(TAG,"Connect success");
        Toast.makeText(this,"Connect to server "+URI+" successful",Toast.LENGTH_LONG).show();

        if (topic.length() > 0) {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (client.isConnected()) {
                            client.subscribe(topic, 0);
                        }
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
        Log.d(TAG, "Connect fail ");
    }

    @Override
    public void onOutputPostExecute(String s) {

        if (SettingActivity.cur_command == SettingActivity.COMMAND_INVITESHARE_UNKNOWN) {
            // TODO: Just send a Toast
            Toast.makeText(getApplicationContext(), "[InviteShare] Unknown response from server " , Toast.LENGTH_LONG).show();

        }else if (SettingActivity.cur_command == SettingActivity.COMMAND_INVITESHARE_GEN_PINCODE) {
            pincode = s;
            Toast.makeText(getApplicationContext(), "[InviteShare] just received the pincode ( " + pincode + " ) for topic " + topic , Toast.LENGTH_LONG).show();

        }
        else if (SettingActivity.cur_command == SettingActivity.COMMAND_INVITESHARE_GET_TOPIC) {
            setTopic(s);
            Toast.makeText(getApplicationContext(), "[InviteShare] just received the topic ( " + topic + " ) for pincode " + pincode , Toast.LENGTH_LONG).show();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (client.isConnected()) {
                            client.subscribe(topic, 0);
                        }
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (INTENT_SETTING_RESULT_CODE) : {
                if (resultCode == SettingActivity.INTENT_RESULT_OK && data != null) {

                    //inviteSRV =  data.getStringExtra(MainActivity.share_invite_srv);
                    //mqttSRV = data.getStringExtra(MainActivity.share_mqtt_srv);


                    String topic_tmp = data.getStringExtra(MainActivity.share_topic);
                    setTopic(topic_tmp);

                    pincode = data.getStringExtra(MainActivity.share_pin);

                }
                break;
            }
        }
    }

    class AlljoynWrapper{


        private static final int MESSAGE_ALLJOYN_GETTOPIC_REPLY = 2;
        private static final int MESSAGE_POST_TOAST = 3;
        private static final int MESSAGE_ALLJOYN_START_PROGRESS_DIALOG = 4;
        private static final int MESSAGE_ALLJOYN_STOP_PROGRESS_DIALOG = 5;

        private static final String TAG = "GetTopic";

//        public static final String key = "key";
        final public String keyValue;


        /* Handler used to make calls to AllJoyn methods. See onCreate(). */
        private BusHandler mBusHandler;

        private ProgressDialog mDialog;

        AlljoynWrapper(){
            HandlerThread busThread = new HandlerThread("BusHandler");
            busThread.start();
            mBusHandler = new BusHandler(busThread.getLooper());
            keyValue = VUtility.getMAC(getApplicationContext());
        }




    /* This class will handle all AllJoyn calls. See onCreate(). */

        class BusHandler extends Handler {
            /*
             * Name used as the well-known name and the advertised name of the service this client is
             * interested in.  This name must be a unique name both to the bus and to the network as a
             * whole.
             *
             * The name uses reverse URL style of naming, and matches the name used by the service.
             */
            private static final String SERVICE_NAME = "org.alljoyn.Bus.VEriK.GetTopic";
            private static final String OBJ_PATH = "/GetTopic";
            private static final short CONTACT_PORT=25;

            private BusAttachment mBus;
            private ProxyBusObject mProxyObj;
            private BasicInterface mBasicInterface;

            private int 	mSessionId;
            private boolean mIsInASession;
            private boolean mIsConnected;
            private boolean mIsStoppingDiscovery;

            /* These are the messages sent to the BusHandler from the UI. */
            public static final int CONNECT = 1;
            public static final int JOIN_SESSION = 2;
            public static final int DISCONNECT = 3;
            public static final int GET_TOPIC = 4;

            public BusHandler(Looper looper) {
                super(looper);

                mIsInASession = false;
                mIsConnected = false;
                mIsStoppingDiscovery = false;
            }

            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
            /* Connect to a remote instance of an object implementing the BasicInterface. */
                    case CONNECT: {
                        org.alljoyn.bus.alljoyn.DaemonInit.PrepareDaemon(getApplicationContext());
                /*
                 * All communication through AllJoyn begins with a BusAttachment.
                 *
                 * A BusAttachment needs a name. The actual name is unimportant except for internal
                 * security. As a default we use the class name as the name.
                 *
                 * By default AllJoyn does not allow communication between devices (i.e. bus to bus
                 * communication). The second argument must be set to Receive to allow communication
                 * between devices.
                 */
                        mBus = new BusAttachment(getPackageName(), BusAttachment.RemoteMessage.Receive);

                /*
                 * Create a bus listener class
                 */
                        mBus.registerBusListener(new BusListener() {
                            @Override
                            public void foundAdvertisedName(String name, short transport, String namePrefix) {
                                logInfo(String.format("MyBusListener.foundAdvertisedName(%s, 0x%04x, %s)", name, transport, namePrefix));
                    	/*
                    	 * This client will only join the first service that it sees advertising
                    	 * the indicated well-known name.  If the program is already a member of
                    	 * a session (i.e. connected to a service) we will not attempt to join
                    	 * another session.
                    	 * It is possible to join multiple session however joining multiple
                    	 * sessions is not shown in this sample.
                    	 */
                                if(!mIsConnected) {
                                    Message msg = obtainMessage(JOIN_SESSION);
                                    msg.arg1 = transport;
                                    msg.obj = name;
                                    sendMessage(msg);
                                }
                            }
                        });

                /* To communicate with AllJoyn objects, we must connect the BusAttachment to the bus. */
                        Status status = mBus.connect();
                        logStatus("BusAttachment.connect()", status);
                        if (Status.OK != status) {
                            finish();
                            return;
                        }

                /*
                 * Now find an instance of the AllJoyn object we want to call.  We start by looking for
                 * a name, then connecting to the device that is advertising that name.
                 *
                 * In this case, we are looking for the well-known SERVICE_NAME.
                 */
                        status = mBus.findAdvertisedName(SERVICE_NAME);
                        logStatus(String.format("BusAttachement.findAdvertisedName(%s)", SERVICE_NAME), status);
                        if (Status.OK != status) {
                            finish();
                            return;
                        }

                        break;
                    }
                    case (JOIN_SESSION): {
            	/*
                 * If discovery is currently being stopped don't join to any other sessions.
                 */
                        if (mIsStoppingDiscovery) {
                            break;
                        }

                /*
                 * In order to join the session, we need to provide the well-known
                 * contact port.  This is pre-arranged between both sides as part
                 * of the definition of the chat service.  As a result of joining
                 * the session, we get a session identifier which we must use to
                 * identify the created session communication channel whenever we
                 * talk to the remote side.
                 */
                        short contactPort = CONTACT_PORT;
                        SessionOpts sessionOpts = new SessionOpts();
                        sessionOpts.transports = (short)msg.arg1;
                        Mutable.IntegerValue sessionId = new Mutable.IntegerValue();

                        Status status = mBus.joinSession((String) msg.obj, contactPort, sessionId, sessionOpts, new SessionListener() {
                            @Override
                            public void sessionLost(int sessionId, int reason) {
                                mIsConnected = false;
                                logInfo(String.format("MyBusListener.sessionLost(sessionId = %d, reason = %d)", sessionId,reason));
                                mHandler.sendEmptyMessage(MESSAGE_ALLJOYN_START_PROGRESS_DIALOG);
                            }
                        });
                        logStatus("BusAttachment.joinSession() - sessionId: " + sessionId.value, status);

                        if (status == Status.OK) {
                	/*
                     * To communicate with an AllJoyn object, we create a ProxyBusObject.
                     * A ProxyBusObject is composed of a name, path, sessionID and interfaces.
                     *
                     * This ProxyBusObject is located at the well-known SERVICE_NAME, under path
                     * "/sample", uses sessionID of CONTACT_PORT, and implements the BasicInterface.
                     */
                            mProxyObj =  mBus.getProxyBusObject(SERVICE_NAME,
                                    OBJ_PATH,
                                    sessionId.value,
                                    new Class<?>[] { BasicInterface.class });

                	/* We make calls to the methods of the AllJoyn object through one of its interfaces. */
                            mBasicInterface =  mProxyObj.getInterface(BasicInterface.class);

                            mSessionId = sessionId.value;
                            mIsConnected = true;
                            mHandler.sendEmptyMessage(MESSAGE_ALLJOYN_STOP_PROGRESS_DIALOG);
                        }
                        break;
                    }

            /* Release all resources acquired in the connect. */
                    case DISCONNECT: {
                        mIsStoppingDiscovery = true;
                        if (mIsConnected) {
                            Status status = mBus.leaveSession(mSessionId);
                            logStatus("BusAttachment.leaveSession()", status);
                        }
                        mBus.disconnect();
                        getLooper().quit();
                        break;
                    }

            /*
             * Call the service's Cat method through the ProxyBusObject.
             *
             * This will also print the String that was sent to the service and the String that was
             * received from the service to the user interface.
             */
                    case GET_TOPIC: {
                        try {
                            if (mBasicInterface != null) {
                                //sendUiMessage(MESSAGE_PING, msg.obj + " and " + msg.obj);
                                //String reply = mBasicInterface.cat((String) msg.obj, (String) msg.obj);
                                String reply = mBasicInterface.get_topic((String)msg.obj);
                                sendUiMessage(MESSAGE_ALLJOYN_GETTOPIC_REPLY, reply);
                            }
                        } catch (BusException ex) {
                            logException("BasicInterface.cat()", ex);
                        }
                        break;
                    }
                    default:
                        break;
                }
            }

            /* Helper function to send a message to the UI thread. */
            private void sendUiMessage(int what, Object obj) {
                mHandler.sendMessage(mHandler.obtainMessage(what, obj));
            }
        }

        private void logStatus(String msg, Status status) {
            String log = String.format("%s: %s", msg, status);
            if (status == Status.OK) {
                Log.i(TAG, log);
            } else {
                //Message toastMsg = mHandler.obtainMessage(MESSAGE_POST_TOAST, log);
                //mHandler.sendMessage(toastMsg);
                Log.e(TAG, log);
            }
        }

        private void logException(String msg, BusException ex) {
            String log = String.format("%s: %s", msg, ex);
            //Message toastMsg = mHandler.obtainMessage(MESSAGE_POST_TOAST, log);
            //mHandler.sendMessage(toastMsg);
            Log.e(TAG, log, ex);
        }

        /*
         * print the status or result to the Android log. If the result is the expected
         * result only print it to the log.  Otherwise print it to the error log and
         * Sent a Toast to the users screen.
         */
        private void logInfo(String msg) {
            Log.i(TAG, msg);
        }


        void handleSendText(Intent intent) {
            String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (sharedText != null) {
                // Update UI to reflect text being shared
            }
        }
    }

}
