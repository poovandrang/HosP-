package com.example.hosp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Previous_Rate_Helper extends SQLiteOpenHelper {

    public Previous_Rate_Helper(Context context)
    {
        super(context,"Rating_Helper.DB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String qry="create Table RatingDetails(Doctor  TEXT , Patient TEXT ,Rating FLOAT )";
        sqLiteDatabase.execSQL(qry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String qry="drop Table if exists RatingDetails";
        sqLiteDatabase.execSQL(qry);
    }

    //////////////Insert DATA//////////////////
    public boolean InsertData(String doctor,String patient,Float rate){

        SQLiteDatabase MyDB=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Doctor",doctor);
        contentValues.put("Patient",patient);
        contentValues.put("Rating",rate);

        long result=MyDB.insert("RatingDetails",null,contentValues);
        if (result==-1) return false;
        else
            return true;

    }


    public Cursor Getdata()
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor= DB.rawQuery("Select * from RatingDetails",null);
        return cursor;

    }


    public boolean UpdateRate (String doctor, String patient,Float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Doctor",doctor);
        contentValues.put("Patient",patient);
        contentValues.put("Rating",rating);
        db.update("RatingDetails", contentValues,"Doctor" +" = ?" , new String[] {doctor} );
        return true;
    }



}
