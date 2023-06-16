package com.example.hosp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Patient_Notification_Adapter extends RecyclerView.Adapter<Patient_Notification_Adapter.MyviewHolder> {

    private Context context;
    private ArrayList doctor_name,date;

    public Patient_Notification_Adapter(Context context, ArrayList doctor_name ,ArrayList date) {
        this.context = context;
        this.doctor_name = doctor_name;
        this.date=date;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.rate_notification, parent, false);

        return new Patient_Notification_Adapter.MyviewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        holder.Doctorname.setText(String.valueOf(doctor_name.get(position)));

        holder.Date.setText(String.valueOf(date.get(position)));

        String d = holder.Doctorname.getText().toString().trim();
        holder.Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Okay", Toast.LENGTH_SHORT).show();


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do You Want To Rate Him ");
                builder.setTitle(" RATE");
                builder.setCancelable(false);
                builder.setPositiveButton("RATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Intent intent = new Intent(context, Rating.class);
                        intent.putExtra("doctor_key", d);
                        context.startActivity(intent);


                    }
                });

                builder.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });


                builder.show();

            }
        });


        holder.No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Status_Helper helper2=new Status_Helper(context);
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                Boolean delete=helper2.DeleteData_Notification(d);

                if (delete==true){
                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context,Home_Patient.class);
                    context.startActivity(intent);


                }else {
                    Toast.makeText(context, "Not Deleted", Toast.LENGTH_SHORT).show();
                }
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





            }
        });


    }

    @Override
    public int getItemCount() {
        return doctor_name.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        public TextView Doctorname,Date;
        public ImageView Okay, No;


        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            Doctorname = itemView.findViewById(R.id.Doctor_Sir);
            Date=itemView.findViewById(R.id.DaTe_DaTe);
            Okay = itemView.findViewById(R.id.YES_RATE);
            No = itemView.findViewById(R.id.NO_RATE);


        }
    }
}
