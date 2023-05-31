package com.hziee.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements Callbacks{
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment == null) {
//            fragment = ManagementFragment.getInstance();
            fragment = ProjectListFragment.newInstance();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
    }

    @Override
    public void onItemSelected(Integer itemId) {
        Log.d(TAG,"OnItemSelected:"+itemId);
        ProjectFragment fragment = ProjectFragment.newInstance(itemId);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container,fragment)
                .addToBackStack(null)
                .commit();
    }
}