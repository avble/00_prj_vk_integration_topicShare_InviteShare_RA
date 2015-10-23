package remote.service.verik.com.remoteaccess;

/**
 * Created by huyle on 10/22/15.
 */

import android.os.AsyncTask;
import android.text.Editable;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


interface  httpWrapperInterface {
    public void onOutputPostExecute(String s);

}
/**
 * Implementation of AsyncTask, to fetch the data in the background away from
 * the UI thread.
 */

 class HttpWrapper extends AsyncTask<String, Void, String> {

    public httpWrapperInterface listener;
    public Editable topic;


    @Override
    protected String doInBackground(String... urls) {
        try {
            return httpRequest(urls[0]);
        } catch (IOException e) {
            String tmp  = new String("Connection to ");
            tmp += urls[0];
            tmp += " Error";

            return tmp;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if (listener != null)
            listener.onOutputPostExecute(s);
    }


    public void setOutputListener(httpWrapperInterface in)
    {
        listener = in;

    }

    /** Initiates the fetch operation. */
    private String httpRequest(String urlString) throws IOException {
        InputStream stream = null;
        String str ="";

        try {
            // MEFIX:
            if (urlString.contains("generateInvite"))
                stream = GenerateInviteS(urlString);
            else if(urlString.contains("getTopic")) {
                stream = GetTopicS(urlString);
            }
            str = readIt(stream, 500);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return str;
    }


    private InputStream GetTopicS(String urlString) throws IOException {

        HttpsURLConnection con;
        URL url = new URL(urlString);
        con = (HttpsURLConnection) url.openConnection();

        con.setReadTimeout(10000 /* milliseconds */);
        con.setConnectTimeout(15000 /* milliseconds */);
        con.setRequestMethod("GET");

        // FIXME: Add authorization field
        con.setRequestProperty("Authorization", "5a105e8b9d40e1329780d62ea2265d8a");



        // Create an HostnameVerifier that hardwires the expected hostname.
// Note that is different than the URL's hostname:
// example.com versus example.org
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                HostnameVerifier hv =
                        HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify("veriksystems.com", session);
            }
        };


        con.setHostnameVerifier(hostnameVerifier);
        con.connect();

        //int responseCode = conns.getResponseCode();
        //System.out.println("POST Response Code :: " + responseCode);

        InputStream stream = con.getInputStream();
        return stream;
        // END_INCLUDE(get_inputstream)
    }



    private InputStream GenerateInviteS(String urlString) throws IOException {

        // BEGIN_INCLUDE(get_inputstream)
        HttpsURLConnection conn;
        URL url = new URL(urlString);
        conn = (HttpsURLConnection) url.openConnection();

        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("POST");


        // Create an HostnameVerifier that hardwires the expected hostname.
// Note that is different than the URL's hostname:
// example.com versus example.org
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                HostnameVerifier hv =
                        HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify("veriksystems.com", session);
            }
        };

        conn.setHostnameVerifier(hostnameVerifier);


        String topic_xml = "<?xml version=\"1.0\"?>" +
                "<inviteGenerate>" +
                "<topic>" + topic + "</topic>" +
                "</inviteGenerate>";

        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        conn.setRequestProperty("Content-Length", "" +
                Integer.toString(topic_xml.getBytes().length));
        conn.setRequestProperty("Content-Language", "en-US");

        // FIXME: Add authorization field
        conn.setRequestProperty("Authorization", "5a105e8b9d40e1329780d62ea2265d8a");



        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        //conns.connect();

        //Send request
        DataOutputStream wr = new DataOutputStream (
                conn.getOutputStream ());
        wr.writeBytes (topic_xml);
        wr.flush ();
        wr.close ();


        // Start the query

        InputStream stream = conn.getInputStream();
        return stream;
        // END_INCLUDE(get_inputstream)
    }


    /** Reads an InputStream and converts it to a String.
     * @param stream InputStream containing HTML from targeted site.
     * @param len Length of string that this method returns.
     * @return String concatenated according to len parameter.
     * @throws java.io.IOException
     * @throws java.io.UnsupportedEncodingException
     */
    private String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        int rc = reader.read(buffer);
        return new String(buffer, 0, rc);
    }


    public static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {


            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                //return null;
                return new java.security.cert.X509Certificate[0];
            }
        }};

        try {
            SSLContext sc = SSLContext.getInstance("TLS");

            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            //conns.setSSLSocketFactory(sc.getSocketFactory());

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }




}
