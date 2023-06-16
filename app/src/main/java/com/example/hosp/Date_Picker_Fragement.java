package com.example.hosp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class Date_Picker_Fragement extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar calendar=Calendar.getInstance();
        int Year=calendar.get(Calendar.YEAR);
        int Month=calendar.get(Calendar.MONTH);
        int Date=calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog pickerDialog=new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(),Year,Month,Date);
        pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        return pickerDialog;
    }
}
