package com.example.hosp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class doctor_register extends AppCompatActivity  implements AdapterView.OnItemSelectedListener    {
    EditText duser,dpass,dmobile,dmail,dage,dconform_pass,dname;
    Button dbutton;
    Spinner spinner_1;
    RadioGroup dradioGroup;
    RadioButton gender;
    String Specialist;
    specialist_helper specialist_helper;

    ImageView eye_password,eye_conform_Password;
    String getSpecialist="Select";
    private static final Pattern PASSWORD_PATTERN=
            Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");




    Helper_doc helper_doc=new Helper_doc(this);
    Doc_UNR helper_unr=new Doc_UNR(this);

    private SharedPreferences sharedPreferences_doctor;
    private SharedPreferences.Editor editor_doctor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        sharedPreferences_doctor = this.getSharedPreferences("DOCTOR", MODE_PRIVATE);
        editor_doctor = sharedPreferences_doctor.edit();


        eye_password=findViewById(R.id.eye_pass_doc);
        eye_conform_Password=findViewById(R.id.eye_pass_conform_doc);





        specialist_helper=new specialist_helper(this);









        duser=findViewById(R.id.DUSERNAME);
        dname=findViewById(R.id.DNAME);
        dpass=findViewById(R.id.DPASSWORD);
        dconform_pass=findViewById(R.id.DCONFORMPASSWORD);
        dmobile=findViewById(R.id.DMOBILE);
        dmail=findViewById(R.id.DMAIL);
        dage=findViewById(R.id.DAGE);
        spinner_1=findViewById(R.id.SPINNER_SPECIALIST);


        /*SPINNER*/
        spinner_1=findViewById(R.id.SPINNER_SPECIALIST);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(
                this,R.array.Specialist,
                R.layout.color_spinner_layout
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner_1.setAdapter(adapter);

        spinner_1.setOnItemSelectedListener(this);
        Specialist=spinner_1.getSelectedItem().toString();

        /*SPINNER*/






        /*BUTTON*/
        dbutton=findViewById(R.id.SUBMIT_BTN);
        dbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Username=duser.getText().toString().trim();
                String Name=dname.getText().toString().trim();
                String Password=dpass.getText().toString().trim();
                String Conform_Password=dconform_pass.getText().toString().trim();
                String Mobile=dmobile.getText().toString().trim();
                String Mail=dmail.getText().toString().trim();
                String Age=dage.getText().toString().trim();
                int isSelected=dradioGroup.getCheckedRadioButtonId();
                gender=findViewById(isSelected);











                /* OWN CODEING */
                //Radio Button//
                // getCheckedRadioButton return -1 if no radio button is selected



                if (Username.isEmpty()) {
                    duser.setError("Create Unique Username");
                    Toast.makeText(doctor_register.this, "Enter Username", Toast.LENGTH_SHORT).show();
                } else if (Name.isEmpty()) {
                    dname.setError("Enter Your Name");
                    Toast.makeText(doctor_register.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }
                else if (Password.isEmpty()) {
                    dpass.setError("Enter Your Password");
                    Toast.makeText(doctor_register.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if (Mobile.isEmpty()) {
                    Toast.makeText(doctor_register.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
                } else if (Mail.isEmpty()) {
                    Toast.makeText(doctor_register.this, "Enter E-Mail", Toast.LENGTH_SHORT).show();
                } else if (Age.isEmpty()) {
                    Toast.makeText(doctor_register.this, "Enter Your Age", Toast.LENGTH_SHORT).show();
                } else if (isSelected==-1)
                {
                    Toast.makeText(doctor_register.this,"Choose Your Gender",Toast.LENGTH_SHORT).show();
                }else if (dmobile.length()<10){
                    Toast.makeText(doctor_register.this,"Enter Correct Mobile Number",Toast.LENGTH_SHORT).show();
                }else if (Specialist.equals("Select")){

                    Toast.makeText(doctor_register.this,"Select Specialist",Toast.LENGTH_SHORT).show();
                }else if (Age.equals("0")||Age.equals("1")||Age.equals("3")||Age.equals("4")||Age.equals("5")||Age.equals("6")||Age.equals("7")||Age.equals("8")||Age.equals("9")||Age.equals("10")||Age.equals("11")||Age.equals("12")||Age.equals("2")||Age.equals("13")||Age.equals("14")||Age.equals("15")||Age.equals("16")||Age.equals("17")||Age.equals("18")){
                    Toast.makeText(doctor_register.this, "The Minimum Age for Doctor is 18", Toast.LENGTH_SHORT).show();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(Mail).matches()){
                    dmail.setError("invalid email");
                }else if (!PASSWORD_PATTERN.matcher(Password).matches()) {
                    Toast.makeText(doctor_register.this, " Weak Password", Toast.LENGTH_SHORT).show();
                    dpass.setError("Example: Password@123");
                }else if (Conform_Password.isEmpty()){
                    Toast.makeText(doctor_register.this,"Confirm your Password",Toast.LENGTH_SHORT).show();
                }

                else {
                    String Gender = gender.getText().toString();

                    if (!spinner_1.getSelectedItem().toString().trim().equals(getSpecialist)) {

                        if (dpass.getText().toString().trim().equals(dconform_pass.getText().toString().trim())) {

                            Boolean checkuser = helper_doc.CheckUsername(Username);
                            if (checkuser == false) {

                                Boolean insert = helper_doc.InsertData(Username,Name,Password, Mobile, Mail, Age, Gender, Specialist);
                                if (insert == true) {









                                    AlertDialog.Builder builder = new AlertDialog.Builder(doctor_register.this);
                                    builder.setMessage("Register Request Send Successfully Thank you !\n\nOnce Admin Approve Your Request by reviewing your details,\n\nPatients Can Request You For Appointment");
                                    builder.setTitle("NOTE");
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("OkaY", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            Intent ipatient = new Intent(doctor_register.this, MainActivity.class);
                                            startActivity(ipatient);
                                            finish();

                                        }
                                    });


                                    builder.show();











                                } else {
                                    Toast.makeText(doctor_register.this, "Failed", Toast.LENGTH_SHORT).show();

                                }

                            } else {
                                Toast.makeText(doctor_register.this, "User Already exist", Toast.LENGTH_SHORT).show();
                                duser.setError("Username Already exist");
                            }

                        } else {

                            Toast.makeText(doctor_register.this, "Password MisMatch", Toast.LENGTH_SHORT).show();


                        }
                    }
                    else{
                        Toast.makeText(doctor_register.this,"Select Specialist",Toast.LENGTH_SHORT).show();
                    }
                }

                /* OWN CODEING */

            }
        });
        /*BUTTON*/








        /*RADIO GROUP*/
        dradioGroup=findViewById(R.id.DRADIO_GROUP);
        //getCheckedRadioButton return -1 if no radio button is selected

        /*RADIO GROUP*/



    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Specialist=spinner_1.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }





    public void Password_eye(View view){


        if(view.getId()==R.id.eye_pass_doc) {
            eye_password = findViewById(R.id.eye_pass_doc);
            dpass = findViewById(R.id.DPASSWORD);
            if (dpass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                eye_password.setImageResource(R.drawable.eye_close_png);
                dpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                eye_password.setImageResource(R.drawable.eye_open_png);
                dpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }}}


    public void Conform_Password_Eye(View view) {

        if (view.getId() == R.id.eye_pass_conform_doc) {
            dconform_pass = findViewById(R.id.DCONFORMPASSWORD);
            if (dconform_pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {

                eye_conform_Password.setImageResource(R.drawable.eye_close_png);
                dconform_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            } else {

                eye_conform_Password.setImageResource(R.drawable.eye_open_png);
                dconform_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }



    public void Dialog() {



    }






    }