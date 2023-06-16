package com.example.hosp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Previous_rate_adapter extends RecyclerView.Adapter<Previous_rate_adapter.MyViewHolder> {

    Context context;
    private ArrayList Patient_Name;
    private ArrayList Doctor_Name;
    private  ArrayList Rating;

    public Previous_rate_adapter(Context context, ArrayList patient_Name, ArrayList doctor_Name, ArrayList rating)
    {
        this.context = context;
        Patient_Name = patient_Name;
        Doctor_Name = doctor_Name;
        Rating = rating;
    }

    @NonNull
    @Override
    public Previous_rate_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {

        View v= LayoutInflater.from(context).inflate(R.layout.previous_rating_view,parent,false);




        return new Previous_rate_adapter.MyViewHolder(v);





    }

    @Override
    public void onBindViewHolder(@NonNull Previous_rate_adapter.MyViewHolder holder, int position)
    {
        holder.patient.setText(String.valueOf(Patient_Name.get(position)));
        holder.rate.setText(String.valueOf(Rating.get(position)));
        holder.Doctor.setText(String.valueOf(Doctor_Name.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout.setVisibility(View.VISIBLE);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.linearLayout.setVisibility(View.GONE);
                    }
                });




            }
        });




    }

    @Override
    public int getItemCount()
    {
        return Patient_Name.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  {

        TextView patient,rate,Doctor;
        LinearLayout linearLayout;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            patient=itemView.findViewById(R.id.Patient_NAME_RATE);
            rate=itemView.findViewById(R.id.RATE_NAME);
            Doctor=itemView.findViewById(R.id.RATE_NAME_DOCTOR);
            linearLayout=itemView.findViewById(R.id.Linear_Visible);




        }


    }







}
