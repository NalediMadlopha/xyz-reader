package com.example.xyzreader.view;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.xyzreader.view.Constants.Key.SELECTED_ARTICLE_INDEX;

@Retention(RetentionPolicy.SOURCE)
@StringDef(SELECTED_ARTICLE_INDEX)
public @interface Constants {

    @interface Key {
        String ARTICLE_ID = "ARTICLE_ID";
        String SELECTED_ARTICLE_INDEX = "SELECTED_ARTICLE_INDEX";
        String ARTICLE_LIST_SIZE = "ARTICLE_LIST_SIZE";
    }

}