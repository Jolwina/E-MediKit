package com.mca.alisha.e_medicare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    DatabaseReference databasePatients;

    Button submit;
    EditText name,pNo,eId,DOB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        mToolbar=findViewById(R.id.updateAppBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Patient Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databasePatients = FirebaseDatabase.getInstance().getReference("Patients");


        name=findViewById(R.id.name);
        eId=findViewById(R.id.emailId);
        pNo=findViewById(R.id.phno);
        DOB=findViewById(R.id.DOB);
        submit=findViewById(R.id.Submit);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPatient();
            }
        });


    }



    public void AddPatient() {

        String PName = name.getText().toString().trim();
        String PeId = eId.getText().toString().trim();
        String phoneNo = pNo.getText().toString().trim();
        String PDob = DOB.getText().toString().trim();


        if (!TextUtils.isEmpty(PName)) {
            String id = databasePatients.push().getKey();
            Patients patient = new Patients(id,PName,PeId,phoneNo,PDob);
            databasePatients.child(id).setValue(patient);
            Toast.makeText(this,"Member Added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_LONG).show();
        }

    }


}
