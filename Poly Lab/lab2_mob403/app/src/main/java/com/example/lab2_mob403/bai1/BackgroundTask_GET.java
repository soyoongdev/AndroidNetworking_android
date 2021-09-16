package com.example.lab2_mob403.bai1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BackgroundTask_GET extends AsyncTask<String, Void, String> {
    private final TextView tvResult;
    private final String txtName;
    private final String txtScore;
    private String server_response, link;
    private ProgressDialog progressDialog;
    private final Context context;
    private final String TAG = "BackgroundTask_GET";
    private HttpURLConnection urlConnection = null;
    private URL url;

    public BackgroundTask_GET(Context context, String link, String txtName, String txtScore, TextView tvResult) {
        this.tvResult = tvResult;
        this.txtName = txtName;
        this.txtScore = txtScore;
        this.context = context;
        this.link = link;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Sending..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        Log.d(TAG, "onPostExecute: " + server_response);
        tvResult.setText(server_response);
    }


    @Override
    protected String doInBackground(String... strings) {
        link += "?name=" + this.txtName + "&score=" + this.txtScore;
        Log.i(TAG, "Link reslut: " + link);
        try {
            url = new URL(link);

            urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
            server_response = sb.toString();
            Log.i(TAG, "server_response: " + server_response);
            urlConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "IOException: " + e.getMessage());
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

}
