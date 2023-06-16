package com.example.hosp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
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

public class notification_adapter extends RecyclerView.Adapter<notification_adapter.MyViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {


    Context context;
    private ArrayList Doctor_Name;
    private ArrayList Patient_Name;
    private  ArrayList Mobile;


    public notification_adapter(Context context, ArrayList doctor_Name, ArrayList patient_Name ,ArrayList mobile) {
        this.context = context;
        Doctor_Name = doctor_Name;
        Patient_Name=patient_Name;
        Mobile=mobile;
    }

    @NonNull
    @Override
    public notification_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.notification_view,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull notification_adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.doctor_name.setText(String.valueOf(Doctor_Name.get(position)));
        holder.mobile.setText(String.valueOf(Mobile.get(position)));
        holder.message.setText("SEND REQUEST TO APPOINT PATIENT \n\t->"+"\t\t"+Patient_Name.get(position));

        String mobile_no=holder.mobile.getText().toString().trim();
        String D=holder.doctor_name.getText().toString().trim();



        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "ACCEPT", Toast.LENGTH_SHORT).show();



                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Do You Want To Accept His Request");
                builder.setTitle(" ACCEPT");

                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        /////////////////////////////////////SMS SEND TO PATIENT CONFORMED///////////////////////////////////////////
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                            if (!mobile_no.equals("")) {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(mobile_no, null, "Your Request for Appointment Has been Accepted by the Admin",null, null);
                                Toast.makeText(context, "Sms Sent Sucessfully", Toast.LENGTH_SHORT).show();
                                holder.reject.setVisibility(View.GONE);



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

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Do You Want To decline His Request");
                builder.setTitle(" DECLINED");

                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        /////////////////////////////////////SMS SEND TO PATIENT CONFORMED///////////////////////////////////////////
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                            if (!mobile_no.equals("")) {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(mobile_no, null, "Your Request for Appointment Has been Declined by the Admin of Hamsa Dental Clinic WELCOME!!!",null, null);
                                Toast.makeText(context, "Sms Sent Sucessfully", Toast.LENGTH_SHORT).show();


                                /////////////////////DELETE SQLITE////////////////////////////
                                Doctor_Request_Helper helper=new Doctor_Request_Helper(context);

                                Boolean delete=helper.DeleteData_Appointment(D);
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
    public int getItemCount() {
        return Doctor_Name.size();
    }








    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView doctor_name,message,mobile;
        ImageButton accept,reject;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);



            message=itemView.findViewById(R.id.Doct_Message);
            doctor_name=itemView.findViewById(R.id.Doct_Notifi);
            mobile=itemView.findViewById(R.id.MOBILE_NOTIFY);
            accept=itemView.findViewById(R.id.ACCCEPT_REQ);
            reject=itemView.findViewById(R.id.REJECT_REQ);



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


        Doctor_Name.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, Doctor_Name.size());


    }




}
