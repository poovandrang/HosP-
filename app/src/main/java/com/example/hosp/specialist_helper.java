package com.example.hosp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class specialist_helper extends SQLiteOpenHelper {
    public specialist_helper(Context context) {
        super(context,"Specialist.DB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {

        String qry="create Table Specialist_list_DS(Username TEXT,Dental_Surgeon TEXT,Rating TEXT )";
        String qry1="create Table Specialist_list_ED(Username TEXT,Endodontist TEXT,Rating TEXT)";
        String qry2="create Table Specialist_list_OD(Username TEXT,Orthodonist TEXT,Rating TEXT)";
        String qry3="create Table Get_DocTor_Notification(Patient TEXT,Time TEXT,Date TEXT,Doctor_Name TEXT,Mobile TEXT)";


        String qry4="create Table Only_For_List(Username TEXT,Specialist TEXT,Rating TEXT)";

        MyDB.execSQL(qry);
        MyDB.execSQL(qry1);
        MyDB.execSQL(qry2);
        MyDB.execSQL(qry3);
        MyDB.execSQL(qry4);







    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String qry="drop Table if exists Specialist_list";
        sqLiteDatabase.execSQL(qry);

    }

    //////////////Insert DATA//////////////////
    public boolean InsertDental_Surgeon(String username,String specialist){

        SQLiteDatabase MyDB=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Dental_Surgeon",specialist);
        contentValues.put("Rating",0);


        long result=MyDB.insert("Specialist_list_DS",null,contentValues);
        if (result==-1) return false;
        else
            return true;

    }

    public boolean InsertEndodontist(String username,String specialist){

        SQLiteDatabase MyDB=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Endodontist",specialist);
        contentValues.put("Rating",0);


        long result=MyDB.insert("Specialist_list_ED",null,contentValues);
        if (result==-1) return false;
        else
            return true;

    }


    public boolean InsertOrthodonist(String username,String specialist){

        SQLiteDatabase MyDB=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Orthodonist",specialist);
        contentValues.put("Rating",0);


        long result=MyDB.insert("Specialist_list_OD",null,contentValues);
        if (result==-1) return false;
        else
            return true;

    }

    public boolean Insert_Doc_Notification(String patientname,String time,String date,String doctor,String mobile){

        SQLiteDatabase MyDB=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Patient",patientname);
        contentValues.put("Time",time);
        contentValues.put("Date",date);
        contentValues.put("Doctor_Name",doctor);
        contentValues.put("Mobile",mobile);


        long result=MyDB.insert("Get_DocTor_Notification",null,contentValues);
        if (result==-1) return false;
        else
            return true;

    }


    //////////////Insert DATA//////////////////
    public boolean Insert(String username,String specialist){

        SQLiteDatabase MyDB=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Specialist",specialist);
        contentValues.put("Rating",0);


        long result=MyDB.insert("Only_For_List",null,contentValues);
        if (result==-1) return false;
        else
            return true;

    }



    public boolean UpdateRating (String username,float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Rating",rating);

        db.update("Only_For_List", contentValues,"Username" +" = ?" , new String[] {username} );
        return true;
    }











    public  Boolean Delete_Notife(String patient_name){

        SQLiteDatabase MyDB=this.getWritableDatabase();

        Cursor cursor=MyDB.rawQuery("Select * from Get_DocTor_Notification where Patient = ?",new String[]{String.valueOf(patient_name)} );
        if (cursor.getCount()>0){


            long result=MyDB.delete("Get_DocTor_Notification","Patient=?",new String[]{String.valueOf(patient_name)});
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
        Cursor cursor= DB.rawQuery("Select * from Get_DocTor_Notification",null);
        return cursor;

    }



    public Cursor Getdata_DS()
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor= DB.rawQuery("Select * from Specialist_list_DS",null);
        return cursor;

    }

    public Cursor Getdata_ED()
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor= DB.rawQuery("Select * from Specialist_list_ED ",null);
        return cursor;

    }

    public Cursor Getdata_OD()
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor= DB.rawQuery("Select * from Specialist_list_OD",null);
        return cursor;

    }

















    public List<String> getAllLabels(){


        List<String> list=new ArrayList<String>();

        SQLiteDatabase MyDB=this.getReadableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from Specialist_list_DS",null);

        if (cursor.moveToFirst()){

            do {
            list.add(cursor.getString(0));
            }while (cursor.moveToNext());


        }

        return list;

    }



    public List<String> getAllLabels1(){


        List<String> list=new ArrayList<String>();

        SQLiteDatabase MyDB=this.getReadableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from Specialist_list_ED",null);

        if (cursor.moveToFirst()){

            do {
                list.add(cursor.getString(0));
            }while (cursor.moveToNext());


        }

        return list;

    }



    public List<String> getAllLabels2(){


        List<String> list=new ArrayList<String>();

        SQLiteDatabase MyDB=this.getReadableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from Specialist_list_OD",null);

        if (cursor.moveToFirst()){

            do {
                list.add(cursor.getString(0));
            }while (cursor.moveToNext());


        }

        return list;

    }

    public boolean UpdateRating_DS (String username,float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Rating",rating);

        db.update("Specialist_list_DS", contentValues,"Username" +" = ?" , new String[] {username} );
        return true;
    }

    public boolean UpdateRating_ED (String username,float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Rating",rating);

        db.update("Specialist_list_ED", contentValues,"Username" +" = ?" , new String[] {username} );
        return true;
    }

    public boolean UpdateRating_OD (String username,float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Rating",rating);

        db.update("Specialist_list_OD", contentValues,"Username" +" = ?" , new String[] {username} );
        return true;
    }









    public Boolean CheckDoctor_DS( String doctor){
        SQLiteDatabase MyDB=this.getReadableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from Specialist_list_DS where Username = ?",new String[] {doctor});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }


    public Boolean CheckDoctor_ED( String doctor){
        SQLiteDatabase MyDB=this.getReadableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from Specialist_list_ED where Username = ?",new String[] {doctor});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }


    public Boolean CheckDoctor_OD( String doctor){
        SQLiteDatabase MyDB=this.getReadableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from Specialist_list_OD where Username = ?",new String[] {doctor});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }


    public String getDS_User(String username) {
        String average = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Specialist_list_DS where Username = ?",new String[]{username} );
        if (cursor.moveToFirst()) {
            average = cursor.getString(0);
        }
        return average;
    }

    public String getED_User(String username) {
        String average = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Specialist_list_ED where Username = ?",new String[]{username} );
        if (cursor.moveToFirst()) {
            average = cursor.getString(0);
        }
        return average;
    }

    public String getOD_User(String username) {
        String average = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Specialist_list_ED where Username = ?",new String[]{username} );
        if (cursor.moveToFirst()) {
            average = cursor.getString(0);
        }
        return average;
    }









}
