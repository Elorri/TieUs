package com.elorri.android.friendforcast.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.data.AddActionData;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ActionDAO;
import com.elorri.android.friendforcast.db.VectorDAO;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.elorri.android.friendforcast.extra.Tools;

/**
 * Created by Elorri on 28/04/2016.
 */

public class AddActionAdapter extends RecyclerView.Adapter<AddActionAdapter.ViewHolder> {


    public static final int VIEW_ACTION_RECAP = 0;
    public static final int VIEW_TITLE = 1;
    public static final int VIEW_ACTION_ITEM = 2;
    public static final int VIEW_VECTOR_ITEM = 3;


    public static int[] viewTypes;

    private Cursor mCursor;
    private Context mContext;
    private Callback mCallback;


    interface Callback {
        void setActionId(String actionId);

        void setVectorId(String vectorId);

        void showFab(String actionId, String vectorId, long timeStart);
    }

    public AddActionAdapter(Cursor cursor, Callback callback) {
        mCursor = cursor;
        mCallback = callback;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public View divider;
        public ImageView vectorLogo;
        public ImageView clock;
        public TextView action;
        public TextView timeStart;
        public TextView title;
        public TextView textView;
        public ImageView imageView;
        public TextView label;
        public View mView;

        public ViewHolder(View view, int viewType) {
            super(view);
            this.mView = view;

            switch (viewType) {
                case VIEW_ACTION_RECAP: {
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                    vectorLogo = (ImageView) view.findViewById(R.id.vector_logo);
                    clock = (ImageView) view.findViewById(R.id.clock);
                    action = (TextView) view.findViewById(R.id.action);
                    timeStart = (TextView) view.findViewById(R.id.time_start);
                    break;
                }
                case VIEW_TITLE: {
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                    divider = view.findViewById(R.id.divider);
                    title = (TextView) view.findViewById(R.id.title);
                    break;
                }
                case VIEW_ACTION_ITEM: {
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                    textView = (TextView) view.findViewById(R.id.textview);
                    break;
                }
                case VIEW_VECTOR_ITEM: {
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                    imageView = (ImageView) view.findViewById(R.id.imageview);
                    label = (TextView) view.findViewById(R.id.label);
                    break;
                }
            }
        }
    }

    @Override
    public AddActionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        ViewHolder viewHolder = null;
        View view;
        switch (viewType) {
            case VIEW_ACTION_RECAP: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action_recap,
                        parent, false);
                viewHolder = new ViewHolder(view, VIEW_ACTION_RECAP);
                break;
            }
            case VIEW_TITLE: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title,
                        parent, false);
                viewHolder = new ViewHolder(view, VIEW_TITLE);
                break;
            }
            case VIEW_ACTION_ITEM: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_textview,
                        parent, false);
                viewHolder = new ViewHolder(view, VIEW_ACTION_ITEM);
                break;
            }
            case VIEW_VECTOR_ITEM: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_imageview_label,
                        parent, false);
                viewHolder = new ViewHolder(view, VIEW_VECTOR_ITEM);
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AddActionAdapter.ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_ACTION_RECAP: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                int vectorIdx = mCursor.getColumnIndex(FriendForecastContract.VectorTable.COLUMN_DATA);
                int actionIdx = mCursor.getColumnIndex(FriendForecastContract.ActionTable.COLUMN_NAME);
                int timeStartIdx = mCursor.getColumnIndex(FriendForecastContract.EventTable.COLUMN_TIME_START);
                String actionName;
                long timeStartLong;
                if (actionIdx != -1) {
                    actionName = mCursor.getString(actionIdx);
                    holder.action.setText(actionName);
                    if (vectorIdx != -1) {
                        int mimetypeIdx = mCursor.getColumnIndex(FriendForecastContract.VectorTable.COLUMN_MIMETYPE);

                        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" +
                                mCursor.getString(mimetypeIdx) +
                                " " + mCursor.getString(vectorIdx));
                        Tools.setVectorBackground(mContext, holder.vectorLogo,
                                mCursor.getString(mimetypeIdx),
                                mCursor.getString(vectorIdx));
                        if (timeStartIdx != -1) {//All action fields are known. Add the event.
                            String actionId = mCursor.getString(mCursor.getColumnIndex
                                    (FriendForecastContract.ActionTable.VIEW_ACTION_ID));
                            String vectorId = mCursor.getString(mCursor.getColumnIndex
                                    (FriendForecastContract.VectorTable.VIEW_VECTOR_ID));
                            holder.clock.setVisibility(View.VISIBLE);
                            holder.clock.setBackgroundResource(R.drawable.ic_schedule_black_24dp);
                            timeStartLong = mCursor.getLong(timeStartIdx);
                            holder.timeStart.setText(DateUtils.getFriendlyDateString(mContext, timeStartLong));
                            mCallback.showFab(actionId, vectorId, timeStartLong);
                        }
                    }
                }
                break;
            }
            case VIEW_TITLE: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                int visibility = position == 0 ? View.INVISIBLE : View.VISIBLE;
                holder.divider.setVisibility(visibility);
                holder.title.setText(mCursor.getString(AddActionData.TitleQuery.COL_TITLE));
                break;
            }
            case VIEW_ACTION_ITEM: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                holder.textView.setText(mCursor.getString(ActionDAO.ActionQuery.COL_ACTION_NAME));
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition();
                        mCursor.moveToPosition(adapterPosition);
                        final String actionId = mCursor.getString(ActionDAO.ActionQuery.COL_ID);
                        mCallback.setActionId(actionId);
                    }
                });
                break;
            }
            case VIEW_VECTOR_ITEM: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                holder.imageView.setBackground(null);
                Tools.setVectorBackground(mContext, holder.imageView, mCursor.getString(
                                VectorDAO.VectorQuery.COL_MIMETYPE),
                        mCursor.getString(VectorDAO.VectorQuery.COL_DATA)
                );

                holder.label.setText(mCursor.getString(VectorDAO.VectorQuery.COL_VECTOR_NAME));
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition();
                        mCursor.moveToPosition(adapterPosition);
                        final String vectorId = mCursor.getString(VectorDAO.VectorQuery.COL_ID);
                        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + vectorId);
                        mCallback.setVectorId(vectorId);
                    }
                });
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
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
        mCursor = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
        return viewTypes[position];
    }

}
