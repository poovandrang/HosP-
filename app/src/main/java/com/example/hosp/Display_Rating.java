package com.example.hosp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Display_Rating extends AppCompatActivity {

    TextView rating;
    private SharedPreferences sharedPreferences_doctor;
    RecyclerView recyclerView;
    Previous_rate_adapter adapter;
    ArrayList Patient,Rate,Doctor;
    Previous_Rate_Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_rating);


        rating=findViewById(R.id.OVERALL_RATE);
        recyclerView=findViewById(R.id.Previous_Rating);



        sharedPreferences_doctor = this.getSharedPreferences("Login_Doctor", MODE_PRIVATE);
        String username=sharedPreferences_doctor.getString("username_doctor","");



        Helper_doc helper_doc=new Helper_doc(this);
        Float rate=helper_doc.getAverageScore(username);
        String r=rate.toString();
        rating.setText(r);




        helper=new Previous_Rate_Helper(this);
        Patient=new ArrayList();
        Rate=new ArrayList();
        Doctor=new ArrayList();


        adapter=new Previous_rate_adapter(this,Patient,Doctor,Rate);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DisplayData();


    }






    private void DisplayData() {
        Cursor cursor = helper.Getdata();
        if (cursor.getCount() == 0) {
            Toast.makeText(Display_Rating.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {



                Patient.add(cursor.getString(1));
                Rate.add(cursor.getString(2));
                Doctor.add(cursor.getString(0));






            }

        }

    }











}