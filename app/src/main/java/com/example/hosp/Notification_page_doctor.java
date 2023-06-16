package com.example.hosp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class Notification_page_doctor extends AppCompatActivity {

    RecyclerView recyclerView;
    specialist_helper helper;
    Notification_Doctor_Adapter adapter;
    ArrayList<String> Patient,Time,Date,Doctor_Name,Mobile;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page_doctor);

        recyclerView=findViewById(R.id.NOTIFICATION_DOCTOR);
        swipeRefreshLayout=findViewById(R.id.SWIPE_TO_REFRESH_NOTIFICATION_Doctor);


        helper=new specialist_helper(this);
        Patient=new ArrayList<>();
        Time=new ArrayList<>();
        Date=new ArrayList<>();
        Doctor_Name=new ArrayList<>();
        Mobile=new ArrayList<>();


        adapter = new Notification_Doctor_Adapter(this, Patient,Time,Date,Doctor_Name,Mobile);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DisplayData();
        Refresh();



    }


    ///////////////////////////Display Data in RecyclerView///////////////////////////////////////////////////////////
    private void DisplayData() {
        Cursor cursor = helper.Getdata();
        if (cursor.getCount() == 0) {
            Toast.makeText(Notification_page_doctor.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {

                Patient.add(cursor.getString(0));
                Time.add(cursor.getString(1));
                Date.add(cursor.getString(2));
                Doctor_Name.add(cursor.getString(3));
                Mobile.add(cursor.getString(4));

            }

        }
    }
///////////////////////////Display Data in RecyclerView//////////////////////////////////////////////////////////

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








}