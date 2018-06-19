package com.example.xyzreader.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.xyzreader.model.Article;

import javax.inject.Singleton;

@Singleton
@Database(entities = {Article.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract ArticleDao getArticleDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "xyz_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public static AppDatabase getCache(Context context) {
        if (instance == null) {
            instance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

}
