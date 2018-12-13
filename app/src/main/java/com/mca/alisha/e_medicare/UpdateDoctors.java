package com.mca.alisha.e_medicare;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.List;

public class UpdateDoctors extends AppCompatActivity {
    private List<Doctors> doctorsList;

    private Toolbar mToolbar;
    Button Updatebutton;
    private ProgressDialog mProgress;
    EditText dName,dEmailId,dPhno,dClinic;
    TextView dTyp;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doctors);
        mToolbar=findViewById(R.id.updateAppBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Update Doctor Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Doctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mProgress = new ProgressDialog(this);

        dName=findViewById(R.id.dname);
        dEmailId=findViewById(R.id.demId);
        dPhno=findViewById(R.id.dPhoneNo);
        dClinic=findViewById(R.id.Clinic);
        dTyp=findViewById(R.id.dType);
        Updatebutton=findViewById(R.id.Updatebutton);

        Updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDetails();
                Toast.makeText(getApplicationContext(),"Details Saved",Toast.LENGTH_LONG).show();
            }
        });

        DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("Doctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        if(db!=null){
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dTyp.setText(dataSnapshot.child("doc_Type").getValue().toString());
                    dClinic.setText(dataSnapshot.child("doc_clinic").getValue().toString());
                    dEmailId.setText(dataSnapshot.child("doc_email").getValue().toString());
                    dName.setText(dataSnapshot.child("doc_name").getValue().toString());
                    dPhno.setText(dataSnapshot.child("doc_phone").getValue().toString());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{

        }


    }

    private void saveDetails() {
        mProgress.setMessage("Saving Profile.......");
        mDatabase.child("doc_name").setValue(dName.getText().toString());
        mDatabase.child("doc_email").setValue(dEmailId.getText().toString());
        mDatabase.child("doc_phone").setValue(dPhno.getText().toString());
        mDatabase.child("doc_type").setValue(dTyp.getText().toString());
        mDatabase.child("doc_clinic").setValue(dClinic.getText().toString());


    }

}
