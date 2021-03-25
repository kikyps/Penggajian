package com.kp.penggajian.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kp.penggajian.R;

public class SmsVerifyOTP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_verify_otp);

        TextView number = findViewById(R.id.getour_number);

        String putIdnumber = SignupActivity3.getNumber;
        String putNumber = SignupActivity3.getIdNumber;


        number.setText(putIdnumber+putNumber);

        ImageView back = (ImageView) findViewById(R.id.signup_back_button);
        back.setClickable(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button chgnumber = findViewById(R.id.change_number);
        chgnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity3.class);
                startActivity(intent);
                finish();
            }
        });
    }
}