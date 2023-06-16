package com.example.hosp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Share_helper {


    private final SharedPreferences sharedPreferences;
    private final Gson gson;




    public Share_helper(Context context){

        sharedPreferences=context.getSharedPreferences("sharedPreferences",Context.MODE_PRIVATE);
        gson=new Gson();

    }

    public void SaveTasks(ArrayList<Appointment_List> tasks){

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Tasks",gson.toJson(tasks));
        editor.apply();


    }

    public   ArrayList<Appointment_List> GetTasks(){

       String Task_String= sharedPreferences.getString("Tasks",null);

        Type taskListType=new TypeToken<ArrayList<Appointment_List>>(){}.getType();
       ArrayList<Appointment_List> tasks = gson.fromJson(Task_String,taskListType);


       if (tasks!=null) return tasks;
       else return new ArrayList<>();


    }




}
