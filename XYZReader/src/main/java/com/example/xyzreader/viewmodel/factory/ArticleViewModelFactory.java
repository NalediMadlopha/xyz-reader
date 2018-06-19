package com.example.xyzreader.viewmodel.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.xyzreader.viewmodel.ArticleViewModel;

import javax.inject.Inject;


public class ArticleViewModelFactory implements ViewModelProvider.Factory {

    private ArticleViewModel viewModel;

    @Inject
    public ArticleViewModelFactory(ArticleViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ArticleViewModel.class)) {
            return (T) viewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }

}