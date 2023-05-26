package com.hziee.management.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "user")
public class User {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userid")
    private Integer mId;

    @ColumnInfo(name = "username")
    private String mUserName;

    @ColumnInfo(name = "nickname")
    private String mNickName;

    @ColumnInfo(name = "password")
    private String mPassWord;

    @ColumnInfo(name = "last_update")
    private Date mDate;

    @Ignore
    public User(String userName) {
        mUserName = userName;
        mNickName = userName;
        mPassWord = "123456";
        mDate = new Date(System.currentTimeMillis());
    }

    public User(Integer id, String userName,String passWord,String nickName , Date date) {
        this.mId = id;
        this.mUserName = userName;
        this.mNickName = nickName;
        this.mPassWord = passWord;
        this.mDate = date;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer mId) {
        this.mId = mId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String mNickName) {
        this.mNickName = mNickName;
    }

    public String getPassWord() {
        return mPassWord;
    }

    public void setPassWord(String mPassWord) {
        this.mPassWord = mPassWord;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "mId='" + mId + '\'' +
                ", mUserName='" + mUserName + '\'' +
                ", mNickName='" + mNickName + '\'' +
                ", mPassWord='" + mPassWord + '\'' +
                ", mDate=" + mDate +
                '}';
    }
}
