package com.hziee.management;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hziee.management.data.ProjectRepository;
import com.hziee.management.entity.Project;
import com.hziee.management.entity.User;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ProjectListFragment extends Fragment implements Callbacks{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "ProjectListFragment";
    // TODO: Customize parameters
    private UserViewModel userViewModel;
    private ProjectListViewModel projectListViewModel;
    private ProjectListRecyclerViewAdapter projectAdapter;
    private RecyclerView projectRecyclerView;
    private final Callbacks mCallbacks = projectId -> {
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

        projectListViewModel = new ViewModelProvider(getActivity()).get(ProjectListViewModel.class);

        projectListViewModel.initDatabase(ProjectRepository.getInstance());

        Intent intent = getActivity().getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(TAG, query);
        }

        NavController navController = NavHostFragment.findNavController(this);

        NavBackStackEntry navBackStackEntry = navController.getCurrentBackStackEntry();
        SavedStateHandle savedStateHandle = navBackStackEntry.getSavedStateHandle();
        savedStateHandle.getLiveData(LoginFragment.LOGIN_SUCCESSFUL)
                .observe(navBackStackEntry, new Observer<Object>() {
                    @Override
                    public void onChanged(Object success) {
                        if (!(Boolean)success) {
                            int startDestination = navController.getGraph().getStartDestination();
                            NavOptions navOptions = new NavOptions.Builder()
                                    .setPopUpTo(startDestination, true)
                                    .build();
                            navController.navigate(startDestination, null, navOptions);
                        }
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);
        projectRecyclerView  = view.findViewById(R.id.project_recycler_view);
        projectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.fragment_project_list,menu);

                SearchView searchView = (SearchView) menu.findItem(R.id.search_project).getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Log.d(TAG, "onQueryTextSubmit: "+query);
                        updateUI(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
                searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        Log.d(TAG, "onClose: 关闭搜索框");
                        updateUI();
                        return false;
                    }
                });
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.new_project:
                        Project project = new Project();
                        long pk = projectListViewModel.addProject(project);

                        mCallbacks.onItemSelected(Long.valueOf(pk).intValue());

                }
                return false;
            }
        },getViewLifecycleOwner(), Lifecycle.State.RESUMED);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        final NavController navController = Navigation.findNavController(view);
        Log.d(TAG, "onViewCreated: "+userViewModel);
        userViewModel.user.observe(getViewLifecycleOwner(), (Observer<User>) user -> {
            if (user != null) {
                Log.d(TAG, "onViewCreated: welcome");
            } else {
                navController.navigate(R.id.login_fragment);
            }

        });
        updateUI();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void updateUI() {
        projectListViewModel.getProjects().observe(
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
    private void updateUI(String value) {
        projectListViewModel.getProjects(value).observe(
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