package com.haunh.androidstudio.lab1_mob403.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

public class AsyncTaskRunner extends AsyncTask<String, String, String> {
    private String res;
    ProgressDialog dialog;
    TextView tvResult;
    EditText edtTime;
    Context context;

    public AsyncTaskRunner(Context context, TextView tvResult, EditText edtTime){
        this.context = context;
        this.tvResult = tvResult;
        this.edtTime = edtTime;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Loading..", "Wait for "
                + edtTime.getText().toString() + " seconds");
    }

    // Show lên các ProgressDi
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(dialog.isShowing()){
            dialog.dismiss();
        }
        tvResult.setText(s);
    }

    // Doing something in there!
    @Override
    protected String doInBackground(String... strings) {
        publishProgress("Sleeping...");
        try {
            int time = Integer.parseInt(strings[0]) * 1000;
            Thread.sleep(time);
            res = "Sleep for " + strings[0] + " seconds";
        }catch (Exception e){
            e.printStackTrace();
            res = e.getMessage();
        }
        return res;
    }
}
