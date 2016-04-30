package com.elorri.android.friendforcast.fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
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
import com.elorri.android.friendforcast.db.ActionVectorTemplatesDAO;
import com.elorri.android.friendforcast.db.VectorDAO;
import com.elorri.android.friendforcast.extra.DateUtils;

/**
 * Created by Elorri on 28/04/2016.
 */

public class AddActionAdapter extends RecyclerView.Adapter<AddActionAdapter.ViewHolder> {


    public static final int VIEW_ACTION_RECAP = 0;
    public static final int VIEW_TITLE = 1;
    public static final int VIEW_ACTION_ITEM = 2;
    public static final int VIEW_VECTOR_ITEM = 3;
    public static final int VIEW_TEMPLATE_ITEM = 4;

    public static int[] viewTypes;

    private Cursor mCursor;
    private Context mContext;
    private Callback mCallback;


    interface Callback {
        void setActionId(String actionId);

        void setVectorId(String vectorId);

        void setTemplateId(String templateId);

        void showFab(String actionId, long timeStart);
    }

    public AddActionAdapter(Cursor cursor, Callback callback) {
        mCursor = cursor;
        mCallback = callback;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public View divider;
        public ImageView vectorLogo;
        public TextView action;
        public TextView timeStart;
        public TextView template;
        public TextView title;
        public TextView textView;
        public ImageView imageView;
        public View mView;

        public ViewHolder(View view, int viewType) {
            super(view);
            this.mView = view;

            switch (viewType) {
                case VIEW_ACTION_RECAP: {
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                    vectorLogo = (ImageView) view.findViewById(R.id.vector_logo);
                    action = (TextView) view.findViewById(R.id.action);
                    timeStart = (TextView) view.findViewById(R.id.time_start);
                    template = (TextView) view.findViewById(R.id.template);
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
                    break;
                }
                case VIEW_TEMPLATE_ITEM: {
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                    textView = (TextView) view.findViewById(R.id.textview);
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_imageview,
                        parent, false);
                viewHolder = new ViewHolder(view, VIEW_VECTOR_ITEM);
                break;
            }
            case VIEW_TEMPLATE_ITEM: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_textview,
                        parent, false);
                viewHolder = new ViewHolder(view, VIEW_TEMPLATE_ITEM);
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
                int vectorIdx = mCursor.getColumnIndex(FriendForecastContract.VectorTable.COLUMN_LOGO_ID);
                int actionIdx = mCursor.getColumnIndex(FriendForecastContract.ActionTable.COLUMN_NAME);
                int temptateIdx = mCursor.getColumnIndex(FriendForecastContract.ActionVectorTemplatesTable.COLUMN_VALUE);
                int timeStartIdx = mCursor.getColumnIndex(FriendForecastContract.EventTable.COLUMN_TIME_START);
                String actionId;
                long timeStartLong;
                if (actionIdx != -1){
                    actionId=mCursor.getString(actionIdx);
                    holder.action.setText(actionId);
                    if (vectorIdx != -1){
                        holder.vectorLogo.setBackgroundResource(mCursor.getInt(vectorIdx));
                        if (temptateIdx != -1) {
                            holder.template.setText(mCursor.getString(temptateIdx));
                            if (timeStartIdx != -1) {
                                timeStartLong = mCursor.getLong(timeStartIdx);
                                holder.timeStart.setText(DateUtils.getFriendlyDateString(mContext, timeStartLong));
                                mCallback.showFab(actionId, timeStartLong);
                            }
                        }
                    }
                }
                break;
            }
            case VIEW_TITLE: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                int visibility = position == 1 ? View.INVISIBLE : View.VISIBLE;
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
//                holder.imageView.setBackgroundResource(mCursor.getInt(VectorDAO.VectorQuery
//                        .COL_RESSOURCE_ID));
//                Glide.with(mContext)
//                        .load(mCursor.getInt(VectorDAO.VectorQuery.COL_RESSOURCE_ID))
//                        .crossFade()
//                        .into(holder.imageView);
                int ressourceId= mCursor.getInt(VectorDAO.VectorQuery.COL_RESSOURCE_ID);
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""+ressourceId);
                holder.imageView.setImageBitmap(BitmapFactory.decodeResource(mContext
                        .getResources(),ressourceId));
                //holder.imageView.setBackgroundResource(R.drawable.ic_mail_outline_black_24dp);

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition();
                        mCursor.moveToPosition(adapterPosition);
                        final String vectorId = mCursor.getString(VectorDAO.VectorQuery.COL_ID);
                        Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""+vectorId);
                        mCallback.setVectorId(vectorId);
                    }
                });
                break;
            }
            case VIEW_TEMPLATE_ITEM: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                holder.textView.setText(mCursor.getString(ActionVectorTemplatesDAO
                        .ActionVectorTemplatesQuery.COL_VALUE));
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition();
                        mCursor.moveToPosition(adapterPosition);
                        final String templateId = mCursor.getString
                                (ActionVectorTemplatesDAO.ActionVectorTemplatesQuery.COL_ID);
                        Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""+templateId);
                        mCallback.setTemplateId(templateId);
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
