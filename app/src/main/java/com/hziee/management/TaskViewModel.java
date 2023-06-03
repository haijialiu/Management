package com.hziee.management;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;


import com.hziee.management.data.TaskRepository;
import com.hziee.management.entity.Task;

public class TaskViewModel extends ViewModel {
    private static final String TAG = "TaskViewModel";
    private TaskRepository taskRepository;
    private MutableLiveData<Integer> taskIdLiveData;
    private LiveData<Task> taskLiveData;

    public TaskViewModel(){
        this.taskIdLiveData= new MutableLiveData<>();
        taskRepository = TaskRepository.getInstance();
    }
    public LiveData<Task> loadTask(Integer taskId){
        taskLiveData = Transformations.switchMap(taskIdLiveData, input -> taskRepository.getTaskById(taskId));
        taskIdLiveData.setValue(taskId);
        return taskLiveData;
    }

    public void saveTask(Task task){
        taskRepository.updateTask(task);
    }
    public void deleteTask(Integer taskId){taskRepository.deleteTask(taskId);}
}
