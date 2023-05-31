package com.hziee.management;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hziee.management.data.ProjectRepository;
import com.hziee.management.database.ManagementDatabase;
import com.hziee.management.entity.Project;

import java.util.List;

public class ProjectListViewModel extends ViewModel {
    private ProjectRepository projectRepository;
    private LiveData<List<Project>> crimeListLiveData;

    public void initDatabase(ProjectRepository projectRepository) {
        projectRepository = projectRepository;
    }

    public LiveData<List<Project>> getCrimes(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        crimeListLiveData = projectRepository.getProjects();
        return crimeListLiveData;

    }
}
