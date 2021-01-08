package com.example.fasalmandi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsertypeActivity extends AppCompatActivity {

    Button farmerButton;
    Button companyButton;
    SharedPreferences language=null;
    SharedPreferences userType=null;
    Context context;
    Resources resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertype);
        language = getSharedPreferences("com.example.fasalmandi", MODE_PRIVATE);
        userType = getSharedPreferences("com.example.fasalmandi", MODE_PRIVATE);

        farmerButton=findViewById(R.id.farmerButton);
        companyButton=findViewById(R.id.companyButton);

        if(language.getString("language","english").equals("hindi")) {
            context = LanguageChanger.setLocale(UsertypeActivity.this, "hi");
            resources = context.getResources();
            farmerButton.setText(resources.getString(R.string.farmer_button));
            companyButton.setText(resources.getString(R.string.company_button));
        }

        farmerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType.edit().putString("userType","farmer").apply();
                startActivity(new Intent(UsertypeActivity.this, FarmerLoginActivity.class));
                finish();
            }
        });

        companyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType.edit().putString("userType","company").apply();

            }
        });
    }

}