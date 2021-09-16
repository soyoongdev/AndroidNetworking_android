package com.haunh.androidstudio.lab1_mob403.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class loadImageWithAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private Listener mListener;
    private ProgressDialog progressDialog;

    public loadImageWithAsyncTask(lession3 listener, Context context) {
        this.mListener = listener;
        progressDialog = new ProgressDialog(context);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("Downloading image.. ");
        progressDialog.show();

    }

    // Đẩy lên giao
    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (result != null){
            mListener.onImageLoaded(result);
        }
        else {
            mListener.onError();
        }
    }

    // Xử
    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            return BitmapFactory.decodeStream((InputStream) new URL(strings[0]).getContent());
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
