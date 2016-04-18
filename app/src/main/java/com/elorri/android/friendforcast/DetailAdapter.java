package com.elorri.android.friendforcast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ContactActionEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.elorri.android.friendforcast.extra.Tools;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    public static final int VIEW_EMOICON = 0;
    public static final int VIEW_ACTION = 1;
    public static int[] viewTypes;

    private Cursor mCursor;
    private Callback mCallback;
    private Context mContext;
    private AlertDialog mAlertEmoDialog;
    private int mEmoIconRessource;
    private String mContactId;

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
        public TextView timeStart;

        public View mView;


        public ViewHolder(View view, int viewType) {
            super(view);
            this.mView = view;

            switch (viewType) {
                case VIEW_EMOICON: {
                    emoIcon = (ImageView) view.findViewById(R.id.emo_icon);
                    break;
                }
                case VIEW_ACTION: {
                    action = (TextView) view.findViewById(R.id.action);
                    timeStart = (TextView) view.findViewById(R.id.time_start);
                    break;
                }
            }
        }
    }


    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
        ViewHolder viewHolder = null;
        View view;
        switch (viewType) {
            case VIEW_EMOICON: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emoicon,
                        parent, false);
                view.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        mAlertEmoDialog = builder.create();
                        LinearLayout listContainer = (LinearLayout) View.inflate(mContext, R.layout
                                .emoicon_list, null);
                        RelativeLayout happyItem = (RelativeLayout) listContainer.findViewById(R.id.happy_item);
                        RelativeLayout neutralItem = (RelativeLayout) listContainer.findViewById(R.id.neutral_item);
                        RelativeLayout dissatisfiedItem = (RelativeLayout) listContainer.findViewById(R.id.dissatisfied_item);
                        RelativeLayout untrackedItem = (RelativeLayout) listContainer.findViewById(R.id.untracked_item);
                        happyItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mEmoIconRessource != R.drawable.ic_sentiment_satisfied_black_48dp) {
                                    update(mContactId, String.valueOf(R.drawable.ic_sentiment_satisfied_black_48dp));
                                    mAlertEmoDialog.cancel();
                                }
                            }
                        });
                        neutralItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mEmoIconRessource != R.drawable.ic_sentiment_neutral_black_48dp) {
                                    update(mContactId, String.valueOf(R.drawable.ic_sentiment_neutral_black_48dp));
                                    mAlertEmoDialog.cancel();
                                }
                            }
                        });
                        dissatisfiedItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mEmoIconRessource != R.drawable.ic_sentiment_dissatisfied_black_48dp) {
                                    update(mContactId, String.valueOf(R.drawable.ic_sentiment_dissatisfied_black_48dp));
                                    mAlertEmoDialog.cancel();
                                }
                            }
                        });
                        untrackedItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mEmoIconRessource != R.drawable.ic_do_not_disturb_alt_black_48dp) {
                                    update(mContactId, String.valueOf(R.drawable.ic_do_not_disturb_alt_black_48dp));
                                    mAlertEmoDialog.cancel();
                                }
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
                                            ContactDAO.ContactQuery.PROJECTION,
                                            ContactDAO.ContactQuery.SELECTION,
                                            new String[]{contactId},
                                            null);
                            cursor.moveToFirst();
                            ContentValues contentvalues = ContactDAO.getContentValues(cursor);
                            contentvalues = Tools.updateContactValues(contentvalues,
                                    FriendForecastContract.ContactTable.COLUMN_EMOICON_ID,
                                    emoIconRessourceId);
                            Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                                    + contentvalues.getAsString(FriendForecastContract.ContactTable.COLUMN_EMOICON_ID));

                            mContext.getContentResolver()
                                    .update(FriendForecastContract.ContactTable.CONTENT_URI,
                                            contentvalues,
                                            ContactDAO.ContactQuery.SELECTION,
                                            new String[]{contactId});
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            mCallback.updateFragment();
                        }
                    }
                });
                viewHolder = new ViewHolder(view, VIEW_EMOICON);
                break;
            }
            case VIEW_ACTION: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action,
                        parent, false);
                viewHolder = new ViewHolder(view, VIEW_ACTION);
                break;
            }
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(DetailAdapter.ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_EMOICON: {
                mContactId = mCursor.getString(ContactDAO.ContactQuery
                        .COL_ID);
                mCallback.setTitle(mCursor.getString(ContactDAO.ContactQuery
                        .COL_ANDROID_CONTACT_NAME));
                mCallback.setThumbnail(mCursor.getString(ContactDAO.ContactQuery
                        .COL_THUMBNAIL));
                mEmoIconRessource = mCursor.getInt(ContactDAO.ContactQuery.COL_EMOICON_BY_ID);
                holder.emoIcon.setBackgroundResource(mEmoIconRessource);
                break;
            }
            case VIEW_ACTION: {
                holder.action.setText(mCursor.getString(ContactActionEventDAO
                        .ActionByContactIdQuery.COL_ACTION_NAME));
                long dueDateLong = mCursor.getLong(ContactActionEventDAO
                        .ActionByContactIdQuery.COL_TIME_START);
                holder.timeStart.setText(DateUtils.getFriendlyDateString(mContext, dueDateLong));
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
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
        mCursor = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
        return viewTypes[position];
    }
}
