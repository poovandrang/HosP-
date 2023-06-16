package com.example.hosp;

public class Appointment_List {

    public String Patient_Name,Mobile,Time,Specialist,Date,Appointment_Date;

    public Appointment_List(String patient_Name, String mobile, String time, String specialist, String date, String appointment_Date) {
        Patient_Name = patient_Name;
        Mobile = mobile;
        Time = time;
        Date = date;
        Specialist = specialist;
        Appointment_Date=appointment_Date;
    }

    public String getPatient_Name() {
        return Patient_Name;
    }

    public void setPatient_Name(String patient_Name) {
        Patient_Name = patient_Name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getSpecialist() {
        return Specialist;
    }

    public void setSpecialist(String specialist) {
        Specialist = specialist;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAppointment_Date() {
        return Appointment_Date;
    }

    public void setAppointment_Date(String appointment_Date) {
        Appointment_Date = appointment_Date;
    }
}
