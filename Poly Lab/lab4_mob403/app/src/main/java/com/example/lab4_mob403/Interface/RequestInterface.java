package com.example.lab4_mob403.Interface;

import com.example.lab4_mob403.Model.ServerRequest;
import com.example.lab4_mob403.Model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {
    @POST("learn-login-register/")
    Call<ServerResponse> operation(@Body ServerRequest request);
}
