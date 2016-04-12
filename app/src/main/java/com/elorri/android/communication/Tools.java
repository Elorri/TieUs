package com.elorri.android.communication;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
}
