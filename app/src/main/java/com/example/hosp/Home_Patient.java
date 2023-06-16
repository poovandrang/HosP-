package com.example.hosp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Home_Patient extends AppCompatActivity {

    Toolbar toolbarpat;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView_patient_home ;
    Appointment_Adapter_Patient adapter_app;
    Appointment_Helper helper=new Appointment_Helper(this);
    ArrayList<String> patient_name;
    ArrayList<String> mobile;
    ArrayList<String> time;
    ArrayList<String> specialist,date,appointment_date,status;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView user_display;
    DBHelper DBhelper=new DBHelper(this);


    private SharedPreferences sharedPreferences_patient;
    private SharedPreferences.Editor editor_patient;




    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_patient);



        sharedPreferences_patient = this.getSharedPreferences("Login_Patient", MODE_PRIVATE);
        editor_patient = sharedPreferences_patient.edit();

        if (sharedPreferences_patient.getString("islogin_patient", "false").equals("false")) {
            OpenLogin_Patient();
        }






        floatingActionButton=findViewById(R.id.FLOATING_BTN);
        recyclerView_patient_home=findViewById(R.id.RYCYCLER_APPOINTMENT_LIST);
        user_display=findViewById(R.id.Display_Username);



        String username=sharedPreferences_patient.getString("username","");
        user_display.setText(username);












        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentFloating=new Intent(Home_Patient.this,Appointment_activity.class);
                startActivity(intentFloating);



            }



        });


        String patient=helper.Get_Patient(username);
        if (username.equals(patient)){

            floatingActionButton.setVisibility(View.GONE);

        }else {
            floatingActionButton.setVisibility(View.VISIBLE);
        }







        SetToolBar();
        ImageView MenuBtn=toolbarpat.findViewById(R.id.TOOL_BAR_MENU);
        MenuBtn.setVisibility(View.GONE);





        ////////////////////////////Recycler View/////////////////////////////////////////////////////////

        helper = new Appointment_Helper(this);
        patient_name = new ArrayList<>();
        mobile = new ArrayList<>();
        time = new ArrayList<>();
        specialist = new ArrayList<>();
        date = new ArrayList<>();
        appointment_date=new ArrayList<>();

        swipeRefreshLayout=findViewById(R.id.SWIPE_TO_REFRESH_PATIENT);
        adapter_app=new Appointment_Adapter_Patient(this,patient_name,mobile,time,specialist,date,appointment_date);
        recyclerView_patient_home.setAdapter(adapter_app);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        recyclerView_patient_home.setLayoutManager(layoutManager);
        DisplayData(username);







        Refresh();




































        ////////////////////////////Recycler View/////////////////////////////////////////////////////////



    }



    ///////////////set title to tool bar////////
    private void SetToolBar() {

        toolbarpat = findViewById(R.id.TOOLBAR);
        TextView title = toolbarpat.findViewById(R.id.TITLE_TOOL_BAR);
        TextView subtitle = toolbarpat.findViewById(R.id.SUBTITLE_TOOL_BAR);

        toolbarpat.inflateMenu(R.menu.menu_patient);
        toolbarpat.setOnMenuItemClickListener(menuItem->onMenuItemClick(menuItem));
        title.setText("HOME");
        subtitle.setText("PATIENT");


    }

    ///////////////set title to tool bar////////

    ///////////////////////////Display Data in RecyclerView///////////////////////////////////////////////////////////
    private void DisplayData(String username) {


        Cursor cursor = helper.Getdata_Meow(username);
        if (cursor.getCount() == 0) {
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


        ///////////////////////////////////////Refresh Code//////////////////////////////////////

    private void Refresh()
    {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(),"Refreshing",Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                recreate();
            }
        }) ;

        ///////////////////////////////////////Refresh Code//////////////////////////////////////



    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert=new AlertDialog.Builder(Home_Patient.this);
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

        if (menuItem.getItemId()==R.id.EXIT_MENU_VIEW){




            android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
            builder.setMessage("Do You Logout ?");
            builder.setTitle("LOGOUT");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    editor_patient.putString("islogin_patient","false");
                    editor_patient.commit();
                    OpenLogin_Patient();


                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();
                }
            });
            builder.show();

        }




        else if (menuItem.getItemId()==R.id.ABOUT_MENU_VIEW)
        {
            startActivity(new Intent(Home_Patient.this,About_doc_pat.class));
        }
        else if (menuItem.getItemId()==R.id.REACH_US_VIEW_PAT)
        {
            openlink("https://maps.app.goo.gl/nDAJozPfebP4KAQJ6");
        }
        else if (menuItem.getItemId()==R.id.DOCTORS_LIST_MENU_VIEW)
        {
            startActivity(new Intent(Home_Patient.this, Patient_View_Doctor.class));
        }
        else if (menuItem.getItemId()==R.id.NOTIFICATION_MENU_VIEW)
        {

            startActivity(new Intent(Home_Patient.this, Notification_Page_Patient.class));

        }

        return true;
    }



    private void OpenLogin_Patient() {

        startActivity(new Intent(Home_Patient.this,MainActivity.class));
        finish();

    }

    private void openlink(String s)
    {
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }


}


