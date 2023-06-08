package com.hziee.management;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.hziee.management.entity.Project;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProjectFragment extends Fragment {
    private static final String ARG_PROJECT_ID = "project_id";
    private static final String TAG = "ProjectFragment";
    private Project mProject;
    private ProjectViewModel projectViewModel;

    private LiveData<Project> projectLiveData;
    private EditText projectTitleEditText;
    private Button startDateButton;
    private Button startTimeButton;
    private Button endDateButton;
    private Button endTimeButton;
    private Button viewTasksButton;
    private Button deleteButton;

    private enum TimeType{
        START_DATE,START_TIME,END_DATE,END_TIME;
    }

    private Callbacks callbacks = projectId -> {
        ProjectFragmentDirections.NavigateToTaskList directions
                = ProjectFragmentDirections.navigateToTaskList(projectId);
        NavHostFragment.findNavController(this).navigate(directions);
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Integer projectId = (Integer) getArguments().getSerializable(ARG_PROJECT_ID);
        Log.d(TAG,"传递过来的工程记录ID为："+projectId.toString());

        projectViewModel = new ViewModelProvider(this)
                .get(ProjectViewModel.class);


        projectLiveData = projectViewModel.loadProject(projectId);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_project, container, false);
        projectTitleEditText = v.findViewById(R.id.project_title);
        startDateButton = v.findViewById(R.id.project_start_date);
        startTimeButton = v.findViewById(R.id.project_start_time);
        endDateButton = v.findViewById(R.id.project_end_date);
        endTimeButton = v.findViewById(R.id.project_end_time);
        viewTasksButton = v.findViewById(R.id.view_tasks);
        deleteButton = v.findViewById(R.id.delete_project);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        projectLiveData.observe(getViewLifecycleOwner(), new Observer<Project>() {
            @Override
            public void onChanged(Project project) {
                mProject = project;
                updateUI();
            }
        });


    }
    @Override
    public void onStart() {

        super.onStart();
        deleteButton.setOnClickListener(v -> {
            projectViewModel.deleteProject(mProject.getId());
            //getActivity().onBackPressed();
            NavHostFragment.findNavController(this).popBackStack();
        });
        projectTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mProject.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"传递projectId: "+mProject.getId());
                //TaskListFragmentDirections.navigateToTaskDetail()
                callbacks.onItemSelected(mProject.getId());
            }
        });
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date startTime = mProject.getStartTime();
                NavController navController = Navigation.findNavController(v);
                ProjectFragmentDirections.NavigateToTimePick directions =
                        ProjectFragmentDirections.navigateToTimePick(startTime.getTime());
                navController.navigate(directions);
                observeDialogResult(TimeType.START_TIME);
            }
        });
        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date endTime = mProject.getEndTime();
                NavController navController = Navigation.findNavController(v);
                ProjectFragmentDirections.NavigateToTimePick directions =
                        ProjectFragmentDirections.navigateToTimePick(endTime.getTime());
                navController.navigate(directions);

                observeDialogResult(TimeType.END_TIME);
            }
        });
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date startDate = mProject.getStartTime();
                NavController navController = Navigation.findNavController(v);
                ProjectFragmentDirections.NavigateToDatePick directions =
                        ProjectFragmentDirections.navigateToDatePick(startDate.getTime());
                navController.navigate(directions);

                observeDialogResult(TimeType.START_DATE);
            }
        });
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date endDate = mProject.getEndTime();
                NavController navController = Navigation.findNavController(v);
                ProjectFragmentDirections.NavigateToDatePick directions =
                        ProjectFragmentDirections.navigateToDatePick(endDate.getTime());
                navController.navigate(directions);
                observeDialogResult(TimeType.END_DATE);
            }
        });

    }
    @Override
    public void onStop() {
        super.onStop();
        if(mProject!=null)
            projectViewModel.saveProject(mProject);
    }
    private void updateUI() {
        if(mProject!=null) {
            projectTitleEditText.setText(mProject.getName());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
            startDateButton.setText(dateFormat.format(mProject.getStartTime()));
            endDateButton.setText(dateFormat.format(mProject.getEndTime()));
            dateFormat.applyPattern("kk:mm");
            startTimeButton.setText(dateFormat.format(mProject.getStartTime()));
            endTimeButton.setText(dateFormat.format(mProject.getEndTime()));
        }

    }
    public static ProjectFragment newInstance(Integer projectId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROJECT_ID,projectId);

        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }



    private void observeDialogResult(TimeType timeType){
        NavController navController = NavHostFragment.findNavController(this);
//        final NavBackStackEntry navBackStackEntry = navController.getBackStackEntry(R.id.projectFragment);
        final NavBackStackEntry navBackStackEntry = navController.getBackStackEntry(R.id.projectFragment);
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
                            updateCalendar.setTime(mProject.getStartTime());
                            updateCalendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
                            updateCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
                            mProject.setStartTime(updateCalendar.getTime());
                            break;
                        case START_DATE:
                            calendar = (Calendar)navBackStackEntry.getSavedStateHandle().get("date");
                            if(calendar==null)
                                return;
                            updateCalendar.setTime(mProject.getStartTime());
                            updateCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
                            updateCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                            updateCalendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
                            mProject.setStartTime(updateCalendar.getTime());
                            break;
                        case END_TIME:
                            calendar = (Calendar)navBackStackEntry.getSavedStateHandle().get("time");
                            if(calendar==null)
                                return;
                            updateCalendar.setTime(mProject.getEndTime());
                            updateCalendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
                            updateCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
                            mProject.setEndTime(updateCalendar.getTime());
                            break;
                        case END_DATE:
                            calendar = (Calendar)navBackStackEntry.getSavedStateHandle().get("date");
                            if(calendar==null)
                                return;
                            updateCalendar.setTime(mProject.getEndTime());
                            updateCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
                            updateCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                            updateCalendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
                            mProject.setEndTime(updateCalendar.getTime());
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
