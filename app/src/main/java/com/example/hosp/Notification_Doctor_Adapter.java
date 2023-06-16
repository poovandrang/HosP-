package com.example.hosp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Notification_Doctor_Adapter extends RecyclerView.Adapter<Notification_Doctor_Adapter.MyViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {

    Context context;
    ArrayList Patient;
    ArrayList Time;
    ArrayList Date;
    ArrayList Doctor_Name;
    ArrayList Mobile;


    public Notification_Doctor_Adapter(Context context, ArrayList patient, ArrayList time, ArrayList date, ArrayList doctor_Name ,ArrayList mobile) {
        this.context = context;
        Patient = patient;
        Time = time;
        Date = date;
        Doctor_Name = doctor_Name;
        Mobile=mobile;
    }

    @NonNull
    @Override
    public Notification_Doctor_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(context).inflate(R.layout.doctor_notification_view,parent,false);
        return new Notification_Doctor_Adapter.MyViewHolder(v);



    }

    @Override
    public void onBindViewHolder(@NonNull Notification_Doctor_Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.patient.setText(String.valueOf(Patient.get(position)));
        holder.time.setText(String.valueOf(Time.get(position)));
        holder.date.setText(String.valueOf(Date.get(position)));
        holder.doctor.setText(String.valueOf(Doctor_Name.get(position)));
        holder.mobile.setText(String.valueOf(Mobile.get(position)));

        String pat=holder.patient.getText().toString();
        String doc=holder.doctor.getText().toString();
        String dat=holder.date.getText().toString();
        String time=holder.time.getText().toString();
        String mobile_number=holder.mobile.getText().toString();





        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                    if (!mobile_number.equals("")) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(mobile_number, null, "Your Appointment is Confirm By Dr. "+doc+"\nOn\t"+dat+".",null, null);
                        Toast.makeText(context, "Sms Sent Sucessfully", Toast.LENGTH_SHORT).show();


                        specialist_helper helper=new specialist_helper(context);
                        Boolean delete=helper.Delete_Notife(pat);
////////////////////////////////////////////////////////////////////////////////////////////////////
                        if (delete==true){
                            RemoveItem(position);


                            Appointment_Helper appointment_helper=new Appointment_Helper(context);
                            appointment_helper.DeleteData_Appointment(pat);





                        }else{
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }

////////////////////////////////////////////////////////////////////////////////////////////////////




                    } else {

                        Toast.makeText(context, "Sms Not send", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    ActivityCompat.requestPermissions((AppCompatActivity)context,new String[]{Manifest.permission.SEND_SMS},100);
                }
/////////////////////////////////////SMS SEND TO PATIENT////////////////////////////////////////









            }
        });


       holder.reject.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {



               if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                   if (!mobile_number.equals("")) {
                       SmsManager smsManager = SmsManager.getDefault();
                       smsManager.sendTextMessage(mobile_number, null, "Your Appointment Request is Rejected By Dr. "+doc,null, null);
                       Toast.makeText(context, "Sms Sent Sucessfully", Toast.LENGTH_SHORT).show();


                       specialist_helper helper=new specialist_helper(context);
                       Boolean delete=helper.Delete_Notife(pat);
////////////////////////////////////////////////////////////////////////////////////////////////////
                       if (delete==true){
                           RemoveItem(position);
                       }else{
                          Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                       }

////////////////////////////////////////////////////////////////////////////////////////////////////





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





    }

    @Override
    public int getItemCount() {
        return Patient.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView patient,time,date,doctor,mobile;
        ImageButton accept,reject;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            patient=itemView.findViewById(R.id.Patient_Sir);
            time=itemView.findViewById(R.id.Time_TD);
            date=itemView.findViewById(R.id.Date_TD);
            doctor=itemView.findViewById(R.id.Doct_Name);
            accept=itemView.findViewById(R.id.NOTI_DOC_OK);
            reject=itemView.findViewById(R.id.NOTI_DOC_NO);
            mobile=itemView.findViewById(R.id.Number_123);





        }
    }























    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();

        }
    }


    private void RemoveItem(int position) {


        Patient.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, Patient.size());


    }



}
