package com.example.lab3_mob403.bai3;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET("android/jsonandroid")
    Call<JSONResponse> getJSON();
}
