package com.example.fasalmandi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    String userId;

    FirebaseFirestore db;
    TextView nameTextView, mobileTextView, kccIdTextView;




    public ProfileFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        nameTextView = v.findViewById(R.id.nameTextView);
        mobileTextView = v.findViewById(R.id.mobileTextView);
        kccIdTextView = v.findViewById(R.id.kccIDTextView);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        db.collection("farmer").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                nameTextView.setText(documentSnapshot.getString("name"));
                mobileTextView.setText(documentSnapshot.getString("mobileNo"));
                kccIdTextView.setText(documentSnapshot.getString("kccID"));
            }
        });
        return v;
    }
}
