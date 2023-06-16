package com.example.hosp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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
import java.util.List;

public class Table_Status_Appo extends AppCompatActivity {

    RecyclerView recyclerView;
    Table_Status_Adapter adapter;
    Status_Helper helper;
    ArrayList<String> patient_name;
    ArrayList<String> appointment_date;
    ArrayList<String> time;
    ArrayList<String> status;

    DrawerLayout drawerLayout3;
    Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_status_appo);

        drawerLayout3 = findViewById(R.id.drawerLayout_Table);
        SetToolBar();
        recyclerView=findViewById(R.id.TABLE_RECYCLER);



        sharedPreferences=this.getSharedPreferences("Login",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        if (sharedPreferences.getString("islogin","false").equals("false")){

            OpenLogin();
        }



        helper=new Status_Helper(this);
        patient_name=new ArrayList<>();
        appointment_date=new ArrayList<>();
        time=new ArrayList<>();
        status=new ArrayList<>();


        adapter = new Table_Status_Adapter(this,patient_name,appointment_date,time,status);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DisplayData();




    }


    ///////////////////////////Display Data in RecyclerView///////////////////////////////////////////////////////////
    private void DisplayData() {
        Cursor cursor = helper.Getdata();
        if (cursor.getCount() == 0) {
            Toast.makeText(Table_Status_Appo.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {



                patient_name.add(cursor.getString(0));
                time.add(cursor.getString(1));
                appointment_date.add(cursor.getString(2));
                status.add(cursor.getString(3));





            }

        }

    }
    ////////////////////////////////////////////////Menu Display//////////////////////////////////////////
    public void ClickMenu(View view) {

       Home_Activity.openDrawer(drawerLayout3);
    }

    public void ClickLogo(View view) {

        Home_Activity.closeDrawer(drawerLayout3);

    }

    public void ClickHome(View view) {

        Home_Activity.redirectActivity(this, Home_Activity.class);
    }

    public void ClickPatientList(View view) {

        Home_Activity.redirectActivity(this, View_patient_List.class);
    }

    public void ClickDoctorList(View view) {

        Home_Activity.redirectActivity(this, View_doctor_List.class);
    }


    public void ClickNotifiCation(View view) {


        Home_Activity.redirectActivity(this, NotifiCation.class);
    }


    public void ClickAboutUs(View view) {

        Home_Activity.redirectActivity(this,About_Us.class);

    }
    public void ClickStatus(View view) {

        recreate();

    }

    public void ClickReach_Us(View  view){

        openlink("https://maps.app.goo.gl/nDAJozPfebP4KAQJ6");


    }


    public void ClickLogout(View view) {

        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
        builder.setMessage("Do You Exit ?");
        builder.setTitle("Exit");
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

    @Override
    protected void onPause() {
        super.onPause();
        Home_Activity.closeDrawer(drawerLayout3);
    }





    ////////////////////////////////////////Menu Display//////////////////////////////////////////
    ///////////////set title to tool bar////////
    private void SetToolBar() {

        toolbar = findViewById(R.id.TOOLBAR);
        TextView title = toolbar.findViewById(R.id.TITLE_TOOL_BAR);
        TextView subtitle = toolbar.findViewById(R.id.SUBTITLE_TOOL_BAR);
        title.setText("END APPOINTMENT LIST");
        subtitle.setText("Doctor");


    }


    private void OpenLogin()
    {
        startActivity(new Intent(Table_Status_Appo.this,MainActivity.class));
        finish();

    }
















    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Table_Status_Appo.this, Home_Activity.class);
        startActivity(intent);
    }






    private void openlink(String s)
    {
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }







}

