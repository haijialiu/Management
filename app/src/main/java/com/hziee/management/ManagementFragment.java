package com.hziee.management;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hziee.management.data.ProjectRepository;
import com.hziee.management.entity.Project;

import java.util.List;

public class ManagementFragment extends Fragment {
    private static final String TAG = "ManagementFragment";
    private static final String ARG_ITEM_ID = "item_id";
    private ProjectListViewModel projectListViewModel;
    private TaskListViewModel taskListViewModel;
    private ProjectListRecyclerViewAdapter projectAdapter;
    private RecyclerView projectRecyclerView;
    private Callbacks mCallbacks = null;

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }
    @Override
    public void onDetach(){
        super.onDetach();
        mCallbacks = null;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        projectListViewModel = new ViewModelProvider(this,ViewModelProvider
                .AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(ProjectListViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);
        projectRecyclerView  = view.findViewById(R.id.project_recycler_view);
        projectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        updateUI();
    }



    public static ManagementFragment newInstance(Integer itemId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM_ID,itemId);
        ManagementFragment fragment = new ManagementFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static ManagementFragment getInstance(){
        return new ManagementFragment();
    }
    private void updateUI() {

        projectListViewModel.getCrimes(ProjectRepository.getInstance()).observe(
                getViewLifecycleOwner(), new Observer<List<Project>>() {
                    @Override
                    public void onChanged(List<Project> projects) {
                        Log.i(TAG,"得到的projects数为："+projects.size());
                        projectAdapter = new ProjectListRecyclerViewAdapter(projects,getActivity());
                        projectRecyclerView.setAdapter(projectAdapter);
                    }
                }
        );
    }
}
