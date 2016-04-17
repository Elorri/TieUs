package com.elorri.android.friendforcast.extra;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

    public static int[] convertToArrayViewTypes(ArrayList<Integer> viewTypesList) {
        int[] viewTypes = new int[viewTypesList.size()];
        int i = 0;
        for (int viewType : viewTypesList) {
            viewTypes[i] = viewType;
            i++;
        }
        return viewTypes;
    }

    public static Cursor getOneLineCursor(String id) {
        String[] cursor_columns = {"_id"};
        MatrixCursor cursor = new MatrixCursor(cursor_columns);
        cursor.addRow(new Object[]{id});
        return cursor;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }


    public static void printArray(String[] array) {
        String arrayString="";
        for(String item:array){
            arrayString+=item+",";
        }
        Log.e("Communication", "" + arrayString);
    }


    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
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
}
