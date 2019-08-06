package com.example.smartgamers.ServerRequests;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ConnectToServer extends AsyncTask<String, Void, String> {

    private String url = "https://www.cse.iitb.ac.in/~yashap/intern/ii2.php";

    public ConnectToServer(String url) {
        this.url = url;
    }
    public ConnectToServer() { }


    //   strings[0] is the number of parameters passed, and then follows (key, value).
    @Override
    protected String doInBackground(String... strings) {
        String ans = "";

        try {
            URL url_login = new URL(url);
            Map<String, String> params = new HashMap();
            int k = Integer.parseInt(strings[0]);
            for (int i = 1; i < k; i += 2)
                params.put(strings[i], strings[i + 1]);

            StringBuilder postData = makePostable(params);

            HttpURLConnection urlConnection = (HttpURLConnection) url_login.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);

            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(postData.toString());
            wr.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            ans = sb.toString();
//            Log.d("connecttoserverinBack",ans);

            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Log.d("connectotserverAns",ans);
        return ans;
    }

    private StringBuilder makePostable(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (postData.length() != 0)
                postData.append("&");
            postData.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return postData;
    }

    //            #TODO: Maybe I will make 3 different functions connect(), disconnect() and post() if it is possible...

}
