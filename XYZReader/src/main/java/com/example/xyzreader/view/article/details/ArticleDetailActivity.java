package com.example.xyzreader.view.article.details;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import com.example.xyzreader.R;
import com.example.xyzreader.dependency.App;
import com.example.xyzreader.viewmodel.ArticleViewModel;
import com.example.xyzreader.viewmodel.factory.ArticleViewModelFactory;

import javax.inject.Inject;

import static com.example.xyzreader.view.Constants.Key.ARTICLE_ID;
import static com.example.xyzreader.view.Constants.Key.ARTICLE_LIST_SIZE;
import static com.example.xyzreader.view.Constants.Key.SELECTED_ARTICLE_INDEX;

public class ArticleDetailActivity extends AppCompatActivity {

    @Inject
    ArticleViewModelFactory viewModelFactory;

    private long articleId;
    private int selectedArticleIndex;
    private int numberOfArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.appComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        articleId = getIntent().getLongExtra(ARTICLE_ID, 1);
        selectedArticleIndex = getIntent().getIntExtra(SELECTED_ARTICLE_INDEX, 0);
        numberOfArticles = getIntent().getIntExtra(ARTICLE_LIST_SIZE, 0);

        setupPager();
        ViewModelProviders.of(this, viewModelFactory).get(ArticleViewModel.class);
    }

    private void setupPager() {
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(selectedArticleIndex);
        pager.setPageMarginDrawable(new ColorDrawable(0x22000000));
        pager.setPageMargin((int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener());
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return ArticleDetailFragment.newInstance(articleId);
        }

        @Override
        public int getCount() {
            return numberOfArticles;
        }
    }
}