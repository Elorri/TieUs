package com.elorri.android.tieus.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.data.FriendForecastContract;
import com.elorri.android.tieus.db.ContactActionVectorEventDAO;
import com.elorri.android.tieus.db.ContactDAO;
import com.elorri.android.tieus.db.EventDAO;
import com.elorri.android.tieus.db.MatrixCursors;
import com.elorri.android.tieus.db.ViewTypes;
import com.elorri.android.tieus.extra.DateUtils;
import com.elorri.android.tieus.extra.Status;
import com.elorri.android.tieus.extra.Tools;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {


    private Cursor mCursor;
    private Callback mCallback;
    private Context mContext;
    private AlertDialog mAlertEmoDialog;
    private AlertDialog mFeedbackDialog;
    private AlertDialog mFeedBackFrequencyDialog;
    private long mFeedBackDelay;
    private long mFrequencyDelay;
    private int mEmoIconResource;
    private String mContactId;

    public ItemTouchHelper.SimpleCallback simpleItemTouchCallBack =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                    int position = viewHolder.getAdapterPosition();
                    int viewType = getItemViewType(position);
                    if ((viewType == ViewTypes.VIEW_NEXT_ACTION) || (viewType == ViewTypes
                            .VIEW_DONE_ACTION)) {
                        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
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
    private boolean isMoodUnknown;


    interface Callback {
        void setTitle(String title);

        void setThumbnail(String url, int color);

        void setAndroidContactUri(Uri uri);

        void updateFragment();

        void hideFab();

        void showFab();
    }


    public DetailAdapter(Cursor cursor, Callback callback) {
        mCursor = cursor;
        mCallback = callback;
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView moodUnknown;
        public ImageView moodIcon;
        public TextView action;
        public ImageView actionVectorImageView;
        public TextView time;
        public TextView message;
        public TextView ok;
        public TextView feedback;
        public TextView feedbackTitle;
        public TextView expectedFeedbackTitle;
        public TextView frequency;
        public TextView frequencyTitle;
        public TextView expectedFrequencyTitle;
        public RelativeLayout feedbackAloneView;
        public LinearLayout feedbackView;
        public LinearLayout frequencyView;


        public View divider;
        public TextView title;

        public View mView;


        public ViewHolder(View view, int viewType) {
            super(view);
            this.mView = view;

            switch (viewType) {
                case ViewTypes.VIEW_CONTACT: {
                    moodIcon = (ImageView) view.findViewById(R.id.moodIcon);
                    moodUnknown = (TextView) view.findViewById(R.id.unknown_mood);
                    break;
                }
                case ViewTypes.VIEW_FILL_IN_DELAY_FEEDBACK: {
                    feedback = (TextView) view.findViewById(R.id.feedback);
                    feedbackTitle = (TextView) view.findViewById(R.id.feedback_title);
                    expectedFeedbackTitle = (TextView) view.findViewById(R.id.expected_feedback_title);
                    feedbackAloneView = (RelativeLayout) view.findViewById(R.id.feedback_alone_view);
                    break;
                }
                case ViewTypes.VIEW_FEEDBACK_FREQUENCY: {
                    feedbackView = (LinearLayout) view.findViewById(R.id.feedback_view);
                    feedback = (TextView) view.findViewById(R.id.feedback);
                    frequency = (TextView) view.findViewById(R.id.frequency);
                    frequencyTitle = (TextView) view.findViewById(R.id.frequency_title);
                    expectedFrequencyTitle = (TextView) view.findViewById(R.id.expected_frequency_title);
                    frequencyView = (LinearLayout) view.findViewById(R.id.frequency_view);
                    break;
                }
                case ViewTypes.VIEW_TITLE: {
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emoicon,
                        parent, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openMoodDialog();
                    }
                });
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_CONTACT);
                break;
            }
            case ViewTypes.VIEW_FILL_IN_DELAY_FEEDBACK: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback_alone,
                        parent, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFeedbackDialog();
                    }
                });
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_FILL_IN_DELAY_FEEDBACK);
                break;
            }
            case ViewTypes.VIEW_FEEDBACK_FREQUENCY: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback_frequency,
                        parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_FEEDBACK_FREQUENCY);
                viewHolder.feedbackView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFeedbackDialog();
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
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
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
        mFeedBackFrequencyDialog = builder.create();
        ScrollView listContainer = (ScrollView) View.inflate(mContext, R.layout.frequency_list,
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
                if (mFrequencyDelay != Tools._24H) {
                    ContentValues values = getFrequencyContactValues(
                            String.valueOf(Tools._24H));
                    update(values);
                }
                mFeedBackFrequencyDialog.cancel();
            }
        });
        everyWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFrequencyDelay != Tools._1WEEK) {
                    ContentValues values = getFrequencyContactValues(
                            String.valueOf(Tools._1WEEK));
                    update(values);
                }
                mFeedBackFrequencyDialog.cancel();
            }
        });
        every2weeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFrequencyDelay != Tools._2WEEKS) {
                    ContentValues values = getFrequencyContactValues(
                            String.valueOf(Tools._2WEEKS));
                    update(values);
                }
                mFeedBackFrequencyDialog.cancel();
            }
        });
        everyMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFrequencyDelay != Tools._1MONTH) {
                    ContentValues values = getFrequencyContactValues(
                            String.valueOf(Tools._1MONTH));
                    update(values);
                }
                mFeedBackFrequencyDialog.cancel();
            }
        });
        every3months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFrequencyDelay != Tools._3MONTHS) {
                    ContentValues values = getFrequencyContactValues(
                            String.valueOf(Tools._3MONTHS));
                    update(values);
                }
                mFeedBackFrequencyDialog.cancel();
            }
        });
        every6months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFrequencyDelay != Tools._6MONTHS) {
                    ContentValues values = getFrequencyContactValues(
                            String.valueOf(Tools._6MONTHS));
                    update(values);
                }
                mFeedBackFrequencyDialog.cancel();
            }
        });
        everyYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFrequencyDelay != Tools._1YEAR) {
                    ContentValues values = getFrequencyContactValues(
                            String.valueOf(Tools._1YEAR));
                    update(values);
                }
                mFeedBackFrequencyDialog.cancel();
            }
        });
        //Must be done before mAlertDialog.show() Let you customize only the main
        // content, not the title and button
        //mAlertDialog.setView(listContainer);
        mFeedBackFrequencyDialog.show();
        //Must be done after mAlertDialog.show() Let you customize everything including
        // title and buttons.
        mFeedBackFrequencyDialog.setContentView(listContainer);
    }

    private void openMoodDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        mAlertEmoDialog = builder.create();
        LinearLayout listContainer = (LinearLayout) View.inflate(mContext, R.layout.mood_list, null);
        RelativeLayout happyItem = (RelativeLayout) listContainer.findViewById(R.id.happy_item);
        RelativeLayout neutralItem = (RelativeLayout) listContainer.findViewById(R.id.neutral_item);
        RelativeLayout dissatisfiedItem = (RelativeLayout) listContainer.findViewById(R.id.dissatisfied_item);
        RelativeLayout untrackedItem = (RelativeLayout) listContainer.findViewById(R.id.untracked_item);
        happyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmoIconResource != R.drawable.ic_sentiment_satisfied_black_48dp
                        || (mEmoIconResource == R.drawable
                        .ic_sentiment_satisfied_black_48dp && isMoodUnknown)) {
                    ContentValues values = getMoodContactValues(
                            String.valueOf(R.drawable.ic_sentiment_satisfied_black_48dp));
                    update(values);
                }
                mAlertEmoDialog.cancel();
            }

        });
        neutralItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmoIconResource != R.drawable.ic_sentiment_neutral_black_48dp
                        || mEmoIconResource == R.drawable.ic_sentiment_neutral_black_48dp && isMoodUnknown) {
                    ContentValues values = getMoodContactValues(
                            String.valueOf(R.drawable.ic_sentiment_neutral_black_48dp));
                    update(values);
                }
                mAlertEmoDialog.cancel();
            }
        });
        dissatisfiedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmoIconResource != R.drawable.ic_sentiment_dissatisfied_black_48dp
                        || mEmoIconResource == R.drawable.ic_sentiment_dissatisfied_black_48dp && isMoodUnknown) {
                    ContentValues values = getMoodContactValues(
                            String.valueOf(R.drawable.ic_sentiment_dissatisfied_black_48dp));
                    update(values);
                }
                mAlertEmoDialog.cancel();
            }
        });
        untrackedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmoIconResource != R.drawable.ic_do_not_disturb_alt_black_48dp
                        || mEmoIconResource == R.drawable.ic_do_not_disturb_alt_black_48dp && isMoodUnknown) {
                    ContentValues values = getUntrackedContactValues(
                            String.valueOf(FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE));
                    update(values);
                }
                mAlertEmoDialog.cancel();
            }
        });
        //Must be done before mAlertDialog.show() Let you customize only the main
        // content, not the title and button
        //mAlertDialog.setView(listContainer);
        mAlertEmoDialog.show();
        //Must be done after mAlertDialog.show() Let you customize everything including
        // title and buttons.
        mAlertEmoDialog.setContentView(listContainer);
    }

    private void openFeedbackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        mFeedbackDialog = builder.create();
        LinearLayout listContainer = (LinearLayout) View.inflate(mContext, R.layout.feedback_list, null);
        TextView _24h = (TextView) listContainer.findViewById(R.id._24h);
        TextView _48h = (TextView) listContainer.findViewById(R.id._48h);
        TextView _4days = (TextView) listContainer.findViewById(R.id._4days);
        TextView _1week = (TextView) listContainer.findViewById(R.id._1week);
        TextView _2weeks = (TextView) listContainer.findViewById(R.id._2weeks);

        _24h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFeedBackDelay != Tools._24H) {
                    ContentValues values = getFeedbackContactValues(
                            String.valueOf(Tools._24H),
                            String.valueOf(Tools._48H));
                    update(values);
                }
                mFeedbackDialog.cancel();
            }
        });
        _48h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFeedBackDelay != Tools._48H) {
                    ContentValues values = getFeedbackContactValues(
                            String.valueOf(Tools._48H),
                            String.valueOf(Tools._4DAYS));
                    update(values);
                }
                mFeedbackDialog.cancel();
            }
        });
        _4days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFeedBackDelay != Tools._4DAYS) {
                    ContentValues values = getFeedbackContactValues(
                            String.valueOf(Tools._4DAYS),
                            String.valueOf(Tools._1WEEK));
                    update(values);
                }
                mFeedbackDialog.cancel();
            }
        });
        _1week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFeedBackDelay != Tools._1WEEK) {
                    ContentValues values = getFeedbackContactValues(
                            String.valueOf(Tools._1WEEK),
                            String.valueOf(Tools._2WEEKS));
                    update(values);
                }
                mFeedbackDialog.cancel();
            }
        });
        _2weeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFeedBackDelay != Tools._4DAYS) {
                    ContentValues values = getFeedbackContactValues(
                            String.valueOf(Tools._2WEEKS),
                            String.valueOf(Tools._1MONTH));
                    update(values);
                }
                mFeedbackDialog.cancel();
            }
        });
        //Must be done before mAlertDialog.show() Let you customize only the main
        // content, not the title and button
        //mAlertDialog.setView(listContainer);
        mFeedbackDialog.show();
        //Must be done after mAlertDialog.show() Let you customize everything including
        // title and buttons.
        mFeedbackDialog.setContentView(listContainer);
    }


    @Override
    public void onBindViewHolder(final DetailAdapter.ViewHolder holder, final int position) {
        mCursor.moveToPosition(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ViewTypes.VIEW_CONTACT: {
                mContactId = mCursor.getString(ContactDAO.ContactQuery.COL_ID);
                mCallback.setTitle(Tools.toProperCase(mCursor.getString(ContactDAO.ContactQuery.COL_ANDROID_CONTACT_NAME)));
                mCallback.setThumbnail(
                        mCursor.getString(ContactDAO.ContactQuery.COL_THUMBNAIL),
                        mCursor.getInt(ContactDAO.ContactQuery.COL_BACKGROUND_COLOR));
                isMoodUnknown = mCursor.getInt(ContactDAO.ContactQuery.COL_MOOD_UNKNOWN) == 1;
                // Creates a contact lookup Uri from contact ID and lookup_key
                final Uri androidContactUri = ContactsContract.Contacts.getLookupUri(
                        mCursor.getLong(ContactDAO.ContactQuery.COL_ANDROID_ID),
                        mCursor.getString(ContactDAO.ContactQuery.COL_ANDROID_LOOKUP_KEY));
                mCallback.setAndroidContactUri(androidContactUri);


                if (mCursor.getString(ContactDAO.ContactQuery.COL_UNTRACKED).equals(
                        FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE)) {
                    holder.moodIcon.setBackgroundResource(R.drawable.ic_do_not_disturb_alt_black_48dp);
                    mCallback.hideFab();
                } else {
                    mEmoIconResource = mCursor.getInt(ContactDAO.ContactQuery.COL_MOOD_ID);
                    holder.moodIcon.setBackgroundResource(mEmoIconResource);
                    mCallback.showFab();
                }
                boolean isMoodKnown = mCursor.getString(
                        ContactActionVectorEventDAO.PeopleQuery.COL_MOOD_UNKNOWN).equals(
                        FriendForecastContract.ContactTable.MOOD_UNKNOWN_ON_VALUE);
                boolean isUntracked = mCursor.getString(
                        ContactActionVectorEventDAO.PeopleQuery.COL_UNTRACKED).equals(
                        FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE);
                if (isMoodKnown && !isUntracked)
                    holder.moodUnknown.setVisibility(View.VISIBLE);
                else
                    holder.moodUnknown.setVisibility(View.INVISIBLE);
                break;
            }
            case ViewTypes.VIEW_FILL_IN_DELAY_FEEDBACK: {
                String feedBackDelay = mCursor.getString(ContactActionVectorEventDAO
                        .PeopleElligibleForFrequencyUpdateQuery.COL_FEEDBACK_EXPECTED_DELAY);
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "mFeedBackDelay " + mFeedBackDelay);
                if (feedBackDelay == null) {
                    holder.feedbackTitle.setVisibility(View.INVISIBLE);
                    holder.feedback.setVisibility(View.INVISIBLE);
                    holder.expectedFeedbackTitle.setVisibility(View.VISIBLE);
                    holder.feedbackAloneView.setBackgroundColor(mContext.getResources().getColor(R.color.message_background));
                } else {
                    holder.feedbackTitle.setVisibility(View.VISIBLE);
                    holder.feedback.setVisibility(View.VISIBLE);
                    holder.expectedFeedbackTitle.setVisibility(View.INVISIBLE);
                    mFeedBackDelay = Long.valueOf(feedBackDelay);
                    holder.feedback.setText(Tools.getReadableDelay(mContext, mFeedBackDelay));
                    holder.feedbackAloneView.setBackgroundColor(mContext.getResources().getColor(R.color.mdtp_white));
                }
                break;
            }
            case ViewTypes.VIEW_FEEDBACK_FREQUENCY: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                Long feedBackDelay = mCursor.getLong(ContactActionVectorEventDAO
                        .PeopleElligibleForFrequencyUpdateQuery.COL_FEEDBACK_EXPECTED_DELAY);
                holder.feedback.setText(Tools.getReadableDelay(mContext,
                        feedBackDelay));
                String frequencyDelay = mCursor.getString(ContactActionVectorEventDAO
                        .PeopleElligibleForFrequencyUpdateQuery.COL_FREQUENCY_OF_CONTACT);
                if (frequencyDelay == null) {
                    holder.frequencyTitle.setVisibility(View.GONE);
                    holder.frequency.setVisibility(View.GONE);
                    holder.expectedFrequencyTitle.setVisibility(View.VISIBLE);
                    holder.frequencyView.setBackgroundColor(mContext.getResources().getColor(R.color
                            .message_background));
                } else {
                    holder.frequencyTitle.setVisibility(View.VISIBLE);
                    holder.frequency.setVisibility(View.VISIBLE);
                    holder.expectedFrequencyTitle.setVisibility(View.GONE);
                    mFrequencyDelay = Long.valueOf(frequencyDelay);
                    holder.frequency.setText(Tools.getReadableDelay(mContext, mFrequencyDelay));
                    holder.frequencyView.setBackgroundColor(mContext.getResources().getColor(R.color
                            .mdtp_white));
                }
                break;
            }

            case ViewTypes.VIEW_TITLE: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
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
                        startVector(mContext, mimetype, vectorData, vectorName);
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
                final String mimetype = mCursor.getString(ContactActionVectorEventDAO.VectorActionByContactIdQuery.COL_VECTOR_MIMETYPE);
                final String vectorData = mCursor.getString(ContactActionVectorEventDAO.VectorActionByContactIdQuery.COL_VECTOR_DATA);
                final String vectorName = mCursor.getString(ContactActionVectorEventDAO.VectorActionByContactIdQuery.COL_VECTOR_NAME);
                ((ViewHolderAction) holder).time.setText(DateUtils.getFriendlyDateTimeString(mContext, doneDateLong));

                ((ViewHolderAction) holder).mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startVector(mContext, mimetype, vectorData, vectorName);
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

    private void startVector(Context context, String mimetype, String vectorData, String
            vectorName) {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + vectorData + " " + R.drawable.ic_meeting_24dp);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + vectorData + " " + R.drawable.ic_textsms_black_24dp);
        if (mimetype.equals(FriendForecastContract.VectorTable.MIMETYPE_VALUE_RESSOURCE)) {
            if (Long.valueOf(vectorData) == R.drawable.ic_meeting_24dp) {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + vectorData + " " + R.drawable.ic_meeting_24dp);
                Toast.makeText(context, context.getResources().getString(
                                R.string.outside_event, vectorName),
                        Toast.LENGTH_SHORT).show();
                return;
            } else if (Long.valueOf(vectorData) == R.drawable.ic_textsms_black_24dp) {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + vectorData + " " + R.drawable.ic_textsms_black_24dp);
                Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                //TODO add phone nummber
//                smsIntent.putExtra("address", "0646632699");
//                smsIntent.putExtra("sms_body", "message");
                context.startActivity(smsIntent);
            }
        } else if (vectorData.equals(context.getResources().getString(R.string.vector_package_phone))) {
            Intent phoneIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            //TODO add real phone number here
//            Intent callIntent = new Intent(Intent.ACTION_CALL);
//            callIntent.setData(Uri.parse("tel:0377778888"));
            context.startActivity(phoneIntent);
        } else {
            Tools.launchExternalApp(context, vectorData, vectorName);
        }
    }

    private void bindActionCommonsViews(ViewHolderAction holder) {
        Tools.setVectorBackground(mContext, holder.actionVectorImageView,
                mCursor.getString(ContactActionVectorEventDAO
                        .VectorActionByContactIdQuery.COL_VECTOR_MIMETYPE),
                mCursor.getString(ContactActionVectorEventDAO
                        .VectorActionByContactIdQuery.COL_VECTOR_DATA)
        );
        holder.action.setText(mCursor.getString(ContactActionVectorEventDAO
                .VectorActionByContactIdQuery.COL_ACTION_NAME));
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
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "position " + position);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "viewtype " + viewtype);
        return viewtype;
    }


    private ContentValues getUntrackedContactValues(String value) {
        ContentValues values = new ContentValues();
        values.put(FriendForecastContract.ContactTable._ID, mContactId);
        values.put(FriendForecastContract.ContactTable.COLUMN_UNTRACKED, value);
        return values;
    }

    private ContentValues getMoodContactValues(String value) {
        ContentValues values = new ContentValues();
        values.put(FriendForecastContract.ContactTable._ID, mContactId);
        values.put(FriendForecastContract.ContactTable.COLUMN_MOOD, value);
        values.put(FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN,
                FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE);
        values.put(FriendForecastContract.ContactTable.COLUMN_UNTRACKED,
                FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE);
        return values;
    }

    private ContentValues getFeedbackContactValues(String feedbackValue,
                                                   String feedbackIncreasedValue) {
        ContentValues values = new ContentValues();
        values.put(FriendForecastContract.ContactTable._ID, mContactId);
        values.put(FriendForecastContract.ContactTable
                .COLUMN_FEEDBACK_EXPECTED_DELAY, feedbackValue);
        values.put(FriendForecastContract.ContactTable
                .COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY, feedbackIncreasedValue);
        return values;
    }

    private ContentValues getFrequencyContactValues(String value) {
        ContentValues values = new ContentValues();
        values.put(FriendForecastContract.ContactTable._ID, mContactId);
        values.put(FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT, value);
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
            String contactId = (String) values.get(FriendForecastContract.ContactTable._ID);

            mContext.getContentResolver()
                    .update(FriendForecastContract.ContactTable.CONTENT_URI,
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
                    FriendForecastContract.EventTable.CONTENT_URI,
                    values, FriendForecastContract.EventTable._ID + "=?", new String[]{eventId}
            );

            values = Tools.getContentValues(
                    FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN,
                    FriendForecastContract.ContactTable.MOOD_UNKNOWN_ON_VALUE);
            mContext.getContentResolver().update(
                    FriendForecastContract.ContactTable.CONTENT_URI,
                    values, FriendForecastContract.ContactTable._ID + "=?", new String[]{mContactId}
            );
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(mContext,
                    mContext.getResources().getString(R.string.action_completed),
                    Toast.LENGTH_SHORT).show();
            mCallback.updateFragment();
        }
    }

    private class MarkActionAsUnCompletedTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            ContentValues values = EventDAO.getContentValues(null);
            String eventId = params[0];
            mContext.getContentResolver().update(
                    FriendForecastContract.EventTable.CONTENT_URI,
                    values, FriendForecastContract.EventTable._ID + "=?", new String[]{eventId}
            );
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(mContext,
                    mContext.getResources().getString(R.string.action_uncompleted),
                    Toast.LENGTH_LONG).show();
            mCallback.updateFragment();
        }
    }


    private class DeleteActionTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String eventId = params[0];
            mContext.getContentResolver().delete(
                    FriendForecastContract.EventTable.CONTENT_URI,
                    FriendForecastContract.EventTable._ID + "=?", new String[]{eventId}
            );
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(mContext,
                    mContext.getResources().getString(R.string.action_deleted),
                    Toast.LENGTH_SHORT).show();
            mCallback.updateFragment();
        }
    }
}