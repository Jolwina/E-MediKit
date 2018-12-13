package com.mca.alisha.e_medicare;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MembersActivity extends AppCompatActivity
{
    //private Activity context;

    private Toolbar mToolbar;
    DatabaseReference databaseMembers;

    Button submit;
    EditText name, dob;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        mToolbar=findViewById(R.id.updateAppBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Member Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseMembers = FirebaseDatabase.getInstance().getReference("Members");


        name=findViewById(R.id.name);
        dob=findViewById(R.id.dob);
        submit=findViewById(R.id.button);


        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AddMembers();

            }
        });

    }

    public void AddMembers() {
       // LayoutInflater inflater=context.getLayoutInflater();

        /*Users users = eventsList.get(position);


        View users=inflater.inflate(R.layout.activity_login,null,true);
        final String Id = users.getId().toString();*/



        String MemName = name.getText().toString().trim();
        String MemDob = dob.getText().toString().trim();


        if ((!TextUtils.isEmpty(MemName)) && (!TextUtils.isEmpty(MemDob))) {
            String id = databaseMembers.push().getKey();
            Members members = new Members(id,MemName,MemDob);
            databaseMembers.child(id).setValue(members);
            Toast.makeText(this,"Member Added",Toast.LENGTH_LONG).show();
            name.setText(null);
            dob.setText(null);

        } else {
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_LONG).show();
        }

    }
}