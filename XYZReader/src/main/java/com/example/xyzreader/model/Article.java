package com.example.xyzreader.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

@Entity(tableName = "article_table")
public class Article implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "body")
    private String body;
    @ColumnInfo(name = "thumb")
    private String thumb;
    @ColumnInfo(name = "photo")
    private String photo;
    @ColumnInfo(name = "aspect_ratio")
    private String aspect_ratio;
    @ColumnInfo(name = "published_date")
    private String published_date;

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    @Ignore
    public Article() {
    }

    public Article(long id, String title, String author, String body,
                   String thumb, String photo, String aspect_ratio, String published_date) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.body = body;
        this.thumb = thumb;
        this.photo = photo;
        this.aspect_ratio = aspect_ratio;
        this.published_date = published_date;
    }

    protected Article(Parcel in) {
        id = in.readLong();
        title = in.readString();
        author = in.readString();
        body = in.readString();
        thumb = in.readString();
        photo = in.readString();
        aspect_ratio = in.readString();
        published_date = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(String aspectRatio) {
        this.aspect_ratio = aspectRatio;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String publishedDate) {
        this.published_date = publishedDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(body);
        dest.writeString(thumb);
        dest.writeString(photo);
        dest.writeString(aspect_ratio);
        dest.writeString(published_date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(getId(), article.getId()) &&
                Objects.equals(getTitle(), article.getTitle()) &&
                Objects.equals(getAuthor(), article.getAuthor()) &&
                Objects.equals(getBody(), article.getBody()) &&
                Objects.equals(getThumb(), article.getThumb()) &&
                Objects.equals(getPhoto(), article.getPhoto()) &&
                Objects.equals(getAspect_ratio(), article.getAspect_ratio()) &&
                Objects.equals(getPublished_date(), article.getPublished_date());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getAuthor(), getBody(), getThumb(), getPhoto(), getAspect_ratio(), getPublished_date());
    }

}
