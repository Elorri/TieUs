package com.elorri.android.tieus.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.data.FriendForecastContract;
import com.elorri.android.tieus.db.ContactActionVectorEventDAO;
import com.elorri.android.tieus.db.ContactDAO;
import com.elorri.android.tieus.db.MatrixCursors;
import com.elorri.android.tieus.db.ViewTypes;
import com.elorri.android.tieus.extra.DateUtils;
import com.elorri.android.tieus.extra.Status;
import com.elorri.android.tieus.extra.Tools;
import com.elorri.android.tieus.ui.AvatarView;

/**
 * Created by Elorri on 12/04/2016.
 */
public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {


    private Cursor mCursor;
    private Callback mCallback;
    private Context mContext;
    private int mPosition = RecyclerView.NO_POSITION;
    private ItemChoiceManager mItemChoiceManager;
    private String ADAPTER_POSITION = "adapter_position";


    public BoardAdapter(Cursor cursor, Callback callback, int choiceMode) {
        mCursor = cursor;
        mCallback = callback;
        mItemChoiceManager = new ItemChoiceManager(this);
        mItemChoiceManager.setChoiceMode(choiceMode);
    }

    public int getSelectedItemPosition(Context context) {
//        if (context.getResources().getInteger(R.integer.orientation) == MainActivity.W700dp_LAND)
//            return mItemChoiceManager.getSelectedItemPosition();
        return mPosition;
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public void selectView(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof BoardAdapter.ViewHolder) {
            BoardAdapter.ViewHolder vh = (BoardAdapter.ViewHolder) viewHolder;
            // vh.mView.performClick();
            Log.e("FF", Thread.currentThread().getStackTrace()[2] + " " + vh.mView + " " + vh.itemView);
            //This works too
            vh.itemView.performClick();
        }
    }

    public void onRestoreInstanceState(Context context, Bundle savedInstanceState) {
//        if (context.getResources().getInteger(R.integer.orientation) == MainActivity.W700dp_LAND)
//            mItemChoiceManager.onRestoreInstanceState(savedInstanceState);
//        else
            mPosition = savedInstanceState.getInt(ADAPTER_POSITION, RecyclerView.NO_POSITION);
    }

    public void onSaveInstanceState(Context context, Bundle outState) {
//        if (context.getResources().getInteger(R.integer.orientation) == MainActivity.W700dp_LAND)
//            mItemChoiceManager.onSaveInstanceState(outState);
//        else
            outState.putInt(ADAPTER_POSITION, mPosition);
    }


    interface Callback {
        void onContactClicked(Uri uri);

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
        public ImageView moodIcon;
        public TextView moodUnknown;
        public TextView message;
        public TextView emptyCursorMessage;
        public TextView ok;

        public View mView;


        public ViewHolder(View view, int viewType) {
            super(view);
            this.mView = view;
            avatar = (AvatarView) view.findViewById(R.id.avatar);
            contactName = (TextView) view.findViewById(R.id.title);
            moodIcon = (ImageView) view.findViewById(R.id.moodIcon);
            moodUnknown = (TextView) view.findViewById(R.id.unknown_mood);

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
                case ViewTypes.VIEW_DELAY_PEOPLE: {
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
    public BoardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
            case ViewTypes.VIEW_UNMANAGED_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_UNMANAGED_PEOPLE);
                break;
            }
            case ViewTypes.VIEW_FILL_IN_DELAY_FEEDBACK: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_FILL_IN_DELAY_FEEDBACK);
                break;
            }
            case ViewTypes.VIEW_UPDATE_MOOD: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_UPDATE_MOOD);
                break;
            }
            case ViewTypes.VIEW_SET_UP_A_FREQUENCY_OF_CONTACT: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_SET_UP_A_FREQUENCY_OF_CONTACT);
                break;
            }
            case ViewTypes.VIEW_ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK);
                break;
            }
            case ViewTypes.VIEW_APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY);
                break;
            }
            case ViewTypes.VIEW_NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY);
                break;
            }
            case ViewTypes.VIEW_CONFIRM_MESSAGE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_confirm_message, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_CONFIRM_MESSAGE);
                break;
            }
            case ViewTypes.VIEW_DELAY_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_undone_event, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_DELAY_PEOPLE);
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
            case ViewTypes.VIEW_UNTRACKED_PEOPLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_basic_people, parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_UNTRACKED_PEOPLE);
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
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "ratio "
                        + mCursor.getString(ContactDAO.RatioQuery.COL_RATIO));
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
            case ViewTypes.VIEW_UNMANAGED_PEOPLE: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_FILL_IN_DELAY_FEEDBACK: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_UPDATE_MOOD: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_SET_UP_A_FREQUENCY_OF_CONTACT: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY: {
                bindCommonViews(holder);
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_CONFIRM_MESSAGE: {
                holder.message.setText(mCursor.getString(MatrixCursors.ConfirmMessageQuery.COL_MESSAGE));
                holder.ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Status.getLastMessageIdx(mContext) == Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK)
                            Status.setLastMessageIdxUI(mContext, Status.APPROCHING_DEAD_LINE);
                        else if (Status.getLastMessageIdx(mContext) == Status.APPROCHING_DEAD_LINE)
                            Status.setLastMessageIdxUI(mContext, Status.NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY);
                        else if (Status.getLastMessageIdx(mContext) == Status.NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY)
                            Status.setLastMessageIdxUI(mContext, Status.NOTHING_TO_SAY);
                        else
                            Status.setLastMessageIdxUI(mContext, Status.MANAGE_UNMANAGED_PEOPLE);
                        mCallback.restartLoader();
                    }
                });
                break;
            }
            case ViewTypes.VIEW_DELAY_PEOPLE: {
                bindCommonViews(holder);
                Tools.setVectorBackground(mContext, holder.vectorIcon,
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_MIMETYPE),
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_DATA));
                holder.action.setText(mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_ACTION));
                long dueDate = mCursor.getLong(ContactActionVectorEventDAO.DelayPeopleQuery.COL_TIME_START);
                holder.dueDate.setText(DateUtils.getFriendlyDateString(mContext, dueDate));
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_TODAY_PEOPLE: {
                bindCommonViews(holder);
                Tools.setVectorBackground(mContext, holder.vectorIcon,
                        mCursor.getString(ContactActionVectorEventDAO.TodayPeopleQuery.COL_VECTOR_MIMETYPE),
                        mCursor.getString(ContactActionVectorEventDAO.TodayPeopleQuery.COL_VECTOR_DATA));
                holder.action.setText(mCursor.getString(ContactActionVectorEventDAO.TodayPeopleQuery.COL_ACTION));
                long dueDate = mCursor.getLong(ContactActionVectorEventDAO.TodayPeopleQuery.COL_TIME_START);
                holder.dueDate.setText(DateUtils.getFriendlyDateString(mContext, dueDate));
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_TODAY_DONE_PEOPLE: {
                bindCommonViews(holder);
                Tools.setVectorBackground(mContext, holder.vectorIcon,
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_MIMETYPE),
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_DATA));
                holder.action.setText(mCursor.getString(ContactActionVectorEventDAO.TodayDonePeopleQuery.COL_ACTION));
                long doneDate = mCursor.getLong(ContactActionVectorEventDAO.TodayDonePeopleQuery
                        .COL_TIME_END);
                holder.doneDate.setText(DateUtils.getFriendlyDateTimeString(mContext, doneDate));
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_NEXT_PEOPLE: {
                bindCommonViews(holder);
                Tools.setVectorBackground(mContext, holder.vectorIcon,
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_MIMETYPE),
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_DATA));
                holder.action.setText(mCursor.getString(ContactActionVectorEventDAO.NextPeopleQuery.COL_ACTION));
                long dueDate = mCursor.getLong(ContactActionVectorEventDAO.NextPeopleQuery.COL_TIME_START);
                holder.dueDate.setText(DateUtils.getFriendlyDateString(mContext, dueDate));
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_UNTRACKED_PEOPLE: {
                bindCommonViews(holder, R.drawable.ic_do_not_disturb_alt_black_48dp);
                setOnClickListener(holder);
                break;
            }
        }
    }

    private void bindCommonViews(ViewHolder holder) {
        bindCommonViews(holder, mCursor.getInt(ContactActionVectorEventDAO
                .PeopleQuery.COL_MOOD_ID));
    }

    private void bindCommonViews(ViewHolder holder, int moodResId) {
        holder.avatar.loadImage(
                mCursor.getString(ContactActionVectorEventDAO.PeopleQuery.COL_THUMBNAIL),
                mCursor.getInt(ContactActionVectorEventDAO.PeopleQuery.COL_BACKGROUND_COLOR));
        holder.contactName.setText(Tools.toProperCase(mCursor.getString(ContactActionVectorEventDAO
                .PeopleQuery.COL_CONTACT_NAME)));
        holder.moodIcon.setBackgroundResource(moodResId);
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

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "holder.getAdapterPosition() " +
                "" + holder.getAdapterPosition());
        mItemChoiceManager.onBindViewHolder(holder, holder.getAdapterPosition());
    }

    private void setOnClickListener(final ViewHolder holder) {
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = holder.getAdapterPosition();
                mCursor.moveToPosition(mPosition);
                int contactId = mCursor.getInt(ContactActionVectorEventDAO.PeopleQuery.COL_ID);
                Uri uri = FriendForecastContract.DetailData.buildDetailUri(contactId);
                mCallback.onContactClicked(uri);
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
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "position " + position);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "viewtype " + viewtype);
        return viewtype;
    }


}
