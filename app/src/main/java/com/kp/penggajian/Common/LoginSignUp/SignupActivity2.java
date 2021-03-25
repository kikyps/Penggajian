package com.kp.penggajian.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kp.penggajian.R;

import java.util.Calendar;

public class SignupActivity2 extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        radioGroup = findViewById(R.id.radio_groub);
        datePicker = findViewById(R.id.date_picker);

        ImageView back = (ImageView) findViewById(R.id.signup_back_button);
        back.setClickable(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button next3 = findViewById(R.id.next2);
        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateGender()){
                    return;
                } else if (!validateAge()){
                        return;
                } else {

                    selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
                    String gender = selectedGender.getText().toString();

                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth();
                    int year = datePicker.getYear();

                    String date = day+"/"+month+"/"+year;

                    Intent intent = new Intent(getApplicationContext(), SignupActivity3.class);
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
                finish();
            }
        });

    }

    private boolean validateGender(){
        if (radioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), getString(R.string.select_gender), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 18){
            Toast.makeText(getApplicationContext(), getString(R.string.select_age), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}