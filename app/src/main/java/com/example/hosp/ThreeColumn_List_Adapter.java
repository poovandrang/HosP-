package com.example.hosp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ThreeColumn_List_Adapter extends ArrayAdapter<Patient_List> {



    private LayoutInflater inflater;
    private ArrayList<Patient_List> User;
    private int ViewResourceID;


    public ThreeColumn_List_Adapter(Context context,int textviewResourceID,ArrayList<Patient_List> User){

        super(context,textviewResourceID,User);
        this.User=User;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewResourceID=textviewResourceID;
    }

    public View getView(int position, View contentview, ViewGroup parents){

        contentview=inflater.inflate(ViewResourceID,null);

        Patient_List user=User.get(position);

        if (user!=null){

            TextView textView1=contentview.findViewById(R.id.PatientColumn);
            TextView textView2=contentview.findViewById(R.id.MobileColumn);
            TextView textView3=contentview.findViewById(R.id.MailColumn);

            if (textView1!=null){
                textView1.setText(user.getPatient());
            }
            if (textView2!=null){
                textView2.setText(user.getMobile());
            }
            if (textView3!=null){
                textView3.setText(user.getMail());
            }

        }

        return contentview;
    }





}
