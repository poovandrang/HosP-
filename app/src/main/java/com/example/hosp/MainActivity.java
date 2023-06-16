package com.example.hosp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button login,signin;
    EditText username,password;
    AppCompatCheckBox checkBox;
    Spinner spinnerlog;
    DBHelper dbHelper;
    Helper_doc dbHelper_doc;
    String UserRole;
    View_doctor_List view_doctor_list;
    TextView froget,forget_doc;

    private SharedPreferences sharedPreferences,sharedPreferences_patient,sharedPreferences_doctor;
    private SharedPreferences.Editor editor,editor_patient,editor_doctor;




    private String[] LoginMode=new String[]{"ADMIN","DOCTOR","PATIENT"};






    ///////SPINNER MODE



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);










////////////////////////////////////////ADMIN LOGIN SHARE PREFRENCES//////////////////////////////////////
        sharedPreferences = this.getSharedPreferences("Login", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("islogin", "false").equals("yes")) {
            OpenDash();
        }
////////////////////////////////////////ADMIN LOGIN SHARE PREFRENCES//////////////////////////////////////

        ////////////////////////////////////////PATIENT LOGIN SHARE PREFRENCES//////////////////////////////////////

        sharedPreferences_patient = this.getSharedPreferences("Login_Patient", MODE_PRIVATE);
        editor_patient = sharedPreferences_patient.edit();

        if (sharedPreferences_patient.getString("islogin_patient", "false").equals("yes")) {
            OpenDash_Patient();
        }

        ////////////////////////////////////////PATIENT LOGIN SHARE PREFRENCES//////////////////////////////////////

        ////////////////////////////////////////DOCTOR LOGIN SHARE PREFRENCES//////////////////////////////////////

        sharedPreferences_doctor = this.getSharedPreferences("Login_Doctor", MODE_PRIVATE);
        editor_doctor = sharedPreferences_doctor.edit();

        if (sharedPreferences_doctor.getString("islogin_doctor", "false").equals("yes")) {
            OpenDash_Doctor();
        }

        ////////////////////////////////////////DOCTOR LOGIN SHARE PREFRENCES//////////////////////////////////////




        login=findViewById(R.id.LOGBTN);
        signin=findViewById(R.id.SIGNINBTN);
        username=findViewById(R.id.LUSERNAME);
        password=findViewById(R.id.LPASSWORD);
        froget=findViewById(R.id.FGET);
        forget_doc=findViewById(R.id.FGET_Doc);


        froget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,Forget_Password.class);
                startActivity(intent);



            }
        });

        forget_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,Forget_Password_Doctor.class);
                startActivity(intent);



            }
        });







        dbHelper=new DBHelper(this);
        view_doctor_list=new View_doctor_List();
        dbHelper_doc=new Helper_doc(this);

        /////////////////////PASSSWORD VISIBILITY////////////////////////

        checkBox=(AppCompatCheckBox) findViewById(R.id.Pass_Check_Box);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (!isChecked){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else {


                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });


        /////////////////////PASSSWORD VISIBILITY////////////////////////







        ///////////SPINNER MODE
        spinnerlog=findViewById(R.id.SPINNER_MODE);
        spinnerlog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                adapterView.getChildAt(0);
                UserRole=(String) spinnerlog.getSelectedItem();

                
                if (i==1){
                    forget_doc.setVisibility(View.VISIBLE);
                    froget.setVisibility(View.GONE);
                }else if (i==2){
                    froget.setVisibility(View.VISIBLE);
                    forget_doc.setVisibility(View.GONE);
                }
                
                

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> adapter_role = new ArrayAdapter<String>(this,
                R.layout.color_spinner_layout, LoginMode);
        adapter_role
                .setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerlog.setAdapter(adapter_role);
        ///////////SPINNER MODE









        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (UserRole.equals("ADMIN")) {

                    String user_admin = username.getText().toString().trim();
                    String pass_admin = password.getText().toString().trim();

                    Toast.makeText(MainActivity.this, "ADMIN MOD LOGIN", Toast.LENGTH_SHORT).show();

                    if (user_admin.equals("ADMIN")&pass_admin.equals("ADMIN123"))
                    {
                        editor.putString("islogin","yes");
                        editor.commit();
                        OpenDash();

                    }else {
                        Toast.makeText(MainActivity.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                    }


                }
                else if (UserRole.equals("PATIENT"))
                {





                    String user_patient = username.getText().toString().trim();
                    String pass_patient = password.getText().toString().trim();
                    if (user_patient.equals("") || pass_patient.equals("")) {





                    } else {
                        Boolean checkuserpass = dbHelper.CheckUsername_password(user_patient,pass_patient);
                        if (checkuserpass == true) {
                            Toast.makeText(MainActivity.this, "LOG IN", Toast.LENGTH_SHORT).show();

                            editor_patient.putString("islogin_patient","yes");
                            editor_patient.putString("username",user_patient);
                            editor_patient.commit();

                            OpenDash_Patient();

                        } else {
                            Toast.makeText(MainActivity.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                        }
                    }



                }
                else
                {
                    Toast.makeText(MainActivity.this,"DOCTOR MODE LOGIN",Toast.LENGTH_SHORT).show();

                    String user = username.getText().toString().trim();
                    String pass = password.getText().toString().trim();
                    if (user.equals("") || pass.equals("")) {

                        Toast.makeText(MainActivity.this, "PLESE FILL", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean checkuserpass = dbHelper_doc.CheckUsername_password(user,pass);
                        if (checkuserpass == true) {
                            Toast.makeText(MainActivity.this, "LOG IN", Toast.LENGTH_SHORT).show();

                           editor_doctor.putString("islogin_doctor","yes");
                            editor_doctor.putString("username_doctor",user);
                           editor_doctor.commit();
                           OpenDash_Doctor();

                        } else {
                            Toast.makeText(MainActivity.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                        }
                    }



                }

            }
        });













//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////SIGN BUTTON///////

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1= new Intent(MainActivity.this,Login2.class);
                startActivity(i1);

            }
        });


    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
    private void OpenDash_Doctor()
    {

        startActivity(new Intent(MainActivity.this,Home_Doctor.class));
        finish();

    }

    private void OpenDash_Patient()
    {
        username=findViewById(R.id.LUSERNAME);
        String patient=username.getText().toString().trim();
        Intent intent=new Intent(MainActivity.this,Home_Patient.class);
        intent.putExtra("patient_display",patient);
        startActivity(intent);
        finish();

    }


    private void OpenDash()
    {

        startActivity(new Intent(MainActivity.this,Home_Activity.class));
        finish();

    }


}