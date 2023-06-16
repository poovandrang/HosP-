package com.example.hosp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Status_Helper extends SQLiteOpenHelper {


    public Status_Helper( Context context) {

        super(context,"Status.DB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB)
    {
        String qry="create table Appointment_Status(Patient_Name TEXT,TIME TEXT,Appointment_Date TEXT,Status TEXT )";
        String qry2="create table End_Appointment_Status(Doctor_Name TEXT ,Patient TEXT ,Date TEXT )";
        MyDB.execSQL(qry);
        MyDB.execSQL(qry2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1)
    {
        String qry="DROP TABLE IF EXISTS Appointment_Status";
        String qry2="DROP TABLE IF EXISTS End_Appointment_Status";
        MyDB.execSQL(qry);
        MyDB.execSQL(qry2);

    }


    public Boolean InsertData_Status(String patient_name, String time,String appointment_date,String status){

        SQLiteDatabase MyDB=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Patient_Name",patient_name);
        contentValues.put("Time",time);
        contentValues.put("Appointment_Date",appointment_date);
        contentValues.put("Status",status);

        long result=MyDB.insert("Appointment_Status",null,contentValues);
        if (result==-1) return false;
        else
            return true;
    }

    public Boolean Insert_End_Data(String doctor_name,String patient,String date){

        SQLiteDatabase MyDB=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Doctor_Name",doctor_name);
        contentValues.put("Patient",patient);
        contentValues.put("Date",date);

        long result=MyDB.insert("End_Appointment_Status",null,contentValues);
        if (result==-1)return false;
        else
            return true;

    }

    public  Boolean DeleteData_Notification(String doctor){

        SQLiteDatabase MyDB=this.getWritableDatabase();

        Cursor cursor=MyDB.rawQuery("Select * from End_Appointment_Status where Doctor_Name = ?",new String[]{doctor} );
        if (cursor.getCount()>0){


            long result=MyDB.delete("End_Appointment_Status","Doctor_Name=?",new String[]{doctor});
            if (result==-1){
                return false;

            }else {
                return true;
            }
        }else {
            return false;
        }

    }











    public Cursor Getdata()
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor= DB.rawQuery("Select * from Appointment_Status",null);
        return cursor;

    }
    public  Cursor Getdata2(String patient){

        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("Select * from End_Appointment_Status  where Patient = ?",new String[]{patient});
        return cursor;

    }




}
