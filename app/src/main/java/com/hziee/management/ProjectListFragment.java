package com.hziee.management;

import android.app.Application;
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
import com.hziee.management.entity.Project;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ProjectListFragment extends Fragment implements Callbacks{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "ProjectListFragment";
    // TODO: Customize parameters
    private ProjectListViewModel projectListViewModel;
    private ProjectListRecyclerViewAdapter projectAdapter;
    private RecyclerView projectRecyclerView;
    private Callbacks mCallbacks = projectId -> {
        com.hziee.management.ProjectListFragmentDirections.NavigateToProductDetail
                directions = ProjectListFragmentDirections.navigateToProductDetail(projectId);
        NavHostFragment.findNavController(this).navigate(directions);
    };
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProjectListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProjectListFragment newInstance(int columnCount) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        //mCallbacks = (Callbacks) context;

    }
    @Override
    public void onDetach(){
        super.onDetach();
        //mCallbacks = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        projectListViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) ViewModelProvider
                .AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(ProjectListViewModel.class);
        projectListViewModel.initDatabase(ProjectRepository.getInstance());
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

    private void updateUI() {
        projectListViewModel.getCrimes(ProjectRepository.getInstance()).observe(
                getViewLifecycleOwner(), new Observer<List<Project>>() {
                    @Override
                    public void onChanged(List<Project> projects) {
                        projectAdapter = new ProjectListRecyclerViewAdapter(projects,mCallbacks);
                        projectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        projectRecyclerView.setAdapter(projectAdapter);
                    }
                }
        );
    }

    public static ProjectListFragment newInstance(){
        return new ProjectListFragment();
    }

    @Override
    public void onItemSelected(Integer projectId) {
        com.hziee.management.ProjectListFragmentDirections
                .NavigateToProductDetail directions =
                ProjectListFragmentDirections.navigateToProductDetail(projectId);
        NavHostFragment.findNavController(this).navigate(directions);
    }
}