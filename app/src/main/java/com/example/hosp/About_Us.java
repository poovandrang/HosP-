package com.example.hosp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About_Us extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);



        sharedPreferences=this.getSharedPreferences("Login",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        if (sharedPreferences.getString("islogin","false").equals("false")){

            OpenLogin();
        }




        drawerLayout = findViewById(R.id.drawerLayout2);
        SetToolBar();


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


        Home_Activity.redirectActivity(this, NotifiCation.class);
    }


    public void ClickAboutUs(View view) {

        recreate();

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


    ///////////////set title to tool bar////////
    private void SetToolBar() {

        toolbar = findViewById(R.id.TOOLBAR);
        TextView title = toolbar.findViewById(R.id.TITLE_TOOL_BAR);
        TextView subtitle = toolbar.findViewById(R.id.SUBTITLE_TOOL_BAR);
        title.setText("ABOUT US");
        subtitle.setText("");

///////////////set title to tool bar////////
    }


    private void OpenLogin()
    {
        startActivity(new Intent(About_Us.this,MainActivity.class));
        finish();

    }




    @Override
    public void onBackPressed() {
        Intent intent = new Intent(About_Us.this, Home_Activity.class);
        startActivity(intent);
    }


    private void openlink(String s)
    {
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

}