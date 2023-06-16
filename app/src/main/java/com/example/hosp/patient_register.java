package com.example.hosp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.widget.ImageView;
import   android.widget.RadioButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.text.method.TransformationMethod;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class patient_register extends AppCompatActivity {
    EditText user,password,mobile,mail,age,conformpassword,name;
    Button submit;
    RadioGroup pradioGroup;
    RadioButton gender;
    ImageView eye_password,eye_conform_Password;
    private static final Pattern PASSWORD_PATTERN=
            Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");

   DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        eye_password=findViewById(R.id.eye_pass);
        eye_conform_Password=findViewById(R.id.eye_pass_conform);


        user=findViewById(R.id.PUSERNAME);
        name=findViewById(R.id.PNAME);
        password=findViewById(R.id.PPASSWORD);
        conformpassword=findViewById(R.id.PCONFORMPASSWORD);
        mobile=findViewById(R.id.PMOBILE);
        mail=findViewById(R.id.PMAIL);
        age=findViewById(R.id.PAGE);
        pradioGroup=findViewById(R.id.RADIO_GROUP);
        submit=findViewById(R.id.PBTN);
        ///////IMPORT HELPER CLASS CODE
        dbHelper=new DBHelper(this);



        /*RADIO GROUP*/



        /*RADIO GROUP*/




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                String Username=user.getText().toString().trim();
                String Name=name.getText().toString().trim();
                String Password=password.getText().toString().trim();
                String Conform_Password=conformpassword.getText().toString().trim();
                String Mobile=mobile.getText().toString().trim();
                String Mail=mail.getText().toString().trim();
                String Age=age.getText().toString().trim();
                int IsSelected=pradioGroup.getCheckedRadioButtonId();
                gender=findViewById(IsSelected);






///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                if (Username.isEmpty()){
                    user.setError("Create Unique Username");
                    Toast.makeText(patient_register.this, "Enter Username", Toast.LENGTH_SHORT).show();
                }else if (Name.isEmpty()) {
                    name.setError("Enter Your Name");
                    Toast.makeText(patient_register.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }
                else if (Password.isEmpty())
                {
                    password.setError("Enter Your Password");
                    Toast.makeText(patient_register.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if (Mobile.isEmpty()){
                    Toast.makeText(patient_register.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
                }
                else if (Mail.isEmpty()){
                    Toast.makeText(patient_register.this, "Enter E-Mail", Toast.LENGTH_SHORT).show();


                }else if (Age.isEmpty()){
                    Toast.makeText(patient_register.this, "Enter Your Age", Toast.LENGTH_SHORT).show();
                }else if (IsSelected==-1)
                {
                    Toast.makeText(patient_register.this,"Choose Your Gender",Toast.LENGTH_SHORT).show();
                }else if (Conform_Password.isEmpty()){

                    Toast.makeText(patient_register.this,"Conform Password",Toast.LENGTH_SHORT).show();
                }else if (mobile.length()<10){

                    Toast.makeText(patient_register.this,"Enter Correct Mobile Number",Toast.LENGTH_SHORT).show();
                }else if (Age.equals("0")||Age.equals("1")||Age.equals("2")){
                    Toast.makeText(patient_register.this,"The Minimum Age For Patient is 3",Toast.LENGTH_SHORT).show();

                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(Mail).matches()){

                    mail.setError("invalid email");
                }else if (!PASSWORD_PATTERN.matcher(Password).matches()){
                    Toast.makeText(patient_register.this, " Weak Password", Toast.LENGTH_SHORT).show();
                    password.setError("Example: Password@123");
                }



                else
                {
                    String Gender= gender.getText().toString();

                    if (password.getText().toString().trim().equals(conformpassword.getText().toString().trim())) {

                        Boolean checkuser = dbHelper.CheckUsername(Username);
                        if (checkuser == false) {

                            Boolean insert = dbHelper.InsertData(Username,Name, Password, Mobile, Mail, Age, Gender);
                            if (insert == true) {
                                Toast.makeText(patient_register.this, "Resgistered Successfully ", Toast.LENGTH_SHORT).show();
                                Intent idoctor = new Intent(patient_register.this, MainActivity.class);
                                startActivity(idoctor);
                                finish();
                            } else {
                                Toast.makeText(patient_register.this, "Failed", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            Toast.makeText(patient_register.this, "User Already exist", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else
                    {
                        Toast.makeText(patient_register.this,"Password MisMatch",Toast.LENGTH_SHORT).show();
                    }







                }

            }
        });


    }



public void Password_eye(View view){

        if (view.getId()==R.id.eye_pass) {
            password = findViewById(R.id.PPASSWORD);
            if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                eye_password.setImageResource(R.drawable.eye_close_png);
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                eye_password.setImageResource(R.drawable.eye_open_png);
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }}}


    public void Conform_Password_Eye(View view){

        if (view.getId()==R.id.eye_pass_conform) {
            conformpassword= findViewById(R.id.PCONFORMPASSWORD);
            if (conformpassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))
            {
                eye_conform_Password.setImageResource(R.drawable.eye_close_png);
                conformpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else
            {
                eye_conform_Password.setImageResource(R.drawable.eye_open_png);
                conformpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }}


    }






}