package edu.avans.dionb.cnc_app.Activities.Other;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dionb on 5-11-2017.
 */

public class VoerQueryUit extends AsyncTask<String, Void, String> {

    WebTaskListener listener;

    public VoerQueryUit(WebTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String answer = "";
        try {
            Log.i("URL", params[0]);
            //Log.i("Object", params[1]);

            URL url = new URL(params[0]); //Enter URL here
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("GET"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
            httpURLConnection.setRequestProperty("Content-Type", "text");
            httpURLConnection.connect();
//            DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
//            output.writeBytes(params[1]);
            DataInputStream input = new DataInputStream(httpURLConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            answer = reader.readLine().toString();
            String line;
            while ((line = reader.readLine()) != null) {
                answer += line;
            }
            input.close();
//            output.close();
        } catch (MalformedURLException e) {
            listener.TaskError(e.toString());
            //e.printStackTrace();
        } catch (IOException e) {
            listener.TaskError(e.toString());
            //e.printStackTrace();
        }
        return answer;
    }


    @Override
    protected void onPostExecute(String message) {
        Log.i("Message", "gelukt");
        listener.TaskDone(message);
    }
}
