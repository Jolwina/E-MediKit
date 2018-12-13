package com.mca.alisha.e_medicare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    DatabaseReference databaseDoctors;

    Button submit;
    EditText name,pNo,eId,docType,clinicName;
    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        mToolbar=findViewById(R.id.updateAppBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Doctor Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseDoctors = FirebaseDatabase.getInstance().getReference("Doctors");

        name=findViewById(R.id.name);
        eId=findViewById(R.id.emailId);
        pNo=findViewById(R.id.phno);
        docType=findViewById(R.id.type);
        clinicName=findViewById(R.id.clinicname);
        submit=findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDoctors();

            }
        });

    }

    public void AddDoctors() {

        String Doc_Name = name.getText().toString().trim();
        String Doc_email = eId.getText().toString().trim();
        String Doc_phone = pNo.getText().toString().trim();
        String Doc_Type = docType.getText().toString().trim();
        String Doc_clinic = clinicName.getText().toString().trim();


        if ((!TextUtils.isEmpty(Doc_Name)) && (!TextUtils.isEmpty(Doc_email)) && (!TextUtils.isEmpty(Doc_phone)) && (!TextUtils.isEmpty(Doc_Type)) && (!TextUtils.isEmpty(Doc_clinic))) {
            String id = databaseDoctors.push().getKey();
            Doctors doctors = new Doctors(id,Doc_Name,Doc_email,Doc_phone,Doc_Type,Doc_clinic);
            databaseDoctors.child(id).setValue(doctors);
            Toast.makeText(this,"Doctor Details Added",Toast.LENGTH_LONG).show();
            name.setText(null);
            eId.setText(null);
            pNo.setText(null);
            docType.setText(null);
            clinicName.setText(null);

        } else {
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_LONG).show();
        }

    }
}