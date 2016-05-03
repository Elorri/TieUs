package com.elorri.android.friendforcast.extra;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.elorri.android.friendforcast.R;

/**
 * Created by Elorri on 03/05/2016.
 */
public class Status {

    /**
     *
     * @param context Context used to get the SharedPreferences
     * @return the google book serveur status integer type
     */
    public static final boolean IS_MARK_ACTION_FEATURE_AWARE = false;
    public static boolean getMarkActionFeatureStatus(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(context.getString(R.string.pref_mark_action_feature_status_key),
                IS_MARK_ACTION_FEATURE_AWARE);
    }

    /**
     * Sets the MarkActionFeature status into shared preference.  This function should not be called from
     * the UI thread because it uses commit to write to the shared preferences. Nb:if call from
     * UI thread use, apply instead.
     *
     * @param context      Context to get the PreferenceManager from.
     * @param markActionFeatureStatus The IntDef value to set
     */
    public static  void setMarkActionFeatureStatus(Context context,boolean
            markActionFeatureStatus) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean(context.getString(R.string.pref_mark_action_feature_status_key),
                markActionFeatureStatus);
        spe.commit();
    }
}
