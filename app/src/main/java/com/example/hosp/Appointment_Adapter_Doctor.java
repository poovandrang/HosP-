package com.example.hosp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Appointment_Adapter_Doctor extends RecyclerView.Adapter<Appointment_Adapter_Doctor.MyViewHolder>implements ActivityCompat.OnRequestPermissionsResultCallback{

    private Context context;
    private ArrayList patient_name,mobile,time,specialist,appointment_date;
    private ArrayList date;






    public Appointment_Adapter_Doctor(Context context, ArrayList patient_name, ArrayList mobile, ArrayList time, ArrayList specialist, ArrayList date, ArrayList appointment_date) {
        this.context = context;
        this.patient_name = patient_name;
        this.mobile = mobile;
        this.time = time;
        this.specialist = specialist;
        this.date = date;
        this.appointment_date = appointment_date;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View v= LayoutInflater.from(context).inflate(R.layout.appointment_view,parent,false);


        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position)

    {






        holder.Patientname.setText(String.valueOf(patient_name.get(position)));
        holder.Mobile.setText(String.valueOf(mobile.get(position)));
        holder.Time.setText(String.valueOf(time.get(position)));
        holder.Specialist.setText(String.valueOf(specialist.get(position)));
        holder.Appointment_Date.setText(String.valueOf(appointment_date.get(position)));
        holder.Date.setText(String.valueOf(date.get(position)));

        String patient_name=holder.Patientname.getText().toString();
        String mobile_number=holder.Mobile.getText().toString();
        String time_number=holder.Time.getText().toString();
        String appo_date = holder.Appointment_Date.getText().toString();


        holder.Delete.setVisibility(View.GONE);
        holder.Edit.setVisibility(View.GONE);
        holder.Assign_Myself_Request.setVisibility(View.GONE);
        holder.End_Appo.setVisibility(View.GONE);

        holder.End_Appo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Do You Want to End This Appointment ?");
                builder.setTitle(" END APPOINTMENT");

                builder.setCancelable(false);
                builder.setPositiveButton("Sucessfully Completed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        Status_Helper helper=new Status_Helper(context);

                        Boolean insert=helper.InsertData_Status(patient_name,time_number,appo_date,"Completed Successfully");

                        if (insert==true)
                        {
                            /////////////////////DELETE SQLITE////////////////////////////
                            Appointment_Helper helper2=new Appointment_Helper(context);

                            Boolean delete=helper2.DeleteData_Appointment(patient_name);
                            if (delete==true){
                                RemoveItem(position);

                               SharedPreferences sharedPreferences = context.getSharedPreferences("Login_Doctor",context.MODE_PRIVATE);
                                String username=sharedPreferences.getString("username_doctor"," ");




                              helper.Insert_End_Data(username,patient_name,appo_date);




                            }else{
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }
                            /////////////////////DELETE SQLITE////////////////////////////

                        }else
                        {
                            Toast.makeText(context.getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                        }





                    }
                });
                builder.setNegativeButton("Not Completed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {





                        Status_Helper helper=new Status_Helper(context);

                        Boolean insert=helper.InsertData_Status(patient_name,time_number,appo_date,"Not Completed");

                        if (insert==true)
                        {
                            /////////////////////DELETE SQLITE////////////////////////////
                            Appointment_Helper helper2=new Appointment_Helper(context);

                            Boolean delete=helper2.DeleteData_Appointment(patient_name);
                            if (delete==true){
                                RemoveItem(position);

                                SharedPreferences sharedPreferences = context.getSharedPreferences("Login_Doctor",context.MODE_PRIVATE);
                                String username=sharedPreferences.getString("username_doctor"," ");







                            }else{
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }
                            /////////////////////DELETE SQLITE////////////////////////////

                        }else
                        {
                            Toast.makeText(context.getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                        }

















                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });




                builder.show();





            }
        });


/*
        holder.Assign_Myself_Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String patientname=holder.Patientname.getText().toString().trim();
                Intent intent=new Intent(context,Doctor_Request.class);
                intent.putExtra("patient_key",patientname);
                context.startActivity(intent);
            }
        });



*/

        holder.Assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Assign", Toast.LENGTH_SHORT).show();



                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Do You Want To Accept His Appointment");
                builder.setTitle(" ACCEPT");

                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        holder.End_Appo.setVisibility(View.VISIBLE);
                        /////////////////////////////////////SMS SEND TO PATIENT CONFORMED///////////////////////////////////////////
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                            if (!mobile_number.equals("")) {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(mobile_number, null, patient_name+" Your Appointment Request has Been Accepted By HAMSA DENTAL CLINIC\n  Appointment Time:\n "+time_number +"\nDate:"+ appo_date,null, null);
                                Toast.makeText(context, "Sms Sent Sucessfully", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(context, "Sms Not send", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            ActivityCompat.requestPermissions((AppCompatActivity)context,new String[]{Manifest.permission.SEND_SMS},100);
                        }
/////////////////////////////////////SMS SEND TO PATIENT///////////////////////////////////////////

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
        });

        holder.Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();




                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Do You Want To Reject His Appointment");
                builder.setTitle("CANCEL");

                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        /////////////////////////////////////SMS SEND TO PATIENT CONFORMED///////////////////////////////////////////
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                            if (!mobile_number.equals("")) {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(mobile_number, null, patient_name+" Your Appointment Request has Been Rejected By HAMSA DENTAL CLINIC \nAppointment Time: "+time_number +"\nDate:"+ appo_date,null, null);
                                Toast.makeText(context, "Sms Sent Sucessfully", Toast.LENGTH_SHORT).show();



                                /////////////////////DELETE SQLITE////////////////////////////
                                Appointment_Helper helper=new Appointment_Helper(context);

                                Boolean delete=helper.DeleteData_Appointment(patient_name);
                                if (delete==true){
                                    RemoveItem(position);
                                }else{
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                }

                                /////////////////////DELETE SQLITE////////////////////////////











                            } else {

                                Toast.makeText(context, "Sms Not send", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            ActivityCompat.requestPermissions((AppCompatActivity)context,new String[]{Manifest.permission.SEND_SMS},100);
                        }
/////////////////////////////////////SMS SEND TO PATIENT///////////////////////////////////////////

                    }
                });

       /*    builder.setNeutralButton("Time Mistake", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        /////////////////////////////////////SMS SEND TO PATIENT CONFORMED///////////////////////////////////////////
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                            if (!mobile_number.equals("")) {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(mobile_number, null, " Appointment Request Cancel:Due to Appointment Mistake While Selecting Time \nAppointment Time: "+time_number +"\nDate:"+ appo_date,null, null);
                                Toast.makeText(context, "Sms Sent Sucessfully", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(context, "Sms Not send", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            ActivityCompat.requestPermissions((AppCompatActivity)context,new String[]{Manifest.permission.SEND_SMS},100);
                        }
/////////////////////////////////////SMS SEND TO PATIENT///////////////////////////////////////////




                    }
                });*/



                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                    }
                });


                builder.show();




















            }
        });



    }



    @Override
    public int getItemCount()
    {
        return patient_name.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Patientname, Mobile, Time, Specialist,Date,Appointment_Date;
        ImageView Assign,Delete,Edit,Cancel,Assign_Myself_Request,End_Appo;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);



            Patientname = itemView.findViewById(R.id.PATIENT_RECYCLE);
            Mobile = itemView.findViewById(R.id.MOBILE_RECYCLE);
            Time = itemView.findViewById(R.id.TIME_RECYCLE);
            Specialist = itemView.findViewById(R.id.DOCTOR_RECYCLE);
            Date=itemView.findViewById(R.id.DaTe);
            Appointment_Date=itemView.findViewById(R.id.APP_DATE_RECYCLE);



            Assign=itemView.findViewById(R.id.ASSIGNE_DOCTOR);
            Delete=itemView.findViewById(R.id.DELETE_APPOINTMENT);
            Edit=itemView.findViewById(R.id.EDIT_APPO_RECYCLER);
            Cancel=itemView.findViewById(R.id.CANCEL_APPOINTMENT);
            Assign_Myself_Request=itemView.findViewById(R.id.ASSIGNE_MYSELF_REQUEST);
            End_Appo=itemView.findViewById(R.id.END_APPOINTMENT);



        }
    }
    public  String getDate(){

        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

    }


    public void SaveData(){


        SharedPreferences preferences=context.getSharedPreferences("Current_Date", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(date);
        editor.putString("Date_List",json);
        editor.apply();

    }



    public void LoadData(Context context){




            SharedPreferences preferences= context.getSharedPreferences("Current_Date",Context.MODE_PRIVATE);
            Gson gson=new Gson();
            String json=preferences.getString("Date_List",null);
            Type type=new TypeToken<ArrayList<Appointment_Adapter_Doctor>>(){}.getType();
            date=gson.fromJson(json,type);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==100&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

            Toast.makeText(context,"Permission Granted",Toast.LENGTH_SHORT).show();

        }else {

            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();

        }
    }





    private void RemoveItem(int position) {


        patient_name.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, patient_name.size());


    }


}

