package com.elorri.android.tieus.extra;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.elorri.android.tieus.R;

/**
 * Created by Elorri on 03/05/2016.
 * This class contains methods to get and set data in the user SharedPreferences file
 */
public class Status {

    /**
     * @param context Context used to get the SharedPreferences
     * @return the mark action feature status that tell if the user knows hows to validate an action
     */
    public static final boolean DONE_ACTION_AWARE_FALSE = false;

    public static boolean isDoneActionsAware(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(context.getString(R.string.pref_done_action_aware_key),
                DONE_ACTION_AWARE_FALSE);
    }

    /**
     * Sets the DoneActionsAware status into shared preference.  This function should not be called from
     * the UI thread because it uses commit to write to the shared preferences. Nb:if call from
     * UI thread use, apply instead.
     *
     * @param context           Context to get the PreferenceManager from.
     * @param isDoneActionAware The IntDef value to set
     */
    public static void setDoneActionsAware(Context context, boolean isDoneActionAware) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean(context.getString(R.string.pref_done_action_aware_key),
                isDoneActionAware);
        spe.commit();
    }



    /**
     * @param context Context used to get the SharedPreferences
     * @return the mark action feature status that tell if the user knows hows to validate an action
     */
    public static final boolean DELETE_ACTION_AWARE_FALSE = false;

    public static boolean isDeleteActionsAware(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(context.getString(R.string.pref_delete_action_aware_key),
                DELETE_ACTION_AWARE_FALSE);
    }

    /**
     * Sets the DeleteActionsAware status into shared preference.  This function should not be called from
     * the UI thread because it uses commit to write to the shared preferences. Nb:if call from
     * UI thread use, apply instead.
     *
     * @param context           Context to get the PreferenceManager from.
     * @param isDeleteActionAware The IntDef value to set
     */
    public static void setDeleteActionsAware(Context context, boolean isDeleteActionAware) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean(context.getString(R.string.pref_delete_action_aware_key),
                isDeleteActionAware);
        spe.commit();
    }


    /**
     * @param context Context used to get the SharedPreferences
     * @return the index of the last message the user have seen, could be the one on the screen.
     */
    public static final int MANAGE_UNSCHEDULED_PEOPLE = 0;
    public static final int FILL_IN_RESPONSE_TIME_LIMIT = 1;
    public static final int UPDATE_SATISFACTION = 2;
    public static final int SET_UP_A_FREQUENCY_OF_CONTACT = 3;
    public static final int ASK_FOR_RESPONSE_OR_MOVE_TO_UNFOLLOWED = 4;
    public static final int APPROCHING_DEAD_LINE = 5;
    public static final int NOTE_PEOPLE_WHO_DECREASED_SATISFACTION_TODAY = 6;
    public static final int NOTHING_TO_SAY = 7;

    public static int getLastMessageIdx(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(context.getString(R.string.pref_message_idx_status_key),
                MANAGE_UNSCHEDULED_PEOPLE);
    }

    /**
     * Sets the MarkActionFeature status into shared preference.  This function should not be called from
     * the UI thread because it uses commit to write to the shared preferences. Nb:if call from
     * UI thread use, apply instead.
     *
     * @param context    Context to get the PreferenceManager from.
     * @param messageIdx the message index value to set
     */
    public static void setLastMessageIdxBg(Context context, int messageIdx) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spe = sp.edit();
        spe.putInt(context.getString(R.string.pref_message_idx_status_key), messageIdx);
        spe.commit();
    }

    /**
     * Sets the MarkActionFeature status into shared preference.  Nb:if call from
     * BG thread use, setLastMessageIdxBg instead.
     *
     * @param context    Context to get the PreferenceManager from.
     * @param messageIdx the message index value to set
     */
    public static void setLastMessageIdxUI(Context context, int messageIdx) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spe = sp.edit();
        spe.putInt(context.getString(R.string.pref_message_idx_status_key), messageIdx);
        spe.apply();
    }


    public static long getLastUserSatisfactionsConfirmAware(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getLong(context.getString(R.string.pref_last_user_satisfactions_confirm_key),0);
    }


    public static void setLastUserSatisfactionsConfirmAware(Context context, long timestamp) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spe = sp.edit();
        spe.putLong(context.getString(R.string.pref_last_user_satisfactions_confirm_key), timestamp);
        spe.commit();
    }



    public static long getLastNotificationTimestamp(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getLong(context.getString(R.string.pref_last_notification_key),0);
    }


    public static void setLastNotificationTimestamp(Context context, long timestamp) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spe = sp.edit();
        spe.putLong(context.getString(R.string.pref_last_notification_key), timestamp);
        spe.commit();
    }

    public static final String SYNC_UNKNOWWN="sync_unknown";
    public static final String SYNC_START="sync_start";
    public static final String SYNC_DONE="sync_done";
    public static String getSyncStatus(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(context.getString(R.string.pref_sync_status_key), SYNC_UNKNOWWN);
    }


    public static void setSyncStatus(Context context, String status) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(context.getString(R.string.pref_sync_status_key), status);
        spe.commit();
    }


    public static void setSyncStats(Context context, String key, int stat) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spe = sp.edit();
        spe.putInt(key, stat);
        spe.commit();
    }

    public static int getSyncStatsContactAdded(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(context.getString(R.string.pref_sync_stat_contact_added_key), 0);
    }

    public static void setSyncStatsContactAdded(Context context, int contactNumber) {
        setSyncStats(context, context.getString(R.string.pref_sync_stat_contact_added_key),
                contactNumber);
    }

    public static int getSyncStatsContactUpdated(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(context.getString(R.string.pref_sync_stat_contact_updated_key), 0);
    }

    public static void setSyncStatsContactUpdated(Context context, int contactNumber) {
        setSyncStats(context, context.getString(R.string.pref_sync_stat_contact_updated_key),
                contactNumber);
    }

    public static int getSyncStatsContactDeleted(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(context.getString(R.string.pref_sync_stat_contact_deleted_key), 0);
    }

    public static void setSyncStatsContactDeleted(Context context, int contactNumber) {
        setSyncStats(context, context.getString(R.string.pref_sync_stat_contact_deleted_key),
                contactNumber);
    }

    public static Boolean getFirebaseStatsSent(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(context.getString(R.string.pref_firebase_stat_send_key), false);
    }
    public static void setFirebaseStatsSent(Context context, Boolean status) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean(context.getString(R.string.pref_firebase_stat_send_key), status);
        spe.commit();
    }

}
