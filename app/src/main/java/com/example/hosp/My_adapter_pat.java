package com.example.hosp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class My_adapter_pat  extends RecyclerView.Adapter<My_adapter_pat.MyViewHolder> {

    /*MY Details Link Ayrukudu Dah*/

    private Context context;

    private ArrayList username,mobile,mail,age,gender;


    public My_adapter_pat(Context context, ArrayList username, ArrayList mobile, ArrayList mail, ArrayList age, ArrayList gender) {
        this.context = context;
        this.username = username;
        this.mobile = mobile;
        this.mail = mail;
        this.age = age;
        this.gender=gender;



    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.display_details_patients,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull My_adapter_pat.MyViewHolder holder, int position) {

        holder.username.setText(String.valueOf(username.get(position)));
        holder.mobile.setText(String.valueOf(mobile.get(position)));
        holder.mail.setText(String.valueOf(mail.get(position)));
        holder.age.setText(String.valueOf(age.get(position)));
        holder.gender.setText(String.valueOf(gender.get(position)));



    }

    @Override
    public int getItemCount() {
        return username.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView username,mobile,mail,age,gender;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.PATIENT1_NAME);
            mobile=itemView.findViewById(R.id.PATIENT1_MOBILE);
            mail=itemView.findViewById(R.id.PATIENT1_MAIL);
            age=itemView.findViewById(R.id.PATIENT1_AGE);
            gender=itemView.findViewById(R.id.PATIENT1_GENDER);




        }
    }
}
