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
    SharedPreferences verified=null;
    SharedPreferences user=null;
    Context context;
    Resources resources;
    List<AuthUI.IdpConfig> providers;
    private static final int MY_REQUEST_CODE = 1781;
    public FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    ProgressDialog progressDialog;
    private String firebaseUserId;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertype);
        language = getSharedPreferences("com.example.fasalmandi", MODE_PRIVATE);
        userType = getSharedPreferences("com.example.fasalmandi", MODE_PRIVATE);
        verified = getSharedPreferences("com.example.fasalmandi", MODE_PRIVATE);
        user= getSharedPreferences("com.example.fasalmandi", MODE_PRIVATE);

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
//               signIn();
                startActivity(new Intent(UsertypeActivity.this, FarmerLoginActivity.class));
                finish();
            }
        });

        companyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType.edit().putString("userType","company").apply();
//                signIn();
            }
        });
    }

    public void signIn() {
        //Init providers
        providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        showSignInOptions();
    }

    private void showSignInOptions() {
        AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                .Builder(R.layout.loggin)
                .setPhoneButtonId(R.id.phone_button)
                // ...
                //.setTosAndPrivacyPolicyId(R.id.privacyTextView7)
                .build();

        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAuthMethodPickerLayout(customLayout)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                        .setTheme(R.style.MyTheme)
                        .build(),MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                //Get user
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    progressDialog = new ProgressDialog(UsertypeActivity.this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setTitle("Getting user data..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    user.edit().putBoolean("user",true).apply();

                    firebaseUserId = firebaseUser.getUid();
                    String tempUserType = userType.getString("userType", null);
                    if (tempUserType != null) {
                        db.collection(tempUserType).document(firebaseUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    if (documentSnapshot.getBoolean("verified")) {
                                        verified.edit().putBoolean("verified", true).commit();
                                        progressDialog.dismiss();
                                        startActivity(new Intent(UsertypeActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        verified.edit().putBoolean("verified", false).apply();
                                        progressDialog.dismiss();
                                        startActivity(new Intent(UsertypeActivity.this, VerificationActivity.class));
                                        finish();
                                    }
                                } else {
                                    Map<String, Object> userInfo = new HashMap<>();
                                    userInfo.put("verified", false);
                                    userInfo.put("mobileNo", firebaseUser.getPhoneNumber());
                                    db.collection(tempUserType).document(firebaseUserId).set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            progressDialog.dismiss();
                                            startActivity(new Intent(UsertypeActivity.this, VerificationActivity.class));
                                            finish();
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(UsertypeActivity.this, "Try again..", Toast.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(UsertypeActivity.this, "Try again..", Toast.LENGTH_LONG).show();
                }
            }else {
                progressDialog.dismiss();
                Toast.makeText(UsertypeActivity.this, "Try again..", Toast.LENGTH_LONG).show();
            }
        }else{
            progressDialog.dismiss();
            Toast.makeText(UsertypeActivity.this, "Try again..", Toast.LENGTH_LONG).show();
        }
    }


}