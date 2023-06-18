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

public class UserTransferActivity extends AppCompatActivity{
    Button transferButton, backButton;
    EditText recipient, amount;

    Context Content;
    BaseApiService userService;
    public static int RecipientID;
    public static int Amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Content = this;
        setContentView(R.layout.activity_user_transfer);
        userService = UtilsApi.getAPIService();
        backButton = findViewById(R.id.buttonBack);
        transferButton = findViewById(R.id.ButtonSubmitTransfer);
        recipient = findViewById(R.id.editTextTextRecipientNumber);
        amount = findViewById(R.id.editTextAmount);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Content, UserHomeActivity.class);
                startActivity(intent);
            }
        });

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = UserLoginActivity.usernamePars;
                String userPassword = UserLoginActivity.userPasswordPars;
                String recipient = UserTransferActivity.this.recipient.getText().toString();
                String amount = UserTransferActivity.this.amount.getText().toString();
                if (recipient.isEmpty()) {
                    UserTransferActivity.this.recipient.setError("Recipient is required");
                    UserTransferActivity.this.recipient.requestFocus();
                    return;
                }
                if (amount.isEmpty()) {
                    UserTransferActivity.this.amount.setError("Amount is required");
                    UserTransferActivity.this.amount.requestFocus();
                    return;
                }
                if (Integer.parseInt(amount) < 10000) {
                    UserTransferActivity.this.amount.setError("Minimum amount is 10000");
                    UserTransferActivity.this.amount.requestFocus();
                    return;
                }

//                if (Integer.parseInt(amount) > UserHomeActivity.balance) {
//                    UserTransferActivity.this.userPassword.setError("Insufficient balance");
//                    UserTransferActivity.this.userPassword.requestFocus();
//                    return;
//                }
                transfer(recipient, amount);
            }
        });
    }

    private void transfer(String recipient, String amount) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Transferring...");
        progressDialog.show();
        BigInteger amountInt = new BigInteger(amount);
        int recipientInt = Integer.parseInt(recipient);
        userService.requestTransfer(amountInt, recipientInt).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.hide();
                if (response.isSuccessful()) {
                    Toast.makeText(UserTransferActivity.this, "Transfer Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Content, UserHomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Content, "Transfer failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.hide();
                Toast.makeText(Content, "Transfer failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
