package com.hziee.management.data;

import androidx.lifecycle.LiveData;

import com.hziee.management.entity.Project;

import java.util.List;

public class ProjectRepository {
    private static volatile ProjectRepository instance;

    public LiveData<List<Project>> getProjects(){
        //TODO
        return null;
    }
}
