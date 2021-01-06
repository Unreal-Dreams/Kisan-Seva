package com.example.fasalmandi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LanguageActivity extends AppCompatActivity {

    SharedPreferences language=null;
    Button hindiButton,englishButton;


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
                language.edit().putString("language","hindi").apply();
                startActivity(new Intent(LanguageActivity.this,UsertypeActivity.class));
                finish();
            }
        });

        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language.edit().putString("language", "english").apply();
                startActivity(new Intent(LanguageActivity.this,UsertypeActivity.class));
                finish();
            }
        });


    }
}