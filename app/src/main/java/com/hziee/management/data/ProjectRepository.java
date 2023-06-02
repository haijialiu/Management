package com.hziee.management.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hziee.management.dao.ProjectDao;
import com.hziee.management.entity.Project;

import java.util.List;

public class ProjectRepository {
    private static volatile ProjectRepository instance;
    private ProjectDao projectDao;


    public static void Initialize(Application application){
        if(instance == null){
            instance = new ProjectRepository(application);
        }
    }

    private ProjectRepository(Application application){
        ManagementDatabase db = ManagementDatabase.getDatabase(application);
        projectDao = db.projectDao();
    }

    public LiveData<List<Project>> getProjects(){
        return projectDao.getAll();
    }
    public LiveData<Project> getProject(Integer id){
        return projectDao.getProject(id);
    }

    public void updateProject(Project project){
        ManagementDatabase.databaseWriteExecutor.execute(() -> projectDao.updateProject(project));

    }
    public void addProject(Project project){
        ManagementDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                projectDao.addProject(project);
            }
        });
    }


    public static ProjectRepository getInstance(){
        return instance;
    }

}
