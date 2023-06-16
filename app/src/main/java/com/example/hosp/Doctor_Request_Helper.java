package com.example.hosp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Doctor_Request_Helper extends SQLiteOpenHelper {


    public Doctor_Request_Helper(Context context)
    {
        super(context,"Doctor_Request.DB",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String qry="create table Request_Doctor(Doctor_Name TEXT primary key,Mobile TEXT,Patient_Name TEXT)";
        sqLiteDatabase.execSQL(qry);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String qry="DROP TABLE IF EXISTS Request_Doctor";
        sqLiteDatabase.execSQL(qry);

    }


    public Boolean INSERT_REQUEST_DETAILS(String username,String Mobile,String Patient_Name){

        SQLiteDatabase MyDB=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Doctor_Name",username);
        contentValues.put("Mobile",Mobile);
        contentValues.put("Patient_Name",Patient_Name);

        long result=MyDB.insert("Request_Doctor",null,contentValues);
        if (result==-1){
            return false;
        }else {
            return true;
        }

    }

    public Cursor Getdata()
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor= DB.rawQuery("Select * from Request_Doctor",null);
        return cursor;

    }


    public  Boolean DeleteData_Appointment(String doctor_name){

        SQLiteDatabase MyDB=this.getWritableDatabase();

        Cursor cursor=MyDB.rawQuery("Select * from Request_Doctor where Doctor_Name = ?",new String[]{doctor_name} );
        if (cursor.getCount()>0){


            long result=MyDB.delete("Request_Doctor","Doctor_Name =?",new String[]{doctor_name});
            if (result==-1){
                return false;

            }else {
                return true;
            }
        }else {
            return false;
        }

    }





}
