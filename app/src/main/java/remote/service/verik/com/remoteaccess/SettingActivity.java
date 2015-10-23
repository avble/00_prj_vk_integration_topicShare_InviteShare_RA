package remote.service.verik.com.remoteaccess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class SettingActivity extends AppCompatActivity implements httpWrapperInterface {

    String topic;
    String pincode;

    static public int cur_command = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        final String invite_SRV = extras.getString(MainActivity.share_invite_srv);
        EditText ed1 = (EditText)findViewById(R.id.editText_InviteSRV);
        ed1.setText(invite_SRV);

        String mqtt_SRV = extras.getString(MainActivity.share_mqtt_srv);
        EditText ed_mqtt = (EditText)findViewById(R.id.editText_mqttSRV);
        ed_mqtt.setText(mqtt_SRV);

        topic = extras.getString(MainActivity.share_topic);
        EditText ed_topic = (EditText)findViewById(R.id.editText_topic);
        ed_topic.setText(topic);

        pincode = extras.getString(MainActivity.share_pin);
        EditText ed_pincode = (EditText)findViewById(R.id.editText_pincode);
        ed_pincode.setText(pincode);

        HttpWrapper.disableSSLCertificateChecking();


        Button button_gen = (Button)findViewById(R.id.button_generate);
        button_gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpWrapper http_request = new HttpWrapper();
                EditText ed_topic = (EditText)findViewById(R.id.editText_topic);
                http_request.topic = ed_topic.getText();
                http_request.setOutputListener(SettingActivity.this);
                SettingActivity.cur_command = 0;

                String url = new String("https://");
                url += invite_SRV + "/generateInvite";
                http_request.execute(url);
            }
        });


        Button button_get_pin_code = (Button)findViewById(R.id.button_get_pin_code);
        button_get_pin_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpWrapper http_request = new HttpWrapper();
                http_request.setOutputListener(SettingActivity.this);
                SettingActivity.cur_command = 1;

                String url = new String("https://");
                url += invite_SRV + "/getTopic/" + pincode;
                http_request.execute(url);
            }
        });

    }

    @Override
    public void onOutputPostExecute(String s) {
        if (SettingActivity.cur_command == 0) {
            pincode = s;
            EditText ed_pincode = (EditText)findViewById(R.id.editText_pincode);
            ed_pincode.setText(pincode);
        }
        else if (SettingActivity.cur_command == 1) {
            topic = s;
            EditText ed_topic = (EditText)findViewById(R.id.editText_topic);
            ed_topic.setText(topic);

        }
    }


}
