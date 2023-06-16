package com.example.hosp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Notification_Page_Patient extends AppCompatActivity {

    RecyclerView recyclerView;
    Status_Helper helper;
    ArrayList<String> doc_name,date;
    Patient_Notification_Adapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    private SharedPreferences sharedPreferences_patient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page_patient);
        recyclerView=findViewById(R.id.NOTIFICATION_PATIENT);
        swipeRefreshLayout=findViewById(R.id.SWIPE_TO_REFRESH_NOTIFICATION_Patient);



        helper=new Status_Helper(this);
        doc_name=new ArrayList<>();
        date=new ArrayList<>();

        adapter = new Patient_Notification_Adapter(this, doc_name,date);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences_patient = this.getSharedPreferences("Login_Patient", MODE_PRIVATE);
        String username=sharedPreferences_patient.getString("username","");

        DisplayData(username);
        Refresh();









    }


    ///////////////////////////Display Data in RecyclerView///////////////////////////////////////////////////////////
    private void DisplayData(String username) {
        Cursor cursor = helper.Getdata2(username);
        if (cursor.getCount() == 0) {
            Toast.makeText(Notification_Page_Patient.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {

                doc_name.add(cursor.getString(0));
                date.add(cursor.getString(2));

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