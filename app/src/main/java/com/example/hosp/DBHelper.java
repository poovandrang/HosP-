package com.example.hosp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME="Patient.DB";

    public DBHelper( Context context) {
        super(context, DBNAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {

        MyDB.execSQL("create Table PatientDetails(Username TEXT primary key,Name TEXT,Password TEXT,Mobile TEXT ,Email TEXT,Age TEXT,Gender TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists PatientDetails");

    }
    //////////////Insert DATA
    public boolean InsertData(String username,String name,String pass,String mobile,String email,String age,String gender){

        SQLiteDatabase MyDB=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Name",name);
        contentValues.put("Password",pass);
        contentValues.put("Mobile",mobile);
        contentValues.put("Email",email);
        contentValues.put("Age",age);
        contentValues.put("Gender",gender);


        long result=MyDB.insert("PatientDetails",null,contentValues);
        if (result==-1) return false;
        else
            return true;

    }
    ///////////////////CHECKUSER ALREADY EXIST
    public Boolean CheckUsername( String username){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from PatientDetails where Username = ?",new String[] {username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    //////////////////////////////CheckUSERNAME-PASSWORD////

    public Boolean CheckUsername_password(String username,String password){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select *from PatientDetails where Username = ? and Password= ?",new String[]{username,password});
        if (cursor.getCount()>0)
            return true;
        else return false;

    }

    public Cursor Getdata()
    {
     SQLiteDatabase DB=this.getWritableDatabase();
     Cursor cursor= DB.rawQuery("Select * from PatientDetails",null);
     return cursor;

    }



    public String getMobile(String username) {
        String mobile = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from PatientDetails where Username = ?",new String[]{username} );
        if (cursor.moveToFirst()) {
            mobile = cursor.getString(3);
        }
        cursor.close();
        return mobile;
    }


    public Boolean CheckUsername_Mobile(String username,String mobile){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select *from PatientDetails where Username = ? and Mobile= ?",new String[]{username,mobile});
        if (cursor.getCount()>0)
            return true;
        else return false;

    }

    public boolean UpdatePassword (String username,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Password",password);

        db.update("PatientDetails", contentValues,"Username" +" = ?" , new String[] {username} );
        return true;
    }







}
