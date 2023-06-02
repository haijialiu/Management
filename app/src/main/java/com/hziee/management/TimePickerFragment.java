package com.hziee.management;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {
    private static final String ARG_TIME = "time";

    interface Callbacks{
        public abstract void onTimeSelected(Date date);
    }
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Date resultDate = new Date();
                resultDate.setHours(i);
                resultDate.setMinutes(i1);
                ((Callbacks)getTargetFragment()).onTimeSelected(resultDate);
            }
        };
        Calendar calendar = Calendar.getInstance();
        //取出传过来的日期
        Bundle argument = getArguments();
        calendar.setTime((Date)argument.getSerializable(ARG_TIME));

        int initHours = calendar.get(Calendar.HOUR);
        int initMinute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(requireContext(),
                listener,
                initHours,
                initMinute,
                false);
    }

    public static TimePickerFragment newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME,date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
