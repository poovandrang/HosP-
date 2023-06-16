package com.example.hosp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Appo_Adapter extends RecyclerView.Adapter<Appo_Adapter.MyViewHolder> {


ArrayList<Appointment_List> appo_lists;
Context context;

    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{

        void onClick(int position);
    }
    public void setOnItemClickListnerset(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }






    public Appo_Adapter( Context context, ArrayList<Appointment_List> appo_lists ) {
        this.appo_lists = appo_lists;
        this.context = context;
    }

    @NonNull
    @Override
    public Appo_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_view,parent,false);

        return new MyViewHolder(itemView,onItemClickListener);




    }

    @Override
    public void onBindViewHolder(@NonNull Appo_Adapter.MyViewHolder holder, int position) {


        holder.Patientname.setText(appo_lists.get(position).getPatient_Name());

        holder.Mobile.setText(appo_lists.get(position).getMobile());
        holder.Time.setText(appo_lists.get(position).getTime());
        holder.Specialist.setText(appo_lists.get(position).getSpecialist());
        holder.Appointment_Date.setText(appo_lists.get(position).getAppointment_Date());
        holder.Date.setText(getDate());


    }

    @Override
    public int getItemCount() {
        return appo_lists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Patientname, Mobile, Time, Specialist,Date,Appointment_Date,Appointment_Status_conform;

        public MyViewHolder(@NonNull View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);

            Patientname = itemView.findViewById(R.id.PATIENT_RECYCLE);
            Mobile = itemView.findViewById(R.id.MOBILE_RECYCLE);
            Time = itemView.findViewById(R.id.TIME_RECYCLE);
            Specialist = itemView.findViewById(R.id.DOCTOR_RECYCLE);
            Date=itemView.findViewById(R.id.DaTe);
            Appointment_Date=itemView.findViewById(R.id.APP_DATE_RECYCLE);

            itemView.setOnClickListener(view -> onItemClickListener.onClick(getAdapterPosition()));



        }
    }


    public String getDate(){

        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

    }

}
