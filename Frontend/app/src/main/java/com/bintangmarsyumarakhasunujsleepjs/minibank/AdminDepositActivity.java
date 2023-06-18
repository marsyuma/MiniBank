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

public class AdminDepositActivity extends AppCompatActivity{
    Button depositButton, backButton;
    EditText amount, recipient;

    Context Content;
    BaseApiService userService;
    public static BigInteger Amount;
    public static BigInteger RecipientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Content = this;
        setContentView(R.layout.activity_admin_deposit);
        userService = UtilsApi.getAPIService();
        backButton = findViewById(R.id.buttonBack);
        depositButton = findViewById(R.id.ButtonSubmitDeposit);
        amount = findViewById(R.id.editTextAmount);
        recipient = findViewById(R.id.editTextTextRecipientNumber);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Content, AdminHomeActivity.class);
                startActivity(intent);
            }
        });

        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipientID = BigInteger.valueOf(Integer.parseInt(recipient.getText().toString()));
                Amount = BigInteger.valueOf(Integer.parseInt(amount.getText().toString()));
                deposit();
            }
        });
    }

    public void deposit(){
        userService.requestDeposit(RecipientID, Amount).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AdminDepositActivity.this, "Deposit Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Content, AdminHomeActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
