package com.example.hosp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Cancel_Appo_Mobile_Check extends AppCompatActivity {

    EditText mobile_Check;
    Button mobile_verify;
    TextView Patientname;

    Appointment_Helper helper=new Appointment_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_appo_mobile_check);

        mobile_Check=findViewById(R.id.MOBILE_CHECK_DEL);
        mobile_verify=findViewById(R.id.VERIFY_MOB_DEL);

        Patientname = findViewById(R.id.PATIENT_RECYCLE);

        Intent intent=getIntent();
        String mobile_key=intent.getStringExtra("mobile_key");
        String patient_name=intent.getStringExtra("patient_key_check");









        mobile_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobile=mobile_Check.getText().toString().trim();




                if (mobile.equals(mobile_key)){
                    /////////////////////DELETE SQLITE////////////////////////////

                    Boolean delete=helper.DeleteData_Appointment(patient_name);
                    if (delete==true){

                        Intent intent=new Intent(Cancel_Appo_Mobile_Check.this,Home_Patient.class);
                        startActivity(intent);
                        Toast.makeText(Cancel_Appo_Mobile_Check.this, "Appointment Request Cancelled", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(Cancel_Appo_Mobile_Check.this, "Failed To Delete", Toast.LENGTH_SHORT).show();
                    }

                    /////////////////////DELETE SQLITE////////////////////////////




                }else if (mobile.isEmpty()){

                    mobile_Check.setError("Enter Mobile Number");
                }else if (mobile.length()<10){

                    Toast.makeText(Cancel_Appo_Mobile_Check.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Cancel_Appo_Mobile_Check.this, "Mobile Number Not Matched", Toast.LENGTH_SHORT).show();
                }





            }
        });


    }




}