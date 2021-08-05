package com.example.lab3_mob403.bai4;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/json_data.json")
    Call<ContactList> getMyJSON();
}
