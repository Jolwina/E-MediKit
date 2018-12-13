package com.mca.alisha.e_medicare;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Member;
import java.util.List;

public class MemberList  extends ArrayAdapter<Members> {


    private Activity context;
    private List<Members> memberList;
    FirebaseDatabase database;
    DatabaseReference ref;

    public MemberList(Activity context, List<Members> memberList) {
        super(context, R.layout.member_info, memberList);
        this.context = context;
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.member_info, null, true);


        TextView name = (TextView) listViewItem.findViewById(R.id.name);
        TextView dob = (TextView) listViewItem.findViewById(R.id.dob);
        ImageView update  = (ImageView) listViewItem.findViewById(R.id.update);




        Members members = memberList.get(position);

        name.setText(members.getName());
        dob.setText(members.getDob());


        final String Id = members.getId();

        final String mem_name = String.valueOf(name.getText().toString());
        final String mem_dob = String.valueOf(dob.getText().toString());



       /* delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteEvent(Id);
            }
        });*/


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateDialog(Id,mem_name,mem_dob);
            }
        });


        return listViewItem;


    }

    private void showUpdateDialog(final String Id, String mem_name, String mem_dob){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = context.getLayoutInflater();
        //View listViewItem = inflater.inflate(R.layout.events_info, null, true);
        final View dialogView = inflater.inflate(R.layout.activity_update_members, null);
        dialogBuilder.setView(dialogView);

        final EditText name = (EditText) dialogView.findViewById(R.id.name);
        final EditText dob = (EditText) dialogView.findViewById(R.id.dob);

        Button Updatebutton = (Button) dialogView.findViewById(R.id.update);


        dialogBuilder.setTitle(mem_name);

        name.setText(mem_name);
        dob.setText(mem_dob);


        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String memName= name.getText().toString().trim();
                final String memDob= dob.getText().toString().trim();



                updateEvents(Id,memName,memDob);

                alertDialog.dismiss();
            }
        });



    }

    private boolean updateEvents(String id, String memName,String memDob){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Members").child(id);
        Members member = new Members (id,memName,memDob);
        databaseReference.setValue(member);
        Toast.makeText(getContext(),"Family Member Details Updated Succesfully",Toast.LENGTH_LONG).show();
        return true;
    }

    /*private void DeleteEvent(String eventId) {
        DatabaseReference dbEvent = FirebaseDatabase.getInstance().getReference("events").child(eventId);
        dbEvent.removeValue();

        Toast.makeText(getContext(),"Event is deleted", Toast.LENGTH_LONG).show();
    }*/


}

