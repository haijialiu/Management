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

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.hziee.management.entity.Project;
import com.hziee.management.entity.Task;

import java.text.SimpleDateFormat;
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

}
