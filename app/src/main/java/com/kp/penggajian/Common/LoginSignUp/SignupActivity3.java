package com.kp.penggajian.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.kp.penggajian.R;

public class SignupActivity3 extends AppCompatActivity {

    TextInputLayout numberSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);

        numberSign = findViewById(R.id.sign_number);

        Button signnow = findViewById(R.id.sign_now);
        signnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!numberValidate()){
                    return;
                } else {
                    Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                    startActivity(intent);
                }
            }
        });

        ImageView back = (ImageView) findViewById(R.id.signup_back_button);
        back.setClickable(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean numberValidate(){
        String val = numberSign.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            numberSign.setError(getString(R.string.empty_field));
            return false;
        } else {
            numberSign.setError(null);
            numberSign.setErrorEnabled(false);
            return true;
        }
    }

}