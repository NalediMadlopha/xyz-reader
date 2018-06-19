package com.example.xyzreader.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.xyzreader.model.Article;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ArticleDao {

    @Insert(onConflict = REPLACE)
    void insertAll(List<Article> articles);

    @Query("SELECT * FROM article_table")
    LiveData<List<Article>> getAllArticles();

    @Query("SELECT * FROM article_table WHERE id = :id")
    LiveData<Article> getArticle(long id);
}
