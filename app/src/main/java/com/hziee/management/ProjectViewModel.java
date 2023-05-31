package com.hziee.management;

import android.app.Application;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.hziee.management.data.ProjectRepository;
import com.hziee.management.entity.Project;
// 显示工程的详细页面
public class ProjectViewModel extends ViewModel {
    private ProjectRepository projectRepository;
    private MutableLiveData<Integer> projectIdLiveData;
    private LiveData<Project> projectLiveData;

    public LiveData<Project> loadProject(Integer projectId){
        //TODO database
        projectRepository= ProjectRepository.getInstance();
        projectLiveData = Transformations.switchMap(projectIdLiveData,
                new Function<Integer, LiveData<Project>>() {
                    @Override
                    public LiveData<Project> apply(Integer input) {
                        return projectRepository.getProject(projectId);
                    }
                });
        projectIdLiveData.setValue(projectId);
        return projectLiveData;
    }
    public void saveProject(Project project){
        projectRepository.updateProject(project);
    }
}
