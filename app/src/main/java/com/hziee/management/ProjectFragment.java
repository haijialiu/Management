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
import androidx.navigation.fragment.NavHostFragment;

import com.hziee.management.entity.Project;

import java.text.SimpleDateFormat;
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


    private Callbacks callbacks = projectId -> {

        com.hziee.management.ProjectFragmentDirections.NavigateToTaskList directions
                = ProjectFragmentDirections.navigateToTaskList(projectId);
        NavHostFragment.findNavController(this).navigate(directions);
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Integer projectId = (Integer) getArguments().getSerializable(ARG_PROJECT_ID);
        Log.d(TAG,"传递过来的工程记录ID为："+projectId.toString());

        projectViewModel = new ViewModelProvider((ViewModelStoreOwner) this,
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getActivity().getApplication()))
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
    }
    @Override
    public void onStop() {
        super.onStop();
        projectViewModel.saveProject(mProject);
    }
    private void updateUI() {
        projectTitleEditText.setText(mProject.getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        startDateButton.setText(dateFormat.format(mProject.getStartTime()));
        endDateButton.setText(dateFormat.format(mProject.getEndTime()));
        dateFormat.applyPattern("kk:mm");
        startTimeButton.setText(dateFormat.format(mProject.getStartTime()));
        endTimeButton.setText(dateFormat.format(mProject.getEndTime()));

    }
    public static ProjectFragment newInstance(Integer projectId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROJECT_ID,projectId);

        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
