package com.hziee.management.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task")
public class Task {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private Integer id;

    @ColumnInfo(name = "project_id")
    private Integer projectId;
    private String name;
    private String commit;
    @ColumnInfo(name = "start_time")
    private Date startTime;
    @ColumnInfo(name = "end_time")
    private Date endTime;
    private String header;
    @ColumnInfo(name = "created_time")
    private Date createdTime;
    @Ignore
    public Task(Integer projectId){
        this.name = "";
        this.projectId = projectId;
        this.startTime = new Date();
        this.endTime = new Date();
        this.createdTime = new Date();
    }
    @Ignore
    public Task(String name){
        this.name = name;
        this.projectId = 1;
        this.commit = "task commit";
        this.header = "root";
        this.startTime = new Date();
        this.endTime = new Date();
        this.createdTime = new Date();
    }

    public Task(Integer id, Integer projectId, String name, String commit, Date startTime, Date endTime, String header, Date createdTime) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.commit = commit;
        this.startTime = startTime;
        this.endTime = endTime;
        this.header = header;
        this.createdTime = createdTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", name='" + name + '\'' +
                ", commit='" + commit + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", header='" + header + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
