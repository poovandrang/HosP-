package com.example.hosp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class View_patient_List extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ArrayList<String> username, name, mobile, mail, age,gender;
    RecyclerView recyclerView_patient_list;
    My_adapter_pat adapter;
    DBHelper DB;
    Toolbar toolbar;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_list);


        sharedPreferences=this.getSharedPreferences("Login",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        if (sharedPreferences.getString("islogin","false").equals("false")){

            OpenLogin();
        }





        drawerLayout = findViewById(R.id.drawerLayout_Patient_list);
        recyclerView_patient_list = findViewById(R.id.RYCYCLER_PATIENT_LIST);
        SetToolBar();





        ////////////////////////////Recycler View/////////////////////////////////////////////////////////

        DB = new DBHelper(this);
        username = new ArrayList<>();
        name = new ArrayList<>();
        mobile = new ArrayList<>();
        mail = new ArrayList<>();
        age = new ArrayList<>();
        gender=new ArrayList<>();


        adapter = new My_adapter_pat(this, username, mobile, mail, age,gender);
        recyclerView_patient_list.setAdapter(adapter);
        recyclerView_patient_list.setLayoutManager(new LinearLayoutManager(this));
        DisplayData();

        ////////////////////////////Recycler View/////////////////////////////////////////////////////////


    }


    ///////////////////////////Display Data in RecyclerView///////////////////////////////////////////////////////////
    private void DisplayData() {
        Cursor cursor = DB.Getdata();
        if (cursor.getCount() == 0) {
            Toast.makeText(View_patient_List.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {

                username.add(cursor.getString(0));
                mobile.add(cursor.getString(3));
                mail.add(cursor.getString(4));
                age.add(cursor.getString(5));
                gender.add(cursor.getString(6));

            }

        }
    }
///////////////////////////Display Data in RecyclerView//////////////////////////////////////////////////////////


    public void ClickMenu(View view) {

        Home_Activity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {

        Home_Activity.closeDrawer(drawerLayout);

    }

    public void ClickHome(View view) {

        Home_Activity.redirectActivity(this, Home_Activity.class);
    }

    public void ClickPatientList(View view) {

        recreate();
    }

    public void ClickDoctorList(View view) {

        Home_Activity.redirectActivity(this, View_doctor_List.class);
    }


    public void ClickNotifiCation (View view) {


        Home_Activity.redirectActivity(this, NotifiCation.class);
    }


    public void ClickAboutUs(View view) {

        Home_Activity.redirectActivity(this, About_Us.class);

    }
    public void ClickStatus(View view) {

        Home_Activity.redirectActivity(this, Table_Status_Appo.class);

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
        Home_Activity.closeDrawer(drawerLayout);
    }

    public void SetToolBar(){

        toolbar=findViewById(R.id.TOOLBAR);
        TextView title=toolbar.findViewById(R.id.TITLE_TOOL_BAR);
        TextView subtitle=toolbar.findViewById(R.id.SUBTITLE_TOOL_BAR);
        title.setText("PATIENT");
        subtitle.setText("LIST");

    }

    private void OpenLogin()
    {
        startActivity(new Intent(View_patient_List.this,MainActivity.class));
        finish();

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(View_patient_List.this, Home_Activity.class);
        startActivity(intent);
    }



    private void openlink(String s)
    {
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

}