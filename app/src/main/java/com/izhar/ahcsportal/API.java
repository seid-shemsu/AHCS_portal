package com.izhar.ahcsportal;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {
    String host_ip = "http://192.168.137.35:8000/api/";

    @Multipart
    @POST("check/")
    Call<RequestBody> login(
            @Part("username") RequestBody username,
            @Part("password") RequestBody password);
}
