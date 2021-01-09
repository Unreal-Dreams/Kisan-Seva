package com.example.fasalmandi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CropDetilActivity extends AppCompatActivity {
    Spinner spinnerCategory,spinnerWeight;
    EditText typeEdit,weightEdit;
    CheckBox editBox;
    MultiAutoCompleteTextView editAddress ;
    Button submitButton;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    String name,image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_detil);
        spinnerCategory= findViewById(R.id.spinnerCategory);
        spinnerWeight= findViewById(R.id.spinnerWeight);
        typeEdit= findViewById(R.id.typeEdit);
        weightEdit= findViewById(R.id.weightEdit);
        editAddress= findViewById(R.id.editAddress);
        submitButton=findViewById(R.id.submitButton);
        editBox = findViewById(R.id.editBox);
        db = FirebaseFirestore.getInstance();
        name= getIntent().getStringExtra("name");
        image =getIntent().getStringExtra("image");
        editAddress.setEnabled(false);
        loadingDataSpinner();
        editBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editBox.isChecked()){
                    editAddress.setEnabled(true);
                }else{
                    editAddress.setEnabled(false);

                }
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });


    }

    private void loadingDataSpinner(){

        //configurate spinner de state
        String[] weight = getResources().getStringArray(R.array.weight);
        ArrayAdapter<String> adapterweight = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, weight);
        adapterweight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeight.setAdapter(adapterweight);

        //configurate spinner de category
        String[] category = getResources().getStringArray(R.array.category);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,
                category
        );
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategory);

    }

    private void submitData(){
        String type = spinnerCategory.getSelectedItem().toString();
        if(type.equals("Others")){
            spinnerCategory.setVisibility(View.GONE);
            typeEdit.setVisibility(View.VISIBLE);
            if(null !=typeEdit.getText().toString() && !typeEdit.getText().toString().isEmpty() ){
                type = typeEdit.getText().toString();
            }else{
                Toast.makeText(this, R.string.typechecker, Toast.LENGTH_SHORT).show();
            }
        }
        String weightunit= spinnerWeight.getSelectedItem().toString();
        String weight = weightEdit.getText().toString();
        String address = editAddress.getText().toString();
        final String queryKey = UUID.randomUUID().toString().substring(0,16);
        documentReference= db.collection("farmerOrders").document(queryKey);
        if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(weightunit)&& !TextUtils.isEmpty(weight) && !TextUtils.isEmpty(address) && !type.equals("Others")){
            final Map<String , Object> postmap =new HashMap<>();
            postmap.put("Farmerid", FirebaseAuth.getInstance().getCurrentUser().getUid());
            postmap.put("cropName", name);
            postmap.put("cropType",type);
            postmap.put("cropWeight", weight+" "+weightunit);
            postmap.put("linkImage", image);
            postmap.put("time",Timestamp.now());
            documentReference.set(postmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), R.string.queries_post, Toast.LENGTH_SHORT).show();
                    finish();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), R.string.internet_error, Toast.LENGTH_SHORT).show();
                }
            });


        }else{
            Toast.makeText(this, R.string.formchecker, Toast.LENGTH_SHORT).show();
        }
    }
}