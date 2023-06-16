package com.example.hosp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Rate_Notif_adapter_patient extends RecyclerView.Adapter<Rate_Notif_adapter_patient.MyViewHolder> {


    private Context context;
    private ArrayList doctor_name;
    private ArrayList specialist;
    private ArrayList rating;



    public Rate_Notif_adapter_patient(Context context, ArrayList doctor_name, ArrayList specialist, ArrayList rating) {
        this.context = context;
        this.doctor_name = doctor_name;
        this.specialist = specialist;
        this.rating = rating;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(context).inflate(R.layout.doctor_list_patient_view,parent,false);




        return new Rate_Notif_adapter_patient.MyViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull Rate_Notif_adapter_patient.MyViewHolder holder , int position) {

        holder.Doctorname.setText(String.valueOf(doctor_name.get(position)));
        holder.Specialist_doc.setText(String.valueOf(specialist.get(position)));
        holder.Rating.setText(String.valueOf(rating.get(position)));

        String doctor=holder.Doctorname.getText().toString();



        holder.layout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                Toast.makeText(context, doctor, Toast.LENGTH_SHORT).show();


            }
        });



    }

    @Override
    public int getItemCount() {
        return doctor_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView Doctorname, Specialist_doc, Rating ;
        LinearLayout layout , linearLayout;
        EditText Doc;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Doctorname=itemView.findViewById(R.id.RATE_NAME);
            Specialist_doc=itemView.findViewById(R.id.RATE_SPECIALIST);
            Rating=itemView.findViewById(R.id.RATING);
            layout=itemView.findViewById(R.id.CLICK_LIST);
            linearLayout=itemView.findViewById(R.id.MYLINER);





        }
    }





}
