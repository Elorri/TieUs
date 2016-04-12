package com.elorri.android.communication;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Elorri on 12/04/2016.
 */
public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    public static final int VIEW_TITLE = 0;
    public static final int VIEW_UNMANAGED_PEOPLE = 1;
    public static final int VIEW_UNDONE_PEOPLE = 2;
    public static final int VIEW_DONE_PEOPLE = 3;
    public static int[] viewTypes;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView contactName;
        public TextView action;
        public TextView dueDate;
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
                case VIEW_UNDONE_PEOPLE: {
                    action = (TextView) view.findViewById(R.id.action);
                    dueDate = (TextView) view.findViewById(R.id.due_date);
                    break;
                }
                case VIEW_DONE_PEOPLE: {
                    action = (TextView) view.findViewById(R.id.action);
                    dueDate = (TextView) view.findViewById(R.id.due_date);
                    actionIcon = (ImageView) view.findViewById(R.id.action_icon);
                    break;
                }
            }
        }
    }

    private Cursor mCursor;

    public BoardAdapter(Cursor cursor) {
        mCursor = cursor;
    }


    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
            case VIEW_UNDONE_PEOPLE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_undone_event, parent, false);
                viewHolder = new ViewHolder(view, VIEW_UNDONE_PEOPLE);
                break;
            }
            case VIEW_DONE_PEOPLE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_done_event, parent, false);
                viewHolder = new ViewHolder(view, VIEW_DONE_PEOPLE);
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TITLE: {
                break;
            }
            case VIEW_UNMANAGED_PEOPLE: {
                //holder.title.setText(mCursor.getString(MealProvider.MATRIX_ID_VALUE));
                break;
            }
            case VIEW_UNDONE_PEOPLE: {
                break;
            }
            case VIEW_DONE_PEOPLE: {
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
        mCursor = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return viewTypes[position];
    }
}
