package com.example.fasalmandi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LanguageActivity extends AppCompatActivity {

    SharedPreferences language=null;
    Button hindiButton,englishButton;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        language = getSharedPreferences("com.example.fasalmandi", MODE_PRIVATE);
        hindiButton=findViewById(R.id.hindiButton);
        englishButton=findViewById(R.id.englishButton);
        hindiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language.edit().putString("language","hi").apply();
                context = LanguageChanger.setLocale(LanguageActivity.this,"hi");
                startActivity(new Intent(LanguageActivity.this,UsertypeActivity.class));
                finish();
            }
        });

        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language.edit().putString("language", "en").apply();
                context = LanguageChanger.setLocale(LanguageActivity.this,"en");
                startActivity(new Intent(LanguageActivity.this,UsertypeActivity.class));
                finish();
            }
        });


    }
}