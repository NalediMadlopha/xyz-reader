package com.example.xyzreader.ui;

import android.annotation.SuppressLint;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Util {

    private Util() {
        /* no-op */
    }

    public static Date parseDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss", Locale.US);
            return dateFormat.parse(date);
        } catch (ParseException ex) {
            Log.e(Util.class.getSimpleName(), "Passing today's date: " + ex.getMessage(), ex);
            return new Date();
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static Spanned formatDate(Date publishedDate) {
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

}
