package com.mca.alisha.e_medicare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedicineActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    DatabaseReference databasePrescription;
    EditText MName,quantity,PatientName;
    Spinner Pre_Post,MedType,DocName;
    CheckBox breakfast,lunch,dinner,tea;
    Button submit;

    ArrayAdapter<CharSequence> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        mToolbar=findViewById(R.id.updateAppBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Prescription");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databasePrescription = FirebaseDatabase.getInstance().getReference("prescription");

        MName = (EditText) findViewById(R.id.MName);
        quantity = (EditText) findViewById(R.id.quantity);
        PatientName = (EditText) findViewById(R.id.PatientName);

        Pre_Post = (Spinner) findViewById(R.id.Pre_Post);
        MedType = (Spinner) findViewById(R.id.MedType);
        DocName = (Spinner) findViewById(R.id.DocName);

        breakfast = (CheckBox) findViewById(R.id.breakfast);
        lunch = (CheckBox) findViewById(R.id.lunch);
        tea = (CheckBox) findViewById(R.id.tea);
        dinner = (CheckBox) findViewById(R.id.dinner);

        submit = (Button) findViewById(R.id.submit);



        adapter = ArrayAdapter.createFromResource(this,R.array.pre_post,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Pre_Post.setAdapter(adapter);
        //Pre_Post.setPrompt("");

        adapter = ArrayAdapter.createFromResource(this,R.array.MedType,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MedType.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this,R.array.DocName,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DocName.setAdapter(adapter);

        /*Pre_Post.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String prepost_selected = adapterView.getItemAtPosition(position).toString();

                Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(position)+ " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPrescription();

            }
        });

    }

    public void AddPrescription() {
        String BF = " ", Lunch = " ", Dinner =" ",Tea = " ";

        String prepost = Pre_Post.getSelectedItem().toString();
        String type = MedType.getSelectedItem().toString();
        String Dname = DocName.getSelectedItem().toString();

        if(breakfast.isChecked()) {
            BF = breakfast.getText().toString().trim();
        }
        if(lunch.isChecked()) {
            Lunch = lunch.getText().toString().trim();
        }
        if(lunch.isChecked()) {
            Dinner = dinner.getText().toString().trim();
        }
        if(tea.isChecked()){
            Tea = tea.getText().toString().trim();
        }

        String medname = MName.getText().toString().trim();
        String medquantity = quantity.getText().toString().trim();
        String patientname = PatientName.getText().toString().trim();


        if ((!TextUtils.isEmpty(medname)) && (!TextUtils.isEmpty(medquantity)) && (!TextUtils.isEmpty(patientname))) {
            String id = databasePrescription.push().getKey();
            Prescription Prescription = new Prescription(id,medname,prepost,BF,Lunch,Dinner,type,medquantity,Dname,patientname,Tea);
            databasePrescription.child(id).setValue(Prescription);
            Toast.makeText(this,"Prescription Added",Toast.LENGTH_LONG).show();
            MName.setText(null);
            quantity.setText(null);
            PatientName.setText(null);

        } else {
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_LONG).show();
        }

    }
}
