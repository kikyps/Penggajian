package com.kp.penggajian.Common.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kp.penggajian.Dashboard;
import com.kp.penggajian.MainActivity;
import com.kp.penggajian.R;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout usernameValid, passwordValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameValid = findViewById(R.id.login_username);
        passwordValid = findViewById(R.id.login_password);

        ImageView back = (ImageView) findViewById(R.id.login_back_button);
        back.setClickable(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RetailerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button signup = findViewById(R.id.create_account);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("login").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String input1 = usernameValid.getEditText().getText().toString();
                            String input2 = passwordValid.getEditText().getText().toString();

                            if (!validateUsername() | !validatePassword()) {
                                return;
                            } else {
                                if (snapshot.child(input1).exists()) {
                                    if (snapshot.child(input1).child("password").getValue(String.class).equals(input2)) {
                                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), getString(R.string.wrong_password), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), getString(R.string.unknown_data), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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