package com.example.hosp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
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
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class NotifiCation extends AppCompatActivity {

    RecyclerView recyclerView_notification;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    notification_adapter adapter;
    Doctor_Request_Helper helper;
    ArrayList<String> Doctor_Name;
    ArrayList<String> Patient_Name,Mobile;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);




        sharedPreferences=this.getSharedPreferences("Login",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        if (sharedPreferences.getString("islogin","false").equals("false")){

            OpenLogin();
        }


        swipeRefreshLayout=findViewById(R.id.SWIPE_TO_REFRESH_NOTIFICATION);

        drawerLayout = findViewById(R.id.drawerLayout1);

        recyclerView_notification=findViewById(R.id.RECYCLER_NOTI);

        helper =new Doctor_Request_Helper(this);
        Doctor_Name=new ArrayList<String>();
        Patient_Name=new ArrayList<String>();
        Mobile=new ArrayList<String>();
        adapter = new notification_adapter(this,Doctor_Name,Patient_Name,Mobile);
        recyclerView_notification.setAdapter(adapter);
        recyclerView_notification.setLayoutManager(new LinearLayoutManager(this));
        SetToolBar();
        DisplayData();
        Refresh();
    }




    private void DisplayData() {
        Cursor cursor = helper.Getdata();
        if (cursor.getCount() == 0) {
            Toast.makeText(NotifiCation.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {

              Doctor_Name.add(cursor.getString(0));
              Mobile.add(cursor.getString(1));
              Patient_Name.add(cursor.getString(2));

            }

        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    }








    private void OpenLogin()
    {
        startActivity(new Intent(NotifiCation.this,MainActivity.class));
        finish();

    }





    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NotifiCation.this, Home_Activity.class);
        startActivity(intent);
    }



    ///////////////set title to tool bar////////
    private void SetToolBar() {

        toolbar = findViewById(R.id.TOOLBAR);
        TextView title = toolbar.findViewById(R.id.TITLE_TOOL_BAR);
        TextView subtitle = toolbar.findViewById(R.id.SUBTITLE_TOOL_BAR);
        title.setText("NOTIFICATION");
        subtitle.setText("");

///////////////set title to tool bar////////
    }




    ////////////////////////////////////////////////Menu Display//////////////////////////////////////////
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

        Home_Activity.redirectActivity(this, View_patient_List.class);
    }

    public void ClickDoctorList(View view) {

        Home_Activity.redirectActivity(this, View_doctor_List.class);
    }


    public void ClickNotifiCation(View view) {

        recreate();

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



    private void openlink(String s)
    {
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

}