package com.hziee.management;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//https://developer.android.google.cn/guide/topics/ui/controls/pickers?hl=zh-cn
public class DatePickerFragment  extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private static final String ARG_DATE = "date";
    private static final String TAG = "DatePickerFragment";
    private NavController navController;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        long time = DatePickerFragmentArgs.fromBundle(getArguments()).getDate();
        c.setTimeInMillis(time);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        navController = NavHostFragment.findNavController(DatePickerFragment.this);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Log.d(TAG, String.format("%d-%d-%d",year,month,day));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DATE,day);
        navController.getPreviousBackStackEntry().getSavedStateHandle()
                .set("date", calendar);

    }
    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE,date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
