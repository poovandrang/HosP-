package com.example.hosp;

import java.util.ArrayList;

public class Patient_List {

    String patient,mobile,mail,age,gender;

    public Patient_List(String patient,String mobile,String mail,String age,String gender) {
        this.patient = patient;
        this.mobile = mobile;
        this.mail=mail;
        this.age=age;
        this.gender=gender;
    }



    public String getPatient() {
        return patient;
    }
    public String getMobile(){

        return mobile;
    }

    public String getMail(){

        return mail;

    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}
