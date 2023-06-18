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
    @POST("admin/login")
    Call<ResponseBody> requestLoginAdmin(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("transaction/transfer")
    Call<ResponseBody> requestTransfer(@Field("amount") BigInteger amount, @Field("recipient_id") int recipient_id);

    @FormUrlEncoded
    @POST("admin/tambah_nasabah")
    Call<ResponseBody> tambahNasabah(@Field("user_id") BigInteger user_id, @Field("name") String name, @Field("address") String address, @Field("phonenumber") String phonenumber, @Field("balance") BigInteger balance, @Field("job") String job, @Field("username") String username, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("transaction/withdraw")
    Call<ResponseBody> requestWithdraw(@Field("amount") BigInteger amount);

    @POST ("logout")
    Call<ResponseBody> requestLogout();

    @FormUrlEncoded
    @POST("admin/transaction/deposit")
    Call<ResponseBody> requestDeposit(@Field("user_id") BigInteger user_id, @Field("amount") BigInteger amount);

    @GET ("admin/data_nasabah")
    Call<ResponseBody> requestNasabah();

    @GET("admin/transaksi")
    Call<ResponseBody> requestTransaksi();

    @GET("user/history")
    Call<ResponseBody> requestTransaksiById();
}
