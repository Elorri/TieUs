package com.elorri.android.tieus.extra;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.MergeCursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.data.TieUsContract;
import com.elorri.android.tieus.db.MatrixCursors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elorri on 12/04/2016.
 */
public class Tools {

    public static final long NOW = DateUtils.todayStart();
    public static final long _24H = DateUtils.addDay(1, NOW) - NOW;
    public static final long _48H = DateUtils.addDay(2, NOW) - NOW;
    public static final long _4DAYS = DateUtils.addDay(4, NOW) - NOW;
    public static final long _1WEEK = DateUtils.addDay(7, NOW) - NOW;
    public static final long _2WEEKS = _1WEEK + _1WEEK;
    public static final long _1MONTH = DateUtils.addDay(30, NOW) - NOW;
    public static final long _3MONTHS = _1MONTH * 3;
    public static final long _6MONTHS = _1MONTH * 6;
    public static final long _1YEAR = DateUtils.addDay(365, NOW) - NOW;


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


    public static ContentValues getContentValues(String columnName, String value) {
        ContentValues values = new ContentValues();
        values.put(columnName, value);
        return values;
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


    public static List<PackageInfo> getInstalledPackages(PackageManager packageManager) {
        return packageManager.getInstalledPackages(0);
    }

    public static boolean isPackageASocialNetworkVector(Context context, String packageName) {
        if (packageName.equals(context.getString(R.string.vector_package_gmail))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_google_plus))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_linkedin))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_facebook))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_outlook))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_yahoo))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_aol))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_skype))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_messenger))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_strava))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_viadeo))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_skype))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_email))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_phone))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_exchange))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_twitter))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_quora))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_instagram))) return true;
        if (packageName.equals(context.getString(R.string.vector_package_meetup))) return true;
        return false;
    }

    public static void setVectorBackground(Context context, ImageView vectorLogo,
                                           String mimetype, String data) {
        if (mimetype.equals(TieUsContract.VectorTable.MIMETYPE_VALUE_RESSOURCE)) {
            vectorLogo.setBackgroundResource(Integer.valueOf(data));
        } else
            try {
                vectorLogo.setBackground(context.getPackageManager().getApplicationIcon(data));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
    }

    public static void setWidgetVectorBackground(Context context, RemoteViews views, int viewId, String mimetype, String data) {
        if (mimetype.equals(TieUsContract.VectorTable.MIMETYPE_VALUE_RESSOURCE)) {
            views.setImageViewResource(viewId, Integer.valueOf(data));
        } else
            try {
                Drawable drawable = context.getPackageManager().getApplicationIcon(data);
                Bitmap bitmap = drawableToBitmap(drawable);
                views.setImageViewBitmap(viewId, bitmap);
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

    public static int decreaseSatisfaction(int satisfactionIcon) {
        if (satisfactionIcon == R.drawable.ic_sentiment_satisfied_black_48dp)
            return R.drawable.ic_sentiment_neutral_black_48dp;
        if (satisfactionIcon == R.drawable.ic_sentiment_neutral_black_48dp)
            return R.drawable.ic_sentiment_dissatisfied_black_48dp;
        return R.drawable.ic_sentiment_dissatisfied_black_48dp;
    }


    public static String getReadableTimeLimit(Context context, long timeLimit) {

        if (timeLimit == _24H)
            return context.getResources().getString(R.string._24h);
        if (timeLimit == _48H)
            return context.getResources().getString(R.string._48h);
        if (timeLimit == _4DAYS)
            return context.getResources().getString(R.string._4days);
        if (timeLimit == _1WEEK)
            return context.getResources().getString(R.string._1week);
        if (timeLimit == _2WEEKS)
            return context.getResources().getString(R.string._2weeks);
        if (timeLimit == _1MONTH)
            return context.getResources().getString(R.string._1month);
        if (timeLimit == _3MONTHS)
            return context.getResources().getString(R.string._3months);
        if (timeLimit == _6MONTHS)
            return context.getResources().getString(R.string._6months);
        if (timeLimit == _1YEAR)
            return context.getResources().getString(R.string._1year);
        return null;
    }


    /**
     * Transform a drawable to a bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    public static void setRemoteContentDescription(RemoteViews views, int ressource_image, String description) {
        views.setContentDescription(ressource_image, description);
    }

    public static String getSatisfactionDesciption(Context context, Integer satisfaction) {
        int satisfactionDesc;
        switch (satisfaction) {
            case R.drawable.ic_sentiment_satisfied_black_48dp:
                satisfactionDesc = R.string.satisfied;
                break;
            case R.drawable.ic_sentiment_neutral_black_48dp:
                satisfactionDesc = R.string.neutral;
                break;
            case R.drawable.ic_sentiment_dissatisfied_black_48dp:
                satisfactionDesc = R.string.unsatisfied;
                break;
            case R.drawable.ic_do_not_disturb_alt_black_48dp:
                satisfactionDesc = R.string.unfollowed;
                break;
            default:
                return null;
        }
        return context.getResources().getString(satisfactionDesc);
    }


    public static void launchExternalApp(Context context, String appPackageName, String appName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackageName);
        if (intent != null)
            context.startActivity(intent);
        else
            Toast.makeText(context, context.getResources().getString(R.string.app_not_installed, appName),
                    Toast.LENGTH_SHORT).show();
    }


    // Those 2 functions will be useful to handle the case when user is in 2 pane mode and turn
    // device to landscape.
    public static boolean isTablet(Configuration configuration) {
        return (configuration.screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static boolean isLandscape(Configuration configuration) {
        return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static String getForecastDescription(Context context, int forecastRessourceId) {
        int forecastDesc;
        switch (forecastRessourceId) {
            case R.drawable.art_clear:
                forecastDesc = R.string.shining;
                break;
            case R.drawable.art_light_clouds:
                forecastDesc = R.string.slightly_cloudy;
                break;
            case R.drawable.art_clouds:
                forecastDesc = R.string.cloudy;
                break;
            case R.drawable.art_rain:
                forecastDesc = R.string.rainy;
                break;
            default:
                return null;
        }
        return context.getResources().getString(forecastDesc);
    }


    /**
     * Returns true if the network is available or about to become available.
     *
     * @param c Context used to get the ConnectivityManager
     * @return true if the network is available
     */
    //Should be called on main thread
    static public boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
