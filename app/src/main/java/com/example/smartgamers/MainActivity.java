package com.example.smartgamers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartgamers.ServerRequests.SmartCookieLogin;
import com.example.smartgamers.ServerRequests.LoginSmartCookies;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText mUsername, mPassword,mEmail, mPhoneNumber;
    TextView mResponse;
    Button mLogin;
    String myPreferences = "dataFile";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = findViewById(R.id.userName_editText);
        mPassword = findViewById(R.id.password_editText);
        mEmail = findViewById(R.id.email_editText);
        mPhoneNumber = findViewById(R.id.phoneNumber_editText);

        mLogin = findViewById(R.id.login_btn);
        mResponse = findViewById(R.id.response_textView);

        if (checkPastLogin()) {
            Intent intent = new Intent(getApplicationContext(), MainPage.class);
            sharedPreferences = getSharedPreferences(myPreferences, MODE_PRIVATE);
            intent.putExtra("username", sharedPreferences.getString("username", null) );
            startActivity(intent);
            finish();
        }

        mUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                mPhoneNumber.requestFocus();
                return true;
            }
        });

        mPhoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                mEmail.requestFocus();
                return true;
            }
        });

        mEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                mPassword.requestFocus();
                return true;
            }
        });

        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            attemptLogin();
            return  true;
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

    }

    void attemptLogin() {
//        username is actually just a number... this is the member id.
        final String uTemp = mUsername.getText().toString();
        String pTemp = mPassword.getText().toString();
// "User_Name","User_Type":"","User_Pass":"","College_Code":"","country_code":"+91","method":"ANDROID","device_type":"android-phone","device_details":"Moto E (4)","platform_OS":"ANDROID 7.1.1","ip_address":"61.119.161.203","lat":"18.4833353","long":"73.8023457","activity":"","entity_sub_type":""}

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dev.smartcookie.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SmartCookieLogin post = new SmartCookieLogin(uTemp,"MemberId",pTemp);

        LoginSmartCookies loginSmartCookies = retrofit.create(LoginSmartCookies.class);

        Call<SmartCookieLogin> call = loginSmartCookies.getPosts(post);

        call.enqueue(new Callback<SmartCookieLogin>() {
            @Override
            public void onResponse(Call<SmartCookieLogin> call, Response<SmartCookieLogin> response) {
                if (!response.isSuccessful())
                    mResponse.setText(response.message() + "--" + response.code());
                else  {
                    SmartCookieLogin responsePost = response.body();
                    if (responsePost.getResponseStatus()!= 200) {
                        Toast.makeText(getApplicationContext(), "Wrong Id or Password", Toast.LENGTH_SHORT).show();
                        mUsername.setText("");
                        mPassword.setText("");
                    }
                    else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username",uTemp);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(),MainPage.class);
                intent.putExtra("username", uTemp);

                startActivity(intent);
                finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<SmartCookieLogin> call, Throwable t) {
                mResponse.setText(t.getMessage());
            }
        });
    }

    private boolean checkPastLogin() {
        sharedPreferences = getSharedPreferences(myPreferences, MODE_PRIVATE);
        if (sharedPreferences.contains("username")){
            return !(sharedPreferences.getString("username", null) == null);
        }
        else
            return false;

    }

    @Override
    protected void onPostResume() {

        if (checkPastLogin()) {
            Intent intent = new Intent(getApplicationContext(), MainPage.class);
            startActivity(intent);
            finish();
        }
        else
            super.onPostResume();
    }
}
