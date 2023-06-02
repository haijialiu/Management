package com.hziee.management.data;

import android.app.Application;

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

//    public Result<LoggedInUser> login(String username, String password) {
//        Log.i(TAG, String.format("用户登录 用户名：%s，密码：%s",username,password));
//        // handle login
//        LiveData<User> liveData = mUserDao.getUserByUsernameAndPassword(username, password);
//
//        User dataUser = liveData.getValue();
//        System.out.println(dataUser);
//        Result<LoggedInUser> result;
//        if(dataUser !=null){
//            //success
//            user = new LoggedInUser(dataUser.getId(),dataUser.getNickName());
//            Log.i(TAG, String.format("登录成功：%s", dataUser));
//            result = new Result.Success<>(user);
//        }else{
//            //fail
//            Log.i(TAG, String.format("登录失败：用户名：%s，密码：%s",username,password));
//            result = new Result.Error(new IOException("Error logging in"));
//
//        }
//        if (result instanceof Result.Success) {
//            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
//        }
//        return result;
//    }
}