package com.example.hosp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class View_list_Content extends AppCompatActivity {

    DBHelper helper;
    ArrayList<Patient_List> list;
    ListView listView;
    Patient_List user;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.view_list_content);

        helper=new DBHelper(this);

        list=new ArrayList<>();
        Cursor cursor=helper.Getdata();
        int NOROW=cursor.getCount();
        if (NOROW==0){
            Toast.makeText(this, "Empty DB", Toast.LENGTH_SHORT).show();

        }else {

            user=new Patient_List(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            list.add(user);

        }
        ThreeColumn_List_Adapter adapter=new  ThreeColumn_List_Adapter(this,R.layout.list_adapter,list);
        listView=findViewById(R.id.listview);
        listView.setAdapter(adapter);

    }
}
