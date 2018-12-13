package com.mca.alisha.e_medicare;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Doctor_listView extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    List<Doctors> Doctorlist;
    Doctors doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_listview);

        listView = (ListView)findViewById(R.id.listView);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Doctors");

        Doctorlist = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Doctorlist.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) // get the data under events table
                {
                    doctor = ds.getValue(Doctors.class);
                    Doctorlist.add(doctor);
                }
                DoctorList adapter = new DoctorList(Doctor_listView.this,Doctorlist);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               //Toast.makeText(getApplicationContext(),"Event Updated Succesfully",Toast.LENGTH_LONG).show();
                Events event = Eventslist.get(i);
                showUpdateDialog(event.getId(),event.getEventname());
                return false;
            }
        });*/


    }
}
