package com.example.lab2_mob403.bai2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask_POST extends AsyncTask<Void, Void, Void> {
    private TextView tvResult;
    private String width;
    private String length;
    private String server_response, link;
    private ProgressDialog progressDialog;
    private Context context;
    private String TAG = "BackgroundTask_POST";
    private URL url;
    private HttpURLConnection urlConnectionBai2;


    // for bai2
    public BackgroundTask_POST(Context context, String link, String width, String length, TextView tvResult) {
        this.tvResult = tvResult;
        this.width = width;
        this.length = length;
        this.link = link;
        this.context = context;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // for bai2
            url = new URL(link);
            String params = "width=" + URLEncoder.encode(width, "utf-8") + "&length=" + URLEncoder.encode(length, "utf-8");

            Log.i(TAG, "Link reslut bai2: " + url);
            //for bai2
            urlConnectionBai2 = (HttpURLConnection) url.openConnection();
            urlConnectionBai2.setDoInput(true);
            urlConnectionBai2.setRequestMethod("POST");
            urlConnectionBai2.setFixedLengthStreamingMode(params.getBytes().length);

            PrintWriter printWriter = new PrintWriter(urlConnectionBai2.getOutputStream());
            printWriter.print(params);
            printWriter.close();
            String line = "";
            BufferedReader bfr = new BufferedReader(new InputStreamReader(urlConnectionBai2.getInputStream()));
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
            if (urlConnectionBai2 != null) {
                urlConnectionBai2.disconnect();
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
