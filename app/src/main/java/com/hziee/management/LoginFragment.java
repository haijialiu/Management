package com.hziee.management;

import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hziee.management.entity.User;

public class LoginFragment extends Fragment {

    public static String TAG = "LoginFragment";
    public static String LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL";
    private LoginViewModel mViewModel;
    private UserViewModel userViewModel;
    private SavedStateHandle savedStateHandle;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        savedStateHandle = Navigation.findNavController(view)
                .getPreviousBackStackEntry()
                .getSavedStateHandle();
        savedStateHandle.set(LOGIN_SUCCESSFUL, false);
        EditText usernameEditText = view.findViewById(R.id.username);
        EditText passwordEditText = view.findViewById(R.id.password);
        Button loginButton = view.findViewById(R.id.login);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            login(username, password);
        });
    }

    private void login(String username, String password) {
        Log.d(TAG, "login: userViewModel "+userViewModel);
        userViewModel.login(username, password).observe(this.getViewLifecycleOwner(), user -> {
            if (user != null) {
                savedStateHandle.set(LOGIN_SUCCESSFUL, true);
                NavHostFragment.findNavController(this).popBackStack();
            }else{
                Log.d(TAG, "login: login failed");
            }
        });
    }

}