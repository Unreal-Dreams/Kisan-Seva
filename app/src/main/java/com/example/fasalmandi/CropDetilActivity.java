package com.example.fasalmandi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

public class CropDetilActivity extends AppCompatActivity {
    Spinner spinnerCategory,spinnerWeight;
    EditText typeEdit,weightEdit;
    CheckBox editBox;
    MultiAutoCompleteTextView editAddress ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_detil);
        spinnerCategory= findViewById(R.id.spinnerCategory);
        spinnerWeight= findViewById(R.id.spinnerWeight);
        typeEdit= findViewById(R.id.typeEdit);
        weightEdit= findViewById(R.id.weightEdit);
        editAddress= findViewById(R.id.editAddress);
        editBox = findViewById(R.id.editBox);
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
//        String type= spinnerCategory.getSelectedItem().toString();
//        if(type.equals("Others")){
//            spinnerCategory.setVisibility(View.GONE);
//            typeEdit.setVisibility(View.VISIBLE);
//        }

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
}