package com.example.hosp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Table_Status_Adapter extends RecyclerView.Adapter<Table_Status_Adapter.MyViewHolder>{


    Context context;
    ArrayList patientname;
    ArrayList date;
    ArrayList time;
    ArrayList status;


    public Table_Status_Adapter(Context context, ArrayList patientname, ArrayList date, ArrayList time, ArrayList status) {
        this.context = context;
        this.patientname = patientname;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.table_list,parent,false);

        return new Table_Status_Adapter.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {




          holder.Patient_Name.setText(String.valueOf(patientname.get(position)));
          holder.Time.setText(String.valueOf(time.get(position)));
          holder.Status.setText(String.valueOf(status.get(position)));






    }


    @Override
    public int getItemCount() {
        return patientname.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  {


        public static final String TAG = "MyViewHolder";
        TextView Patient_Name,Time,Status;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            Patient_Name=itemView.findViewById(R.id.TABLE_PATIENT);
            Time=itemView.findViewById(R.id.TABLE_TIME);
            Status=itemView.findViewById(R.id.TABLE_STATUS);




        }


    }






}
