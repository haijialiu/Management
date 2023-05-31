package com.hziee.management.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "project")
public class Project {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "project_id")
    private Integer id;
    private String name;
    @ColumnInfo(name = "start_time")
    private Date startTime;
    @ColumnInfo(name = "end_time")
    private Date endTime;
    @ColumnInfo(name = "members_count")
    private Integer membersCount;
    @ColumnInfo(name = "created_time")
    private Date createdTime;
    @Ignore
    public Project(String name){
        this.name=name;
        this.membersCount=0;
        this.startTime = new Date();
        this.endTime = new Date();
        this.createdTime = new Date();
    }

    public Project(Integer id, String name, Date startTime, Date endTime, Integer membersCount, Date createdTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.membersCount = membersCount;
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

    public Integer getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(Integer membersCount) {
        this.membersCount = membersCount;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", membersCount=" + membersCount +
                ", createdTime=" + createdTime +
                '}';
    }
}
