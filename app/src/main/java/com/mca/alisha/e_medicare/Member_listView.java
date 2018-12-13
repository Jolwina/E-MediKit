package com.mca.alisha.e_medicare;

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

public class Member_listView extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    List<Members> Memberlist;
    Members member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_listview);

        listView = (ListView)findViewById(R.id.listView);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Members");

        Memberlist = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Memberlist.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) // get the data under events table
                {
                    member = ds.getValue(Members.class);
                    Memberlist.add(member);
                }
                MemberList adapter = new MemberList(Member_listView.this,Memberlist);
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
