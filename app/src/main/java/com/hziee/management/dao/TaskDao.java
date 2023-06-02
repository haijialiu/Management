package com.hziee.management.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.hziee.management.entity.Project;
import com.hziee.management.entity.Task;

import java.util.List;
import java.util.Map;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAll();

    @Query("SELECT t.* FROM task t join project p on t.project_id = p.project_id where p.project_id=(:projectId)")
    LiveData<List<Task>> getTaskByProjectId(Integer projectId);

    @Query("SELECT * FROM task WHERE task_id=(:id)")
    LiveData<Task> getTask(Integer id);
    @Query("DELETE FROM task")
    void deleteAll();
    @Insert
    void insert(Task task);
    @Update
    void updateProject(Task task);
    @Insert
    void addProject(Task task);
}
