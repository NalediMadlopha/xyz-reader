package com.example.xyzreader.view.article.list;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyzreader.R;
import com.example.xyzreader.viewmodel.ArticleViewModel;

import java.util.Objects;

public class ArticleListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ArticleViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView articleListRecyclerView;
    private ArticleListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);

        articleListRecyclerView = view.findViewById(R.id.article_list_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.article_list_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.amber_900));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ArticleViewModel.class);
        viewModel.init();
        viewModel.getArticles().observe(this, articles -> {
            if (articles != null && !articles.isEmpty()) {
                adapter = new ArticleListAdapter(getContext(), articles);
                articleListRecyclerView.setAdapter(adapter);
            } else {
                Snackbar.make(Objects.requireNonNull(getView()), "No articles where found, check Internet Connection", Snackbar.LENGTH_LONG).show();
                getView().findViewById(R.id.no_articles_found_text_view).setVisibility(View.VISIBLE);
            }
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onRefresh() {
        viewModel.init();
        viewModel.getArticles().observe(this, articles -> {
            if (articles != null && !articles.isEmpty()) {
                adapter.setData(articles);
            } else {
                Snackbar.make(Objects.requireNonNull(getView()), "No articles where found, check Internet Connection", Snackbar.LENGTH_LONG).show();
                getView().findViewById(R.id.no_articles_found_text_view).setVisibility(View.VISIBLE);
            }
            swipeRefreshLayout.setRefreshing(false);
        });
    }

}