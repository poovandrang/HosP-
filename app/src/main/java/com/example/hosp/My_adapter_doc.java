package com.example.hosp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class My_adapter_doc extends RecyclerView.Adapter<My_adapter_doc.MyViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {

    /*MY Details Link Ayrukudu Dah*/

    private Context context;

    private ArrayList username,mobile,mail,age,gender,specialist,rate;








    public My_adapter_doc(Context context, ArrayList username, ArrayList mobile, ArrayList mail, ArrayList age, ArrayList gender, ArrayList specialist ,ArrayList rate) {
        this.context = context;
        this.username = username;
        this.mobile = mobile;
        this.mail = mail;
        this.age = age;
        this.gender=gender;
        this.specialist=specialist;
        this.rate=rate;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.display_details_doctor,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.username.setText(String.valueOf(username.get(position)));
        holder.mobile.setText(String.valueOf(mobile.get(position)));
        holder.mail.setText(String.valueOf(mail.get(position)));
        holder.age.setText(String.valueOf(age.get(position)));
        holder.gender.setText(String.valueOf(gender.get(position)));
        holder.specialist.setText(String.valueOf(specialist.get(position)));
        holder.rate.setText(String.valueOf(rate.get(position)));

        holder.select.setVisibility(View.GONE);
        holder.reject.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.select.setVisibility(View.VISIBLE);
                holder.reject.setVisibility(View.VISIBLE);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        holder.select.setVisibility(View.GONE);
                        holder.reject.setVisibility(View.GONE);


                    }
                });




            }
        });











        String mobile_number=holder.mobile.getText().toString().trim();
        String U=holder.username.getText().toString().trim();
        String Specialist=holder.specialist.getText().toString();

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Do You Want To Confirm His Request");
                builder.setTitle("CONFIRM");
                builder.setCancelable(false);
                builder.create();

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                            if (!mobile_number.equals("")) {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(mobile_number, null,"Your Registration has been approved by the Admin of Hamsa Dental Clinic. WELCOME!!!",null, null);
                                Toast.makeText(context, "Sms Sent Sucessfully", Toast.LENGTH_SHORT).show();


                                Helper_doc helper_doc=new Helper_doc(context);

                                String spelist=helper_doc.get_Spe(U);

                                specialist_helper specialist_helper=new specialist_helper(context);
                                if (spelist.equals("Dental Surgeon")){

                                    specialist_helper.InsertDental_Surgeon(U,Specialist);
                                    Toast.makeText(context, "Inserted DS", Toast.LENGTH_SHORT).show();

                                }else if (spelist.equals("Endodontist"))
                                {
                                    specialist_helper.InsertEndodontist(U,Specialist);
                                    Toast.makeText(context, "Inserted ED", Toast.LENGTH_SHORT).show();

                                }else if (spelist.equals("Orthodonist"))
                                {
                                    specialist_helper.InsertOrthodonist(U,Specialist);
                                    Toast.makeText(context, "Inserted OD", Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(context, "Not Inserted", Toast.LENGTH_SHORT).show();
                                }






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
                builder.setMessage("Do You Want To Reject His Request");
                builder.setTitle("CANCEL");
                builder.setCancelable(false);
                builder.create();

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                            if (!mobile_number.equals("")) {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(mobile_number, null,"Your Registration Request was declined  by the Admin of Hamsa Dental Clinic.",null, null);
                                Toast.makeText(context, "Sms Sent Sucessfully", Toast.LENGTH_SHORT).show();


                                /////////////////////DELETE SQLITE////////////////////////////
                                Helper_doc helper=new Helper_doc(context);

                                Boolean delete=helper.DeleteData_Appointment(U);
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
        return username.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView username,mobile,mail,age,gender,specialist,rate;
        ImageView select,reject;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.DOCTOR_NAME);
            mobile=itemView.findViewById(R.id.DOCTOR_MOBILE);
            mail=itemView.findViewById(R.id.DOCTOR_MAIL);
            age=itemView.findViewById(R.id.DOCTOR_AGE);
            gender=itemView.findViewById(R.id.DOCTOR_GENDER);
            specialist=itemView.findViewById(R.id.DOCTOR_SPECIALIST_LIST);
            select=itemView.findViewById(R.id.DOCTOR_SELECT);
            reject=itemView.findViewById(R.id.DOCTOR_REJECT);
            rate=itemView.findViewById(R.id.RATE_DIS_VIEW);






        }
    }


    private void RemoveItem(int position) {


        username.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, username.size());


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
