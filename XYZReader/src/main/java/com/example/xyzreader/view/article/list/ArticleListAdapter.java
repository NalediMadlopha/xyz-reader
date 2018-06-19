package com.example.xyzreader.view.article.list;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xyzreader.R;
import com.example.xyzreader.model.Article;
import com.example.xyzreader.view.article.details.ArticleDetailActivity;

import java.util.Date;
import java.util.List;

import static com.example.xyzreader.util.Util.formatDate;
import static com.example.xyzreader.util.Util.parseDate;
import static com.example.xyzreader.view.Constants.Key.ARTICLE_ID;
import static com.example.xyzreader.view.Constants.Key.ARTICLE_LIST_SIZE;
import static com.example.xyzreader.view.Constants.Key.SELECTED_ARTICLE_INDEX;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {

    private final Context context;
    private List<Article> articleList;

    ArticleListAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleListAdapter.ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder viewHolder, int position) {
        viewHolder.article = articleList.get(position);
        Date publishedDate = parseDate(viewHolder.article.getPublished_date());

        Glide.with(context)
                .load(viewHolder.article.getThumb())
                .into(viewHolder.imageView);

        viewHolder.titleTextView.setText(viewHolder.article.getTitle());
        viewHolder.authorTextView.setText(viewHolder.article.getAuthor());
        viewHolder.subtitleTextView.setText(formatDate(publishedDate));
    }

    @Override
    public long getItemId(int position) {
        return articleList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void setData(List<Article> articleList) {
        this.articleList = articleList;
        notifyDataSetChanged();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Article article;
        ImageView imageView;
        TextView titleTextView;
        TextView subtitleTextView;
        TextView authorTextView;

        ArticleViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.card_thumbnail);
            titleTextView = itemView.findViewById(R.id.card_article_title);
            subtitleTextView = itemView.findViewById(R.id.card_view_subtitle);
            authorTextView = itemView.findViewById(R.id.card_view_author);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ArticleDetailActivity.class)
                                .putExtra(ARTICLE_ID, article.getId())
                                .putExtra(SELECTED_ARTICLE_INDEX, articleList.indexOf(article))
                                .putExtra(ARTICLE_LIST_SIZE, articleList.size());
            view.getContext().startActivity(intent);
        }
    }

}