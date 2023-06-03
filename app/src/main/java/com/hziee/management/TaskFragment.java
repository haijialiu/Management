package com.hziee.management;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.hziee.management.entity.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TaskFragment extends Fragment {
    private static final String TAG = "ProjectFragment";
    private static final String ARG_TASK_ID = "task_id";

    private TaskViewModel taskViewModel;
    private Task mTask;
    private LiveData<Task> taskLiveData;
    private EditText taskTitleEditText;
    private EditText taskCommitEditText;
    private EditText taskHeaderEditText;
    private Button startDateButton;
    private Button startTimeButton;
    private Button endDateButton;
    private Button endTimeButton;

    private enum TimeType{
        START_DATE,START_TIME,END_DATE,END_TIME;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Integer taskId = TaskFragmentArgs.fromBundle(getArguments()).getTaskId();
        Log.d(TAG,"传递过来的任务记录ID为："+taskId);

        taskViewModel = new ViewModelProvider((ViewModelStoreOwner) this,
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getActivity().getApplication()))
                .get(TaskViewModel.class);
        taskLiveData = taskViewModel.loadTask(taskId);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);
        taskTitleEditText = v.findViewById(R.id.task_title);
        taskCommitEditText = v.findViewById(R.id.task_commit);
        taskHeaderEditText = v.findViewById(R.id.task_header);
        startDateButton = v.findViewById(R.id.task_start_date);
        startTimeButton = v.findViewById(R.id.task_start_time);
        endDateButton = v.findViewById(R.id.task_end_date);
        endTimeButton = v.findViewById(R.id.task_end_time);
        return v;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskLiveData.observe(getViewLifecycleOwner(), new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                mTask = task;
                updateUI();
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        taskTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        taskCommitEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setCommit(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        taskHeaderEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setHeader(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date startTime = mTask.getStartTime();
                NavController navController = Navigation.findNavController(v);
                TaskFragmentDirections.NavigateToTimePick directions =
                        TaskFragmentDirections.navigateToTimePick(startTime.getTime());
                navController.navigate(directions);
                observeDialogResult(TimeType.START_TIME);
            }
        });
        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date endTime =mTask.getEndTime();
                NavController navController = Navigation.findNavController(v);
                TaskFragmentDirections.NavigateToTimePick directions =
                        TaskFragmentDirections.navigateToTimePick(endTime.getTime());
                navController.navigate(directions);

                observeDialogResult(TimeType.END_TIME);
            }
        });
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date startDate = mTask.getStartTime();
                NavController navController = Navigation.findNavController(v);
                TaskFragmentDirections.NavigateToDatePick directions =
                        TaskFragmentDirections.navigateToDatePick(startDate.getTime());
                navController.navigate(directions);

                observeDialogResult(TimeType.START_DATE);
            }
        });
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date endDate = mTask.getEndTime();
                NavController navController = Navigation.findNavController(v);
                TaskFragmentDirections.NavigateToDatePick directions =
                        TaskFragmentDirections.navigateToDatePick(endDate.getTime());
                navController.navigate(directions);
                observeDialogResult(TimeType.END_DATE);
            }
        });

    }
    @Override
    public void onStop() {
        super.onStop();
        taskViewModel.saveTask(mTask);
    }

    private void updateUI() {
        taskTitleEditText.setText(mTask.getName());
        taskCommitEditText.setText(mTask.getCommit());
        taskHeaderEditText.setText(mTask.getHeader());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        startDateButton.setText(dateFormat.format(mTask.getStartTime()));
        endDateButton.setText(dateFormat.format(mTask.getEndTime()));
        dateFormat.applyPattern("kk:mm");
        startTimeButton.setText(dateFormat.format(mTask.getStartTime()));
        endTimeButton.setText(dateFormat.format(mTask.getEndTime()));
    }
    public static ProjectFragment newInstance(Integer taskId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID,taskId);
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private void observeDialogResult(TimeType timeType){
        NavController navController = NavHostFragment.findNavController(this);
//        final NavBackStackEntry navBackStackEntry = navController.getBackStackEntry(R.id.projectFragment);
        final NavBackStackEntry navBackStackEntry = navController.getBackStackEntry(R.id.taskFragment);
        final LifecycleEventObserver observer = new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event.equals(Lifecycle.Event.ON_RESUME)) {
//                    Calendar calendar = (Calendar)navBackStackEntry.getSavedStateHandle().get("time");
                    Calendar calendar;
                    // 处理监听到的数据
                    Calendar updateCalendar = Calendar.getInstance();
                    switch (timeType){
                        case START_TIME:
                            calendar = (Calendar)navBackStackEntry.getSavedStateHandle().get("time");
                            if(calendar==null)
                                return;
                            updateCalendar.setTime(mTask.getStartTime());
                            updateCalendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
                            updateCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
                            mTask.setStartTime(updateCalendar.getTime());
                            break;
                        case START_DATE:
                            calendar = (Calendar)navBackStackEntry.getSavedStateHandle().get("date");
                            if(calendar==null)
                                return;
                            updateCalendar.setTime(mTask.getStartTime());
                            updateCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
                            updateCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                            updateCalendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
                            mTask.setStartTime(updateCalendar.getTime());
                            break;
                        case END_TIME:
                            calendar = (Calendar)navBackStackEntry.getSavedStateHandle().get("time");
                            if(calendar==null)
                                return;
                            updateCalendar.setTime(mTask.getEndTime());
                            updateCalendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
                            updateCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
                            mTask.setEndTime(updateCalendar.getTime());
                            break;
                        case END_DATE:
                            calendar = (Calendar)navBackStackEntry.getSavedStateHandle().get("date");
                            if(calendar==null)
                                return;
                            updateCalendar.setTime(mTask.getEndTime());
                            updateCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
                            updateCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                            updateCalendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
                            mTask.setEndTime(updateCalendar.getTime());
                            break;
                    }
                    Log.d(TAG, updateCalendar.toString());
                    navController.getCurrentBackStackEntry().getSavedStateHandle().remove("date");
                    navController.getCurrentBackStackEntry().getSavedStateHandle().remove("time");
                    updateUI();
                    //如果不把自身移除会有奇怪的bug出现：此方法莫名其妙调用了多次并且把所有日期设置为同一个值
                    navBackStackEntry.getLifecycle().removeObserver(this);
                }
            }
        };

        navBackStackEntry.getLifecycle().addObserver(observer);

//        getViewLifecycleOwner().getLifecycle().addObserver(new LifecycleEventObserver() {
//            @Override
//            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
//                if (event.equals(Lifecycle.Event.ON_DESTROY)) {
//                    navBackStackEntry.getLifecycle().removeObserver(observer);
//                }
//            }
//        });
    }
}
