package com.hziee.management;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import java.util.Calendar;

//https://developer.android.google.cn/guide/topics/ui/controls/pickers?hl=zh-cn
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "TimePickerFragment";
    private NavController navController;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        long time = TimePickerFragmentArgs.fromBundle(getArguments()).getTime();
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        navController = NavHostFragment.findNavController(TimePickerFragment.this);
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        Log.d(TAG, String.format("hour: %d, minute: %d", hourOfDay,minute));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        navController.getPreviousBackStackEntry().getSavedStateHandle()
                .set("time", calendar);
    }
}
//public class TimePickerFragment extends DialogFragment {
//    private static final String ARG_TIME = "time";
//
//    interface Callbacks{
//        public abstract void onTimeSelected(Date date);
//    }
//    @Override
//    public Dialog onCreateDialog(Bundle saveInstanceState){
//        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int i, int i1) {
//                Date resultDate = new Date();
//                resultDate.setHours(i);
//                resultDate.setMinutes(i1);
//                ((Callbacks)getTargetFragment()).onTimeSelected(resultDate);
//            }
//        };
//        Calendar calendar = Calendar.getInstance();
//        //取出传过来的日期
//        Bundle argument = getArguments();
//        calendar.setTime((Date)argument.getSerializable(ARG_TIME));
//
//        int initHours = calendar.get(Calendar.HOUR);
//        int initMinute = calendar.get(Calendar.MINUTE);
//        return new TimePickerDialog(requireContext(),
//                listener,
//                initHours,
//                initMinute,
//                false);
//    }
//
//    public static TimePickerFragment newInstance(Date date) {
//
//        Bundle args = new Bundle();
//        args.putSerializable(ARG_TIME,date);
//        TimePickerFragment fragment = new TimePickerFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//}
