package com.example.hosp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Endodontist_Adapter extends RecyclerView.Adapter<Endodontist_Adapter.MyViewHolder>{


    private Context context;
    private ArrayList doctor;
    private ArrayList rate;

    public Endodontist_Adapter(Context context, ArrayList doctor, ArrayList rate) {
        this.context = context;
        this.doctor = doctor;
        this.rate = rate;
    }

    @NonNull
    @Override
    public Endodontist_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(context).inflate(R.layout.doctor_list_appo,parent,false);
        return new Endodontist_Adapter.MyViewHolder(v);



    }

    @Override
    public void onBindViewHolder(@NonNull Endodontist_Adapter.MyViewHolder holder, int position) {



        holder.Doctor.setText(String.valueOf(doctor.get(position)));
        holder.Rate.setText(String.valueOf(rate.get(position)));


    }

    @Override
    public int getItemCount() {
        return doctor.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder  {

        TextView Doctor,Rate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Doctor=itemView.findViewById(R.id.DRATE);
            Rate=itemView.findViewById(R.id.RRATE);


        }


    }



}
