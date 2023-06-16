package com.example.hosp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Helper_doc extends SQLiteOpenHelper {


    public Helper_doc( Context context) {
        super(context,"Doctor_Bio.DB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {

        String qry="create Table DoctorDetails(Username TEXT primary key,Name TEXT,Password TEXT,Mobile TEXT,Email TEXT,Age TEXT,Gender TEXT,Specialist TEXT,Rating FLOAT,Total_Rating FLOAT,Count_Sum FLOAT )";
        MyDB.execSQL(qry);


    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {

        String qry="drop Table if exists DoctorDetails";
        MyDB.execSQL(qry);


    }

    //////////////Insert DATA//////////////////
    public boolean InsertData(String username,String name,String pass,String mobile,String email,String age,String gender,String specialist){

        SQLiteDatabase MyDB=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Name",name);
        contentValues.put("Password",pass);
        contentValues.put("Mobile",mobile);
        contentValues.put("Email",email);
        contentValues.put("Age",age);
        contentValues.put("Gender",gender);
        contentValues.put("Specialist",specialist);
        contentValues.put("Rating",0);
        contentValues.put("Total_Rating",0);
        contentValues.put("Count_Sum",0);



        long result=MyDB.insert("DoctorDetails",null,contentValues);
        if (result==-1) return false;
        else
            return true;

    }

    ///////////////////CHECKUSER ALREADY EXIST

    public Boolean CheckUsername( String username){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from DoctorDetails where Username = ?",new String[] {username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    //////////////////////////////CheckUSERNAME-PASSWORD////



    public Boolean CheckUsername_password(String username,String password){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from DoctorDetails where Username = ? and Password= ?",new String[]{username,password});
        if (cursor.getCount()>0)
            return true;
        else return false;

    }

    public Cursor Getdata()
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor= DB.rawQuery("Select * from DoctorDetails",null);
        return cursor;

    }

    public Cursor Getdata_Display()
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor= DB.rawQuery("Select * from DoctorDetails where Rating  != 0",null);
        return cursor;

    }




    public  Boolean DeleteData_Appointment(String username){

        SQLiteDatabase MyDB=this.getWritableDatabase();

        Cursor cursor=MyDB.rawQuery("Select * from DoctorDetails where Username = ?",new String[]{username} );
        if (cursor.getCount()>0){


            long result=MyDB.delete("DoctorDetails","Username = ?",new String[]{username});
            if (result==-1){
                return false;

            }else {
                return true;
            }
        }else {
            return false;
        }

    }




    public Boolean UpdateData_Rating(String username,String rating) {


        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Rating",rating);


        Cursor cursor = MyDB.rawQuery("Select * from DoctorDetails where Username = ?", new String[]{username});
        if (cursor.getCount() > 0) {

            long result = MyDB.update("DoctorDetails", contentValues,"Username = ?", new String[]{username});
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





    public boolean UpdateRating (String username,float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Rating",rating);

        db.update("DoctorDetails", contentValues,"Username" +" = ?" , new String[] {username} );
        return true;
    }




    public List<String>getAllLabels(){


        List<String> list=new ArrayList<String>();

       SQLiteDatabase MyDB=this.getReadableDatabase();
       Cursor cursor=MyDB.rawQuery("Select * from DoctorDetails",null);

       if (cursor.moveToFirst()){

           do {
               list.add(cursor.getString(8));
           }while (cursor.moveToNext());


       }
        cursor.close();
        MyDB.close();
        return list;

    }

    public float getAverageScore(String username) {
        float average = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from DoctorDetails where Username = ?",new String[]{username} );
        if (cursor.moveToFirst()) {
            average = cursor.getFloat(8);
        }
        cursor.close();
        return average;
    }

    public String GetUsername( String username){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from DoctorDetails where Username = ?",new String[] {username});
        if (cursor.getCount()>0)
            return username;
        else
            return null;
    }

    public Boolean CheckUsername_Mobile(String username,String mobile){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select *from DoctorDetails where Username = ? and Mobile= ?",new String[]{username,mobile});
        if (cursor.getCount()>0)
            return true;
        else return false;

    }

    public boolean UpdatePassword (String username,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Password",password);
        db.update("DoctorDetails", contentValues,"Username" +" = ?" , new String[] {username} );
        return true;
    }



    public boolean Update_Total_Sum (String username,Float rate_sum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Total_Rating",rate_sum);
        db.update("DoctorDetails", contentValues,"Username" +" = ?" , new String[] {username} );
        return true;
    }





    public float Get_Total_sum(String username) {
        float total = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from DoctorDetails where Username = ?",new String[]{username} );
        if (cursor.moveToFirst()) {
            total = cursor.getFloat(9);
        }
        return total;
    }



    public boolean Update_Count_Sum (String username,Float count_sum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Count_Sum",count_sum);
        db.update("DoctorDetails", contentValues,"Username" +" = ?" , new String[] {username} );
        return true;
    }





    public float Get_Count_sum(String username) {
        float count = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from DoctorDetails where Username = ?",new String[]{username} );
        if (cursor.moveToFirst()) {
            count = cursor.getFloat(10);
        }
        return count;
    }



    public boolean Save_Average(String username,Float rate_sum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Rating",rate_sum);
        db.update("DoctorDetails", contentValues,"Username" +" = ?" , new String[] {username} );
        return true;
    }

    public String get_Spe(String username) {
        String val = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from DoctorDetails where Username = ?",new String[]{username} );
        if (cursor.moveToFirst()) {
            val = cursor.getString(7);
        }
        cursor.close();
        return val;
    }





}

