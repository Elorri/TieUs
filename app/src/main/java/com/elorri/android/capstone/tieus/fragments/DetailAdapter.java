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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.elorri.android.capstone.tieus.R;
import com.elorri.android.capstone.tieus.activities.MainActivity;
import com.elorri.android.capstone.tieus.data.TieUsContract;
import com.elorri.android.capstone.tieus.db.ContactActionVectorEventDAO;
import com.elorri.android.capstone.tieus.db.ContactDAO;
import com.elorri.android.capstone.tieus.db.EventDAO;
import com.elorri.android.capstone.tieus.db.MatrixCursors;
import com.elorri.android.capstone.tieus.db.ViewTypes;
import com.elorri.android.capstone.tieus.extra.DateUtils;
import com.elorri.android.capstone.tieus.extra.Status;
import com.elorri.android.capstone.tieus.extra.Tools;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {


    private Cursor mCursor;
    private Callback mCallback;
    private Context mContext;
    private AlertDialog mAlertEmoDialog;
    private AlertDialog mResponseDialog;
    private AlertDialog mResponseFrequencyDialog;
    private long mResponseTimeLimit;
    private long mFrequencyTimeLimit;
    private int mEmoIconResource;
    private String mContactId;

    public ItemTouchHelper.SimpleCallback simpleItemTouchCallBack =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                      RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    int viewType = getItemViewType(position);
                    if ((viewType == ViewTypes.VIEW_NEXT_ACTION) || (viewType == ViewTypes
                            .VIEW_DONE_ACTION)) {
                        mCursor.moveToPosition(position);
                        DeleteActionTask deleteActionTask = new DeleteActionTask();
                        deleteActionTask.execute(mCursor.getString(ContactActionVectorEventDAO
                                .VectorActionByContactIdQuery.COL_EVENT_ID));
                    }
                }

                @Override
                public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                    if (viewHolder instanceof ViewHolderAction)
                        return super.getSwipeDirs(recyclerView, viewHolder);
                    return 0;
                }
            };
    private boolean isSatisfactionUnknown;


    interface Callback {
        void setTitle(String title);

        void setThumbnail(String url, int color);

        void setAndroidContactUri(Uri uri);

        void updateFragment();

        void hideFab();

        void showFab();

        void setAvatarContentDescription(String contactName);

        void startVector(String mimetype, String vectorData, String vectorName);

        void sendToFirebase(String event, String contentType, String itemId, String itemName);
    }


    public DetailAdapter(Cursor cursor, Callback callback) {
        mCursor = cursor;
        mCallback = callback;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView satisfactionUnknown;
        public ImageView satisfactionIcon;
        public TextView action;
        public TextView time;
        public TextView message;
        public TextView ok;
        public TextView response;
        public TextView responseTitle;
        public TextView expectedResponseTitle;
        public TextView frequency;
        public TextView frequencyTitle;
        public TextView expectedFrequencyTitle;
        public RelativeLayout responseAloneView;
        public LinearLayout responseView;
        public LinearLayout frequencyView;


        public View divider;
        public TextView title;

        public View mView;


        public ViewHolder(View view, int viewType) {
            super(view);
            this.mView = view;

            switch (viewType) {
                case ViewTypes.VIEW_CONTACT: {
                    satisfactionIcon = (ImageView) view.findViewById(R.id.satisfactionIcon);
                    satisfactionUnknown = (TextView) view.findViewById(R.id.unknown_satisfaction);
                    break;
                }
                case ViewTypes.VIEW_FILL_IN_RESPONSE_TIME_LIMIT: {
                    response = (TextView) view.findViewById(R.id.response);
                    responseTitle = (TextView) view.findViewById(R.id.response_title);
                    expectedResponseTitle = (TextView) view.findViewById(R.id.expected_response_title);
                    responseAloneView = (RelativeLayout) view.findViewById(R.id.response_alone_view);
                    break;
                }
                case ViewTypes.VIEW_RESPONSE_FREQUENCY: {
                    responseView = (LinearLayout) view.findViewById(R.id.response_view);
                    response = (TextView) view.findViewById(R.id.response);
                    frequency = (TextView) view.findViewById(R.id.frequency);
                    frequencyTitle = (TextView) view.findViewById(R.id.frequency_title);
                    expectedFrequencyTitle = (TextView) view.findViewById(R.id.expected_frequency_title);
                    frequencyView = (LinearLayout) view.findViewById(R.id.frequency_view);
                    break;
                }
                case ViewTypes.VIEW_TITLE: {
                    divider = view.findViewById(R.id.divider);
                    title = (TextView) view.findViewById(R.id.title);
                    break;
                }
                case ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE: {
                    message = (TextView) view.findViewById(R.id.message);
                    break;
                }
                case ViewTypes.VIEW_CONFIRM_MESSAGE: {
                    message = (TextView) view.findViewById(R.id.message);
                    ok = (TextView) view.findViewById(R.id.ok);
                    break;
                }
            }
        }
    }


    public static class ViewHolderAction extends ViewHolder {
        public TextView action;
        public ImageView actionVectorImageView;
        public TextView time;
        public TextView title;

        public View mView;


        public ViewHolderAction(View view, int viewType) {
            super(view, viewType);
            this.mView = view;

            switch (viewType) {
                case ViewTypes.VIEW_NEXT_ACTION: {
                    action = (TextView) view.findViewById(R.id.action);
                    actionVectorImageView = (ImageView) view.findViewById(R.id.action_vector);
                    time = (TextView) view.findViewById(R.id.time);
                    break;
                }
                case ViewTypes.VIEW_DONE_ACTION: {
                    action = (TextView) view.findViewById(R.id.action);
                    actionVectorImageView = (ImageView) view.findViewById(R.id.action_vector);
                    time = (TextView) view.findViewById(R.id.time);
                    break;
                }
            }
        }
    }


    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        ViewHolder viewHolder = null;
        View view;
        switch (viewType) {
            case ViewTypes.VIEW_CONTACT: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_satisfaction,
                        parent, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openSatisfactionDialog();
                    }
                });
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_CONTACT);
                break;
            }
            case ViewTypes.VIEW_FILL_IN_RESPONSE_TIME_LIMIT: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_response_alone,
                        parent, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openResponseDialog();
                    }
                });
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_FILL_IN_RESPONSE_TIME_LIMIT);
                break;
            }
            case ViewTypes.VIEW_RESPONSE_FREQUENCY: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_response_frequency,
                        parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_RESPONSE_FREQUENCY);
                viewHolder.responseView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openResponseDialog();
                    }
                });
                viewHolder.frequencyView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFrequencyDialog();
                    }
                });
                break;
            }
            case ViewTypes.VIEW_TITLE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title,
                        parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_TITLE);
                break;
            }
            case ViewTypes.VIEW_NEXT_ACTION: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action,
                        parent, false);
                viewHolder = new ViewHolderAction(view, ViewTypes.VIEW_NEXT_ACTION);
                break;
            }
            case ViewTypes.VIEW_DONE_ACTION: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action,
                        parent, false);
                viewHolder = new ViewHolderAction(view, ViewTypes.VIEW_DONE_ACTION);
                break;
            }
            case ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_cursor,
                        parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE);
                break;
            }
            case ViewTypes.VIEW_CONFIRM_MESSAGE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_message,
                        parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_CONFIRM_MESSAGE);
                break;
            }
        }
        return viewHolder;
    }

    private void openFrequencyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        mResponseFrequencyDialog = builder.create();
        ScrollView listContainer = (ScrollView) View.inflate(mContext, R.layout.list_frequency,
                null);
        TextView everyday = (TextView) listContainer.findViewById(R.id.everyday);
        TextView everyWeek = (TextView) listContainer.findViewById(R.id.every_week);
        TextView every2weeks = (TextView) listContainer.findViewById(R.id.every_2weeks);
        TextView everyMonths = (TextView) listContainer.findViewById(R.id.every_months);
        TextView every3months = (TextView) listContainer.findViewById(R.id.every_3months);
        TextView every6months = (TextView) listContainer.findViewById(R.id.every_6months);
        TextView everyYear = (TextView) listContainer.findViewById(R.id.every_year);

        everyday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFrequencyTimeLimit != Tools._24H) {
                    ContentValues values = getFrequencyContactValues(
                            String.valueOf(Tools._24H));
                    update(values);
                }
                mResponseFrequencyDialog.cancel();
            }
        });
        everyWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFrequencyTimeLimit != Tools._1WEEK) {
                    ContentValues values = getFrequencyContactValues(
                            String.valueOf(Tools._1WEEK));
                    update(values);
                }
                mResponseFrequencyDialog.cancel();
            }
        });
        every2weeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFrequencyTimeLimit != Tools._2WEEKS) {
                    ContentValues values = getFrequencyContactValues(
                            String.valueOf(Tools._2WEEKS));
                    update(values);
                }
                mResponseFrequencyDialog.cancel();
            }
        });
        everyMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFrequencyTimeLimit != Tools._1MONTH) {
                    ContentValues values = getFrequencyContactValues(
                            String.valueOf(Tools._1MONTH));
                    update(values);
                }
                mResponseFrequencyDialog.cancel();
            }
        });
        every3months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFrequencyTimeLimit != Tools._3MONTHS) {
                    ContentValues values = getFrequencyContactValues(
                            String.valueOf(Tools._3MONTHS));
                    update(values);
                }
                mResponseFrequencyDialog.cancel();
            }
        });
        every6months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFrequencyTimeLimit != Tools._6MONTHS) {
                    ContentValues values = getFrequencyContactValues(
                            String.valueOf(Tools._6MONTHS));
                    update(values);
                }
                mResponseFrequencyDialog.cancel();
            }
        });
        everyYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFrequencyTimeLimit != Tools._1YEAR) {
                    ContentValues values = getFrequencyContactValues(
                            String.valueOf(Tools._1YEAR));
                    update(values);
                }
                mResponseFrequencyDialog.cancel();
            }
        });
        mResponseFrequencyDialog.show();
        mResponseFrequencyDialog.setContentView(listContainer);
    }

    private void openSatisfactionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        mAlertEmoDialog = builder.create();
        ScrollView listContainer = (ScrollView) View.inflate(mContext, R.layout.list_satisfaction,
                null);
        RelativeLayout happyItem = (RelativeLayout) listContainer.findViewById(R.id.happy_item);
        RelativeLayout neutralItem = (RelativeLayout) listContainer.findViewById(R.id.neutral_item);
        RelativeLayout dissatisfiedItem = (RelativeLayout) listContainer.findViewById(R.id.dissatisfied_item);
        RelativeLayout unfollowedItem = (RelativeLayout) listContainer.findViewById(R.id.unfollowed_item);
        happyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmoIconResource != R.drawable.ic_sentiment_satisfied_black_48dp
                        || (mEmoIconResource == R.drawable
                        .ic_sentiment_satisfied_black_48dp && isSatisfactionUnknown)) {
                    ContentValues values = getSatisfactionContactValues(
                            String.valueOf(R.drawable.ic_sentiment_satisfied_black_48dp));
                    update(values);
                    mCallback.sendToFirebase(FirebaseAnalytics.Event.SELECT_CONTENT, null, null,
                            mContext.getResources().getString(R.string.satisfied));
                }
                mAlertEmoDialog.cancel();
            }

        });
        neutralItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmoIconResource != R.drawable.ic_sentiment_neutral_black_48dp
                        || mEmoIconResource == R.drawable.ic_sentiment_neutral_black_48dp && isSatisfactionUnknown) {
                    ContentValues values = getSatisfactionContactValues(
                            String.valueOf(R.drawable.ic_sentiment_neutral_black_48dp));
                    update(values);
                    mCallback.sendToFirebase(FirebaseAnalytics.Event.SELECT_CONTENT, null, null,
                            mContext.getResources().getString(R.string.neutral));
                }
                mAlertEmoDialog.cancel();
            }
        });
        dissatisfiedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmoIconResource != R.drawable.ic_sentiment_dissatisfied_black_48dp
                        || mEmoIconResource == R.drawable.ic_sentiment_dissatisfied_black_48dp && isSatisfactionUnknown) {
                    ContentValues values = getSatisfactionContactValues(
                            String.valueOf(R.drawable.ic_sentiment_dissatisfied_black_48dp));
                    update(values);
                    mCallback.sendToFirebase(FirebaseAnalytics.Event.SELECT_CONTENT, null, null,
                            mContext.getResources().getString(R.string.unsatisfied));
                }
                mAlertEmoDialog.cancel();
            }
        });
        unfollowedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmoIconResource != R.drawable.ic_do_not_disturb_alt_black_48dp
                        || mEmoIconResource == R.drawable.ic_do_not_disturb_alt_black_48dp && isSatisfactionUnknown) {
                    ContentValues values = getUnfollowedContactValues(
                            String.valueOf(TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE));
                    update(values);
                    mCallback.sendToFirebase(FirebaseAnalytics.Event.SELECT_CONTENT, null, null,
                            mContext.getResources().getString(R.string.unfollowed));
                }
                mAlertEmoDialog.cancel();
            }
        });
        mAlertEmoDialog.show();
        mAlertEmoDialog.setContentView(listContainer);
    }

    private void openResponseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        mResponseDialog = builder.create();
        ScrollView listContainer = (ScrollView) View.inflate(mContext, R.layout.list_response, null);
        TextView _24h = (TextView) listContainer.findViewById(R.id._24h);
        TextView _48h = (TextView) listContainer.findViewById(R.id._48h);
        TextView _4days = (TextView) listContainer.findViewById(R.id._4days);
        TextView _1week = (TextView) listContainer.findViewById(R.id._1week);
        TextView _2weeks = (TextView) listContainer.findViewById(R.id._2weeks);

        _24h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResponseTimeLimit != Tools._24H) {
                    ContentValues values = getResponseContactValues(
                            String.valueOf(Tools._24H),
                            String.valueOf(Tools._48H));
                    update(values);
                }
                mResponseDialog.cancel();
            }
        });
        _48h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResponseTimeLimit != Tools._48H) {
                    ContentValues values = getResponseContactValues(
                            String.valueOf(Tools._48H),
                            String.valueOf(Tools._4DAYS));
                    update(values);
                }
                mResponseDialog.cancel();
            }
        });
        _4days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResponseTimeLimit != Tools._4DAYS) {
                    ContentValues values = getResponseContactValues(
                            String.valueOf(Tools._4DAYS),
                            String.valueOf(Tools._1WEEK));
                    update(values);
                }
                mResponseDialog.cancel();
            }
        });
        _1week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResponseTimeLimit != Tools._1WEEK) {
                    ContentValues values = getResponseContactValues(
                            String.valueOf(Tools._1WEEK),
                            String.valueOf(Tools._2WEEKS));
                    update(values);
                }
                mResponseDialog.cancel();
            }
        });
        _2weeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResponseTimeLimit != Tools._4DAYS) {
                    ContentValues values = getResponseContactValues(
                            String.valueOf(Tools._2WEEKS),
                            String.valueOf(Tools._1MONTH));
                    update(values);
                }
                mResponseDialog.cancel();
            }
        });
        mResponseDialog.show();
        mResponseDialog.setContentView(listContainer);
    }


    @Override
    public void onBindViewHolder(final DetailAdapter.ViewHolder holder, final int
            position) {
        mCursor.moveToPosition(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ViewTypes.VIEW_CONTACT: {
                mContactId = mCursor.getString(ContactDAO.ContactQuery.COL_ID);
                String contactName = mCursor.getString(ContactDAO.ContactQuery.COL_ANDROID_CONTACT_NAME);
                mCallback.setTitle(Tools.toProperCase(contactName));
                mCallback.setThumbnail(
                        mCursor.getString(ContactDAO.ContactQuery.COL_THUMBNAIL),
                        mCursor.getInt(ContactDAO.ContactQuery.COL_BACKGROUND_COLOR));
                if (mContext.getResources().getInteger(R.integer.orientation) != MainActivity.W700dp_LAND)
                    mCallback.setAvatarContentDescription(contactName);
                isSatisfactionUnknown = mCursor.getInt(ContactDAO.ContactQuery.COL_SATISFACTION_UNKNOWN) == 1;
                // Creates a contact lookup Uri from contact ID and lookup_key
                final Uri androidContactUri = ContactsContract.Contacts.getLookupUri(
                        mCursor.getLong(ContactDAO.ContactQuery.COL_ANDROID_ID),
                        mCursor.getString(ContactDAO.ContactQuery.COL_ANDROID_LOOKUP_KEY));
                mCallback.setAndroidContactUri(androidContactUri);


                if (mCursor.getString(ContactDAO.ContactQuery.COL_UNFOLLOWED).equals(
                        TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE)) {
                    holder.satisfactionIcon.setBackgroundResource(R.drawable.ic_do_not_disturb_alt_black_48dp);
                    mCallback.hideFab();
                } else {
                    mEmoIconResource = mCursor.getInt(ContactDAO.ContactQuery.COL_SATISFACTION_ID);
                    holder.satisfactionIcon.setBackgroundResource(mEmoIconResource);
                    mCallback.showFab();
                }
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
                break;
            }
            case ViewTypes.VIEW_FILL_IN_RESPONSE_TIME_LIMIT: {
                String responseTimeLimit = mCursor.getString(ContactActionVectorEventDAO
                        .PeopleElligibleForFrequencyUpdateQuery.COL_EXPECTED_RESPONSE_TIME_LIMIT);
                if (responseTimeLimit == null) {
                    holder.responseTitle.setVisibility(View.INVISIBLE);
                    holder.response.setVisibility(View.INVISIBLE);
                    holder.expectedResponseTitle.setVisibility(View.VISIBLE);
                    holder.responseAloneView.setBackgroundColor(mContext.getResources().getColor(R.color.message_background));
                } else {
                    holder.responseTitle.setVisibility(View.VISIBLE);
                    holder.response.setVisibility(View.VISIBLE);
                    holder.expectedResponseTitle.setVisibility(View.INVISIBLE);
                    mResponseTimeLimit = Long.valueOf(responseTimeLimit);
                    holder.response.setText(Tools.getReadableTimeLimit(mContext, mResponseTimeLimit));
                    holder.responseAloneView.setBackgroundColor(mContext.getResources().getColor(R.color.mdtp_white));
                }
                break;
            }
            case ViewTypes.VIEW_RESPONSE_FREQUENCY: {
                Long responseTimeLimit = mCursor.getLong(ContactActionVectorEventDAO
                        .PeopleElligibleForFrequencyUpdateQuery.COL_EXPECTED_RESPONSE_TIME_LIMIT);
                holder.response.setText(Tools.getReadableTimeLimit(mContext,
                        responseTimeLimit));
                String frequencyTimeLimit = mCursor.getString(ContactActionVectorEventDAO
                        .PeopleElligibleForFrequencyUpdateQuery.COL_FREQUENCY_OF_CONTACT);
                if (frequencyTimeLimit == null) {
                    holder.frequencyTitle.setVisibility(View.GONE);
                    holder.frequency.setVisibility(View.GONE);
                    holder.expectedFrequencyTitle.setVisibility(View.VISIBLE);
                    holder.frequencyView.setBackgroundColor(mContext.getResources().getColor(R.color
                            .message_background));
                } else {
                    holder.frequencyTitle.setVisibility(View.VISIBLE);
                    holder.frequency.setVisibility(View.VISIBLE);
                    holder.expectedFrequencyTitle.setVisibility(View.GONE);
                    mFrequencyTimeLimit = Long.valueOf(frequencyTimeLimit);
                    holder.frequency.setText(Tools.getReadableTimeLimit(mContext, mFrequencyTimeLimit));
                    holder.frequencyView.setBackgroundColor(mContext.getResources().getColor(R.color
                            .mdtp_white));
                }
                break;
            }

            case ViewTypes.VIEW_TITLE: {
                int visibility = (position == 1 || position == 2 || position == 3) ? View.INVISIBLE : View.VISIBLE;
                holder.divider.setVisibility(visibility);
                holder.title.setText(mCursor.getString(MatrixCursors.TitleQuery.COL_TITLE));
                break;
            }
            case ViewTypes.VIEW_NEXT_ACTION: {
                bindActionCommonsViews((ViewHolderAction) holder);
                long dueDateLong = mCursor.getLong(
                        ContactActionVectorEventDAO.VectorActionByContactIdQuery.COL_TIME_START);
                final String mimetype = mCursor.getString(
                        ContactActionVectorEventDAO.VectorActionByContactIdQuery.COL_VECTOR_MIMETYPE);
                final String vectorData = mCursor.getString(
                        ContactActionVectorEventDAO.VectorActionByContactIdQuery.COL_VECTOR_DATA);
                final String vectorName = mCursor.getString(
                        ContactActionVectorEventDAO.VectorActionByContactIdQuery.COL_VECTOR_NAME);
                ((ViewHolderAction) holder).time.setText(DateUtils.getFriendlyDateString(mContext, dueDateLong));

                ((ViewHolderAction) holder).mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.startVector(mimetype, vectorData, vectorName);
                        mCallback.sendToFirebase(
                                mContext.getResources().getString(R.string.event_start),
                                mContext.getResources().getString(R.string.item_vector),
                                null, vectorName);
                    }
                });

                ((ViewHolderAction) holder).mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //Mark action as completed
                        int adapterPosition = holder.getAdapterPosition();
                        mCursor.moveToPosition(adapterPosition);

                        final String eventId = mCursor.getString(ContactActionVectorEventDAO
                                .VectorActionByContactIdQuery.COL_EVENT_ID);
                        MarkActionAsCompletedTask markActionAsCompletedTask = new MarkActionAsCompletedTask();
                        markActionAsCompletedTask.execute(eventId);
                        return false;
                    }
                });
                break;
            }
            case ViewTypes.VIEW_DONE_ACTION: {
                bindActionCommonsViews((ViewHolderAction) holder);
                long doneDateLong = mCursor.getLong(ContactActionVectorEventDAO.VectorActionByContactIdQuery.COL_TIME_END);
                final String mimetype = mCursor.getString(
                        ContactActionVectorEventDAO.VectorActionByContactIdQuery.COL_VECTOR_MIMETYPE);
                final String vectorData = mCursor.getString(
                        ContactActionVectorEventDAO.VectorActionByContactIdQuery.COL_VECTOR_DATA);
                final String vectorName = mCursor.getString(
                        ContactActionVectorEventDAO.VectorActionByContactIdQuery.COL_VECTOR_NAME);
                ((ViewHolderAction) holder).time.setText(DateUtils.getFriendlyDateTimeString(mContext, doneDateLong));

                ((ViewHolderAction) holder).mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.startVector(mimetype, vectorData, vectorName);
                        mCallback.sendToFirebase(mContext.getResources().getString(R.string.event_start), mContext.getResources().getString(R.string.item_vector), null, vectorName);
                    }
                });

                ((ViewHolderAction) holder).mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //Mark action as completed
                        int adapterPosition = holder.getAdapterPosition();
                        mCursor.moveToPosition(adapterPosition);

                        final String eventId = mCursor.getString(ContactActionVectorEventDAO
                                .VectorActionByContactIdQuery.COL_EVENT_ID);
                        MarkActionAsUnCompletedTask markActionAsUnCompletedTask = new
                                MarkActionAsUnCompletedTask();
                        markActionAsUnCompletedTask.execute(eventId);
                        return false;
                    }
                });
                break;
            }
            case ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE: {
                holder.message.setText(mCursor.getString(MatrixCursors.EmptyCursorMessageQuery.COL_MESSAGE));
                break;
            }
            case ViewTypes.VIEW_CONFIRM_MESSAGE: {
                holder.message.setText(mCursor.getString(MatrixCursors.ConfirmMessageQuery.COL_MESSAGE));
                holder.ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 1 && !Status.isDoneActionsAware(mContext)) {
                            Status.setDoneActionsAware(mContext, true);
                            mCallback.updateFragment();
                        } else if (position == 1 && Status.isDoneActionsAware(mContext)) {
                            Status.setDeleteActionsAware(mContext, true);
                            mCallback.updateFragment();
                        } else {
                            Status.setDeleteActionsAware(mContext, true);
                            mCallback.updateFragment();
                        }
                    }
                });
                break;
            }
        }
    }


    private void bindActionCommonsViews(ViewHolderAction holder) {
        Tools.setVectorBackground(mContext, holder.actionVectorImageView,
                mCursor.getString(ContactActionVectorEventDAO
                        .VectorActionByContactIdQuery.COL_VECTOR_MIMETYPE),
                mCursor.getString(ContactActionVectorEventDAO
                        .VectorActionByContactIdQuery.COL_VECTOR_DATA)
        );
        holder.actionVectorImageView.setContentDescription(mCursor.getString(ContactActionVectorEventDAO
                .VectorActionByContactIdQuery.COL_VECTOR_NAME));
        holder.action.setText(mContext.getString(mCursor.getInt(ContactActionVectorEventDAO
                .VectorActionByContactIdQuery.COL_ACTION_NAME_RESOURCE_ID)));
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


    private ContentValues getUnfollowedContactValues(String value) {
        ContentValues values = new ContentValues();
        values.put(TieUsContract.ContactTable._ID, mContactId);
        values.put(TieUsContract.ContactTable.COLUMN_UNFOLLOWED, value);
        return values;
    }

    private ContentValues getSatisfactionContactValues(String value) {
        ContentValues values = new ContentValues();
        values.put(TieUsContract.ContactTable._ID, mContactId);
        values.put(TieUsContract.ContactTable.COLUMN_SATISFACTION, value);
        values.put(TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE);
        values.put(TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
                TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE);
        return values;
    }

    private ContentValues getResponseContactValues(String responseTimeLimitValue,
                                                   String responseIncreasedTimeLimitValue) {
        ContentValues values = new ContentValues();
        values.put(TieUsContract.ContactTable._ID, mContactId);
        values.put(TieUsContract.ContactTable
                .COLUMN_RESPONSE_EXPECTED_TIME_LIMIT, responseTimeLimitValue);
        values.put(TieUsContract.ContactTable
                .COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT, responseIncreasedTimeLimitValue);
        return values;
    }

    private ContentValues getFrequencyContactValues(String value) {
        ContentValues values = new ContentValues();
        values.put(TieUsContract.ContactTable._ID, mContactId);
        values.put(TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT, value);
        return values;
    }


    private void update(ContentValues values) {
        UpdateContactTask updateContactTask = new UpdateContactTask();
        updateContactTask.execute(values);
    }

    private class UpdateContactTask extends AsyncTask<ContentValues, Void, Void> {

        @Override
        protected Void doInBackground(ContentValues... params) {
            ContentValues values = params[0];
            String contactId = (String) values.get(TieUsContract.ContactTable._ID);

            mContext.getContentResolver()
                    .update(TieUsContract.ContactTable.CONTENT_URI,
                            values,
                            ContactDAO.ContactQuery.SELECTION,
                            new String[]{contactId});
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mCallback.updateFragment();
        }
    }


    private class MarkActionAsCompletedTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            long now = System.currentTimeMillis();
            ContentValues values = EventDAO.getContentValues(now);
            String eventId = params[0];
            mContext.getContentResolver().update(
                    TieUsContract.EventTable.CONTENT_URI,
                    values, TieUsContract.EventTable._ID + "=?", new String[]{eventId}
            );

            values = Tools.getContentValues(
                    TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                    TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE);
            mContext.getContentResolver().update(
                    TieUsContract.ContactTable.CONTENT_URI,
                    values, TieUsContract.ContactTable._ID + "=?", new String[]{mContactId}
            );
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(mContext,
                    mContext.getResources().getString(R.string.action_completed),
                    Toast.LENGTH_SHORT).show();
            mCallback.updateFragment();
            mCallback.sendToFirebase(mContext.getResources().getString(R.string.event_completed),
                    mContext.getResources().getString(R.string.item_action), null, null);
        }
    }

    private class MarkActionAsUnCompletedTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            ContentValues values = EventDAO.getContentValues(null);
            String eventId = params[0];
            mContext.getContentResolver().update(
                    TieUsContract.EventTable.CONTENT_URI,
                    values, TieUsContract.EventTable._ID + "=?", new String[]{eventId}
            );
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(mContext,
                    mContext.getResources().getString(R.string.action_uncompleted),
                    Toast.LENGTH_LONG).show();
            mCallback.updateFragment();
            mCallback.sendToFirebase(mContext.getResources().getString(R.string.event_uncompleted),
                    mContext.getResources().getString(R.string.item_action), null, null);
        }
    }


    private class DeleteActionTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String eventId = params[0];
            mContext.getContentResolver().delete(
                    TieUsContract.EventTable.CONTENT_URI,
                    TieUsContract.EventTable._ID + "=?", new String[]{eventId}
            );
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(mContext,
                    mContext.getResources().getString(R.string.action_deleted),
                    Toast.LENGTH_SHORT).show();
            mCallback.updateFragment();
            mCallback.sendToFirebase(mContext.getResources().getString(R.string.event_delete),
                    mContext.getResources().getString(R.string.item_action), null, null);
        }
    }
}
