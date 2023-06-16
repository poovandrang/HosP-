package com.example.hosp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home_Activity extends AppCompatActivity {

    RecyclerView recyclerView_home;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    Appointment_Adapter_Admin adapter_app;
    Appointment_Helper helper;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<String> patient_name, mobile, time, specialist,appointment_date;
    ArrayList<String> date;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;






    // ArrayList<Appointment_List> appointment_lists=new ArrayList<>();
    //Appo_Adapter appo_adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SetToolBar();



        sharedPreferences=this.getSharedPreferences("Login",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        if (sharedPreferences.getString("islogin","false").equals("false")){

            OpenLogin();
        }







        swipeRefreshLayout=findViewById(R.id.SWIPE_TO_REFRESH_ADMIN);

        drawerLayout = findViewById(R.id.DrawerLayout);
        toolbar = findViewById(R.id.TOOLBAR);
        recyclerView_home = findViewById(R.id.RYCYCLER_MENU);




        ////////////////////////////Recycler View/////////////////////////////////////////////////////////

        helper = new Appointment_Helper(this);
        patient_name = new ArrayList<>();
        mobile = new ArrayList<>();
        time = new ArrayList<>();
        specialist = new ArrayList<>();
        date = new ArrayList<>();
        appointment_date=new ArrayList<>();







        adapter_app = new Appointment_Adapter_Admin(this,patient_name, mobile, time, specialist,date,appointment_date);
        recyclerView_home.setAdapter(adapter_app);
        recyclerView_home.setLayoutManager(new LinearLayoutManager(this));









        //appo_adapter=new Appo_Adapter(this,appointment_lists);
        //recyclerView_home.setAdapter(appo_adapter);
        //recyclerView_home.setLayoutManager(new LinearLayoutManager(this));





        //addData();
        Refresh();

        ////////////////////////////Recycler View/////////////////////////////////////////////////////////








    }




    private void Refresh()
    {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh()
            {
                swipeRefreshLayout.setRefreshing(false);


                recreate();

                }
            }) ;

        }


    private void DisplayData() {
        Cursor cursor = helper.Getdata();
        if (cursor.getCount() == 0) {
            Toast.makeText(Home_Activity.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
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
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    }






///////////////////////////Display Data in RecyclerView//////////////////////////////////////////////////////////



    ////////////////////////////////////////////////Menu Display//////////////////////////////////////////
    public void ClickMenu(View view) {

        openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);

    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }


    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    public void ClickHome(View view) {

        recreate();
    }

    public void ClickPatientList(View view) {

        redirectActivity(this, View_patient_List.class);
    }

    public void ClickDoctorList(View view) {

        redirectActivity(this, View_doctor_List.class);
    }

    public void ClickNotifiCation(View view) {

        redirectActivity(this, NotifiCation.class);

    }


    public void ClickAboutUs(View view) {

        redirectActivity(this, About_Us.class);

    }
    public void ClickStatus(View view) {

        Home_Activity.redirectActivity(this, Table_Status_Appo.class);

    }
    public void ClickReach_Us(View  view){

        openlink("https://maps.app.goo.gl/nDAJozPfebP4KAQJ6");


    }

    public void ClickLogout(View view) {

        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
        builder.setMessage("Do You Logout ?");
        builder.setTitle("LOGOUT");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                editor.putString("islogin","false");
                editor.commit();
                OpenLogin();


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


    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();

        closeDrawer(drawerLayout);
    }


    ////////////////////////////////////////Menu Display//////////////////////////////////////////
    ///////////////set title to tool bar////////
    private void SetToolBar() {

        toolbar = findViewById(R.id.TOOLBAR);
        TextView title = toolbar.findViewById(R.id.TITLE_TOOL_BAR);
        TextView subtitle = toolbar.findViewById(R.id.SUBTITLE_TOOL_BAR);
        title.setText("HOME");
        subtitle.setText("ADMIN");


    }


  /*  private void addData() {


        Cursor cursor = helper.Getdata();
        if (cursor.getCount() == 0) {
            Toast.makeText(Home_Activity.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {

               Appointment_List appointment=new Appointment_List(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
               appointment_lists.add(appointment);



            }

        }


    }*/







    private void OpenLogin()
    {
        startActivity(new Intent(Home_Activity.this,MainActivity.class));
        finish();

    }






















    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert=new AlertDialog.Builder(Home_Activity.this);
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

    private void openlink(String s)
    {
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }





}


