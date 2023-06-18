package com.bintangmarsyumarakhasunujsleepjs.minibank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bintangmarsyumarakhasunujsleepjs.minibank.request.BaseApiService;
import com.bintangmarsyumarakhasunujsleepjs.minibank.request.RetrofitClient;
import com.bintangmarsyumarakhasunujsleepjs.minibank.request.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserWIthdrawActivity extends AppCompatActivity {
    UserWIthdrawActivity Content;
    BaseApiService userService;

    Button withdrawButton, backButton;
    EditText amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Content = this;
        setContentView(R.layout.activity_user_withdraw);

        userService = UtilsApi.getAPIService();
        backButton = findViewById(R.id.buttonBack);
        withdrawButton = findViewById(R.id.buttonWithdraw);
        amount = findViewById(R.id.editTextAmount);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Content, UserHomeActivity.class);
                startActivity(intent);
            }
        });

        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Amount = amount.getText().toString();
                if (Amount.isEmpty()) {
                    Toast.makeText(Content, "Please fill the amount", Toast.LENGTH_SHORT).show();
                } else {
                    withdraw();
                }
            }
        });



    }

    private void withdraw() {
        final ProgressDialog progressDialog = new ProgressDialog(Content);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        BigInteger Amount = new BigInteger(amount.getText().toString());
        userService.requestWithdraw(Amount).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserWIthdrawActivity.this, "Withdraw Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Content, UserHomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Content, "Transfer failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.hide();
                Toast.makeText(Content, "Connection error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
