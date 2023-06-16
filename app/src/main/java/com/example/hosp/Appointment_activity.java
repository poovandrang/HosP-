package com.example.hosp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Appointment_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, ActivityCompat.OnRequestPermissionsResultCallback {

   public TextView Mobile,Appointment_date;



   DBHelper DBhelper=new DBHelper(this);


   TextView Patient_name;
   Spinner Time,Specialist,getSpecialist;
   Button Book;
   TextView Date,Doc,Click,Rateing,TimeInG;
   Appointment_Helper appointment_helper;
   String App_Time,APP_Specialist,Doctor_Req;

    int T1hour,T1minute;


    RelativeLayout relativeLayout;

    SwipeRefreshLayout swipeRefreshLayout;


    RecyclerView recyclerView;
    ArrayList<String> Doctor,Rate;
    Orthodonist_Adapter adapter_OD;
   Endodontist_Adapter adapter_ED;
   Dental_Surgeon_Adapter adapter_DS;

    Helper_doc helper_doc;
    ArrayList<String> doc_name,specialist,rating;
    Rate_Notif_adapter_patient adapter;



    Doc_UNR helper;
    specialist_helper specialistHelper;
    ImageView calender_btn,time_work,Mobile_Btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);


        SharedPreferences sharedPreferences_patient;
        sharedPreferences_patient = this.getSharedPreferences("Login_Patient", MODE_PRIVATE);



        recyclerView=findViewById(R.id.DOCTOR_LIST_RECYCLER);


        helper_doc=new Helper_doc(this);
        doc_name=new ArrayList<>();
        specialist=new ArrayList<>();
        rating=new ArrayList<>();



        adapter = new Rate_Notif_adapter_patient(this, doc_name,specialist,rating);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //DisplayData();

        relativeLayout=findViewById(R.id.RELATED_LAYOUT);
        relativeLayout.setVisibility(View.GONE);






        Patient_name=findViewById(R.id.PATIENT_NAME);
        Mobile=findViewById(R.id.PATIENT_MOBILE);
        Appointment_date=findViewById(R.id.APPOINTMENT_DATE);
        Date=findViewById(R.id.DaTe);
        TimeInG=findViewById(R.id.APPOINTMENT__TIMEING);
        Book=findViewById(R.id.BOOK_APPOINTMENT);
        getSpecialist=findViewById(R.id.SPINNER_LISTDOCTOR);

       // Click=findViewById(R.id.CLICK_VIEW);


        Doc=findViewById(R.id.DDDDDD);

        Rateing=findViewById(R.id.RATE_RATE_RATE);

        //recyclerView=findViewById(R.id.DOCTOR_LIST_RECYCLER);

        swipeRefreshLayout=findViewById(R.id.SWIPE_TO_REFRESH_APPO);


        calender_btn=findViewById(R.id.CALENDER_APPO);
        time_work=findViewById(R.id.CALENDER_APPO_TIME);
        Mobile_Btn=findViewById(R.id.MOBILE_IMG_APPO);

        String username=sharedPreferences_patient.getString("username","");
        Patient_name.setText(username);




        time_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePicker_Dialog();
            }
        });


   ///////////////////////////////////////////////////////////

       final Calendar calendar=Calendar.getInstance();

        calender_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker=new Date_Picker_Fragement();
                datePicker.show(getSupportFragmentManager(),"Date Picker");
            }
        });

        Mobile_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mob= DBhelper.getMobile(username);
                Mobile.setText(mob);



            }
        });


///////////////////////////////////////////////////////////////////




        appointment_helper=new Appointment_Helper(this);
        helper=new Doc_UNR(this);
        specialistHelper=new specialist_helper(this);
        Doctor=new ArrayList<>();
        Rate=new ArrayList<>();



        /*SPINNER*/
        Time=findViewById(R.id.SPINNER_APPOINTMENT_TIME);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(
                this,R.array.Time,
                R.layout.color_spinner_layout
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        Time.setAdapter(adapter);

        Time.setOnItemSelectedListener(this);


        Specialist=findViewById(R.id.SPINNER_APPOINTMENT_DOCTOR);
        ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(
                this,R.array.Specialist,
                R.layout.color_spinner_layout);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_layout);

        Specialist.setAdapter(adapter2);

        Specialist.setOnItemSelectedListener(this);







        APP_Specialist=Specialist.getSelectedItem().toString();
        App_Time=Time.getSelectedItem().toString();

     /*SPINNER*/









        Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                String name=Patient_name.getText().toString();
                String mobile=Mobile.getText().toString();
                String appointment_date=Appointment_date.getText().toString();
              //  String timeing=TimeInG.getText().toString();
                String date=getDate();

                App_Time=Time.getSelectedItem().toString();
                APP_Specialist=Specialist.getSelectedItem().toString();









                if (name.isEmpty()){
                    Patient_name.setError("No Username Found");
                }else if (mobile.isEmpty()){

                    Mobile.setError("Enter Mobile Number");
                }else if (mobile.length()<10){

                    Mobile.setError("Enter valid Mobile Number");
                }

                else if (appointment_date.isEmpty()){
                    Appointment_date.setError("Enter Appointment date");
                }else if (Time.getSelectedItem().toString().equals("Select")){
                    Toast.makeText(Appointment_activity.this, "Select Time", Toast.LENGTH_SHORT).show();
                }
                /*else if (timeing.isEmpty()){

                    Toast.makeText(Appointment_activity.this, "Select Time", Toast.LENGTH_SHORT).show();

                }*/



                else if (Specialist.getSelectedItem().toString().equals("Select")){
                    Toast.makeText(Appointment_activity.this, "Select Specialist", Toast.LENGTH_SHORT).show();
                }
                else if (appointment_helper.CheckDate(appointment_date)&&appointment_helper.CheckTime(App_Time))
                {
                    DialogTime();
                }
                else{

                    APP_Specialist=Specialist.getSelectedItem().toString();
                    App_Time=Time.getSelectedItem().toString();


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                   helper.InsertData(name);



                    try {

                        Doctor_Req=getSpecialist.getSelectedItem().toString();
                        specialistHelper.Insert_Doc_Notification(name,App_Time,appointment_date,Doctor_Req,mobile);
                    }catch (Exception exception){
                        Toast.makeText(Appointment_activity.this, "No Doctor Selected", Toast.LENGTH_SHORT).show();
                    }






////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



                        Boolean insert=appointment_helper.InsertData_Apointment(name,mobile,App_Time,APP_Specialist,date,appointment_date,Doctor_Req);
                        if (insert==true)
                        {
                            SharedPreferences mySharePref_status= getSharedPreferences("Current_Date", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor_conform=mySharePref_status.edit();
                            editor_conform.putString("Date",date);
                            editor_conform.putString("Mobile",mobile);
                            editor_conform.apply();





                            Toast.makeText(Appointment_activity.this,"Successfully Booked",Toast.LENGTH_SHORT).show();





                            Dialog();

                        }else
                        {
                            Toast.makeText(Appointment_activity.this,"Failed",Toast.LENGTH_SHORT).show();
                        }





                }

     //////////////////////////////////////////////////////////////////////////////////////////////////




            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {



        if (i==1){


        /*   adapter_DS = new Dental_Surgeon_Adapter(this, Doctor,Rate);
            recyclerView.setAdapter(adapter_DS);
            LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
            recyclerView.setLayoutManager(layoutManager);
            DisplayData_DS();
            recyclerView.removeAllViewsInLayout();


         */
            LoadSpinner1();





        }else if (i==2){

            /*adapter_ED = new Endodontist_Adapter(this, Doctor,Rate);
            recyclerView.setAdapter(adapter_ED);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            DisplayData_ED();
            recyclerView.removeAllViews();

             */
            LoadSpinner2();




        }else if (i==3){
           /*adapter_OD = new Orthodonist_Adapter(this, Doctor,Rate);
            recyclerView.setAdapter(adapter_OD);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            DisplayData_OD();
            recyclerView.removeAllViews();

            */
            LoadSpinner3();
        }





        if (Specialist.getSelectedItem().equals("Dental Surgeon")||Specialist.getSelectedItem().equals("Endodontist")||Specialist.getSelectedItem().equals("Orthodonist")){
          // relativeLayout.setVisibility(View.VISIBLE);
           Doc.setVisibility(View.VISIBLE);

           getSpecialist.setVisibility(View.VISIBLE);





        }else {
            //relativeLayout.setVisibility(View.GONE);
            Doc.setVisibility(View.GONE);
            getSpecialist.setVisibility(View.GONE);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }


    private void LoadSpinner1(){

        getSpecialist=findViewById(R.id.SPINNER_LISTDOCTOR);
        specialist_helper helper=new specialist_helper(getApplicationContext());
        List<String> labels=helper.getAllLabels();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.color_spinner_layout,labels);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        getSpecialist.setAdapter(adapter);




    }

    private void LoadSpinner2(){

        getSpecialist=findViewById(R.id.SPINNER_LISTDOCTOR);
        specialist_helper helper=new specialist_helper(getApplicationContext());
        List<String> labels=helper.getAllLabels1();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.color_spinner_layout,labels);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        getSpecialist.setAdapter(adapter);

    }

    private void LoadSpinner3(){

        getSpecialist=findViewById(R.id.SPINNER_LISTDOCTOR);
        specialist_helper helper=new specialist_helper(getApplicationContext());
        List<String> labels=helper.getAllLabels2();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.color_spinner_layout,labels);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        getSpecialist.setAdapter(adapter);

    }








    ///////////////////////////Display Data in RecyclerView///////////////////////////////////////////////////////////
    private void DisplayData_DS() {
        Cursor cursor = specialistHelper.Getdata_DS();
        if (cursor.getCount() == 0) {
            Toast.makeText(Appointment_activity.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {

              //  Doctor.add(cursor.getString(0));
              //  Rate.add(cursor.getString(1));

            }

        }
    }
///////////////////////////Display Data in RecyclerView//////////////////////////////////////////////////////////

    ///////////////////////////Display Data in RecyclerView///////////////////////////////////////////////////////////
    private void DisplayData_ED() {
        Cursor cursor = specialistHelper.Getdata_ED();
        if (cursor.getCount() == 0) {
            Toast.makeText(Appointment_activity.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {

          //      Doctor.add(cursor.getString(0));
           //     Rate.add(cursor.getString(1));

            }

        }
    }
///////////////////////////Display Data in RecyclerView//////////////////////////////////////////////////////////

    ///////////////////////////Display Data in RecyclerView///////////////////////////////////////////////////////////
    private void DisplayData_OD() {
        Cursor cursor = specialistHelper.Getdata_OD();
        if (cursor.getCount() == 0) {
            Toast.makeText(Appointment_activity.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {

              //  Doctor.add(cursor.getString(0));
              //  Rate.add(cursor.getString(1));

            }

        }
    }
///////////////////////////Display Data in RecyclerView//////////////////////////////////////////////////////////














    public void Dialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Appointment_activity.this);
        builder.setMessage("Your Request is SuccessFully Sent Our Staffs will Confirm your Appointment With in 2 hours !");
        builder.setTitle("Note");
        builder.setCancelable(false);
        builder.setPositiveButton("OkaY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finish();
            }
        });

        builder.show();
    }

    public void DialogTime() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Appointment_activity.this);
        builder.setMessage("There is an another appointment booked in that time Slot.\n\nSo Kindly select another time Slot");
        builder.setTitle("WARNING");
        builder.setCancelable(false);
        builder.setPositiveButton("OkaY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        builder.show();
    }




    public  void TimePicker_Dialog() {


        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,


                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        T1hour = i;
                        T1minute = i1;
                        String time = T1hour + ":" + T1minute;
                        SimpleDateFormat f24hours = new SimpleDateFormat("hh:mm");

                        try {
                            Date date = f24hours.parse(time);

                            SimpleDateFormat f12 = new SimpleDateFormat("hh:mm aa");

                            TimeInG.setText(f12.format(date));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }, 12, 0, false);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        timePickerDialog.updateTime(T1hour,T1minute);

        timePickerDialog.show();

    }


    public final String getDate() {

        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

    }


    public final String getTIME() {

        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

    }




    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,i);
        calendar.set(Calendar.MONTH,i1);
        calendar.set(Calendar.DAY_OF_MONTH,i2);
        String CurrentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        Appointment_date.setText(CurrentDate);


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



 /*   ///////////////////////////Display Data in RecyclerView///////////////////////////////////////////////////////////
    private void DisplayData() {
        Cursor cursor = helper_doc.Getdata();
        if (cursor.getCount() == 0) {
            Toast.makeText(Appointment_activity.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
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


  */


    private void TimeDialog(){


        final Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);



        TimePickerDialog dialog=new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes) {

                TimeInG=findViewById(R.id.APPOINTMENT__TIMEING);
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minutes);
                TimeInG.setText(selectedTime);



            }
        }, 12, 00, true);




        dialog.show();

    }






}