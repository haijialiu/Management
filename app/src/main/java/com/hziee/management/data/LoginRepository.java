package com.hziee.management.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import com.hziee.management.dao.UserDao;
import com.hziee.management.entity.User;

import java.util.List;


public class LoginRepository {
    private static final String TAG = "LoginRepository";
    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;

    private static volatile LoginRepository instance;

    public static void Initialize(Application application){
        if(instance == null){
            instance = new LoginRepository(application);
        }
    }
    private LoginRepository(Application application) {
        ManagementDatabase db = ManagementDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mAllUsers = mUserDao.getAll();
    }

    public static LoginRepository getInstance() {
        return instance;
    }

    public LiveData<User> login(String username, String password) {
        Log.i(TAG, String.format("用户登录 用户名：%s，密码：%s",username,password));
        return mUserDao.getUserByUsernameAndPassword(username, password);
    }
}