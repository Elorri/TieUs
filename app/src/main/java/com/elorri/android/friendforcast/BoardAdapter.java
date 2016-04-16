package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ContactActionEventDAO;

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

    public BoardAdapter(Cursor cursor, Callback callback) {
        mCursor = cursor;
        mCallback=callback;
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
    }

    interface Callback{
        void onContactClicked(Uri uri);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View divider;
        public ImageView avatar;
        public FrameLayout avatarBg;
        public TextView contactName;
        public TextView action;
        public TextView dueDate;
        public TextView doneDate;
        //public ImageView actionIcon;
        public ImageView emoIcon;

        public View mView;


        public ViewHolder(View view, int viewType) {
            super(view);
            this.mView=view;
            avatarBg = (FrameLayout) view.findViewById(R.id.avatar_bg);
            avatar = (ImageView) view.findViewById(R.id.avatar);
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
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
        ViewHolder viewHolder = null;
        View view;
        switch (viewType) {
            case VIEW_TITLE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title, parent, false);
                viewHolder = new ViewHolder(view, VIEW_TITLE);
                break;
            }
            case VIEW_UNMANAGED_PEOPLE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unmanaged_people, parent, false);
                viewHolder = new ViewHolder(view, VIEW_UNMANAGED_PEOPLE);
                break;
            }
            case VIEW_DELAY_PEOPLE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_undone_event, parent, false);
                viewHolder = new ViewHolder(view, VIEW_DELAY_PEOPLE);
                break;
            }
            case VIEW_TODAY_PEOPLE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_undone_event, parent, false);
                viewHolder = new ViewHolder(view, VIEW_TODAY_PEOPLE);
                break;
            }
            case VIEW_TODAY_DONE_PEOPLE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_done_event, parent, false);
                viewHolder = new ViewHolder(view, VIEW_TODAY_DONE_PEOPLE);
                break;
            }
            case VIEW_NEXT_PEOPLE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_undone_event, parent, false);
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
                setAvatarBgColor(holder);
                holder.contactName.setText(mCursor.getString(ContactActionEventDAO
                        .UnmanagedPeopleQuery.COL_CONTACT_NAME));
                holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactActionEventDAO
                        .UnmanagedPeopleQuery.COL_EMOICON_ID));
                setOnClickListener(holder);
                break;
            }
            case VIEW_DELAY_PEOPLE: {
                setAvatarBgColor(holder);
                holder.contactName.setText(mCursor.getString(ContactActionEventDAO.DelayPeopleQuery.COL_CONTACT_NAME));
                holder.action.setText(mCursor.getString(ContactActionEventDAO.DelayPeopleQuery.COL_ACTION));
                holder.dueDate.setText(mCursor.getString(ContactActionEventDAO.DelayPeopleQuery.COL_TIME_START));
                holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactActionEventDAO
                        .DelayPeopleQuery.COL_EMOICON_ID));
                setOnClickListener(holder);
                break;
            }
            case VIEW_TODAY_PEOPLE: {
                setAvatarBgColor(holder);
                holder.contactName.setText(mCursor.getString(ContactActionEventDAO.TodayPeopleQuery.COL_CONTACT_NAME));
                holder.action.setText(mCursor.getString(ContactActionEventDAO.TodayPeopleQuery.COL_ACTION));
                holder.dueDate.setText(mCursor.getString(ContactActionEventDAO.TodayPeopleQuery.COL_TIME_START));
                holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactActionEventDAO
                        .TodayPeopleQuery.COL_EMOICON_ID));
                setOnClickListener(holder);
                break;
            }
            case VIEW_TODAY_DONE_PEOPLE: {
                setAvatarBgColor(holder);
                holder.contactName.setText(mCursor.getString(ContactActionEventDAO.TodayDonePeopleQuery.COL_CONTACT_NAME));
                holder.action.setText(mCursor.getString(ContactActionEventDAO.TodayDonePeopleQuery.COL_ACTION));
                holder.dueDate.setText(mCursor.getString(ContactActionEventDAO.TodayDonePeopleQuery.COL_TIME_END));
                holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactActionEventDAO
                        .TodayDonePeopleQuery.COL_EMOICON_ID));
                setOnClickListener(holder);
                break;
            }
            case VIEW_NEXT_PEOPLE: {
                setAvatarBgColor(holder);
                holder.contactName.setText(mCursor.getString(ContactActionEventDAO.NextPeopleQuery.COL_CONTACT_NAME));
                holder.action.setText(mCursor.getString(ContactActionEventDAO.NextPeopleQuery.COL_ACTION));
                holder.dueDate.setText(mCursor.getString(ContactActionEventDAO.NextPeopleQuery.COL_TIME_START));
                holder.emoIcon.setBackgroundResource(mCursor.getInt(ContactActionEventDAO
                        .NextPeopleQuery.COL_EMOICON_ID));
                setOnClickListener(holder);
                break;
            }
        }

    }

    private void setOnClickListener(final ViewHolder holder) {
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                mCursor.moveToPosition(adapterPosition);
                int contactId=mCursor.getInt(ContactActionEventDAO.PeopleQuery.COL_ID);
                Uri uri= FriendForecastContract.ContactEntry.buildContactDetailUri(contactId);
                mCallback.onContactClicked(uri);
            }
        });
    }

    private void setAvatarBgColor(ViewHolder holder) {
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int randomColor = generator.getRandomColor();
        holder.avatarBg.setBackgroundColor(randomColor);
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
