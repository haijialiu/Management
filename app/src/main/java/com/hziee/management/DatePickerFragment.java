package com.hziee.management;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment  extends DialogFragment {
    private static final String ARG_DATE = "date";

    interface Callbacks{
        public abstract void onDateSelected(Date date);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Date resultDate = new GregorianCalendar(i,i1,i2).getTime();
                ((Callbacks)getTargetFragment()).onDateSelected(resultDate);
            }
        };
        Calendar calendar = Calendar.getInstance();
        //取出传过来的日期
        Bundle argument = getArguments();
        calendar.setTime((Date)argument.getSerializable(ARG_DATE));

        int initYear = calendar.get(Calendar.YEAR);
        int initMonth = calendar.get(Calendar.MONTH);
        int initDay = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(requireContext(),
                listener,
                initYear,
                initMonth,
                initDay);
    }

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE,date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
