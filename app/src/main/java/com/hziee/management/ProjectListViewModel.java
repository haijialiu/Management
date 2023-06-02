package com.hziee.management;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hziee.management.data.ProjectRepository;
import com.hziee.management.entity.Project;

import java.util.List;

public class ProjectListViewModel extends ViewModel {
    private ProjectRepository projectRepository;
    private LiveData<List<Project>> projectListLiveData;

    public void initDatabase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public LiveData<List<Project>> getProjects(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        projectListLiveData = projectRepository.getProjects();
        return projectListLiveData;

    }
    public LiveData<List<Project>> getProjects() {
        projectListLiveData = projectRepository.getProjects();
        return projectListLiveData;

    }
}
