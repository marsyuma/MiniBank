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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AppCompatActivity {
    Button loginButton, backButton;
    EditText username, userPassword;

    Context Content;
    BaseApiService userService;
    public static String usernamePars;
    public static String userPasswordPars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Content = this;
        setContentView(R.layout.activity_user_login);

        userService = UtilsApi.getAPIService();
        backButton = findViewById(R.id.buttonBack);
        loginButton = findViewById(R.id.buttonLoginUser);
        username = findViewById(R.id.editTextUsername);
        userPassword = findViewById(R.id.editTextPassword);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectRequest();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty() || userPassword.getText().toString().isEmpty()) {
                    Toast.makeText(Content, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }else {
                    usernamePars = username.getText().toString();
                    userPasswordPars = userPassword.getText().toString();
                    requestLogin();
                }
            }
        });
    }

    void requestLogin() {
        userService.requestLogin(usernamePars, userPasswordPars).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("status").equals("true")) {
                            Toast.makeText(Content, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Content, UserHomeActivity.class);
                            startActivity(intent);
                        } else {
                            String error_message = jsonRESULTS.getString("status");
                            Toast.makeText(Content, error_message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Content, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Content, "Internet Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void connectRequest() {
        userService.testConnection().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Content, "Backend Connection Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Content, "Backend Connection Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Content, "Internet Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

