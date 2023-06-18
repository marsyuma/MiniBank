package com.bintangmarsyumarakhasunujsleepjs.minibank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.Toast;

import com.bintangmarsyumarakhasunujsleepjs.minibank.model.nasabah;
import com.bintangmarsyumarakhasunujsleepjs.minibank.model.transaksi;
import com.bintangmarsyumarakhasunujsleepjs.minibank.request.BaseApiService;
import com.bintangmarsyumarakhasunujsleepjs.minibank.request.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHistoryActivity extends AppCompatActivity{
    private ArrayAdapter<String> dataTransaksi;
    private ArrayAdapter<transaksi> transaksiAdapter;
    private ArrayList<transaksi> showTransaksi = new ArrayList<>();
    private ListView listView;

    Button backButton;
    Context mContext;
    BaseApiService UserService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_datatransaksi);

        mContext = this;
        UserService = UtilsApi.getAPIService();

        backButton = findViewById(R.id.back);
        listView = findViewById(R.id.listTransaksi);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserHomeActivity.class);
                startActivity(intent);
            }
        });

        getTransactionList();
    }

    private void getTransactionList() {
        Gson gson = new Gson();
        UserService.requestTransaksiById().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(mContext, "Sukses", Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonRESULTS.getJSONArray("getTransaksibyId");
                        if (jsonRESULTS.getString("message").equals("Transaksi Found")){
                            // Jika login berhasil maka data nama yang ada di response API
                            // akan diparsing ke activity selanjutnya.
                            showTransaksi = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<transaksi>>() {}.getType());
                            transaksiAdapter = new ArrayAdapter<transaksi>(getApplicationContext(),
                                    android.R.layout.simple_list_item_1, showTransaksi);
                            listView.setAdapter(transaksiAdapter);

                        } else {
                            // Jika login gagal
                            String error_message = jsonRESULTS.getString("Gagal");
                            Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, "Data Failed to Connect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        });
    }
}

