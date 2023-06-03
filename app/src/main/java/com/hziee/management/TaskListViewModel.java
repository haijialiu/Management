package com.hziee.management;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hziee.management.data.ProjectRepository;
import com.hziee.management.data.TaskRepository;
import com.hziee.management.entity.Task;

import java.util.List;

public class TaskListViewModel extends ViewModel {
    private TaskRepository taskRepository;
    private LiveData<List<Task>> taskLivaData;

    public void initDatabase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public LiveData<List<Task>> getTasks(Integer projectId){
        taskLivaData = taskRepository.getTasksByProjectId(projectId);
        return taskLivaData;
    }
    public long addTask(Task task){
        return taskRepository.addTask(task);
    }

}
