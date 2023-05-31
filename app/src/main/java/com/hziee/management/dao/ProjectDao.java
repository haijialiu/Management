package com.hziee.management.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hziee.management.entity.Project;

import java.util.List;
@Dao
public interface ProjectDao {
    @Query("SELECT * FROM project")
    LiveData<List<Project>> getAll();
    @Query("SELECT * FROM project WHERE project_id=(:id)")
    LiveData<Project> getProject(Integer id);
    @Query("DELETE FROM project")
    void deleteAll();
    @Insert
    void insert(Project project);
    @Update
    void updateProject(Project project);
    @Insert
    void addProject(Project project);

}
