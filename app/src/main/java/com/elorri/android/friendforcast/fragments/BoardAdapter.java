package com.elorri.android.friendforcast.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.data.BoardData;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.elorri.android.friendforcast.extra.Tools;
import com.elorri.android.friendforcast.ui.AvatarView;

/**
 * Created by Elorri on 12/04/2016.
 */
public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    public static final int VIEW_FORECAST = 0;
    public static final int VIEW_TITLE = 1;
    public static final int VIEW_UNMANAGED_PEOPLE = 2;
    public static final int VIEW_DELAY_PEOPLE = 3;
    public static final int VIEW_TODAY_PEOPLE = 4;
    public static final int VIEW_TODAY_DONE_PEOPLE = 5;
    public static final int VIEW_NEXT_PEOPLE = 6;
    public static final int VIEW_UNTRACKED_PEOPLE = 7;
    public static int[] viewTypes;

    private Cursor mCursor;
    private Callback mCallback;
    private Context mContext;


    public BoardAdapter(Cursor cursor, Callback callback) {
        mCursor = cursor;
        mCallback = callback;
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
    }

    interface Callback {
        void onContactClicked(Uri uri, int avatarColor);

        void setForecast(int forecastRessourceId);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View divider;
        public AvatarView avatar;
        private int avatarColor;
        public TextView contactName;
        public TextView action;
        public TextView dueDate;
        public TextView doneDate;
        public ImageView vectorIcon;
        public ImageView emoIcon;

        public View mView;


        public ViewHolder(View view, int viewType) {
            super(view);
            this.mView = view;
            avatar = (AvatarView) view.findViewById(R.id.avatar);
            contactName = (TextView) view.findViewById(R.id.title);
            emoIcon = (ImageView) view.findViewById(R.id.emo_icon);

            switch (viewType) {
                case VIEW_TITLE: {
                    divider = view.findViewById(R.id.divider);
                    break;
                }
                case VIEW_DELAY_PEOPLE: {
                    action = (TextView) view.findViewById(R.id.action);
                    dueDate = (TextView) view.findViewById(R.id.due_date);
                    vectorIcon = (ImageView) view.findViewById(R.id.vectorIcon);
                    break;
                }
                case VIEW_TODAY_PEOPLE: {
                    action = (TextView) view.findViewById(R.id.action);
                    dueDate = (TextView) view.findViewById(R.id.due_date);
                    vectorIcon = (ImageView) view.findViewById(R.id.vectorIcon);
                    break;
                }
                case VIEW_TODAY_DONE_PEOPLE: {
                    action = (TextView) view.findViewById(R.id.action);
                    doneDate = (TextView) view.findViewById(R.id.done_date);
                    vectorIcon = (ImageView) view.findViewById(R.id.vectorIcon);
                    break;
                }
                case VIEW_NEXT_PEOPLE: {
                    action = (TextView) view.findViewById(R.id.action);
                    dueDate = (TextView) view.findViewById(R.id.due_date);
                    vectorIcon = (ImageView) view.findViewById(R.id.vectorIcon);
                    break;
                }
                default:
                    break;
            }
        }
    }


    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
        mContext = parent.getContext();
        ViewHolder viewHolder = null;
        View view;
        switch (viewType) {
            case VIEW_FORECAST: {
                viewHolder = new ViewHolder(new View(parent.getContext()), VIEW_FORECAST);
                break;
            }
            case VIEW_TITLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_title, parent, false);
                viewHolder = new ViewHolder(view, VIEW_TITLE);
                break;
            }
            case VIEW_UNMANAGED_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_unmanaged_untracked_people, parent, false);
                viewHolder = new ViewHolder(view, VIEW_UNMANAGED_PEOPLE);
                break;
            }
            case VIEW_DELAY_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_undone_event, parent, false);
                viewHolder = new ViewHolder(view, VIEW_DELAY_PEOPLE);
                break;
            }
            case VIEW_TODAY_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_undone_event, parent, false);
                viewHolder = new ViewHolder(view, VIEW_TODAY_PEOPLE);
                break;
            }
            case VIEW_TODAY_DONE_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_done_event, parent, false);
                viewHolder = new ViewHolder(view, VIEW_TODAY_DONE_PEOPLE);
                break;
            }
            case VIEW_NEXT_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_undone_event, parent, false);
                viewHolder = new ViewHolder(view, VIEW_NEXT_PEOPLE);
                break;
            }
            case VIEW_UNTRACKED_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_unmanaged_untracked_people, parent, false);
                viewHolder = new ViewHolder(view, VIEW_UNTRACKED_PEOPLE);
                break;
            }
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_FORECAST: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "ratio "
                        + mCursor.getString(ContactDAO.RatioQuery.COL_RATIO));
                String cursorRatio = mCursor.getString(ContactDAO.RatioQuery.COL_RATIO);
                if (cursorRatio != null) {
                    float ratio = Float.valueOf(cursorRatio);
                    mCallback.setForecast(Tools.getForecastRessourceId(ratio));
                }
                break;
            }
            case VIEW_TITLE: {
                int visibility = position == 1 ? View.INVISIBLE : View.VISIBLE;
                holder.divider.setVisibility(visibility);
                holder.contactName.setText(mCursor.getString
                        (BoardData.TitleQuery.COL_TITLE));
                break;
            }
            case VIEW_UNMANAGED_PEOPLE: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case VIEW_DELAY_PEOPLE: {
                bindCommonViews(holder);
                Tools.setVectorBackground(mContext, holder.vectorIcon,
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_MIMETYPE),
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_DATA));
                holder.action.setText(mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_ACTION));
                long dueDate = mCursor.getLong(ContactActionVectorEventDAO.DelayPeopleQuery.COL_TIME_START);
                holder.dueDate.setText(DateUtils.getFriendlyDateString(mContext, dueDate));
                holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactActionVectorEventDAO
                        .DelayPeopleQuery.COL_EMOICON_ID));
                setOnClickListener(holder);
                break;
            }
            case VIEW_TODAY_PEOPLE: {
                bindCommonViews(holder);
                Tools.setVectorBackground(mContext, holder.vectorIcon,
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_MIMETYPE),
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_DATA));
                holder.action.setText(mCursor.getString(ContactActionVectorEventDAO.TodayPeopleQuery.COL_ACTION));
                long dueDate = mCursor.getLong(ContactActionVectorEventDAO.TodayPeopleQuery.COL_TIME_START);
                holder.dueDate.setText(DateUtils.getFriendlyDateString(mContext, dueDate));
                holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactActionVectorEventDAO
                        .TodayPeopleQuery.COL_EMOICON_ID));
                setOnClickListener(holder);
                break;
            }
            case VIEW_TODAY_DONE_PEOPLE: {
                bindCommonViews(holder);
                Tools.setVectorBackground(mContext, holder.vectorIcon,
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_MIMETYPE),
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_DATA));
                holder.action.setText(mCursor.getString(ContactActionVectorEventDAO.TodayDonePeopleQuery.COL_ACTION));
                long doneDate = mCursor.getLong(ContactActionVectorEventDAO.TodayDonePeopleQuery
                        .COL_TIME_END);
                holder.doneDate.setText(DateUtils.getFriendlyDateTimeString(mContext, doneDate));
                holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactActionVectorEventDAO
                        .TodayDonePeopleQuery.COL_EMOICON_ID));
                setOnClickListener(holder);
                break;
            }
            case VIEW_NEXT_PEOPLE: {
                bindCommonViews(holder);
                Tools.setVectorBackground(mContext, holder.vectorIcon,
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_MIMETYPE),
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_DATA));
                holder.action.setText(mCursor.getString(ContactActionVectorEventDAO.NextPeopleQuery.COL_ACTION));
                long dueDate = mCursor.getLong(ContactActionVectorEventDAO.NextPeopleQuery.COL_TIME_START);
                holder.dueDate.setText(DateUtils.getFriendlyDateString(mContext, dueDate));
                holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactActionVectorEventDAO
                        .NextPeopleQuery.COL_EMOICON_ID));
                setOnClickListener(holder);
                break;
            }
            case VIEW_UNTRACKED_PEOPLE: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
        }

    }

    private void bindCommonViews(ViewHolder holder) {
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        holder.avatarColor = generator.getRandomColor();
        holder.avatar.loadImage(mCursor.getString(ContactActionVectorEventDAO
                .PeopleQuery.COL_THUMBNAIL), holder.avatarColor);
        holder.contactName.setText(Tools.toProperCase(mCursor.getString(ContactActionVectorEventDAO
                .PeopleQuery.COL_CONTACT_NAME)));
        holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactActionVectorEventDAO
                .PeopleQuery.COL_EMOICON_ID));
    }

    private void setOnClickListener(final ViewHolder holder) {
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                mCursor.moveToPosition(adapterPosition);
                int contactId = mCursor.getInt(ContactActionVectorEventDAO.PeopleQuery.COL_ID);
                Uri uri = FriendForecastContract.DetailData.buildDetailUri(contactId);
                mCallback.onContactClicked(uri, holder.avatarColor);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor data) {
        mCursor = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return viewTypes[position];
    }
}
