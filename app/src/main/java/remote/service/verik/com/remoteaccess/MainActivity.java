package remote.service.verik.com.remoteaccess;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;

import remote.service.verik.com.remoteaccess.model.Adapter;
import remote.service.verik.com.remoteaccess.model.Device;

//TODO:
// 1)
// 2)

public class MainActivity extends ActionBarActivity implements MqttCallback, IMqttActionListener {

    public ArrayList<Device> devices;

    public static final String TAG = "MQTT";

//    public static final String URI = "tcp://10.0.0.120:1883";
    public static final String URI = "tcp://52.88.81.183:1883";

    public static final String CLIENT_ID = "02";

//    public static final String TOPIC = "remoteAccess/bulb";

    public static MqttAndroidClient client;

    public static Handler handler;

    public static Adapter adapter;

    public static String share_invite_srv = "share_invite_srv";
    public static String share_mqtt_srv = "share_mqtt_srv";
    public static String share_topic = "topic";
    public static String share_pin = "pincode";


    private String inviteSRV = "52.88.81.183:8100";
    private String mqttSRV = "52.88.81.183:1883";
    public static String topic = "/VEriK/1234567890";
    private String pincode = "02f7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(Looper.getMainLooper());

        devices = new ArrayList<>();
        devices.add(new Device(1, "Z-Wave Light Bulb", false, true));

        ListView list = (ListView) findViewById(R.id.list);
        adapter = new Adapter(this, devices);
        list.setAdapter(adapter);

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
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.option_menu_setting:
                Intent intent1 = new Intent(this, SettingActivity.class);
                intent1.putExtra(share_invite_srv, inviteSRV);
                intent1.putExtra(share_mqtt_srv, mqttSRV);
                intent1.putExtra(share_topic, topic);
                intent1.putExtra(share_pin, pincode);
                startActivity(intent1);
                return true;
            case R.id.option_menu_help:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void connectionLost(Throwable throwable) {
        Log.d(TAG, "Connection to server has been lost, message: " + throwable.getMessage());
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        Log.d(TAG,"Received data from topic: "+s+", Mqtt message: "+mqttMessage.toString());
        if(adapter != null){
            Device device = adapter.getItem(0);
            if(mqttMessage.toString().equals("on")){
                device.setTurnOn(true);
            }else if(mqttMessage.toString().equals("off")){
                device.setTurnOn(false);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        Log.d(TAG,"Delivered message successful");
    }

    @Override
    public void onSuccess(IMqttToken iMqttToken) {
        Log.d(TAG,"Connect success");
        Toast.makeText(this,"Connect to server "+URI+" successful",Toast.LENGTH_LONG).show();
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

    @Override
    public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
        Log.d(TAG,"Connect fail ");
    }
}
