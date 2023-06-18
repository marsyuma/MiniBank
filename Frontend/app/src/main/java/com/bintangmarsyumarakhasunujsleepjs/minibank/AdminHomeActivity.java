package com.bintangmarsyumarakhasunujsleepjs.minibank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bintangmarsyumarakhasunujsleepjs.minibank.request.BaseApiService;
import com.bintangmarsyumarakhasunujsleepjs.minibank.request.UtilsApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHomeActivity extends AppCompatActivity {
    Button tambahNasabah, deposit, transaksi, nasabah;
    AdminHomeActivity Content;
    BaseApiService userService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Content = this;
        setContentView(R.layout.activity_admin_home);

        userService = UtilsApi.getAPIService();

        tambahNasabah = findViewById(R.id.buttonTambahNasabah);
        deposit = findViewById(R.id.buttonDepositNasabah);
        transaksi = findViewById(R.id.buttonDataTransaksi);
        nasabah = findViewById(R.id.buttonDataNasabah);

        tambahNasabah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Content, AdminAddNasabahActivity.class);
                startActivity(intent);
            }
        });

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Content, AdminDepositActivity.class);
                startActivity(intent);
            }
        });

        nasabah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Content, AdminDataNasabahActivity.class);
                startActivity(intent);
            }
        });

        transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Content, AdminDataTransaksiActivity.class);
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
