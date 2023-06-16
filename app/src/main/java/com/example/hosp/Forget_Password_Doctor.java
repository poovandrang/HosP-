package com.example.hosp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Forget_Password_Doctor extends AppCompatActivity {

    EditText username,mobile;
    EditText new_Pass,Conform_Pass;
    Button Verify,Reset;
    LinearLayout linearLayout;
    Helper_doc helper_doc=new Helper_doc(this);
    ImageView eye_password,eye_conform_Password;

    private static final Pattern PASSWORD_PATTERN=
            Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_doctor);

        username=findViewById(R.id.FGET_USERNAME_DOC);
        mobile=findViewById(R.id.FGET_MOBILE_DOC);
        Verify=findViewById(R.id.FGET_VERIFY_BTN_DOC);
        Reset=findViewById(R.id.FGET_RESTE_BTN_DOC);
        new_Pass=findViewById(R.id.FGET_NEWPASS_DOC);
        Conform_Pass=findViewById(R.id.FGET_CONPASS_DOC);
        linearLayout=findViewById(R.id.FGET_LINEAR_DOC);
        eye_password=findViewById(R.id._FGET_eye_pass_DOC);
        eye_conform_Password=findViewById(R.id._FGET_eye_pass_conform_DOC);



        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name=username.getText().toString().trim();
                String mob=mobile.getText().toString().trim();

                if (name.isEmpty()){
                    username.setError("Enter Username");
                }
                else if (mob.isEmpty())
                {
                    mobile.setError("Enter Mobile Number");
                }else {

                    Boolean Match=helper_doc.CheckUsername_Mobile(name,mob);
                    if (Match==true)
                    {
                        Toast.makeText(Forget_Password_Doctor.this, "Both Are Equal", Toast.LENGTH_SHORT).show();
                        linearLayout.setVisibility(View.VISIBLE);
                        Verify.setVisibility(View.GONE);



                    }
                    else
                    {
                        Toast.makeText(Forget_Password_Doctor.this, "NoT Equal", Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });


        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name=username.getText().toString().trim();
                String pass1=new_Pass.getText().toString().trim();
                String pass2=Conform_Pass.getText().toString().trim();
                if (pass1.isEmpty())
                {
                    new_Pass.setError("Enter New Password");
                }
                else if (pass2.isEmpty())
                {
                    Conform_Pass.getText().toString().trim();
                }else if (!PASSWORD_PATTERN.matcher(pass1).matches()) {
                    Toast.makeText(Forget_Password_Doctor.this, " Weak Password", Toast.LENGTH_SHORT).show();
                    new_Pass.setError("Example: Password@123");
                }


                else {

                    if (pass1.equals(pass2)){

                        Boolean update=helper_doc.UpdatePassword(name,pass1);
                        if (update==true){
                            Toast.makeText(Forget_Password_Doctor.this, "Password Changed", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(Forget_Password_Doctor.this,MainActivity.class);
                            startActivity(intent);




                        }else{
                            Toast.makeText(Forget_Password_Doctor.this, "Not Updated", Toast.LENGTH_SHORT).show();
                        }


                    }else {
                        Toast.makeText(Forget_Password_Doctor.this, "Password Not Matched", Toast.LENGTH_SHORT).show();
                    }




                }




            }
        });











    }


    public void Password_eye(View view){

        if (view.getId()==R.id._FGET_eye_pass_DOC) {
            new_Pass = findViewById(R.id.FGET_NEWPASS_DOC);
            if (new_Pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                eye_password.setImageResource(R.drawable.eye_close_png);
                new_Pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                eye_password.setImageResource(R.drawable.eye_open_png);
                new_Pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }}}


    public void Conform_Password_Eye(View view){

        if (view.getId()==R.id._FGET_eye_pass_conform_DOC) {
            Conform_Pass= findViewById(R.id.FGET_CONPASS_DOC);
            if (Conform_Pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))
            {
                eye_conform_Password.setImageResource(R.drawable.eye_close_png);
                Conform_Pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else
            {
                eye_conform_Password.setImageResource(R.drawable.eye_open_png);
                Conform_Pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }}


    }











}