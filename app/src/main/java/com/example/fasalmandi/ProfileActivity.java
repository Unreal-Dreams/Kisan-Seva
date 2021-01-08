package com.example.fasalmandi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    String userId;

    FirebaseFirestore db;
    TextView nameTextView, mobileTextView, kccIdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameTextView=findViewById(R.id.nameTextView);
        mobileTextView=findViewById(R.id.mobileTextView);
        kccIdTextView=findViewById(R.id.kccIDTextView);

        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        db=FirebaseFirestore.getInstance();
        db.collection("farmer").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                nameTextView.setText(documentSnapshot.getString("name"));
                mobileTextView.setText(documentSnapshot.getString("mobileNo"));
                kccIdTextView.setText(documentSnapshot.getString("kccID"));
            }
        });
    }
}