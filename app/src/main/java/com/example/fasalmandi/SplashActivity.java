package com.example.fasalmandi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    SharedPreferences user=null;
    SharedPreferences verified=null;
//    SharedPreferences language=null;
//    SharedPreferences userType=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        user= getSharedPreferences("com.example.fasalmandi", MODE_PRIVATE);
        verified = getSharedPreferences("com.example.fasalmandi", MODE_PRIVATE);
//        language = getSharedPreferences("com.example.fasalmandi", MODE_PRIVATE);
//        userType = getSharedPreferences("com.example.fasalmandi", MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                        if(!user.getBoolean("user",false)){
                            startActivity(new Intent(SplashActivity.this, LanguageActivity.class));
                            finish();
                        }else{
                            if(!verified.getBoolean("verified",false)){
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            }else {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            }
                        }
            }
        }, SPLASH_TIME_OUT);
    }
}