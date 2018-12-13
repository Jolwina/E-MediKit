package com.mca.alisha.e_medicare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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

import java.util.List;

public class DoctorList extends ArrayAdapter<Doctors> {

    private Activity context;
    private List<Doctors> doctorsList;
    FirebaseDatabase database;
    DatabaseReference ref;

    public DoctorList(Activity context, List<Doctors> doctorsList) {
        super(context, R.layout.doctors_info, doctorsList);
        this.context = context;
        this.doctorsList = doctorsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.doctors_info, null, true);


        TextView name = (TextView) listViewItem.findViewById(R.id.name);
        TextView emailid = (TextView) listViewItem.findViewById(R.id.emailid);
        TextView phoneNo = (TextView) listViewItem.findViewById(R.id.phoneNo);
        TextView type  = (TextView) listViewItem.findViewById(R.id.type);
        TextView clinic  = (TextView) listViewItem.findViewById(R.id.clinic);
        ImageView update  = (ImageView) listViewItem.findViewById(R.id.update);



        Doctors doctors = doctorsList.get(position);

        name.setText(doctors.getDoc_name());
        emailid.setText(doctors.getDoc_email());
        phoneNo.setText(doctors.getDoc_phone());
        type.setText(doctors.getDoc_Type());
        clinic.setText(doctors.getDoc_clinic());


        final String Id = doctors.getId();

        final String Doc_name = String.valueOf(name.getText().toString());
        final String Doc_emailid = String.valueOf(emailid.getText().toString());
        final String Doc_phone = String.valueOf(phoneNo.getText().toString());
        final String Doc_type = String.valueOf(type.getText().toString());
        final String Doc_clinic = String.valueOf(clinic.getText().toString());



       /* delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteEvent(Id);
            }
        });*/


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateDialog(Id,Doc_name,Doc_emailid,Doc_phone,Doc_type,Doc_clinic);
            }
        });


        /*leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetails.class);
                intent.putExtra("Id", Id);
                intent.putExtra("name", name);
                intent.putExtra("coord", coord);
                intent.putExtra("date", date);
                intent.putExtra("attendees", attendees);


                context.startActivity(intent);
            }
        });*/

        return listViewItem;


    }

    private void showUpdateDialog(final String Id, String Doc_name, String Doc_emailid, String Doc_phone, String Doc_type, final String Doc_clinic){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = context.getLayoutInflater();
        //View listViewItem = inflater.inflate(R.layout.events_info, null, true);
        final View dialogView = inflater.inflate(R.layout.activity_update_doctors, null);
        dialogBuilder.setView(dialogView);

        final EditText DocName = (EditText) dialogView.findViewById(R.id.dname);
        final EditText DocEmail = (EditText) dialogView.findViewById(R.id.demId);
        final EditText DocPhone =(EditText) dialogView.findViewById(R.id.dPhoneNo);
        final TextView DocClinic = (TextView) dialogView.findViewById(R.id.dType);
        final EditText DocType = (EditText) dialogView.findViewById(R.id.Clinic);
        Button Updatebutton = (Button) dialogView.findViewById(R.id.Updatebutton);


        dialogBuilder.setTitle(Doc_name);

        DocName.setText(Doc_name);
        DocEmail.setText(Doc_emailid);
        DocPhone.setText(Doc_phone);
        DocClinic.setText(Doc_clinic);
        DocType.setText(Doc_type);


        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String docname= DocName.getText().toString().trim();
                final String docemail= DocEmail.getText().toString().trim();
                final String docphone= DocPhone.getText().toString().trim();
                final String docclinic= DocClinic.getText().toString().trim();
                final String doctype= DocType.getText().toString().trim();


                updateEvents(Id,docname,docemail,docphone,doctype,docclinic);

                alertDialog.dismiss();
            }
        });



    }

    private boolean updateEvents(String id, String name,String email,String phone,String clinic,String type){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Doctors").child(id);
        Doctors doctor = new Doctors (id,name,email,phone,type,clinic);
        databaseReference.setValue(doctor);
        Toast.makeText(getContext(),"Doctor Details Updated Succesfully",Toast.LENGTH_LONG).show();
        return true;
    }

    /*private void DeleteEvent(String eventId) {
        DatabaseReference dbEvent = FirebaseDatabase.getInstance().getReference("events").child(eventId);
        dbEvent.removeValue();

        Toast.makeText(getContext(),"Event is deleted", Toast.LENGTH_LONG).show();
    }*/


}
