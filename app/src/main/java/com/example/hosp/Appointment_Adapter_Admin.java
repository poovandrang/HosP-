package com.example.hosp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Appointment_Adapter_Admin extends RecyclerView.Adapter<Appointment_Adapter_Admin.MyViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback{

    private Context context;
    private ArrayList patient_name;
    private ArrayList mobile;
    private ArrayList time;
    private ArrayList specialist;
    private ArrayList date;
    private ArrayList appointment_date;





    //private ArrayList<Task> tasks=new ArrayList<Task>();





    public Appointment_Adapter_Admin(Context context, ArrayList patient_name, ArrayList mobile, ArrayList time, ArrayList specialist, ArrayList date, ArrayList appointment_date ) {
        this.context = context;
        this.patient_name = patient_name;
        this.mobile = mobile;
        this.time = time;
        this.specialist = specialist;
        this.date=date;
        this.appointment_date=appointment_date;







    }






    @NonNull
    @Override
    public Appointment_Adapter_Admin.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View v= LayoutInflater.from(context).inflate(R.layout.appointment_view,parent,false);




        return new  MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Appointment_Adapter_Admin.MyViewHolder holder, @SuppressLint("RecyclerView") int position)
    {







        holder.Patientname.setText(String.valueOf(patient_name.get(position)));
        holder.Mobile.setText(String.valueOf(mobile.get(position)));
        holder.Time.setText(String.valueOf(time.get(position)));
        holder.Specialist.setText(String.valueOf(specialist.get(position)));
        holder.Appointment_Date.setText(String.valueOf(appointment_date.get(position)));
        holder.Date_Ass.setText(String.valueOf(date.get(position)));





        holder.Edit.setVisibility(View.GONE);
        holder.Delete.setVisibility(View.GONE);
        holder.Assigne_Myself.setVisibility(View.GONE);
        holder.End_Appo.setVisibility(View.GONE);





        String mobile_number=holder.Mobile.getText().toString();
        String time_number=holder.Time.getText().toString();
        String appo_date = holder.Appointment_Date.getText().toString();




        String p=holder.Patientname.getText().toString();
        String t=holder.Time.getText().toString();
        String d=holder.Appointment_Date.getText().toString();





//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        holder.Assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Do You Want To Confirm His Appointment ?");
                builder.setTitle("CONFIRM");
                builder.setCancelable(false);
                builder.create();

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                            if (!mobile_number.equals("")) {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(mobile_number, null, p+" Your Appointment Request has been Confirmed By HAMSA DENTAL CLINIC\n Appointment Time: "+time_number+"\nDate:"+ appo_date,null, null);
                                Toast.makeText(context, "Sms Sent Sucessfully", Toast.LENGTH_SHORT).show();
                                holder.Cancel.setVisibility(View.GONE);





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

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Do You Want To Cancel His Appointment");
                builder.setTitle(" CANCEL");

                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        /////////////////////////////////////SMS SEND TO PATIENT CONFORMED///////////////////////////////////////////
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                            if (!mobile_number.equals("")) {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(mobile_number, null, p+" Your Appointment Request has Been Rejected By HAMSA DENTAL CLINIC\n  Appointment Time:\n "+time_number +"\nDate:"+ appo_date,null, null);
                                Toast.makeText(context, "Sms Sent Sucessfully", Toast.LENGTH_SHORT).show();

                                /////////////////////DELETE SQLITE////////////////////////////
                                Appointment_Helper helper=new Appointment_Helper(context);

                                Boolean delete=helper.DeleteData_Appointment(p);
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

        return patient_name.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder  {


        public static final String TAG = "MyViewHolder";

        public TextView Patientname, Mobile, Time, Specialist,Date_Ass,Appointment_Date;
        ImageView Assign,Delete,Edit,Cancel,Assigne_Myself,End_Appo;








        public MyViewHolder(@NonNull View itemView) {
            super(itemView);



            Patientname = itemView.findViewById(R.id.PATIENT_RECYCLE);
            Mobile = itemView.findViewById(R.id.MOBILE_RECYCLE);
            Time = itemView.findViewById(R.id.TIME_RECYCLE);
            Specialist = itemView.findViewById(R.id.DOCTOR_RECYCLE);
            Date_Ass=itemView.findViewById(R.id.DaTe);
            Appointment_Date=itemView.findViewById(R.id.APP_DATE_RECYCLE);





            Assign=itemView.findViewById(R.id.ASSIGNE_DOCTOR);
            Delete=itemView.findViewById(R.id.DELETE_APPOINTMENT);
            Edit=itemView.findViewById(R.id.EDIT_APPO_RECYCLER);
            Cancel=itemView.findViewById(R.id.CANCEL_APPOINTMENT);
            Assigne_Myself=itemView.findViewById(R.id.ASSIGNE_MYSELF_REQUEST);
            End_Appo=itemView.findViewById(R.id.END_APPOINTMENT);








        }


        }


                private void RemoveItem(int position) {


                    patient_name.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, patient_name.size());


                }


                public String getDate() {

                    return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();

        }
    }








}

