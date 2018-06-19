package com.example.xyzreader.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.xyzreader.dependency.App;
import com.example.xyzreader.model.Article;
import com.example.xyzreader.repository.ArticleRepository;

import java.util.List;

import javax.inject.Inject;


public class ArticleViewModel extends ViewModel {

    ArticleRepository repository;

    @Inject
    public ArticleViewModel(ArticleRepository repository) {
        App.appComponent().inject(this);
        this.repository = repository;
    }

    public void init() {
        repository.fetchRemoteData();
    }

    public LiveData<Article> getArticle(long articleId) {
        return repository.getArticle(articleId);
    }

    public LiveData<List<Article>> getArticles() {
        return repository.getArticles();
    }

}