package com.example.lab2_mob403.bai3;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask_POST2 extends AsyncTask<Void, Void, Void> {
    private TextView tvResult;
    private String canh;
    private String server_response, link;
    private ProgressDialog progressDialog;
    private Context context;
    private String TAG = "BackgroundTask_POST";
    private URL url;
    private HttpURLConnection urlConnection;

    public BackgroundTask_POST2(Context context, String link, String canh, TextView tvResult) {
        this.tvResult = tvResult;
        this.canh = canh;
        this.link = link;
        this.context = context;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // for bai3
            url = new URL(link);
            String params = "canh=" + URLEncoder.encode(canh, "utf-8");

            Log.i(TAG, "Link reslut bai3: " + url);
            //for bai2
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setFixedLengthStreamingMode(params.getBytes().length);

            PrintWriter printWriter = new PrintWriter(urlConnection.getOutputStream());
            printWriter.print(params);
            printWriter.close();
            String line = "";
            BufferedReader bfr = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuffer sb = new StringBuffer();

            while ((line = bfr.readLine()) != null){
                sb.append(line);
            }
            server_response = sb.toString();
            bfr.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception error: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Calculating..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        tvResult.setText(server_response);
    }
}
