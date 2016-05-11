package com.elorri.android.friendforcast.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
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
import android.widget.TextView;
import android.widget.Toast;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.ContactVectorsDAO;
import com.elorri.android.friendforcast.db.EventDAO;
import com.elorri.android.friendforcast.db.MatrixCursors;
import com.elorri.android.friendforcast.db.ViewTypes;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.elorri.android.friendforcast.extra.Status;
import com.elorri.android.friendforcast.extra.Tools;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {


    private Cursor mCursor;
    private Callback mCallback;
    private Context mContext;
    private AlertDialog mAlertEmoDialog;
    private int mEmoIconResource;
    private String mContactId;

    public ItemTouchHelper.SimpleCallback simpleItemTouchCallBack = new ItemTouchHelper
            .SimpleCallback(
            ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
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
    };

    interface Callback {
        void setTitle(String title);

        void setThumbnail(String url);

        void updateFragment();
    }


    public DetailAdapter(Cursor cursor, Callback callback) {
        mCursor = cursor;
        mCallback = callback;
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView emoIcon;
        public TextView action;
        public ImageView actionVectorImageView;
        public TextView time;
        public TextView message;
        public TextView ok;


        public View divider;
        public TextView title;

        public View mView;


        public ViewHolder(View view, int viewType) {
            super(view);
            this.mView = view;

            switch (viewType) {
                case ViewTypes.VIEW_CONTACT: {
                    emoIcon = (ImageView) view.findViewById(R.id.emo_icon);
                    break;
                }
                case ViewTypes.VIEW_TITLE: {
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                    divider = view.findViewById(R.id.divider);
                    title = (TextView) view.findViewById(R.id.title);
                    break;
                }
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
                case ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE: {
                    message = (TextView) view.findViewById(R.id.message);
                    break;
                }
                case ViewTypes.VIEW_EDUCATE_MESSAGE: {
                    message = (TextView) view.findViewById(R.id.message);
                    ok = (TextView) view.findViewById(R.id.ok);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        mAlertEmoDialog = builder.create();
                        LinearLayout listContainer = (LinearLayout) View.inflate(mContext, R.layout.emoicon_list, null);
                        RelativeLayout happyItem = (RelativeLayout) listContainer.findViewById(R.id.happy_item);
                        RelativeLayout neutralItem = (RelativeLayout) listContainer.findViewById(R.id.neutral_item);
                        RelativeLayout dissatisfiedItem = (RelativeLayout) listContainer.findViewById(R.id.dissatisfied_item);
                        RelativeLayout untrackedItem = (RelativeLayout) listContainer.findViewById(R.id.untracked_item);
                        happyItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mEmoIconResource != R.drawable.ic_sentiment_satisfied_black_48dp) {
                                    update(mContactId, String.valueOf(R.drawable.ic_sentiment_satisfied_black_48dp));
                                }
                                mAlertEmoDialog.cancel();
                            }
                        });
                        neutralItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mEmoIconResource != R.drawable.ic_sentiment_neutral_black_48dp) {
                                    update(mContactId, String.valueOf(R.drawable.ic_sentiment_neutral_black_48dp));
                                }
                                mAlertEmoDialog.cancel();
                            }
                        });
                        dissatisfiedItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mEmoIconResource != R.drawable.ic_sentiment_dissatisfied_black_48dp) {
                                    update(mContactId, String.valueOf(R.drawable.ic_sentiment_dissatisfied_black_48dp));
                                }
                                mAlertEmoDialog.cancel();
                            }
                        });
                        untrackedItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mEmoIconResource != R.drawable.ic_do_not_disturb_alt_black_48dp) {
                                    update(mContactId, String.valueOf(R.drawable.ic_do_not_disturb_alt_black_48dp));
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

                    private void update(String contactId, String emoIconRessource) {
                        UpdateContactTask updateContactTask = new UpdateContactTask();
                        updateContactTask.execute(contactId, emoIconRessource);
                    }

                    class UpdateContactTask extends AsyncTask<String, Void, Void> {

                        @Override
                        protected Void doInBackground(String... params) {
                            String contactId = params[0];
                            String emoIconRessourceId = params[1];
                            Cursor cursor = mContext.getContentResolver()
                                    .query(FriendForecastContract.ContactTable.CONTENT_URI,
                                            ContactDAO.ContactQuery.PROJECTION_QUERY,
                                            ContactDAO.ContactQuery.SELECTION,
                                            new String[]{contactId},
                                            null);
                            try {
                                cursor.moveToFirst();
                                ContentValues contentvalues = ContactDAO.getContentValues(cursor);
                                contentvalues = Tools.updateContactValues(contentvalues,
                                        FriendForecastContract.ContactTable.COLUMN_MOOD,
                                        emoIconRessourceId);
                                Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                                        + contentvalues.getAsString(FriendForecastContract.ContactTable.COLUMN_MOOD));

                                mContext.getContentResolver()
                                        .update(FriendForecastContract.ContactTable.CONTENT_URI,
                                                contentvalues,
                                                ContactDAO.ContactQuery.SELECTION,
                                                new String[]{contactId});
                            } finally {
                                if (cursor != null) cursor.close();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            mCallback.updateFragment();
                        }
                    }
                });
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_CONTACT);
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
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_NEXT_ACTION);
                break;
            }
            case ViewTypes.VIEW_DONE_ACTION: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action,
                        parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_DONE_ACTION);
                break;
            }
            case ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_cursor,
                        parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE);
                break;
            }
            case ViewTypes.VIEW_EDUCATE_MESSAGE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_educate,
                        parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_EDUCATE_MESSAGE);
                break;
            }
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final DetailAdapter.ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ViewTypes.VIEW_CONTACT: {
                mContactId = mCursor.getString(ContactDAO.ContactQuery.COL_ID);
                mCallback.setTitle(Tools.toProperCase(mCursor.getString(ContactDAO.ContactQuery.COL_ANDROID_CONTACT_NAME)));
                mCallback.setThumbnail(mCursor.getString(ContactDAO.ContactQuery.COL_THUMBNAIL));
                mEmoIconResource = mCursor.getInt(ContactDAO.ContactQuery.COL_EMOICON_BY_ID);
                holder.emoIcon.setBackgroundResource(mEmoIconResource);
                break;
            }
            case ViewTypes.VIEW_TITLE: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                int visibility = position == 1 ? View.INVISIBLE : View.VISIBLE;
                holder.divider.setVisibility(visibility);
                holder.title.setText(mCursor.getString(MatrixCursors.TitleQuery.COL_TITLE));
                break;
            }
            case ViewTypes.VIEW_NEXT_ACTION: {
                bindActionCommonsViews(holder);
                long dueDateLong = mCursor.getLong(ContactActionVectorEventDAO.VectorActionByContactIdQuery.COL_TIME_START);
                holder.time.setText(DateUtils.getFriendlyDateString(mContext, dueDateLong));
                holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
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
                bindActionCommonsViews(holder);
                long doneDateLong = mCursor.getLong(ContactActionVectorEventDAO
                        .VectorActionByContactIdQuery.COL_TIME_END);
                holder.time.setText(DateUtils.getFriendlyDateTimeString(mContext, doneDateLong));

                holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
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
            case ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE: {//TODO remove this empty cursor
                holder.message.setText(mCursor.getString(ContactVectorsDAO.ContactVectorsQuery.COL_ID));
                break;
            }
            case ViewTypes.VIEW_EDUCATE_MESSAGE: {
                holder.message.setText(mCursor.getString(MatrixCursors.EducateMessageQuery.COL_MESSAGE));
                holder.ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Status.setMarkActionFeatureStatus(mContext, true);
                        mCallback.updateFragment();
                    }
                });
                break;
            }
        }
    }

    private void bindActionCommonsViews(ViewHolder holder) {
        Tools.setVectorBackground(mContext, holder.actionVectorImageView,
                mCursor.getString(ContactActionVectorEventDAO
                        .VectorActionByContactIdQuery.COL_VECTOR_MIMETYPE),
                mCursor.getString(ContactActionVectorEventDAO
                        .VectorActionByContactIdQuery.COL_VECTOR_LOGO_ID)
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
