package com.elorri.android.communication.extra;

import android.database.Cursor;

/**
 * Created by Elorri on 14/04/2016.
 */
public class CursorUtils {

    public static String[] addCursorColumnsNames(String[] oldColumns, String... newColumns) {
        int columnsCount = oldColumns.length + newColumns.length;
        String[] columns = new String[columnsCount];
        int i;
        for (i = 0; i < oldColumns.length; i++)
            columns[i] = oldColumns[i];
        for (int j = 0; i < columnsCount; i++, j++)
            columns[i] = newColumns[j];
        return columns;
    }

    public static Object[] addCursorColumnsValues(Cursor appCursorOldValues, String... newValues) {
        int columnsCount = appCursorOldValues.getColumnCount()+newValues.length;
        Object[] values=new Object[columnsCount];
        int i;
        for (i = 0; i < appCursorOldValues.getColumnCount(); i++)
            values[i] = appCursorOldValues.getString(i);
        for (int j = 0; i < columnsCount; i++, j++)
            values[i] = newValues[j];
        return values;
    }




}
