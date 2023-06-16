package com.example.hosp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class Patient_View_Doctor extends AppCompatActivity {

    RecyclerView recyclerView;
    Helper_doc helper;
    ArrayList<String> doc_name,specialist,rating;
    Rate_Notif_adapter_patient adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_notification);

        recyclerView=findViewById(R.id.RYCYCLER_RATE_DOC_LIST);

        helper=new Helper_doc(this);
        doc_name=new ArrayList<>();
        specialist=new ArrayList<>();
        rating=new ArrayList<>();
        adapter = new Rate_Notif_adapter_patient(this, doc_name,specialist,rating);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DisplayData();

    }

    ///////////////////////////Display Data in RecyclerView///////////////////////////////////////////////////////////
    private void DisplayData() {
        Cursor cursor = helper.Getdata_Display();
        if (cursor.getCount() == 0) {
            Toast.makeText(Patient_View_Doctor.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {

              doc_name.add(cursor.getString(0));
              specialist.add(cursor.getString(7));
              rating.add(cursor.getString(8));

            }

        }
    }
///////////////////////////Display Data in RecyclerView//////////////////////////////////////////////////////////



}