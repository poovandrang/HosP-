package com.example.hosp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Appointment_Adapter_Patient extends RecyclerView.Adapter<Appointment_Adapter_Patient.MyViewHolder>  {

    private Context context;
    private ArrayList patient_name;
    private ArrayList mobile;
    private ArrayList time;
    private ArrayList specialist;
    private ArrayList date;
    private ArrayList appointment_date;


    public Appointment_Adapter_Patient(Context context, ArrayList patient_name, ArrayList mobile, ArrayList time, ArrayList specialist, ArrayList date, ArrayList appointment_date) {
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


                    holder.Cancel.setVisibility(View.GONE);
                    holder.Assign_Myself.setVisibility(View.GONE);
                    holder.Edit.setVisibility(View.GONE);
                    holder.Assign.setVisibility(View.GONE);
                    holder.Mobile.setVisibility(View.GONE);
                    holder.Mobile_TextView.setVisibility(View.GONE);
                    holder.End_Appo.setVisibility(View.GONE);


                    holder.Edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();


                        }
                    });

                    holder.Delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Do You Want To Cancel your Appointment ?");
                            builder.setTitle("CANCEL");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                    String mobile_check = holder.Mobile.getText().toString().trim();
                                    String Patient_Check = holder.Patientname.getText().toString();
                                    Intent intent = new Intent(context, Cancel_Appo_Mobile_Check.class);
                                    intent.putExtra("mobile_key", mobile_check);
                                    intent.putExtra("patient_key_check", Patient_Check);
                                    context.startActivity(intent);


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





        int val=patient_name.size();
        return val;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {


        public static final String TAG = "MyViewHolder";

        TextView Patientname, Mobile, Time, Specialist,Date,Appointment_Date;
        ImageView Assign,Delete,Edit,Cancel,Assign_Myself,End_Appo;
        TextView Mobile_TextView;





        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            Patientname = itemView.findViewById(R.id.PATIENT_RECYCLE);
            Mobile = itemView.findViewById(R.id.MOBILE_RECYCLE);
            Time = itemView.findViewById(R.id.TIME_RECYCLE);
            Specialist = itemView.findViewById(R.id.DOCTOR_RECYCLE);
            Date=itemView.findViewById(R.id.DaTe);
            Appointment_Date=itemView.findViewById(R.id.APP_DATE_RECYCLE);

            Mobile_TextView=itemView.findViewById(R.id.MOBILE_RECYCLE);


            Assign=itemView.findViewById(R.id.ASSIGNE_DOCTOR);
            Delete=itemView.findViewById(R.id.DELETE_APPOINTMENT);
            Edit=itemView.findViewById(R.id.EDIT_APPO_RECYCLER);
            Cancel=itemView.findViewById(R.id.CANCEL_APPOINTMENT);
            Assign_Myself=itemView.findViewById(R.id.ASSIGNE_MYSELF_REQUEST);
            End_Appo=itemView.findViewById(R.id.END_APPOINTMENT);








        }

    }




    public String getDate(){

        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

    }


    private void RemoveItem(int position) {


        patient_name.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, patient_name.size());


    }







}

