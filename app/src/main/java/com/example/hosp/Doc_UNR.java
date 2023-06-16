package com.example.hosp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Doc_UNR extends SQLiteOpenHelper {
    public static final String DBNAME="Rating.DB";

    public Doc_UNR(Context context)
    {
        super(context,DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("create Table RatingDetails(Username TEXT primary key,Name TEXT,Rating TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("drop Table if exists RatingDetails");

    }

    //////////////Insert DATA
    public boolean InsertData(String username){

        SQLiteDatabase MyDB=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Username",username);


        long result=MyDB.insert("RatingDetails",null,contentValues);
        if (result==-1) return false;
        else
            return true;

    }

    public Boolean UpdateData_Rating(String username,String name,String rating) {


        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Name",name);
        contentValues.put("Rating",rating);


        @SuppressLint("Recycle") Cursor cursor = MyDB.rawQuery("Select * from RatingDetails where Username = ?", new String[]{String.valueOf(username)});
        if (cursor.getCount() > 0) {

            long result = MyDB.update("RatingDetails", contentValues,"Username=?", new String[]{String.valueOf(username)});
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


    public boolean updateContact (String username,String name,String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Name",name);
        contentValues.put("Rating",rating);

        db.update("RatingDetails", contentValues, "Username = ? ", new String[] {String.valueOf(username)} );
        return true;
    }

    public Cursor Getdata()
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor= DB.rawQuery("Select * from RatingDetails ",null);
        return cursor;

    }


    public Boolean CheckUsername( String username){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from RatingDetails where Username = ?",new String[] {username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }





}
