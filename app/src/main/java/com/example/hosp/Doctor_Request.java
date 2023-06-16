package com.example.hosp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Doctor_Request extends AppCompatActivity {

    EditText mobile;
    TextView doct_user_name;
    TextView patient_name;
    Button send_request;
    Doctor_Request_Helper helper;
    Helper_doc doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_request);


        helper=new Doctor_Request_Helper(this);
        doc=new Helper_doc(this);

        doct_user_name=findViewById(R.id.DOCTOR_USER_NAME);
        mobile=findViewById(R.id.DOCTOR_REQ_MOBILE);
        send_request=findViewById(R.id.SEND_REQUEST);
/////////////////////////////////////////////////////////////////////////////////////////
        patient_name=findViewById(R.id.DOCTOR_REQ_PATIENT);
        Intent intent=getIntent();
        String patientname=intent.getStringExtra("patient_key");
        patient_name.setText(patientname);


        SharedPreferences sharedPreferences_doctor;
        sharedPreferences_doctor = this.getSharedPreferences("Login_Doctor", MODE_PRIVATE);
        String username=sharedPreferences_doctor.getString("username_doctor","");
        doct_user_name.setText(username);



///////////////////////////////////////////////////////////////////////////////////////////



        send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String User = doct_user_name.getText().toString().trim();
                String Mobile = mobile.getText().toString().trim();
                String Patient = patient_name.getText().toString().trim();


                if (User.isEmpty()) {

                    doct_user_name.setError("Enter Your User Name");
                } else if (Mobile.isEmpty()) {
                    mobile.setError("Enter Your Mobile Number");

                } else if (mobile.length()<10){
                    mobile.setError("Enter Valid Mobile Number");
                }
                else if (Patient.isEmpty()) {
                    patient_name.setError("mention Your Patient Name");
                } else {




                            Boolean insert = helper.INSERT_REQUEST_DETAILS(User,Mobile, Patient);

                            if (insert == true) {
                                Toast.makeText(Doctor_Request.this, "Request Send", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Doctor_Request.this,Home_Doctor.class);
                                startActivity(intent);


                            } else {
                                Toast.makeText(Doctor_Request.this, "Failed", Toast.LENGTH_SHORT).show();
                            }








                }
            }
        });



    }
}