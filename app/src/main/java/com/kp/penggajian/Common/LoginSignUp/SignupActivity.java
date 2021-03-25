package com.kp.penggajian.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.kp.penggajian.R;

public class SignupActivity extends AppCompatActivity {

    public static String getEmail;

    TextInputLayout usernameValid, emailValid, passwordValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //hooks data for validation error
        usernameValid = findViewById(R.id.signup_username);
        emailValid = findViewById(R.id.signup_email);
        passwordValid = findViewById(R.id.signup_password);

        ImageView back = (ImageView) findViewById(R.id.signup_back_button);
        back.setClickable(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RetailerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button next2 = findViewById(R.id.next2);
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validateEmail() | !validatePassword()){
                    return;
                } else {
                    getEmail = emailValid.getEditText().getText().toString();

                    Intent intent = new Intent(getApplicationContext(), SignupActivity2.class);
                    startActivity(intent);
                }
            }
        });

        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateUsername(){
        String val = usernameValid.getEditText().getText().toString().trim();
        String checkspace = "\\A\\w{1,20}\\z";      //white spaces validate

        if (val.isEmpty()){
            usernameValid.setError(getString(R.string.empty_field));
            return false;
        } else if (val.length() > 20){
            usernameValid.setError(getString(R.string.username_to_large));
            return false;
        } else if (!val.matches(checkspace)){
            usernameValid.setError(getString(R.string.username_no_white_spaces));
            return false;
        } else {
            usernameValid.setError(null);
            usernameValid.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = emailValid.getEditText().getText().toString().trim();
        String email = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";  //email validate

        if (val.isEmpty()){
            emailValid.setError(getString(R.string.empty_field));
            return false;
        } else if (!val.matches(email)){
            emailValid.setError(getString(R.string.email_validate));
            return false;
        } else {
            emailValid.setError(null);
            emailValid.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword(){
        String val = passwordValid.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[0-9])" +          //at least 1 digit
                //"(?=.*[a-z])" +          //at least 1 lower case letter
                //"(?=.*[A-Z])" +          //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +       //any letter
                //"(?=.*[@#$%^&+=-])" +    //at least 1 special character
                "(?=\\S+$)" +            //no white spaces
                //".{4,}" +                //at least 4 characters
                "$";

        if (val.isEmpty()){
            passwordValid.setError(getString(R.string.empty_field));
            return false;
        } else if (val.matches(checkPassword)){
            passwordValid.setError(getString(R.string.password_validate));
            return false;
        } else {
            passwordValid.setError(null);
            passwordValid.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), RetailerActivity.class);
        startActivity(intent);
        finish();
    }
}