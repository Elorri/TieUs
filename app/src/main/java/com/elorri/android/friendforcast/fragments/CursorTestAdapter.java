package com.elorri.android.friendforcast.fragments;

/**
 * Created by Elorri on 04/05/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elorri.android.friendforcast.R;

/**
 * Created by Elorri on 12/04/2016.
 */
public class CursorTestAdapter extends RecyclerView.Adapter<CursorTestAdapter.ViewHolder> {

    public static final int VIEW_EVENT = 31;
    public static final int VIEW_FACEBOOK = 34;
    public static final int VIEW_GMAIL = 32;

    public static final int VIEW_GOOGLE_PLUS = 33;
    public static final int VIEW_LINKEDIN = 35;
    public static final int VIEW_PHONE = 30;
    public static final int VIEW_MAIL = 29;

    private Cursor mCursor;
    private Context mContext;



    public CursorTestAdapter(Cursor cursor) {
        mCursor = cursor;
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView action;


        public ViewHolder(View view) {
            super(view);
            action = (TextView) view.findViewById(R.id.action);
        }
    }


    @Override
    public CursorTestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        ViewHolder viewHolder = null;
        View view;
        switch (viewType) {
            case VIEW_EVENT: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_event, parent, false);
                viewHolder = new ViewHolder(view);
                break;
            }
            case VIEW_GMAIL: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_gmail, parent, false);
                viewHolder = new ViewHolder(view);
                break;
            }
            case VIEW_FACEBOOK: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_facebook, parent, false);
                viewHolder = new ViewHolder(view
                );
                break;
            }
            case VIEW_GOOGLE_PLUS: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_event, parent, false);
                viewHolder = new ViewHolder(view);
                break;
            }
            case VIEW_LINKEDIN: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_gmail, parent, false);
                viewHolder = new ViewHolder(view);
                break;
            }
            case VIEW_MAIL: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_facebook, parent, false);
                viewHolder = new ViewHolder(view);
                break;
            }
            case VIEW_PHONE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_event, parent, false);
                viewHolder = new ViewHolder(view);
                break;
            }
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
        mCursor.moveToPosition(position);
        int viewtype=mCursor.getInt(mCursor.getColumnIndex("viewtype"));
        Log.e("FF", Thread.currentThread().getStackTrace()[2]+"position "+position);
        Log.e("FF", Thread.currentThread().getStackTrace()[2]+"viewtype "+viewtype);
        return viewtype;
    }
}
