package com.example.hosp;

import android.content.Context;
import android.widget.TextView;

import java.io.Serializable;

public class notifi_list
{
    String Title;
    String Message;

    public notifi_list() {
    }

    public notifi_list(String title, String message) {
        Title = title;
        Message = message;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
