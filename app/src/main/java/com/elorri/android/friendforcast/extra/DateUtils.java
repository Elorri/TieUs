package com.elorri.android.friendforcast.extra;

import android.content.Context;

import com.elorri.android.friendforcast.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Elorri on 17/04/2016.
 */
public class DateUtils {
    public static final String DAY_OF_WEEK_FORMAT = "EEEE";
    public static final String DAY_MONTH_FORMAT = "dd MMMM";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String HOURS_MINUTES_FORMAT_ = "HH:mm:ss";

    /**
     * Convert a date in a form of a long to a readable date adapted to user locale and timezone.
     * Note : when no timezone is set, the user default is used, that's why we don't do a
     * setTimezone here.
     */
    public static String fromLongToString(long dateLong, String stringFormat, Locale locale) {
        SimpleDateFormat df = new SimpleDateFormat(stringFormat, locale);
        return df.format(new Date(dateLong));
    }


    public static String getFriendlyFormat(Context context, long date) {
        long todayStart = 0;
        long tomorrowStart = 0;
        long in7daysStart = 0;
        long nextyearStart = 0;
        if (date >= todayStart && date < tomorrowStart)
            return context.getResources().getString(R.string.today);
        if (date >= tomorrowStart && date < in7daysStart)
            return DAY_OF_WEEK_FORMAT;
        if (date >= in7daysStart && date < nextyearStart)
            return DAY_MONTH_FORMAT;
        else
            return DATE_FORMAT;
    }
}
