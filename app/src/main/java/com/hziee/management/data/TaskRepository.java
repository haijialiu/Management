package com.hziee.management.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hziee.management.dao.TaskDao;
import com.hziee.management.entity.Task;

import java.util.List;

public class TaskRepository {
    private static volatile TaskRepository instance;
    private TaskDao taskDao;
    public static void Initialize(Application application) {
        if (instance == null) {
            instance = new TaskRepository(application);
        }
    }
    private TaskRepository(Application application){
        ManagementDatabase db = ManagementDatabase.getDatabase(application);
        taskDao = db.taskDao();
    }
    public LiveData<List<Task>> getAllTasks(){
        return taskDao.getAll();
    }
    public LiveData<List<Task>> getTasksByProjectId(Integer projectId){
        return taskDao.getTaskByProjectId(projectId);
    }
    public void updateTask(Task task){
        ManagementDatabase.databaseWriteExecutor.execute(()->{
            taskDao.updateProject(task);
        });
    }
    public void addTask(Task task){
        ManagementDatabase.databaseWriteExecutor.execute(()->{
            taskDao.insert(task);
        });
    }
    public static TaskRepository getInstance(){
        return instance;
    }
}
