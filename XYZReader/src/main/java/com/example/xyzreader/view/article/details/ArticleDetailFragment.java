package com.example.xyzreader.view.article.details;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xyzreader.R;
import com.example.xyzreader.model.Article;
import com.example.xyzreader.view.article.list.ArticleListActivity;
import com.example.xyzreader.viewmodel.ArticleViewModel;

import java.util.Objects;

import static com.example.xyzreader.util.Util.formatDate;
import static com.example.xyzreader.util.Util.parseDate;
import static com.example.xyzreader.view.Constants.Key.ARTICLE_ID;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleListActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */
public class ArticleDetailFragment extends Fragment {

    private long articleId;
    private CoordinatorLayout articleDetailContainer;
    private ProgressBar articleDetailProgressBar;
    private ImageView backDropImageView;
    private TextView titleTextView;
    private TextView subtitleTextView;
    private TextView bodyTextView;
    private Article article;

    public static ArticleDetailFragment newInstance(long articleId) {
        Bundle arguments = new Bundle();
        arguments.putLong(ARTICLE_ID, articleId);
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARTICLE_ID)) {
            articleId = getArguments().getLong(ARTICLE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);

        setupToolBar(view);

        articleDetailContainer = view.findViewById(R.id.article_detail_container);
        articleDetailProgressBar = view.findViewById(R.id.article_detail_progress_bar);
        backDropImageView = view.findViewById(R.id.backdrop_image_view);
        titleTextView = view.findViewById(R.id.detail_article_title);
        subtitleTextView = view.findViewById(R.id.article_byline);
        bodyTextView = view.findViewById(R.id.article_body);

        setupFabButton(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArticleViewModel viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ArticleViewModel.class);
        viewModel.getArticle(articleId).observe(this, article -> {
            this.article = article;

            Glide.with(getContext())
                    .load(article.getThumb())
                    .into(backDropImageView);

            titleTextView.setText(article.getTitle());
            subtitleTextView.setText(Html.fromHtml(getString(R.string.detail_article_subtitle,
                    formatDate(parseDate(article.getPublished_date())),
                    "<font color='#ffffff'>" + article.getAuthor() + "</font>")));
            bodyTextView.setText(Html.fromHtml(article.getBody()));

            articleDetailProgressBar.setVisibility(View.GONE);
            articleDetailContainer.setVisibility(View.VISIBLE);
        });
    }

    private void setupFabButton(View view) {
        view.findViewById(R.id.share_fab).setOnClickListener(v -> startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                .setType("text/plain")
                .setText(article.getTitle())
                .getIntent(), getString(R.string.action_share))));
    }

    private void setupToolBar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (toolbar != null) {
          toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
          toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        }
    }
}