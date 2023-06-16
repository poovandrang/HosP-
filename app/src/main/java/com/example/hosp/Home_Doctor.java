package com.example.hosp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Home_Doctor extends AppCompatActivity {

    Toolbar toolbardoc;
    RecyclerView recyclerView_doctor_View;
    Appointment_Helper helper;
    ArrayList<String> patient_name;
    Appointment_Adapter_Doctor adapter_app;
    ArrayList<String> mobile;
    ArrayList<String> time;
    ArrayList<String> specialist,appointment_date;
    ArrayList<String> date;
    TextView user_display;


    private SharedPreferences sharedPreferences_doctor;
    private SharedPreferences.Editor editor_doctor;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_doctor);



        sharedPreferences_doctor = this.getSharedPreferences("Login_Doctor", MODE_PRIVATE);
        editor_doctor = sharedPreferences_doctor.edit();

        if (sharedPreferences_doctor.getString("islogin_doctor", "false").equals("false")) {
            OpenLogin_Doctor();
        }








        recyclerView_doctor_View=findViewById(R.id.Doctor_Appointment_View);
        user_display=findViewById(R.id.Display_Username_Doctor);



        String username=sharedPreferences_doctor.getString("username_doctor","");
        user_display.setText(username);


        SetToolBar();
        ImageView MenuBtn=toolbardoc.findViewById(R.id.TOOL_BAR_MENU);
        MenuBtn.setVisibility(View.GONE);




        ////////////////////////////Recycler View/////////////////////////////////////////////////////////

        helper = new Appointment_Helper(this);
        patient_name = new ArrayList<>();
        mobile = new ArrayList<>();
        time = new ArrayList<>();
        specialist = new ArrayList<>();
        date=new ArrayList<>();
        appointment_date=new ArrayList<>();



        adapter_app=new Appointment_Adapter_Doctor(this,patient_name,mobile,time,specialist,date,appointment_date);
        recyclerView_doctor_View.setAdapter(adapter_app);
        recyclerView_doctor_View.setLayoutManager(new LinearLayoutManager(this));
        DisplayData(username);


        ////////////////////////////Recycler View/////////////////////////////////////////////////////////





    }




    ///////////////////////////Display Data in RecyclerView///////////////////////////////////////////////////////////
    private void DisplayData(String doctor) {
        Cursor cursor = helper.Getdata_Doc(doctor);
        if (cursor.getCount() == 0) {
            Toast.makeText(Home_Doctor.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {



                patient_name.add(cursor.getString(0));
                mobile.add(cursor.getString(1));
                time.add(cursor.getString(2));
                specialist.add(cursor.getString(3));
                date.add(cursor.getString(4));
                appointment_date.add(cursor.getString(5));





            }

        }

    }



    ///////////////set title to tool bar////////
    private void SetToolBar(){

        toolbardoc=findViewById(R.id.TOOLBAR);
        TextView title=toolbardoc.findViewById(R.id.TITLE_TOOL_BAR);
        TextView subtitle=toolbardoc.findViewById(R.id.SUBTITLE_TOOL_BAR);

        toolbardoc.inflateMenu(R.menu.menu_doctor);
        toolbardoc.setOnMenuItemClickListener(menuItem->onMenuItemClick(menuItem));
        title.setText("HOME");
        subtitle.setText("DOCTOR");

///////////////set title to tool bar////////

    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert=new AlertDialog.Builder(Home_Doctor.this);
        alert.setTitle("EXIT");
        alert.setMessage("Do You Want To Exit App ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        alert.show();
    }


    private boolean onMenuItemClick(MenuItem menuItem) {

        if (menuItem.getItemId()==R.id.EXIT_MENU_VIEW_DOCTOR){


            Toast.makeText(this, "EXIT", Toast.LENGTH_SHORT).show();

            android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
            builder.setMessage("Do You Want to Logout ?");
            builder.setTitle("Exit");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    editor_doctor.putString("islogin_doctor","false");
                    editor_doctor.commit();
                    OpenLogin_Doctor();


                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();
                }
            });
            builder.show();




        }else if (menuItem.getItemId()==R.id.ABOUT_MENU_VIEW_DOCTOR)
        {
            startActivity(new Intent(Home_Doctor.this,About_doc_pat.class));
        }
        else if (menuItem.getItemId()==R.id.REACH_US_VIEW_DOC)
        {
            openlink("https://maps.app.goo.gl/nDAJozPfebP4KAQJ6");
        }
        else if (menuItem.getItemId()==R.id.NOTIFICATION_VIEW_DOC)
        {
            startActivity(new Intent(Home_Doctor.this,Notification_page_doctor.class));
        }
        else if (menuItem.getItemId()==R.id.RATING_MENU_VIEW_DOCTOR)
        {
            startActivity(new Intent(Home_Doctor.this,Display_Rating.class));
        }


        return true;
    }


    private void OpenLogin_Doctor() {

        startActivity(new Intent(Home_Doctor.this,MainActivity.class));
        finish();

    }

    private void openlink(String s)
    {
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }






}