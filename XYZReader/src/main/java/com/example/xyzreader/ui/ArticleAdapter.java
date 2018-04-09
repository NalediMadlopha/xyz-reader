package com.example.xyzreader.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

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
        ImageLoader imageLoader = ImageLoaderHelper.getInstance(mContext).getImageLoader();
        float aspectRatio = cursor.getFloat(ArticleLoader.Query.ASPECT_RATIO);
        String title = cursor.getString(ArticleLoader.Query.TITLE);
        Date publishedDate = parseDate(cursor.getString(ArticleLoader.Query.PUBLISHED_DATE));
        String author = cursor.getString(ArticleLoader.Query.AUTHOR);

        viewHolder.imageView.setImageUrl(thumbnailUrl, imageLoader);
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

    private Date parseDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss", Locale.US);
            return dateFormat.parse(date);
        } catch (ParseException ex) {
            Log.e(this.getClass().getSimpleName(), "Passing today's date: " + ex.getMessage(), ex);
            return new Date();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private Spanned formatDate(Date publishedDate) {
        // Most time functions can only handle 1902 - 2037
        GregorianCalendar startOfEpoch = new GregorianCalendar(2,1,1);

        if (!publishedDate.before(startOfEpoch.getTime())) {
            return Html.fromHtml(
                    DateUtils.getRelativeTimeSpanString(
                            publishedDate.getTime(),
                            System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                            DateUtils.FORMAT_ABBREV_ALL).toString());
        } else {
            return Html.fromHtml(new SimpleDateFormat().format(publishedDate));
        }
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
