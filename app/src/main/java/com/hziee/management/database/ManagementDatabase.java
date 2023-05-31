package com.hziee.management.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hziee.management.dao.ProjectDao;
import com.hziee.management.dao.UserDao;
import com.hziee.management.entity.Project;
import com.hziee.management.entity.Task;
import com.hziee.management.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Project.class, Task.class}, version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ManagementDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ProjectDao projectDao();
    private static volatile ManagementDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                UserDao userDao = INSTANCE.userDao();
                ProjectDao projectDao = INSTANCE.projectDao();
                userDao.deleteAll();
                User root = new User("root");
                userDao.insert(root);
                User admin = new User("admin");
                userDao.insert(admin);
                projectDao.deleteAll();
                Project project1 = new Project("project1");
                Project project2 = new Project("project2");
                projectDao.insert(project1);
                projectDao.insert(project2);
            });
        }
    };

    public static ManagementDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ManagementDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ManagementDatabase.class, "management_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}