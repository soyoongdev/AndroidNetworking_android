package com.haunh.androidstudio.lab1_mob403.views;

import android.graphics.Bitmap;

public interface Listener {
    void onImageLoaded(Bitmap bitmap);
    void onError();
}
