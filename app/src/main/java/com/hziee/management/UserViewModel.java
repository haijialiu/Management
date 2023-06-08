package com.hziee.management;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hziee.management.data.LoginRepository;
import com.hziee.management.entity.User;

public class UserViewModel extends ViewModel {
    private LoginRepository loginRepository;
    public LiveData<User> user;


    public UserViewModel(){
        this.loginRepository = LoginRepository.getInstance();
        MutableLiveData<User> data = new MutableLiveData<>();
        data.setValue(null);
        user = data;
    }

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<User> login(String username, String password) {
        user = loginRepository.login(username, password);
        return user;
    }
}
