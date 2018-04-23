package com.example.xyzreader.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;

import java.util.Date;

import static com.example.xyzreader.ui.Util.formatDate;
import static com.example.xyzreader.ui.Util.parseDate;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private final Context mContext;
    private final Cursor cursor;

    ArticleAdapter(Context mContext, Cursor mCursor) {
        this.mContext = mContext;
        this.cursor = mCursor;
    }

    @NonNull
    @Override
    public ArticleAdapter.ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder viewHolder, int position) {
        cursor.moveToPosition(position);

        String thumbnailUrl = cursor.getString(ArticleLoader.Query.THUMB_URL);
        float aspectRatio = cursor.getFloat(ArticleLoader.Query.ASPECT_RATIO);
        String title = cursor.getString(ArticleLoader.Query.TITLE);
        Date publishedDate = parseDate(cursor.getString(ArticleLoader.Query.PUBLISHED_DATE));
        String author = cursor.getString(ArticleLoader.Query.AUTHOR);

        Glide.with(mContext).load(thumbnailUrl).into(viewHolder.imageView);

        viewHolder.imageView.setAspectRatio(aspectRatio);
        viewHolder.titleTextView.setText(title);
        viewHolder.authorTextView.setText(author);
        viewHolder.subtitleTextView.setText(formatDate(publishedDate));
    }

    @Override
    public long getItemId(int position) {
        cursor.moveToPosition(position);
        return cursor.getLong(ArticleLoader.Query._ID);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        DynamicHeightNetworkImageView imageView;
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
            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                    ItemsContract.Items.buildItemUri(super.getItemId())));
        }

    }

}
