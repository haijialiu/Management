package com.hziee.management.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hziee.management.dao.TaskDao;
import com.hziee.management.entity.Task;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
    public LiveData<Task> getTaskById(Integer taskId){
        return taskDao.getTask(taskId);
    }
    public void updateTask(Task task){
        ManagementDatabase.databaseWriteExecutor.execute(()->{
            taskDao.updateProject(task);
        });
    }
    public long addTask(Task task){
        Callable<Long> insertCallable = () -> taskDao.insert(task);
        Future<Long> submit = ManagementDatabase.databaseWriteExecutor.submit(insertCallable);
        try {
            return submit.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public static TaskRepository getInstance(){
        return instance;
    }
}
