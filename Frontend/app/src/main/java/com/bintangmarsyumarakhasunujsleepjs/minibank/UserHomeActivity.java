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
import java.net.CookieHandler;

import okhttp3.Cookie;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserHomeActivity extends AppCompatActivity {
    Button transferButton, withdrawButton;

    UserHomeActivity Content;
    BaseApiService userService;
//    public static String usernamePars;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Content = this;
        setContentView(R.layout.activity_user_home);

        userService = UtilsApi.getAPIService();
        transferButton = findViewById(R.id.buttonTransfer);
        withdrawButton = findViewById(R.id.buttonWithdraw);

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Content, UserTransferActivity.class);
                startActivity(intent);
            }
        });

        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Content, UserWIthdrawActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            case R.id.history:
                Intent intent = new Intent(Content, UserHistoryActivity.class);
                startActivity(intent);
                return true;
            case R.id.profile:
                Intent intent2 = new Intent(Content, UserProfileActivity.class);
                startActivity(intent2);
                return true;

                default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        userService.requestLogout().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(Content, "Logout Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Content, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Content, "Logout Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Content, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
