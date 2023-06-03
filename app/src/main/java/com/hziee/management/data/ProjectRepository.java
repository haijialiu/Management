package com.hziee.management.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hziee.management.dao.ProjectDao;
import com.hziee.management.entity.Project;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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
    public LiveData<List<Project>> getProjects(String condition,String value){
        return projectDao.getByCondition(condition,value);
    }
    public LiveData<Project> getProject(Integer id){
        return projectDao.getProject(id);
    }

    public void updateProject(Project project){
        ManagementDatabase.databaseWriteExecutor.execute(() -> projectDao.updateProject(project));

    }
    public void deleteProject(Integer projectId){
        ManagementDatabase.databaseWriteExecutor.execute(() -> projectDao.deleteProject(projectId));
    }
    public long addProject(Project project){
        Callable<Long> insertCallable = () -> projectDao.addProject(project);
        Future<Long> submit = ManagementDatabase.databaseWriteExecutor.submit(insertCallable);
        try {
            return submit.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static ProjectRepository getInstance(){
        return instance;
    }

}
