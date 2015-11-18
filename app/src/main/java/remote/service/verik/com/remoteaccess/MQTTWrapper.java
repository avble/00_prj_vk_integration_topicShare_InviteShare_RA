package remote.service.verik.com.remoteaccess;

import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by huyle on 11/18/15.
 */
public class MQTTWrapper {

    public static void PublishRemoteAccessMsg(String topic, MqttMessage message)
    {

        try {
            MainActivity.client.publish(topic, message);
        } catch (MqttException e) {
            Log.d(MainActivity.TAG, "Publish error with message: " + e.getMessage());
        }

    }


}
