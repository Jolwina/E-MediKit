package com.mca.alisha.e_medicare;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout mUsername, mEmail,mPassword;
    private Button mregister;
    private FirebaseAuth mAuth,mGuest;
    private Toolbar mToolbar;
    private DatabaseReference mDBRemoveUser;

    private ProgressDialog mRegProgress;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername=findViewById(R.id.Uname);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);

        mregister=findViewById(R.id.submit_reg_btn);
        mToolbar=findViewById(R.id.reg_appbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegProgress=new ProgressDialog(this);

        mDBRemoveUser=FirebaseDatabase.getInstance().getReference().child("users");


        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean CheckNetwork=isNetworkAvailable();
                if(CheckNetwork.equals(false)){
                    Toast.makeText(getApplicationContext(),"Check Internet Connection",Toast.LENGTH_SHORT).show();
                }
                else{
                    String DisplayName= mUsername.getEditText().getText().toString();
                    String email= mEmail.getEditText().getText().toString();
                    String password= mPassword.getEditText().getText().toString();
                    mAuth = FirebaseAuth.getInstance();


                    if(!TextUtils.isEmpty(DisplayName)|| !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
                        mRegProgress.setTitle("Registering your Account");
                        mRegProgress.setMessage("Please wait while your account is being created");
                        mRegProgress.setCanceledOnTouchOutside(false);
                        mRegProgress.show();
                        registerUser(DisplayName,email,password);
                    }
                }

            }

        });
    }

    private void registerUser(final String displayName, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user!=null){

                            }
                            String uid=user.getUid();
                            String token= FirebaseInstanceId.getInstance().getToken();

                            mDatabase=FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                            HashMap<String,String> userMap=new HashMap<>();
                            userMap.put("name",displayName);
                            userMap.put("token",token);

                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        mRegProgress.dismiss();
                                        // Sign in success, update UI with the signed-in user's information
                                        Intent mainIntent=new Intent(getApplicationContext(),MainActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();
                                    }
                                }
                            });


                            Toast.makeText(getApplicationContext(), "Authentication Successful.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            mRegProgress.hide();
                            Boolean CheckNetwork=isNetworkAvailable();
                            if(CheckNetwork.equals(false)){
                                Toast.makeText(getApplicationContext(),"Check Internet Connection",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Registration failed, Please check form and try again.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        // ...
                    }
                });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
