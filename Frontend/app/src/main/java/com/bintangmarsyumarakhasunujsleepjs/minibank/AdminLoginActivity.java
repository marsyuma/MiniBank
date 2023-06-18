package com.bintangmarsyumarakhasunujsleepjs.minibank;

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
import com.bintangmarsyumarakhasunujsleepjs.minibank.request.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import com.bintangmarsyumarakhasunujsleepjs.minibank.request.BaseApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLoginActivity extends AppCompatActivity{
    Button loginButton, backButton;
    EditText username, userPassword;

    Context Content;
    BaseApiService userService;
    public static String usernamePars;
    public static String userPasswordPars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        userService = UtilsApi.getAPIService();
        Content = this;
        backButton = findViewById(R.id.buttonBackAdmin);
        loginButton = findViewById(R.id.buttonLoginAdmin);
        username = findViewById(R.id.editTextAdmin);
        userPassword = findViewById(R.id.editTextPasswordAdmin);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Content, MainActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() || userPassword.getText().toString().isEmpty()) {
                    Toast.makeText(Content, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    usernamePars = username.getText().toString();
                    userPasswordPars = userPassword.getText().toString();
                    requestLoginAdmin();

                }

            }
        });
    }

    void requestLoginAdmin(){
        userService.requestLoginAdmin(usernamePars, userPasswordPars).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Content, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Content, AdminHomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Content, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Content, "Internet Connection Problem", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}

