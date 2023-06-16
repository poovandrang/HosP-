package com.example.hosp;

public class StatusModel {

    String Name;
    String Date;
    String Time;
    String Status;


    public StatusModel(String name, String date, String time, String status) {
        Name = name;
        Date = date;
        Time = time;
        Status = status;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
