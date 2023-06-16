package com.example.hosp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Appointment_Helper extends SQLiteOpenHelper {

    public static final String DBNAME="Appointment_New.DB";

    public Appointment_Helper(Context context) {

        super(context,"Apponintment.DB",null,1);

    }



    @Override
    public void onCreate(SQLiteDatabase MyDB) {

        String qry="create table Appointment_Details(Patient_Name TEXT,Mobile TEXT,TIME TEXT,Specialist TEXT,Date TEXT,Appointment_Date TEXT,Doctor_Req TEXT DEFAULT 'Select')";
        MyDB.execSQL(qry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {

        String qry="DROP TABLE IF EXISTS Appointment_Details";
        MyDB.execSQL(qry);


    }

    public Boolean InsertData_Apointment(String patient_name, String mobile, String time, String specialist,String date,String appointment_date,String doctor_required){

        SQLiteDatabase MyDB=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Patient_Name",patient_name);
        contentValues.put("Mobile",mobile);
        contentValues.put("Time",time);
        contentValues.put("Specialist",specialist);
        contentValues.put("Date", date);
        contentValues.put("Appointment_Date",appointment_date);
        contentValues.put("Doctor_Req",doctor_required);




        long result=MyDB.insert("Appointment_Details",null,contentValues);
        if (result==-1) return false;
        else
            return true;
    }

    public Boolean CheckUsername( String patient_name){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from Appointment_Details where Patient_Name = ?",new String[] {patient_name});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }










    public  Boolean DeleteData_Appointment(String patient_name){

        SQLiteDatabase MyDB=this.getWritableDatabase();

        Cursor cursor=MyDB.rawQuery("Select * from Appointment_Details where Patient_Name = ?",new String[]{String.valueOf(patient_name)} );
        if (cursor.getCount()>0){


            long result=MyDB.delete("Appointment_Details","Patient_Name=?",new String[]{String.valueOf(patient_name)});
            if (result==-1){
                return false;

            }else {
                return true;
            }
        }else {
            return false;
        }

    }

    public Boolean UpdateData_Appointment(String patient_name, String mobile, String time, String specialist,String date,String appointment_date) {


        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Patient_Name", patient_name);
        contentValues.put("Mobile", mobile);
        contentValues.put("Time", time);
        contentValues.put("Specialist",specialist);
        contentValues.put("Date",date);
        contentValues.put("Appointment_Date",appointment_date);

        Cursor cursor = MyDB.rawQuery("Select * from Appointment_Details where Patient_Name = ?", new String[]{patient_name});
        if (cursor.getCount() > 0) {


            long result = MyDB.update("Appointment_Details", contentValues,"Patient_Name=?", new String[]{patient_name});
            if (result == -1)
            {
                return false;

            } else
            {
                return true;
            }
        }
        else
        {
            return false;
        }

    }






    public Boolean CheckTime( String time){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from Appointment_Details where Time = ?",new String[] {time});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }


    public Boolean CheckDate( String date){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from Appointment_Details where Appointment_Date = ?",new String[] {date});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }














        public Cursor Getdata()
    {
        SQLiteDatabase DB=this.getReadableDatabase();
        Cursor cursor= DB.rawQuery("Select * from Appointment_Details",null);
        return cursor;

    }


    public Cursor Getdata_Meow(String patientname)
    {
        SQLiteDatabase DB=this.getReadableDatabase();
        Cursor cursor= DB.rawQuery("Select * from Appointment_Details where Patient_Name = ?",new String[]{patientname});
        return cursor;

    }

    public Cursor Getdata_Doc(String doctor_name)
    {
        SQLiteDatabase DB=this.getReadableDatabase();
        Cursor cursor= DB.rawQuery("Select * from Appointment_Details where Doctor_Req = ?",new String[]{doctor_name});
        return cursor;

    }



    public String Get_Patient(String username) {
        String name = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Appointment_Details where Patient_Name = ?",new String[]{username} );
        if (cursor.moveToFirst()) {
            name = cursor.getString(0);
        }
        return name;
    }







    public Cursor Getdata_DISPALAY(String username)
    {
        SQLiteDatabase DB=this.getReadableDatabase();
        Cursor cursor= DB.rawQuery("Select * from Appointment_Details",null);

        if (cursor.getString(8).equals(username)){



            return cursor;

        }



        return null;

    }



}
