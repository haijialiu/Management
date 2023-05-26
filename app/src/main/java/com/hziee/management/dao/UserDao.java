package com.hziee.management.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.hziee.management.entity.User;

import java.util.List;

@Dao
public interface UserDao {
        @Query("SELECT * FROM user")
        LiveData<List<User>> getAll();

        @Query("SELECT * FROM user WHERE userid IN (:userIds)")
        LiveData<List<User>> loadAllByIds(String[] userIds);

        @Query("SELECT * FROM user WHERE username = (:username) and password = (:password)")
        LiveData<User> getUserByUsernameAndPassword(String username,String password);

//
//        @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//                "last_name LIKE :last LIMIT 1")
//        User findByName(String first, String last);

        @Insert
        void insertAll(User... users);
        @Insert
        void insert(User user);

        @Delete
        void delete(User user);

        @Query("DELETE FROM user")
        void deleteAll();
}

