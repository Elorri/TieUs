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
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.MatrixCursors;
import com.elorri.android.friendforcast.db.ViewTypes;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.elorri.android.friendforcast.extra.Tools;
import com.elorri.android.friendforcast.ui.AvatarView;

/**
 * Created by Elorri on 12/04/2016.
 */
public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {


    private Cursor mCursor;
    private Callback mCallback;
    private Context mContext;
    private int mPosition;


    public BoardAdapter(Cursor cursor, Callback callback) {
        mCursor = cursor;
        mCallback = callback;
    }

    public int getSelectedItemPosition() {
        return mPosition;
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
        public ImageView moodIcon;
        public TextView message;
        public TextView emptyCursorMessage;

        public View mView;


        public ViewHolder(View view, int viewType) {
            super(view);
            this.mView = view;
            avatar = (AvatarView) view.findViewById(R.id.avatar);
            contactName = (TextView) view.findViewById(R.id.title);
            moodIcon = (ImageView) view.findViewById(R.id.mood_icon);

            switch (viewType) {
                case ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE: {
                    emptyCursorMessage = (TextView) view.findViewById(R.id.message);
                    break;
                }
                case ViewTypes.VIEW_MESSAGE: {
                    message = (TextView) view.findViewById(R.id.message);
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
                int visibility = position == 2 ? View.INVISIBLE : View.VISIBLE;
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
            case ViewTypes.VIEW_DELAY_PEOPLE: {
                bindCommonViews(holder);
                Tools.setVectorBackground(mContext, holder.vectorIcon,
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_MIMETYPE),
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_DATA));
                holder.action.setText(mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_ACTION));
                long dueDate = mCursor.getLong(ContactActionVectorEventDAO.DelayPeopleQuery.COL_TIME_START);
                holder.dueDate.setText(DateUtils.getFriendlyDateString(mContext, dueDate));
                holder.moodIcon.setBackgroundResource(mCursor.getInt(ContactActionVectorEventDAO
                        .DelayPeopleQuery.COL_EMOICON_ID));
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_TODAY_PEOPLE: {
                bindCommonViews(holder);
                Tools.setVectorBackground(mContext, holder.vectorIcon,
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_MIMETYPE),
                        mCursor.getString(ContactActionVectorEventDAO.DelayPeopleQuery.COL_VECTOR_DATA));
                holder.action.setText(mCursor.getString(ContactActionVectorEventDAO.TodayPeopleQuery.COL_ACTION));
                long dueDate = mCursor.getLong(ContactActionVectorEventDAO.TodayPeopleQuery.COL_TIME_START);
                holder.dueDate.setText(DateUtils.getFriendlyDateString(mContext, dueDate));
                holder.moodIcon.setBackgroundResource(mCursor.getInt(ContactActionVectorEventDAO
                        .TodayPeopleQuery.COL_EMOICON_ID));
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
                holder.moodIcon.setBackgroundResource(mCursor.getInt(ContactActionVectorEventDAO
                        .TodayDonePeopleQuery.COL_EMOICON_ID));
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
                holder.moodIcon.setBackgroundResource(mCursor.getInt(ContactActionVectorEventDAO
                        .NextPeopleQuery.COL_EMOICON_ID));
                setOnClickListener(holder);
                break;
            }
            case ViewTypes.VIEW_UNTRACKED_PEOPLE: {
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
        holder.moodIcon.setBackgroundResource(mCursor.getInt(ContactActionVectorEventDAO
                .PeopleQuery.COL_EMOICON_ID));
    }

    private void setOnClickListener(final ViewHolder holder) {
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = holder.getAdapterPosition();
                mCursor.moveToPosition(mPosition);
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
        mCursor.moveToPosition(position);
        int viewtype = mCursor.getInt(mCursor.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE));
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "position " + position);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "viewtype " + viewtype);
        return viewtype;
    }
}
