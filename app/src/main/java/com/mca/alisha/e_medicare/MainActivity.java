package com.mca.alisha.e_medicare;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar mToolbar;
    private DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    private FirebaseAuth firebaseAuth;
    Button meal,medicine;
    private static int TIME_OUT = 60000;
    public Handler handler = new Handler();
    public Runnable runnable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar=findViewById(R.id.mainappbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add your Schedules");

        drawer = findViewById(R.id.drawer_layout);
        NavigationView nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        nav.setItemIconTintList(null);

        //meal=findViewById(R.id.meal);
        //medicine=findViewById(R.id.medicine);

        /*medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent medIntent=new Intent(getApplicationContext(),MedicineActivity.class);
                startActivity(medIntent);
            }
        });*/

        /*meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setAlarm(true,true);
            }
        });*/

        toggle = new ActionBarDrawerToggle(this, drawer, mToolbar,R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /*runnable=new Runnable() {
            @Override
            public void run() {
                onResume();
                handler.postDelayed(this, 2000);
            }
        };

        handler.postDelayed(runnable,1500);*/
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        Calendar rightNow = Calendar.getInstance();
        final int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        final int currentMin=rightNow.get(Calendar.MINUTE);
        // Toast.makeText(getApplicationContext(),"Time: "+currentHour+":"+currentMin,Toast.LENGTH_LONG).show();
        DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("prescription").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String bf="",lch="",din="";
                bf=dataSnapshot.child("bf").getValue().toString();
                lch=dataSnapshot.child("lunch").getValue().toString();
                din=dataSnapshot.child("dinner").getValue().toString();
                //Toast.makeText(getApplicationContext(),bf+lch+din,Toast.LENGTH_LONG).show();

                if(bf.equals("Breakfast")){
                    if(currentHour==8 && currentMin==30){
                        setAlarm(true,true);
                    }
                }
                if(lch.equals("Lunch")){
                    if(currentHour==13 && currentMin==30) {
                        setAlarm(true, true);
                    }
                }
                if(din.equals("Dinner")){
                    if(currentHour==1 && currentMin==16) {
                        setAlarm(true, true);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //int id = menuItem.getItemId();
        //FragmentManager fragmentManager = getFragmentManager();


        switch(item.getItemId()){
            case R.id.medicine_schedule:
                medicine();
                return true;
            case R.id.doctor_info:
                doctor();
                return true;
            case R.id.patient_info:
                patient();
                return true;
            case R.id.clinics:
                clinics();
                return true;
            case R.id.pharmacies:
                pharmacies();
                return true;
            case R.id.ambulance:
                ambulance();
                return true;
            case R.id.member_info:
                members();
                return true;
            case R.id.logout:
                logout();
                return true;
            default:
                return false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.updateinfo:
                update();
                return true;
            case R.id.updateMember:
                updateMembers();
                return true;
            case R.id.help:
                showHelp();
                return true;
            case R.id.setting:
                showSetting();
                return true;
            case R.id.updateDoctor:
                updateDoctors();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSetting() {
    }

    private void showHelp() {
    }

    public void logout()
    {
        startActivity(new Intent(MainActivity.this, StartActivity.class));
    }

    private void update() {
        Intent updateIntent=new Intent(getApplicationContext(),UpdateActivity.class);
        startActivity(updateIntent);
    }

    private void updateMembers() {
        Intent updateMembers=new Intent(getApplicationContext(),Member_listView.class);
        startActivity(updateMembers);
    }

    private void updateDoctors() {
        Intent updateDoctor=new Intent(getApplicationContext(),Doctor_listView.class);
        startActivity(updateDoctor);
    }

    private void medicine() {
        Intent medicine = new Intent(getApplicationContext(), MedicineActivity.class);
        startActivity(medicine);
    }

    private void doctor() {
        Intent doctor = new Intent(getApplicationContext(), DoctorActivity.class);
        startActivity(doctor);
    }

    private void patient() {
        Intent patient = new Intent(getApplicationContext(), PatientActivity.class);
        startActivity(patient);
    }

    private void ambulance() {
        Intent ambulanceIntent=new Intent(getApplicationContext(),AmbulanceActivity.class);
        startActivity(ambulanceIntent);
    }

    private void pharmacies() {
        Intent pharmacyIntent=new Intent(getApplicationContext(),PharmacyActivity.class);
        startActivity(pharmacyIntent);
    }

    private void clinics() {
        Intent clinicIntent=new Intent(getApplicationContext(),ClinicActivity.class);
        startActivity(clinicIntent);
    }

    private void members() {
        Intent members=new Intent(getApplicationContext(),MembersActivity.class);
        startActivity(members);
    }

    /*private void setAlarm(boolean isNotification,boolean isRepeat) {

        final Vibrator vibrate = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for "whatever_time_u_want" milliseconds
        long[] pattern = { 0, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500,500,500,500,500,500,500,500,500};
        vibrate.vibrate(pattern , -1);

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle("Reminder")
                .setMessage("Time for your Medicines..")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.P)
                    public void onClick(DialogInterface dialog, int which) {
                        vibrate.cancel();
                        handler.removeCallbacks(runnable);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                                startActivity(getIntent());
                            }
                        }, TIME_OUT);


                    }
                })
                .setIcon(R.drawable.icon)
                .show();

        AlarmManager manager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = null;
        PendingIntent pendingIntent = null;



        if(isNotification){
            myIntent=new Intent(MainActivity.this,alarm.class);
            pendingIntent=PendingIntent.getBroadcast(this,0,myIntent,0);

        }

        if(!isRepeat){
            manager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), 30*1000, pendingIntent);
            manager.set(AlarmManager.RTC_WAKEUP,SystemClock.elapsedRealtime()+3000,pendingIntent);
        }
        else{
            manager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), 30*1000, pendingIntent);
            manager.setRepeating(AlarmManager.RTC_WAKEUP,SystemClock.elapsedRealtime()+3000,3000,pendingIntent);
        }
    }*/

}
