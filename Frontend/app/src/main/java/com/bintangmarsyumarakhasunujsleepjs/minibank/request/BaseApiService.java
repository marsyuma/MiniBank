package com.bintangmarsyumarakhasunujsleepjs.minibank.request;


import java.math.BigInteger;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface BaseApiService {
    /**
     * Login request call.
     *
     * @param username the username
     * @param password the password
     * @return the call
     */
    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseBody> requestLogin(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @GET ("/hello")
    Call<ResponseBody> testConnection();
}
