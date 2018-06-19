package com.example.xyzreader.repository;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.xyzreader.database.ArticleDao;
import com.example.xyzreader.dependency.App;
import com.example.xyzreader.model.Article;
import com.example.xyzreader.service.Service;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {

    @Inject
    Service service;
    @Inject
    ArticleDao articleDao;

    public ArticleRepository() {
        App.appComponent().inject(this);
    }

    public void fetchRemoteData() {
        service.getArticles().enqueue(new Callback<Article[]>() {
            @Override
            public void onResponse(Call<Article[]> call, Response<Article[]> response) {
                Article[] articles = response.body();
                if (articles != null) {
                    AsyncTask.execute(() -> articleDao.insertAll(Arrays.asList(articles)));
                } else {
                    onFailure(call, new IllegalStateException("There are no articles found"));
                }
            }

            @Override
            public void onFailure(Call<Article[]> call, Throwable t) {
                // I have left out the error case for brevity
            }
        });
    }

    public LiveData<List<Article>> getArticles() {
        return articleDao.getAllArticles();
    }

    public LiveData<Article> getArticle(long articleId) {
        return articleDao.getArticle(articleId);
    }

}
