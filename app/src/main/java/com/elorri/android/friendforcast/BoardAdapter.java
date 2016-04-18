package com.elorri.android.friendforcast;

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
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ContactActionEventDAO;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.elorri.android.friendforcast.ui.AvatarView;

/**
 * Created by Elorri on 12/04/2016.
 */
public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    public static final int VIEW_TITLE = 0;
    public static final int VIEW_UNMANAGED_PEOPLE = 1;
    public static final int VIEW_DELAY_PEOPLE = 2;
    public static final int VIEW_TODAY_PEOPLE = 3;
    public static final int VIEW_TODAY_DONE_PEOPLE = 4;
    public static final int VIEW_NEXT_PEOPLE = 5;
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
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View divider;
        public AvatarView avatar;
        private int avatarColor;
        public TextView contactName;
        public TextView action;
        public TextView dueDate;
        public TextView doneDate;
        //public ImageView actionIcon;
        public ImageView emoIcon;

        public View mView;


        public ViewHolder(View view, int viewType) {
            super(view);
            this.mView = view;
            avatar = (AvatarView) view.findViewById(R.id.avatar);
            contactName = (TextView) view.findViewById(R.id.contact_name);
            emoIcon = (ImageView) view.findViewById(R.id.emo_icon);

            switch (viewType) {
                case VIEW_TITLE: {
                    divider = view.findViewById(R.id.divider);
                    break;
                }
                case VIEW_UNMANAGED_PEOPLE: {
                    break;
                }
                case VIEW_DELAY_PEOPLE: {
                    action = (TextView) view.findViewById(R.id.action);
                    dueDate = (TextView) view.findViewById(R.id.due_date);
                    break;
                }
                case VIEW_TODAY_PEOPLE: {
                    action = (TextView) view.findViewById(R.id.action);
                    dueDate = (TextView) view.findViewById(R.id.due_date);
                    break;
                }
                case VIEW_TODAY_DONE_PEOPLE: {
                    action = (TextView) view.findViewById(R.id.action);
                    doneDate = (TextView) view.findViewById(R.id.done_date);
                    //actionIcon = (ImageView) view.findViewById(R.id.action_icon);
                    break;
                }
                case VIEW_NEXT_PEOPLE: {
                    action = (TextView) view.findViewById(R.id.action);
                    dueDate = (TextView) view.findViewById(R.id.due_date);
                    // actionIcon = (ImageView) view.findViewById(R.id.action_icon);
                    break;
                }
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
            case VIEW_TITLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_title, parent, false);
                viewHolder = new ViewHolder(view, VIEW_TITLE);
                break;
            }
            case VIEW_UNMANAGED_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_unmanaged_people, parent, false);
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
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("position", Thread.currentThread().getStackTrace()[2] + "position " +
                "" + position);
        mCursor.moveToPosition(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TITLE: {
                Log.e("position", Thread.currentThread().getStackTrace()[2] + "VIEW_TITLE " +
                        "position" + position);
                int visibility = position == 0 ? View.INVISIBLE : View.VISIBLE;
                holder.divider.setVisibility(visibility);
                holder.contactName.setText(mCursor.getString
                        (ContactActionEventDAO.TitleQuery.COL_TITLE));
                break;
            }
            case VIEW_UNMANAGED_PEOPLE: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case VIEW_DELAY_PEOPLE: {
                bindCommonViews(holder);
                holder.action.setText(mCursor.getString(ContactActionEventDAO.DelayPeopleQuery.COL_ACTION));
                long dueDate = mCursor.getLong(ContactActionEventDAO.DelayPeopleQuery.COL_TIME_START);
                holder.dueDate.setText(DateUtils.getFriendlyDateString(mContext, dueDate));
                holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactActionEventDAO
                        .DelayPeopleQuery.COL_EMOICON_ID));
                setOnClickListener(holder);
                break;
            }
            case VIEW_TODAY_PEOPLE: {
                bindCommonViews(holder);
                holder.action.setText(mCursor.getString(ContactActionEventDAO.TodayPeopleQuery.COL_ACTION));
                long dueDate = mCursor.getLong(ContactActionEventDAO.TodayPeopleQuery.COL_TIME_START);
                holder.dueDate.setText(DateUtils.getFriendlyDateString(mContext, dueDate));
                holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactActionEventDAO
                        .TodayPeopleQuery.COL_EMOICON_ID));
                setOnClickListener(holder);
                break;
            }
            case VIEW_TODAY_DONE_PEOPLE: {
                bindCommonViews(holder);
                holder.action.setText(mCursor.getString(ContactActionEventDAO.TodayDonePeopleQuery.COL_ACTION));
                long doneDate = mCursor.getLong(ContactActionEventDAO.TodayDonePeopleQuery
                        .COL_TIME_END);
                holder.doneDate.setText(DateUtils.getFriendlyDateString(mContext, doneDate));
                holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactActionEventDAO
                        .TodayDonePeopleQuery.COL_EMOICON_ID));
                setOnClickListener(holder);
                break;
            }
            case VIEW_NEXT_PEOPLE: {
                bindCommonViews(holder);
                holder.action.setText(mCursor.getString(ContactActionEventDAO.NextPeopleQuery.COL_ACTION));
                long dueDate = mCursor.getLong(ContactActionEventDAO.NextPeopleQuery.COL_TIME_START);
                holder.dueDate.setText(DateUtils.getFriendlyDateString(mContext, dueDate));
                holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactActionEventDAO
                        .NextPeopleQuery.COL_EMOICON_ID));
                setOnClickListener(holder);
                break;
            }
        }

    }

    private void bindCommonViews(ViewHolder holder) {
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        holder.avatarColor = generator.getRandomColor();
        Log.e("Color", Thread.currentThread().getStackTrace()[2] + "" + holder.avatarColor);
        holder.avatar.loadImage(mCursor.getString(ContactActionEventDAO
                .PeopleQuery.COL_THUMBNAIL), holder.avatarColor);
        holder.contactName.setText(mCursor.getString(ContactActionEventDAO
                .PeopleQuery.COL_CONTACT_NAME));
        holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactActionEventDAO
                .PeopleQuery.COL_EMOICON_ID));
    }

    private void setOnClickListener(final ViewHolder holder) {
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                mCursor.moveToPosition(adapterPosition);
                int contactId = mCursor.getInt(ContactActionEventDAO.PeopleQuery.COL_ID);
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
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
        mCursor = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
        return viewTypes[position];
    }
}
