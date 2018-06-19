package com.example.xyzreader.dependency;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.example.xyzreader.database.AppDatabase;
import com.example.xyzreader.database.ArticleDao;
import com.example.xyzreader.repository.ArticleRepository;
import com.example.xyzreader.service.Service;
import com.example.xyzreader.viewmodel.factory.ArticleViewModelFactory;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.xyzreader.service.Config.BASE_URL;

@Module
public class AppModule {

    private final Context context;

    AppModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context context() {
        return context;
    }

    @Provides
    ArticleRepository provideRepository() {
        return new ArticleRepository();
    }

    @Provides
    @Inject
    ViewModelProvider.Factory provideArticleListViewModelFactory(ArticleViewModelFactory factory) {
        return factory;
    }

    @Provides
    Service provideService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Service.class);
    }

    @Provides
    ArticleDao provideArticleDao() {
        return AppDatabase.getInstance(context).getArticleDao();
    }

}
