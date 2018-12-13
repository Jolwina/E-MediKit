package com.mca.alisha.e_medicare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {
    private Button mReg_btn,mLogin_Btn;
    private Animation fade;
    private TextView title,welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setButtons();
        title.startAnimation(fade);
        welcome.startAnimation(fade);

        mReg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg_intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(reg_intent);
            }
        });

        mLogin_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mLoginAct=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(mLoginAct);
            }
        });

    }

    private void setButtons() {
        mReg_btn=findViewById(R.id.reg_btn);
        mLogin_Btn=findViewById(R.id.start_login_btn);
        fade=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        title=findViewById(R.id.title);
        welcome=findViewById(R.id.welcome);
    }
}
