package com.hziee.management.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hziee.management.dao.UserDao;
import com.hziee.management.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ManagementDatabase extends RoomDatabase {
    public abstract UserDao userDao();
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
                UserDao dao = INSTANCE.userDao();
                dao.deleteAll();
                User root = new User("root");
                dao.insert(root);
                User admin = new User("admin");
                dao.insert(admin);
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