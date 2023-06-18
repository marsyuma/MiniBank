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
import android.widget.EditText;
import android.view.View;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bintangmarsyumarakhasunujsleepjs.minibank.model.nasabah;
import com.bintangmarsyumarakhasunujsleepjs.minibank.request.BaseApiService;
import com.bintangmarsyumarakhasunujsleepjs.minibank.request.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bintangmarsyumarakhasunujsleepjs.minibank.request.BaseApiService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminDataNasabahActivity extends AppCompatActivity {
    private ArrayAdapter<String> akun;
    private ArrayAdapter<nasabah> nasabahAdapter;
    private ArrayList<nasabah> showNasabah = new ArrayList<>();
    private ListView listView;

    Button backButton;
    Context mContext;
    BaseApiService UserService;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_datanasabah);

        mContext = this;
        UserService = UtilsApi.getAPIService();

        backButton = findViewById(R.id.back);
        listView = findViewById(R.id.listNasabah);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AdminHomeActivity.class);
                startActivity(intent);
            }
        });

        getList();
    }

    public void getList() {
        Gson gson = new Gson();
        UserService.requestNasabah()
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(mContext, "Sukses", Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                JSONArray jsonArray = jsonRESULTS.getJSONArray("getDataNasabah");
                                if (jsonRESULTS.getString("message").equals("Nasabah Found")){
                                    // Jika login berhasil maka data nama yang ada di response API
                                    // akan diparsing ke activity selanjutnya.
                                    showNasabah = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<nasabah>>() {}.getType());
                                    nasabahAdapter = new ArrayAdapter<nasabah>(getApplicationContext(),
                                            android.R.layout.simple_list_item_1, showNasabah);
                                    listView.setAdapter(nasabahAdapter);

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
