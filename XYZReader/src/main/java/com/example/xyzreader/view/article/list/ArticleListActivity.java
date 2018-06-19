package com.example.xyzreader.view.article.list;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.xyzreader.R;
import com.example.xyzreader.dependency.App;
import com.example.xyzreader.viewmodel.ArticleViewModel;
import com.example.xyzreader.viewmodel.factory.ArticleViewModelFactory;

import javax.inject.Inject;

public class ArticleListActivity extends AppCompatActivity {

    @Inject
    ArticleViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.appComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        ViewModelProviders.of(this, viewModelFactory).get(ArticleViewModel.class);
    }
}