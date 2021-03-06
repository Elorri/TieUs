/*
 * The MIT License (MIT)

 Copyright (c) 2016 ETCHEMENDY ELORRI

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package com.elorri.android.capstone.tieus.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elorri.android.capstone.tieus.R;
import com.elorri.android.capstone.tieus.data.TieUsContract;
import com.elorri.android.capstone.tieus.db.ContactActionVectorEventDAO;
import com.elorri.android.capstone.tieus.db.ContactDAO;
import com.elorri.android.capstone.tieus.db.MatrixCursors;
import com.elorri.android.capstone.tieus.db.ViewTypes;
import com.elorri.android.capstone.tieus.extra.DateUtils;
import com.elorri.android.capstone.tieus.extra.Status;
import com.elorri.android.capstone.tieus.extra.Tools;
import com.elorri.android.capstone.tieus.ui.AvatarView;


/**
 * Created by Elorri on 12/04/2016.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {


    private Cursor mCursor;
    private Callback mCallback;
    private Context mContext;
    private int mPosition = RecyclerView.NO_POSITION;
    private ItemChoiceManager mItemChoiceManager;


    public MainAdapter(Cursor cursor, Callback callback, int choiceMode) {
        mCursor = cursor;
        mCallback = callback;
        mItemChoiceManager = new ItemChoiceManager(this);
        mItemChoiceManager.setChoiceMode(choiceMode);
    }

    public int getSelectedItemPosition() {
        return mItemChoiceManager.getSelectedItemPosition();
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public void selectView(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof MainAdapter.ViewHolder) {
            MainAdapter.ViewHolder vh = (MainAdapter.ViewHolder) viewHolder;
            vh.itemView.performClick();
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mItemChoiceManager.onRestoreInstanceState(savedInstanceState);
    }

    public void onSaveInstanceState(Bundle outState) {
        mItemChoiceManager.onSaveInstanceState(outState);
    }


    interface Callback {
        void onContactClicked(Uri uri, View v);

        void setForecast(int forecastRessourceId);

        void restartLoader();


    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View divider;
        public AvatarView avatar;
        public TextView contactName;
        public TextView action;
        public TextView dueDate;
        public TextView doneDate;
        public ImageView vectorIcon;
        public ImageView satisfactionIcon;
        public TextView satisfactionUnknown;
        public TextView message;
        public TextView emptyCursorMessage;
        public TextView ok;

        public View mView;


        public ViewHolder(View view, int viewType) {
            super(view);
            this.mView = view;
            avatar = (AvatarView) view.findViewById(R.id.avatar);
            contactName = (TextView) view.findViewById(R.id.title);
            satisfactionIcon = (ImageView) view.findViewById(R.id.satisfactionIcon);
            satisfactionUnknown = (TextView) view.findViewById(R.id.unknown_satisfaction);

            switch (viewType) {
                case ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE: {
                    emptyCursorMessage = (TextView) view.findViewById(R.id.message);
                    break;
                }
                case ViewTypes.VIEW_MESSAGE: {
                    message = (TextView) view.findViewById(R.id.message);
                    break;
                }
                case ViewTypes.VIEW_CONFIRM_MESSAGE: {
                    message = (TextView) view.findViewById(R.id.message);
                    ok = (TextView) view.findViewById(R.id.ok);
                    break;
                }
                case ViewTypes.VIEW_TITLE: {
                    divider = view.findViewById(R.id.divider);
                    break;
                }
                case ViewTypes.VIEW_DELAYED_PEOPLE: {
                    action = (TextView) view.findViewById(R.id.action);
                    dueDate = (TextView) view.findViewById(R.id.due_date);
                    vectorIcon = (ImageView) view.findViewById(R.id.vectorIcon);
                    break;
                }
                case ViewTypes.VIEW_TODAY_PEOPLE: {
                    action = (TextView) view.findViewById(R.id.action);
                    dueDate = (TextView) view.findViewById(R.id.due_date);
                    vectorIcon = (ImageView) view.findViewById(R.id.vectorIcon);
                    break;
                }
                case ViewTypes.VIEW_TODAY_DONE_PEOPLE: {
                    action = (TextView) view.findViewById(R.id.action);
                    doneDate = (TextView) view.findViewById(R.id.done_date);
                    vectorIcon = (ImageView) view.findViewById(R.id.vectorIcon);
                    break;
                }
                case ViewTypes.VIEW_NEXT_PEOPLE: {
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
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        ViewHolder viewHolder = null;
        View view;
        switch (viewType) {
            case ViewTypes.VIEW_FORECAST: {
                viewHolder = new ViewHolder(new View(parent.getContext()), ViewTypes.VIEW_FORECAST);
                break;
            }
            case ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_empty_cursor, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE);
                break;
            }
            case ViewTypes.VIEW_MESSAGE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_MESSAGE);
                break;
            }
            case ViewTypes.VIEW_TITLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_title, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_TITLE);
                break;
            }
            case ViewTypes.VIEW_UNSCHEDULED_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_UNSCHEDULED_PEOPLE);
                break;
            }
            case ViewTypes.VIEW_FILL_IN_RESPONSE_TIME_LIMIT: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_FILL_IN_RESPONSE_TIME_LIMIT);
                break;
            }
            case ViewTypes.VIEW_UPDATE_SATISFACTION: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_UPDATE_SATISFACTION);
                break;
            }
            case ViewTypes.VIEW_SET_UP_A_FREQUENCY_OF_CONTACT: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_SET_UP_A_FREQUENCY_OF_CONTACT);
                break;
            }
            case ViewTypes.VIEW_ASK_FOR_RESPONSE_OR_MOVE_TO_UNFOLLOWED: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_ASK_FOR_RESPONSE_OR_MOVE_TO_UNFOLLOWED);
                break;
            }
            case ViewTypes.VIEW_APPROCHING_END_OF_MOST_SUITABLE_CONTACT_TIME_LIMIT: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_APPROCHING_END_OF_MOST_SUITABLE_CONTACT_TIME_LIMIT);
                break;
            }
            case ViewTypes.VIEW_NOTE_PEOPLE_WHO_DECREASED_SATISFACTION_TODAY: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_NOTE_PEOPLE_WHO_DECREASED_SATISFACTION_TODAY);
                break;
            }
            case ViewTypes.VIEW_CONFIRM_MESSAGE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_confirm_message, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_CONFIRM_MESSAGE);
                break;
            }
            case ViewTypes.VIEW_DELAYED_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_undone_event, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_DELAYED_PEOPLE);
                break;
            }
            case ViewTypes.VIEW_TODAY_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_undone_event, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_TODAY_PEOPLE);
                break;
            }
            case ViewTypes.VIEW_TODAY_DONE_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_done_event, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_TODAY_DONE_PEOPLE);
                break;
            }
            case ViewTypes.VIEW_NEXT_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_undone_event, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_NEXT_PEOPLE);
                break;
            }
            case ViewTypes.VIEW_UNFOLLOWED_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_UNFOLLOWED_PEOPLE);
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
            case ViewTypes.VIEW_FORECAST: {
                String cursorRatio = mCursor.getString(ContactDAO.RatioQuery.COL_RATIO);
                if (cursorRatio != null) {
                    float ratio = Float.valueOf(cursorRatio);
                    mCallback.setForecast(Tools.getForecastRessourceId(ratio));
                }
                break;
            }
            case ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE: {
                holder.emptyCursorMessage.setText(mCursor.getString(MatrixCursors
                        .EmptyCursorMessageQuery.COL_MESSAGE));
                break;
            }
            case ViewTypes.VIEW_MESSAGE: {
                holder.message.setText(mCursor.getString(MatrixCursors.MessageQuery.COL_MESSAGE));
                break;
            }
            case ViewTypes.VIEW_TITLE: {
                int visibility = (position == 1 || position == 2) ? View.INVISIBLE : View.VISIBLE;
                holder.divider.setVisibility(visibility);
                holder.contactName.setText(mCursor.getString(MatrixCursors.TitleQuery.COL_TITLE));
                break;
            }
            case ViewTypes.VIEW_UNSCHEDULED_PEOPLE: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_FILL_IN_RESPONSE_TIME_LIMIT: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_UPDATE_SATISFACTION: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_SET_UP_A_FREQUENCY_OF_CONTACT: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_ASK_FOR_RESPONSE_OR_MOVE_TO_UNFOLLOWED: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_APPROCHING_END_OF_MOST_SUITABLE_CONTACT_TIME_LIMIT: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_NOTE_PEOPLE_WHO_DECREASED_SATISFACTION_TODAY: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_CONFIRM_MESSAGE: {
                holder.message.setText(mCursor.getString(MatrixCursors.ConfirmMessageQuery.COL_MESSAGE));
                holder.ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Status.getLastMessageIdx(mContext) == Status.ASK_FOR_RESPONSE_OR_MOVE_TO_UNFOLLOWED)
                            Status.setLastMessageIdxUI(mContext, Status.APPROCHING_DEAD_LINE);
                        else if (Status.getLastMessageIdx(mContext) == Status.APPROCHING_DEAD_LINE)
                            Status.setLastMessageIdxUI(mContext, Status.NOTE_PEOPLE_WHO_DECREASED_SATISFACTION_TODAY);
                        else if (Status.getLastMessageIdx(mContext) == Status.NOTE_PEOPLE_WHO_DECREASED_SATISFACTION_TODAY)
                            Status.setLastMessageIdxUI(mContext, Status.NOTHING_TO_SAY);
                        else
                            Status.setLastMessageIdxUI(mContext, Status.MANAGE_UNSCHEDULED_PEOPLE);
                        mCallback.restartLoader();
                    }
                });
                break;
            }
            case ViewTypes.VIEW_DELAYED_PEOPLE: {
                bindCommonViews(holder);
                Tools.setVectorBackground(mContext, holder.vectorIcon,
                        mCursor.getString(ContactActionVectorEventDAO.DelayedPeopleQuery.COL_VECTOR_MIMETYPE),
                        mCursor.getString(ContactActionVectorEventDAO.DelayedPeopleQuery.COL_VECTOR_DATA));
                holder.vectorIcon.setContentDescription(mCursor.getString(ContactActionVectorEventDAO
                        .DelayedPeopleQuery.COL_VECTOR_NAME));
                holder.action.setText(mContext.getResources().getString(mCursor.getInt
                        (ContactActionVectorEventDAO.DelayedPeopleQuery.COL_ACTION_NAME_RESOURCE_ID)));
                long dueDate = mCursor.getLong(ContactActionVectorEventDAO.DelayedPeopleQuery.COL_TIME_START);
                holder.dueDate.setText(DateUtils.getFriendlyDateString(mContext, dueDate));
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_TODAY_PEOPLE: {
                bindCommonViews(holder);
                Tools.setVectorBackground(mContext, holder.vectorIcon,
                        mCursor.getString(ContactActionVectorEventDAO.TodayPeopleQuery.COL_VECTOR_MIMETYPE),
                        mCursor.getString(ContactActionVectorEventDAO.TodayPeopleQuery.COL_VECTOR_DATA));
                holder.vectorIcon.setContentDescription(mCursor.getString(ContactActionVectorEventDAO
                        .TodayPeopleQuery.COL_VECTOR_NAME));
                holder.action.setText(mContext.getString(mCursor.getInt(ContactActionVectorEventDAO
                        .TodayPeopleQuery.COL_ACTION_NAME_RESOURCE_ID)));
                long dueDate = mCursor.getLong(ContactActionVectorEventDAO.TodayPeopleQuery.COL_TIME_START);
                holder.dueDate.setText(DateUtils.getFriendlyDateString(mContext, dueDate));
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_TODAY_DONE_PEOPLE: {
                bindCommonViews(holder);
                Tools.setVectorBackground(mContext, holder.vectorIcon,
                        mCursor.getString(ContactActionVectorEventDAO.TodayDonePeopleQuery.COL_VECTOR_MIMETYPE),
                        mCursor.getString(ContactActionVectorEventDAO.TodayDonePeopleQuery.COL_VECTOR_DATA));
                holder.vectorIcon.setContentDescription(mCursor.getString(ContactActionVectorEventDAO
                        .TodayDonePeopleQuery.COL_VECTOR_NAME));
                holder.action.setText(mContext.getString(mCursor.getInt(ContactActionVectorEventDAO
                        .TodayDonePeopleQuery.COL_ACTION_NAME_RESOURCE_ID)));
                long doneDate = mCursor.getLong(ContactActionVectorEventDAO.TodayDonePeopleQuery
                        .COL_TIME_END);
                holder.doneDate.setText(DateUtils.getFriendlyDateTimeString(mContext, doneDate));
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_NEXT_PEOPLE: {
                bindCommonViews(holder);
                Tools.setVectorBackground(mContext, holder.vectorIcon,
                        mCursor.getString(ContactActionVectorEventDAO.NextPeopleQuery.COL_VECTOR_MIMETYPE),
                        mCursor.getString(ContactActionVectorEventDAO.NextPeopleQuery.COL_VECTOR_DATA));
                holder.vectorIcon.setContentDescription(mCursor.getString(ContactActionVectorEventDAO
                        .NextPeopleQuery.COL_VECTOR_NAME));
                holder.action.setText(mContext.getString(mCursor.getInt(ContactActionVectorEventDAO
                        .NextPeopleQuery.COL_ACTION_NAME_RESOURCE_ID)));
                long dueDate = mCursor.getLong(ContactActionVectorEventDAO.NextPeopleQuery.COL_TIME_START);
                holder.dueDate.setText(DateUtils.getFriendlyDateString(mContext, dueDate));
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_UNFOLLOWED_PEOPLE: {
                bindCommonViews(holder, R.drawable.ic_do_not_disturb_alt_black_48dp);
                setOnClickListener(holder);
                break;
            }
        }
    }

    private void bindCommonViews(ViewHolder holder) {
        bindCommonViews(holder, mCursor.getInt(ContactActionVectorEventDAO
                .PeopleQuery.COL_SATISFACTION_ID));
    }

    private void bindCommonViews(ViewHolder holder, int satisfactionResId) {
        holder.avatar.loadImage(
                mCursor.getString(ContactActionVectorEventDAO.PeopleQuery.COL_THUMBNAIL),
                mCursor.getInt(ContactActionVectorEventDAO.PeopleQuery.COL_BACKGROUND_COLOR));
        holder.contactName.setText(Tools.toProperCase(mCursor.getString(ContactActionVectorEventDAO
                .PeopleQuery.COL_CONTACT_NAME)));
        holder.satisfactionIcon.setBackgroundResource(satisfactionResId);
        holder.satisfactionIcon.setContentDescription(Tools.getSatisfactionDesciption(mContext, satisfactionResId));
        boolean isSatisfactionKnown = mCursor.getString(
                ContactActionVectorEventDAO.PeopleQuery.COL_SATISFACTION_UNKNOWN).equals(
                TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE);
        boolean isUnfollowed = mCursor.getString(
                ContactActionVectorEventDAO.PeopleQuery.COL_UNFOLLOWED).equals(
                TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE);
        if (isSatisfactionKnown && !isUnfollowed)
            holder.satisfactionUnknown.setVisibility(View.VISIBLE);
        else
            holder.satisfactionUnknown.setVisibility(View.INVISIBLE);
        mItemChoiceManager.onBindViewHolder(holder, holder.getAdapterPosition());
    }

    private void setOnClickListener(final ViewHolder holder) {
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = holder.getAdapterPosition();
                mCursor.moveToPosition(mPosition);
                int contactId = mCursor.getInt(ContactActionVectorEventDAO.PeopleQuery.COL_ID);
                Uri uri = TieUsContract.DetailData.buildDetailUri(contactId);
                mCallback.onContactClicked(uri, v);
                mItemChoiceManager.onClick(holder);
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
        mCursor.moveToPosition(position);
        int viewtype = mCursor.getInt(mCursor.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE));
        return viewtype;
    }


}
