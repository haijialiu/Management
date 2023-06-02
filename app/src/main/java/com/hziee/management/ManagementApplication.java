package com.hziee.management;

import android.app.Application;

import com.hziee.management.data.LoginRepository;
import com.hziee.management.data.ProjectRepository;
import com.hziee.management.data.TaskRepository;

public class ManagementApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库,由于此时的application是一样的，所以database最终得到的参数也是一致的，database实例只会有一个
        ProjectRepository.Initialize(this);
        LoginRepository.Initialize(this);
        TaskRepository.Initialize(this);
    }

}
