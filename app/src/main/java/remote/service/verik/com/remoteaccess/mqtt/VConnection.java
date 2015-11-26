package remote.service.verik.com.remoteaccess.mqtt;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import remote.service.verik.com.remoteaccess.MainActivity;

/**
 * Created by huyle on 11/26/15.
 */
public class VConnection {

    MqttConnectOptions conOpt;
    public MqttAndroidClient client;

    String uri;
    String clientID;
    Context context;

    public VConnection(Context context, String serverURI, String clientId) {
        this.uri = serverURI;
        this.clientID = clientId;
        this.context = context;
    }

    public void Connect()
    {

        String clientHandle =  uri + clientID;

        client = new MqttAndroidClient(context, uri, clientID);
        conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(false);
        conOpt.setConnectionTimeout(1000);
        conOpt.setKeepAliveInterval(10);

        client.setCallback(new MqttCallbackHandler(context, uri + clientID));

        //set traceCallback
        client.setTraceCallback(new MqttTraceCallback());
        String[] actionArgs = new String[1];
        actionArgs[0] = clientID;

        final ActionListener callback = new ActionListener(context,
                ActionListener.Action.CONNECT, clientHandle, actionArgs);


        try {
            client.connect(conOpt, null, callback);
        }
        catch (MqttException e) {
            Log.e(this.getClass().getCanonicalName(),
                    "MqttException Occured", e);
        }
    }


    public void PublishRemoteAccessMsg(String topic, MqttMessage message)
    {

        try {
            client.publish(topic, message);
        } catch (MqttException e) {
            Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
        }

    }


}
