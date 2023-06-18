package com.bintangmarsyumarakhasunujsleepjs.minibank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.bintangmarsyumarakhasunujsleepjs.minibank.request.BaseApiService;
import com.bintangmarsyumarakhasunujsleepjs.minibank.request.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAddNasabahActivity extends AppCompatActivity{

    Button backButton, tambahNasabahButton;
    EditText user_id, name, address, phone, balance, job, username, email, password;
    AdminAddNasabahActivity Content;
    BaseApiService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tambahnasabah);
        userService = UtilsApi.getAPIService();

        Content = this;

        backButton = findViewById(R.id.buttonBack);
        tambahNasabahButton = findViewById(R.id.buttonTambahNasabah);
        user_id = findViewById(R.id.editTextUserId);
        name = findViewById(R.id.editTextName);
        address = findViewById(R.id.editTextAddress);
        phone = findViewById(R.id.editTextPhoneNumber);
        balance = findViewById(R.id.editTextBalance);
        job = findViewById(R.id.editTextJob);
        username = findViewById(R.id.editTextUsername);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Content, AdminHomeActivity.class);
                startActivity(intent);
            }
        });

        tambahNasabahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_idText = user_id.getText().toString();
                String nameText = name.getText().toString();
                String addressText = address.getText().toString();
                String phoneText = phone.getText().toString();
                String balanceText = balance.getText().toString();
                String jobText = job.getText().toString();
                String usernameText = username.getText().toString();
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                if (user_idText.isEmpty()) {
                    user_id.setError("User ID is required");
                    user_id.requestFocus();
                    return;
                }

                if (nameText.isEmpty()) {
                    name.setError("Name is required");
                    name.requestFocus();
                    return;
                }

                if (addressText.isEmpty()) {
                    address.setError("Address is required");
                    address.requestFocus();
                    return;
                }

                if (phoneText.isEmpty()) {
                    phone.setError("Phone is required");
                    phone.requestFocus();
                    return;
                }

                if (balanceText.isEmpty()) {
                    balance.setError("Balance is required");
                    balance.requestFocus();
                    return;
                }

                if (jobText.isEmpty()) {
                    job.setError("Job is required");
                    job.requestFocus();
                    return;
                }

                if (usernameText.isEmpty()) {
                    username.setError("Username is required");
                    username.requestFocus();
                    return;
                }

                if (emailText.isEmpty()) {
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }

                if (passwordText.isEmpty()) {
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }

                tambahNasabah(user_idText, nameText, addressText, phoneText, balanceText, jobText, usernameText, emailText, passwordText);
            }
        });
    }

    private void tambahNasabah(String user_id, String name, String address, String phone, String balance, String job, String username, String email, String password) {
        BigInteger user_id_int = BigInteger.valueOf(Integer.parseInt(user_id));
        BigInteger balance_int = BigInteger.valueOf(Integer.parseInt(balance));
        userService.tambahNasabah(user_id_int, name, address, phone, balance_int, job, username, email, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(Content, "Tambah Nasabah Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Content, AdminHomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Content, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}