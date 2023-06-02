package com.hziee.management;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hziee.management.data.ProjectRepository;
import com.hziee.management.data.TaskRepository;
import com.hziee.management.entity.Project;
import com.hziee.management.entity.Task;
import com.hziee.management.placeholder.PlaceholderContent;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class TaskListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String TAG = "TaskListFragment";
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_PROJECT_ID = "project_id";
    private static final String ARG_TASK_ID = "task_id";
    // TODO: Customize parameters
    private TaskListViewModel taskListViewModel;
    private TaskListRecyclerViewAdapter taskAdapter;
    private RecyclerView taskRecyclerView;
    private Integer projectId;

    private final Callbacks mCallbacks = taskId -> {

        TaskListFragmentDirections.NavigateToTaskDetail directions =
                TaskListFragmentDirections.navigateToTaskDetail(taskId);
        NavHostFragment.findNavController(this).navigate(directions);
    };
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TaskListFragment newInstance(int projectId) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PROJECT_ID, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        projectId = ProjectFragmentArgs.fromBundle(getArguments()).getProjectId();
        Log.d(TAG,"传递过来的工程记录ID为："+projectId);
        this.projectId = getArguments().getInt(ARG_PROJECT_ID);
        taskListViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) ViewModelProvider
                .AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(TaskListViewModel.class);
        taskListViewModel.initDatabase(TaskRepository.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        taskRecyclerView  = view.findViewById(R.id.task_recycler_view);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        updateUI();
    }

    private void updateUI() {

        taskListViewModel.getTasks(projectId).observe(
                getViewLifecycleOwner(), new Observer<List<Task>>() {
                    @Override
                    public void onChanged(List<Task> tasks) {
                        //TODO
                        taskAdapter = new TaskListRecyclerViewAdapter(tasks,mCallbacks);
                        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        taskRecyclerView.setAdapter(taskAdapter);
                    }
                }
        );
    }

}