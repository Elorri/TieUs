package com.elorri.android.friendforcast.extra;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.elorri.android.friendforcast.R;

import java.util.Date;

/**
 * Created by Elorri on 03/05/2016.
 */
public class Status {

    /**
     * @param context Context used to get the SharedPreferences
     * @return the mark action feature status that tell if the user knows hows to validate an action
     */
    public static final boolean MARK_ACTION_FEATURE_AWARE_FALSE = false;

    public static boolean getMarkActionFeatureStatus(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(context.getString(R.string.pref_mark_action_feature_status_key),
                MARK_ACTION_FEATURE_AWARE_FALSE);
    }

    /**
     * Sets the MarkActionFeature status into shared preference.  This function should not be called from
     * the UI thread because it uses commit to write to the shared preferences. Nb:if call from
     * UI thread use, apply instead.
     *
     * @param context                 Context to get the PreferenceManager from.
     * @param markActionFeatureStatus The IntDef value to set
     */
    public static void setMarkActionFeatureStatus(Context context, boolean
            markActionFeatureStatus) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean(context.getString(R.string.pref_mark_action_feature_status_key),
                markActionFeatureStatus);
        spe.commit();
    }


    /**
     * @param context Context used to get the SharedPreferences
     * @return the index of the last message the user have seen, could be the one on the screen.
     */
    public static final int MANAGE_UNMANAGED_PEOPLE = 0;
    public static final int FILL_IN_DELAY_FEEDBACK = 1;
    public static final int UPDATE_MOOD = 2;
    public static final int SET_UP_A_FREQUENCY_OF_CONTACT = 3;
    public static final int ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK = 4;
    public static final int APPROCHING_DEAD_LINE = 5;
    public static final int NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY = 6;
    public static final int TAKE_TIME_FOR_FEEDBACK = 7;

    public static int getLastMessageIdx(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(context.getString(R.string.pref_message_idx_status_key),
                MANAGE_UNMANAGED_PEOPLE);
    }

    /**
     * Sets the MarkActionFeature status into shared preference.  This function should not be called from
     * the UI thread because it uses commit to write to the shared preferences. Nb:if call from
     * UI thread use, apply instead.
     *
     * @param context    Context to get the PreferenceManager from.
     * @param messageIdx the message index value to set
     */
    public static void setLastMessageIdx(Context context, int messageIdx) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spe = sp.edit();
        spe.putInt(context.getString(R.string.pref_message_idx_status_key), messageIdx);
        spe.commit();
    }





    public static long getLastUserMoodsConfirmAware(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getLong(context.getString(R.string.pref_last_user_moods_confirm_key),
                (new Date(0)).getTime());
    }


    public static void setLastMessageIdx(Context context, long timestamp) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spe = sp.edit();
        spe.putLong(context.getString(R.string.pref_last_user_moods_confirm_key), timestamp);
        spe.commit();
    }

}
