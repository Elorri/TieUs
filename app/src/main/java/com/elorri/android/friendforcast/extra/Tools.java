package com.elorri.android.friendforcast.extra;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MergeCursor;
import android.os.Build;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.MatrixCursors;
import com.elorri.android.friendforcast.db.ViewTypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Elorri on 12/04/2016.
 */
public class Tools {
    /**
     * Load a text file .cvs, .txt, ... and return a collection of its lines.
     *
     * @param resourceId located in the directory res and automatically referenced with an int
     * @return a collection where each item is a String representing one line of a the file.
     */
    public static ArrayList<String> loadTextFile(Context context, int resourceId) {
        // The InputStream opens the resourceId and sends it to the buffer
        ArrayList<String> listOfLines = new ArrayList<String>();
        //InputStream is = mainActivity.getResources().openRawResource(resourceId);
        InputStream is = context.getResources().openRawResource(resourceId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;

        try {
            // While the BufferedReader line is not null
            while ((line = br.readLine()) != null) {
                //Log.d("MealPlanner", line);
                listOfLines.add(line);
            }

            // Close the InputStream and BufferedReader
            is.close();
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfLines;
    }

    public static String thread() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread())
            return "ThreadUI";
        else return "Background";
    }


    public static Cursor[] convertToArrayCursors(ArrayList<Cursor> cursorsList) {
        Cursor[] cursors = new Cursor[cursorsList.size()];
        int i = 0;
        for (Cursor cursor : cursorsList) {
            cursors[i] = cursor;
            i++;
        }
        return cursors;
    }


    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }


    public static void printArray(String[] array) {
        String arrayString = "";
        for (String item : array) {
            arrayString += item + ",";
        }
        Log.e("Communication", "" + arrayString);
    }


    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static Locale getMostSuitableLocale() {
        if ((Locale.getDefault().getLanguage().equals("fr"))
                && (Locale.getDefault().getCountry().equals("FR")))
            return Locale.getDefault(); //In my list of chosen Locale
        if ((Locale.getDefault().getLanguage().equals("fr"))
                && (Locale.getDefault().getCountry().equals("CA")))
            return new Locale("fr", "FR");
        return new Locale("en", "US"); //only locale Java guarantees is always available. In my
        // list of chosen Locale
    }

    public static ContentValues updateContactValues(ContentValues contentvalues, String column, String value) {
        contentvalues.put(column, value);
        return contentvalues;
    }

    public static int getForecastRessourceId(float ratio) {
        if (ratio >= 0 && ratio < 0.25)
            return R.drawable.art_rain;
        else if (ratio >= 0.25 && ratio < 0.5)
            return R.drawable.art_clouds;
        else if (ratio >= 0.5 && ratio < 0.75)
            return R.drawable.art_light_clouds;
        else
            return R.drawable.art_clear;
    }


    public static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }


    public static List<ResolveInfo> getInstalledApps(PackageManager packageManager) {
        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        //intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
        return packageManager.queryIntentActivities(intent, 0);
    }

    public static List<PackageInfo> getInstalledPackages(PackageManager packageManager) {
        return packageManager.getInstalledPackages(0);
    }

    public static boolean isPackageASocialNetworkVector(Context context, String packageName) {
        if (packageName.equals(context.getString(R.string.vector_package_gmail))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_google_plus))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_linkedin))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_facebook))) return true;
        return false;
    }

    public static void setVectorBackground(Context context, ImageView vectorLogo,
                                           String mimetype, String data) {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "mimetype" + mimetype + "data" + data);
        if (mimetype.equals(FriendForecastContract.VectorTable.MIMETYPE_VALUE_RESSOURCE)) {
            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
            vectorLogo.setBackgroundResource(Integer.valueOf(data));
        } else
            try {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                vectorLogo.setBackground(context.getPackageManager().getApplicationIcon(
                        data));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
    }


    public static Cursor addDisplayProperties(Cursor cursor,
                                              boolean withTitle,
                                              String title,
                                              boolean withEmptyMessageIfEmpty,
                                              String emptyMessage,
                                              boolean displayTitleIfListEmpty) {

        displayTitleIfListEmpty = withTitle ? displayTitleIfListEmpty : false;

        boolean addTitle = false;
        boolean addEmptyMessage = false;

        if (withTitle)
            addTitle = true;
        ArrayList<Cursor> cursors = new ArrayList();
        if (cursor.getCount() == 0) {
            addTitle = displayTitleIfListEmpty;
            if (withEmptyMessageIfEmpty)
                addEmptyMessage = true;
        }


        if (addTitle)
            cursors.add(MatrixCursors.getOneLineCursor(
                    MatrixCursors.TitleQuery.PROJECTION,
                    MatrixCursors.TitleQuery.VALUES,
                    title));
        if (addEmptyMessage)
            cursors.add(MatrixCursors.getOneLineCursor(
                    MatrixCursors.EmptyCursorMessageQuery.PROJECTION,
                    MatrixCursors.EmptyCursorMessageQuery.VALUES,
                    emptyMessage));
        cursors.add(cursor);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    public static int decreaseMood(int moodIcon) {
        if (moodIcon == R.drawable.ic_sentiment_satisfied_black_48dp)
            return R.drawable.ic_sentiment_neutral_black_48dp;
        if (moodIcon == R.drawable.ic_sentiment_neutral_black_48dp)
            return R.drawable.ic_sentiment_dissatisfied_black_48dp;
        return R.drawable.ic_sentiment_dissatisfied_black_48dp;
    }


    public static String getCursorString(Cursor cursor) {
        cursor.moveToPosition(-1);
        String cursorString = "\n";
        if (cursor.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE) != -1) {
            if (cursor.moveToFirst()) {
                int viewType = cursor.getInt(cursor.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE));
                cursorString = cursorString + getCursorHeaderString(cursor);
                cursor.moveToPosition(-1);
                while (cursor.moveToNext()) {
                    if (viewType != cursor.getInt(cursor.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE)))
                        cursorString = cursorString + getCursorHeaderString(cursor);
                    cursorString = cursorString + getCursorRowString(cursor);
                }
            }
        } else {
            cursorString = cursorString + getCursorHeaderString(cursor);
            while (cursor.moveToNext()) {
                cursorString = cursorString + getCursorRowString(cursor);
            }
        }
        return cursorString;
    }

    public static String getCursorHeaderString(Cursor cursor) {
        String header = "header |";
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            header = header + cursor.getColumnName(i) + "|";
        }
        return header + "\n";
    }

    public static String getCursorRowString(Cursor cursor) {
        String row = "row |";
        String cell = null;
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            cell = cursor.getString(i) == null ? "null" : cursor.getString(i);
            row = row + cell + "|";
        }
        return row + "\n";
    }

    public static String getReadableDelay(Context context, long feedBackDelay) {
        final long now = DateUtils.todayStart();
        final long _24h = DateUtils.addDay(1, now) - now;
        final long _48h = DateUtils.addDay(2, now) - now;
        final long _4days = DateUtils.addDay(4, now) - now;
        final long _1week = DateUtils.addDay(7, now) - now;
        final long _2weeks = _1week + _1week;
        final long _1month = DateUtils.addDay(30, now) - now;
        final long _3months = _1month*3;
        final long _6months = _1month*6;
        final long _1year = DateUtils.addDay(365, now) - now;

        if (feedBackDelay==_24h)
                return context.getResources().getString(R.string._24h);
        if (feedBackDelay==_48h)
            return context.getResources().getString(R.string._48h);
        if (feedBackDelay==_4days)
            return context.getResources().getString(R.string._4days);
        if (feedBackDelay==_1week)
            return context.getResources().getString(R.string._1week);
        if (feedBackDelay==_2weeks)
            return context.getResources().getString(R.string._2weeks);
        if (feedBackDelay==_1month)
            return context.getResources().getString(R.string._1month);
        if (feedBackDelay==_3months)
            return context.getResources().getString(R.string._3months);
        if (feedBackDelay==_6months)
            return context.getResources().getString(R.string._6months);
        if (feedBackDelay==_1year)
            return context.getResources().getString(R.string._1year);
        return null;
    }
}
