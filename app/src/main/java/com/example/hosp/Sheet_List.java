package com.example.hosp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Sheet_List extends AppCompatActivity {

    ListView listView_pat,listView_mob,listView_mail,listView_age,listView_gender;
    DBHelper helper;
    List<Patient_List> patient_lists=new ArrayList<Patient_List>();
    String Names[];
    String Mobiles[];
    String Mails[];
    String Age[];
    String Gender[];

    int p=0,m=0,e=0,a=0,g=0;



    @SuppressLint({"WrongViewCast", "Range"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_list);


        listView_pat=findViewById(R.id.PATIENT_LIST);
        listView_mob=findViewById(R.id.PATIENT_LIST_MOBILE);
        listView_mail=findViewById(R.id.PATIENT_LIST_MAIL);
        listView_age=findViewById(R.id.PATIENT_LIST_AGE);
        listView_gender=findViewById(R.id.PATIENT_LIST_GENDER);
        helper=new DBHelper(getApplicationContext());
        Cursor cursor=helper.Getdata();
        Names=new String[cursor.getCount()];
        Mobiles=new String[cursor.getCount()];
        Mails=new String[cursor.getCount()];
        Age=new String[cursor.getCount()];
        Gender=new String[cursor.getCount()];

        while (cursor.moveToNext()){

            Names[p]=cursor.getString(cursor.getColumnIndex("Username"));
            Mobiles[m]=cursor.getString(cursor.getColumnIndex("Mobile"));
            Mails[e]=cursor.getString(cursor.getColumnIndex("Email"));
            Age[a]=cursor.getString(cursor.getColumnIndex("Age"));
            Gender[g]=cursor.getString(cursor.getColumnIndex("Gender"));
            patient_lists.add(new Patient_List(cursor.getString(cursor.getColumnIndex("Username")),cursor.getString(cursor.getColumnIndex("Mobile")),cursor.getString(cursor.getColumnIndex("Email")),cursor.getString(cursor.getColumnIndex("Age")),cursor.getString(cursor.getColumnIndex("Gender"))));
            p++;
            m++;
            e++;
            a++;
            g++;

        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Names);
        listView_pat.setAdapter(arrayAdapter);


        ArrayAdapter<String> arrayAdapter_mob=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,Mobiles);
        listView_mob.setAdapter(arrayAdapter_mob);

        ArrayAdapter<String> arrayAdapter_mail=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,Mails);
        listView_mail.setAdapter(arrayAdapter_mail);

        ArrayAdapter<String> arrayAdapter_age=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,Age);
        listView_age.setAdapter(arrayAdapter_age);

        ArrayAdapter<String> arrayAdapter_gender=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,Gender);
        listView_gender.setAdapter(arrayAdapter_gender);


    }
}