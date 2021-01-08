package com.example.fasalmandi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VerificationActivity extends AppCompatActivity {
    private HomeFragment homeFrag;
    Button accountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        homeFrag=new HomeFragment();
        setFragment(homeFrag);
        accountButton=findViewById(R.id.accountcheckButton);

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerificationActivity.this, ProfileActivity.class));
            }
        });

    }
    //Meathod for setting the fragment in this layout
    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,fragment);
        fragmentTransaction.commit();
    }
}