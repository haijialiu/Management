package com.hziee.management;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.hziee.management.data.ProjectRepository;
import com.hziee.management.entity.Project;

import java.util.Date;

// 显示工程的详细页面
public class ProjectViewModel extends ViewModel {
    private static final String TAG = "ProjectViewModel";
    private ProjectRepository projectRepository;
    private MutableLiveData<Integer> projectIdLiveData;
    private LiveData<Project> projectLiveData;



    public ProjectViewModel(){
        this.projectIdLiveData = new MutableLiveData<>();
        projectRepository = ProjectRepository.getInstance();

    }

    public LiveData<Project> loadProject(Integer projectId){

        //projectRepository = ProjectRepository.getInstance();
        projectLiveData = Transformations.switchMap(projectIdLiveData,
                input -> projectRepository.getProject(projectId));
        projectIdLiveData.setValue(projectId);
        return projectLiveData;
    }

    public void deleteProject(Integer projectId){
        projectRepository.deleteProject(projectId);
    }


    public void saveProject(Project project){
        projectRepository.updateProject(project);
    }
}
