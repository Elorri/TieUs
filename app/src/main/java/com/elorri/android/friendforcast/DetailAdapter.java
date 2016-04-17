package com.elorri.android.friendforcast;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elorri.android.friendforcast.db.ContactActionEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.elorri.android.friendforcast.extra.Tools;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    public static final int VIEW_EMOICON = 0;
    public static final int VIEW_ACTION = 1;
    public static int[] viewTypes;

    private Cursor mCursor;
    private Callback mCallback;
    private Context mContext;

    interface Callback{
        void setTitle(String title);
        void setThumbnail(String url);
    }


    public DetailAdapter(Cursor cursor, Callback callback) {
        mCursor = cursor;
        mCallback=callback;
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView emoIcon;
        public TextView action;
        public TextView timeStart;

        public View mView;


        public ViewHolder(View view, int viewType) {
            super(view);
            this.mView = view;

            switch (viewType) {
                case VIEW_EMOICON: {
                    emoIcon = (ImageView) view.findViewById(R.id.emo_icon);
                    break;
                }
                case VIEW_ACTION: {
                    action = (TextView) view.findViewById(R.id.action);
                    timeStart = (TextView) view.findViewById(R.id.time_start);
                    break;
                }
            }
        }
    }


    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
        ViewHolder viewHolder = null;
        View view;
        switch (viewType) {
            case VIEW_EMOICON: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emoicon,
                        parent, false);
                viewHolder = new ViewHolder(view, VIEW_EMOICON);
                break;
            }
            case VIEW_ACTION: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action,
                        parent, false);
                viewHolder = new ViewHolder(view, VIEW_ACTION);
                break;
            }
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(DetailAdapter.ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_EMOICON: {
                mCallback.setTitle(mCursor.getString(ContactDAO.ContactQuery
                        .COL_ANDROID_CONTACT_NAME));
                mCallback.setThumbnail(mCursor.getString(ContactDAO.ContactQuery
                        .COL_THUMBNAIL));
                holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactDAO.ContactQuery.COL_EMOICON_BY_ID));
                break;
            }
            case VIEW_ACTION: {
                holder.action.setText(mCursor.getString(ContactActionEventDAO
                        .ActionByContactIdQuery.COL_ACTION_NAME));
                long dueDateLong = mCursor.getLong(ContactActionEventDAO
                        .ActionByContactIdQuery.COL_TIME_START);
                holder.timeStart.setText(DateUtils.fromLongToString(dueDateLong,
                        DateUtils.getFriendlyFormat(mContext, dueDateLong), Tools.getMostSuitableLocale()));
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor data) {
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
        mCursor = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
        return viewTypes[position];
    }
}
