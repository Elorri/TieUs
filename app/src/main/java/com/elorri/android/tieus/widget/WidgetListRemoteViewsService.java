package com.elorri.android.tieus.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.activities.MainActivity;
import com.elorri.android.tieus.data.FriendForecastContract;
import com.elorri.android.tieus.db.ContactActionVectorEventDAO;
import com.elorri.android.tieus.extra.Tools;

/**
 * Service which handles updating Football widgets 'next matches collection' with the latest data
 * Created by Elorri on 17/01/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WidgetListRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final long identityToken = Binder.clearCallingIdentity();
                Uri uri = FriendForecastContract.WidgetData.buildWidgetUri(System.currentTimeMillis
                        ());
                data = getContentResolver().query(
                        uri,
                        null,
                        null,
                        null,
                        null);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_item);

                //TODO remove all those ugly comments
                // Extract the data from the Cursor
                Context context = getApplicationContext();
                String contact = data.getString(ContactActionVectorEventDAO.TodayPeopleQuery.COL_CONTACT_NAME);
                //String action = data.getString(ContactActionVectorEventDAO.TodayPeopleQuery.COL_ACTION);
                //Integer contactThumbnail = data.getInt(ContactActionVectorEventDAO.TodayPeopleQuery.COL_THUMBNAIL);
                String vectorMimeType = data.getString(ContactActionVectorEventDAO.TodayPeopleQuery.COL_VECTOR_MIMETYPE);
                String vector = data.getString(ContactActionVectorEventDAO.TodayPeopleQuery.COL_VECTOR_DATA);
                String vectorName = data.getString(ContactActionVectorEventDAO.TodayPeopleQuery.COL_VECTOR_NAME);
//                Integer mood = data.getInt(ContactActionVectorEventDAO.TodayPeopleQuery.COL_MOOD_ID);
//                String moodName = Tools.getMoodDesciption(context, mood);

                // Add the data to the RemoteViews
                Tools.setWidgetVectorBackground(context, views, R.id.vectorIcon, vectorMimeType, vector);
//                views.setImageViewResource(R.id.vectorIcon, vector);
//                views.setImageViewResource(R.id.moodIcon, mood);
                views.setTextViewText(R.id.contact, contact);
//                views.setTextViewText(R.id.action, action);


                //Add content description for images
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
//                    Tools.setRemoteContentDescription(views, R.id.avatar, contact);
                    Tools.setRemoteContentDescription(views, R.id.vectorIcon, vectorName);
//                    Tools.setRemoteContentDescription(views, R.id.moodIcon, moodName);
                }

                // Create an Intent to launch MainActivity
                Intent launchIntent = new Intent(context, MainActivity.class);
                //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,                         launchIntent, 0);
                //views.setOnClickPendingIntent(R.id.widget_list, pendingIntent);
                views.setOnClickFillInIntent(R.id.widget_item, launchIntent);
                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(ContactActionVectorEventDAO.TodayDonePeopleQuery.COL_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
