package com.example.hosp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Get_password extends AppCompatActivity {

    EditText pass1,pass2;
    Button get_otp;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_password);

        pass1=findViewById(R.id.PASS1);
        pass2=findViewById(R.id.PASS2);
        get_otp=findViewById(R.id.GET_OTP_BTN);



        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass1.getText().toString().isEmpty()) {
                    Toast.makeText(Get_password.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if (pass2.getText().toString().isEmpty()){
                    Toast.makeText(Get_password.this, "Conform Password", Toast.LENGTH_SHORT).show();
                }
                else {


                }

            }
        });

    }
}