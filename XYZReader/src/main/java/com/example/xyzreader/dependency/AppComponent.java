package com.example.xyzreader.dependency;

import com.example.xyzreader.database.AppDatabase;
import com.example.xyzreader.repository.ArticleRepository;
import com.example.xyzreader.view.article.details.ArticleDetailActivity;
import com.example.xyzreader.view.article.list.ArticleListActivity;
import com.example.xyzreader.viewmodel.ArticleViewModel;

import dagger.Component;

@Component(modules = AppModule.class )
public interface AppComponent {

    void inject(ArticleListActivity articleListActivity);

    void inject(ArticleDetailActivity articleDetailActivity);

    void inject(ArticleRepository articleRepository);

    void inject(ArticleViewModel articleViewModel);

    void inject(AppDatabase appDatabase);

}
