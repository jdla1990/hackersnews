package com.reigndesign.app.utils;


import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    private static String TAG = "DateHelper";

    public static Date formatDate(String dateString, String format) {

        try {
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
            return dateFormat.parse(dateString);

        } catch (ParseException e) {
            Log.e(TAG, "formatDate: ", e);
            return null;
        }
    }

    public static String formatString(Date date, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
            return dateFormat.format(date);
        } catch (Exception e) {
            Log.e(TAG, "formatDate: ", e);
            return null;
        }
    }

    public static String format(long epoch) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        return sdf.format(new Date(epoch * 1000L));
    }
}
