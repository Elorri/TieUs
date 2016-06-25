/*
 * The MIT License (MIT)

 Copyright (c) 2016 ETCHEMENDY ELORRI

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package com.elorri.android.capstone.tieus.extra;

import android.content.Context;

import com.elorri.android.capstone.tieus.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Elorri on 17/04/2016.
 * Useful class for displaying dates in the correct locale, orworking with them.
 */
public class DateUtils {
    public static final String DAY_OF_WEEK_FORMAT = "EEEE";
    public static final String DAY_MONTH_FORMAT = "dd MMMM";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String HOURS_MINUTES_FORMAT = "HH:mm:ss";
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'hh:mm:ss'Z'";

    /**
     * Convert a date in a form of a long to a readable date adapted to user locale and timezone.
     * Note : when no timezone is set, the user default is used, that's why we don't do a
     * setTimezone here.
     */
    public static String fromLongToString(long dateLong, String stringFormat, Locale locale) {
        SimpleDateFormat df = new SimpleDateFormat(stringFormat, locale);
        return df.format(new Date(dateLong));
    }


    public static String getFriendlyFormat(long date) {
        long todayStart = todayStart();
        long tomorrowStart = tomorrowStart();
        long in7daysStart = addDay(6, todayStart);
        long nextyearStart = nextYearStart();
        if (date >= tomorrowStart && date < in7daysStart)
            return DAY_OF_WEEK_FORMAT;
        if (date >= in7daysStart && date < nextyearStart)
            return DAY_MONTH_FORMAT;
        else
            return DATE_FORMAT;
    }

    public static String getFriendlyDateString(Context context, long date) {
        long todayStart = todayStart();
        long tomorrowStart = tomorrowStart();
        if (date >= todayStart && date < tomorrowStart)
            return context.getResources().getString(R.string.today);
        else
            return fromLongToString(date, getFriendlyFormat(date), Locale.getDefault());
    }

    public static String getFriendlyDateTimeString(Context context, long date) {
        return context.getResources().getString(R.string.completed,
                getFriendlyDateString(context, date),
                fromLongToString(date, HOURS_MINUTES_FORMAT, Locale.getDefault()));
    }

    public static long nextYearStart() {
        long nextYear = addDay(365, System.currentTimeMillis());
        return setZeroYear(nextYear);
    }

    /**
     * This method convert a long representing an instant ex 2016-01-25 19:00:00 to the beginning
     * of the day ex:2016-01-25 00:00:00
     *
     * @param dateInMillis
     * @return long representing the start of the day
     */
    public static long setZeroDay(long dateInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * This method convert a long representing an instant ex 2016-01-25 19:00:00 to the beginning
     * of the day and the beginning of the year ex:2016-01-01 00:00:00
     *
     * @param dateInMillis
     * @return long representing the start of the day
     */
    public static long setZeroYear(long dateInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTimeInMillis();
    }

    public static long todayStart() {
        return setZeroDay(System.currentTimeMillis());
    }


    /**
     * Add any number of days to the current day. Negatives number also possible
     *
     * @param nbDays
     * @param startDateInMillis
     * @return
     */
    public static long addDay(int nbDays, long startDateInMillis) {
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(new Date(startDateInMillis));
        endDate.add(Calendar.DATE, nbDays);
        return endDate.getTime().getTime();
    }


    public static long tomorrowStart() {
        return addDay(1, todayStart());
    }

}
