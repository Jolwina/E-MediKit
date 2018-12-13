package com.mca.alisha.e_medicare;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private Button upload,save,upload2,upload3;
    private ProgressDialog mProgress;
    private TextView pres;
    private ImageView img,img2,img3,tick1,tick2,tick3;
    private EditText name,email,dob;
    String p="1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mToolbar=findViewById(R.id.updateAppBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        upload=findViewById(R.id.uploadpres);
        save=findViewById(R.id.save);
        mProgress = new ProgressDialog(this);
        //pres=findViewById(R.id.prescription);
        img=findViewById(R.id.img);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);
        tick1=findViewById(R.id.tick1);
        tick2=findViewById(R.id.tick2);
        tick3=findViewById(R.id.tick3);
        upload2=findViewById(R.id.upload2);
        upload3=findViewById(R.id.upload3);
        name=findViewById(R.id.namet);
        email=findViewById(R.id.emailt);
        dob=findViewById(R.id.dobt);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p="1";
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }

                img.setVisibility(View.VISIBLE);
                tick1.setVisibility(View.VISIBLE);
            }
        });
        upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p="2";
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }

                img2.setVisibility(View.VISIBLE);
                tick2.setVisibility(View.VISIBLE);
            }
        });
        upload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p="3";
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }

                img3.setVisibility(View.VISIBLE);
                tick3.setVisibility(View.VISIBLE);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails();
                Toast.makeText(getApplicationContext(),"Details Saved",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveDetails() {
        mProgress.setMessage("Saving Profile.......");
        mDatabase.child("name").setValue(name.getText().toString());
        mDatabase.child("dob").setValue(dob.getText().toString());
        mDatabase.child("email").setValue(email.getText().toString());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(),data.getExtras().toString(),Toast.LENGTH_LONG).show();
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            if(p.equals("1"))img.setImageBitmap(photo);
            if(p.equals("2"))img2.setImageBitmap(photo);
            if(p.equals("3"))img3.setImageBitmap(photo);
            encodeBitmapAndSaveToFirebase(photo);

        }
    }


    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

        if(p.equals("1")){
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())
                    .child("prescription1");
            ref.setValue(imageEncoded);
        }
        if(p.equals("2")){
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())
                    .child("prescription2");
            ref.setValue(imageEncoded);
        }
        if(p.equals("3")){
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())
                    .child("prescription3");
            ref.setValue(imageEncoded);
        }


    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
