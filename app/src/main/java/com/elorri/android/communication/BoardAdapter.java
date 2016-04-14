package com.elorri.android.communication;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elorri.android.communication.db.AndroidContactActionEventDAO;

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

    public BoardAdapter(Cursor cursor) {
        mCursor = cursor;
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView contactName;
        public TextView action;
        public TextView dueDate;
        public TextView doneDate;
        public ImageView actionIcon;

        public ViewHolder(View view, int viewType) {
            super(view);
            avatar = (ImageView) view.findViewById(R.id.avatar);
            contactName = (TextView) view.findViewById(R.id.contact_name);

            switch (viewType) {
                case VIEW_TITLE: {
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
                    actionIcon = (ImageView) view.findViewById(R.id.action_icon);
                    break;
                }
                case VIEW_NEXT_PEOPLE: {
                    action = (TextView) view.findViewById(R.id.action);
                    dueDate = (TextView) view.findViewById(R.id.due_date);
                    actionIcon = (ImageView) view.findViewById(R.id.action_icon);
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
                viewHolder = new ViewHolder(view, VIEW_UNMANAGED_PEOPLE);
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
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
        mCursor.moveToPosition(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TITLE: {
                holder.contactName.setText(mCursor.getString
                        (AndroidContactActionEventDAO.TitleQuery.COL_TITLE));
                break;
            }
            case VIEW_UNMANAGED_PEOPLE: {
                holder.contactName.setText(mCursor.getString(AndroidContactActionEventDAO
                        .UnmanagedPeopleQuery.COL_CONTACT_NAME));
                break;
            }
            case VIEW_DELAY_PEOPLE: {
                holder.contactName.setText(mCursor.getString(AndroidContactActionEventDAO.DelayPeopleQuery.COL_CONTACT_NAME));
                holder.action.setText(mCursor.getString(AndroidContactActionEventDAO.DelayPeopleQuery.COL_ACTION));
                holder.dueDate.setText(mCursor.getString(AndroidContactActionEventDAO.DelayPeopleQuery.COL_TIME_START));
                break;
            }
            case VIEW_TODAY_PEOPLE: {
                holder.contactName.setText(mCursor.getString(AndroidContactActionEventDAO.TodayPeopleQuery.COL_CONTACT_NAME));
                holder.action.setText(mCursor.getString(AndroidContactActionEventDAO.TodayPeopleQuery.COL_ACTION));
                holder.dueDate.setText(mCursor.getString(AndroidContactActionEventDAO.TodayPeopleQuery.COL_TIME_START));
                break;
            }
            case VIEW_TODAY_DONE_PEOPLE: {
                holder.contactName.setText(mCursor.getString(AndroidContactActionEventDAO.TodayDonePeopleQuery.COL_CONTACT_NAME));
                holder.action.setText(mCursor.getString(AndroidContactActionEventDAO.TodayDonePeopleQuery.COL_ACTION));
                holder.dueDate.setText(mCursor.getString(AndroidContactActionEventDAO.TodayDonePeopleQuery.COL_TIME_END));
                break;
            }
            case VIEW_NEXT_PEOPLE: {
                holder.contactName.setText(mCursor.getString(AndroidContactActionEventDAO.NextPeopleQuery.COL_CONTACT_NAME));
                holder.action.setText(mCursor.getString(AndroidContactActionEventDAO.NextPeopleQuery.COL_ACTION));
                holder.dueDate.setText(mCursor.getString(AndroidContactActionEventDAO.NextPeopleQuery.COL_TIME_START));
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
