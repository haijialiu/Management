package com.hziee.management;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ManagementFragment extends Fragment {
    private static final String TAG = "ManagementFragment";
    private static final String ARG_ITEM_ID = "item_id";
    private ProjectListViewModel projectListViewModel;
    private TaskListViewModel taskListViewModel;
    private MyProjectListRecyclerViewAdapter projectAdapter;

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
}
