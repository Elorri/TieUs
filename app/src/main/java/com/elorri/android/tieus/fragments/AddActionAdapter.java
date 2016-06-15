package com.elorri.android.tieus.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.data.TieUsContract;
import com.elorri.android.tieus.db.ActionDAO;
import com.elorri.android.tieus.db.MatrixCursors;
import com.elorri.android.tieus.db.VectorDAO;
import com.elorri.android.tieus.db.ViewTypes;
import com.elorri.android.tieus.extra.Tools;

/**
 * Created by Elorri on 28/04/2016.
 */

public class AddActionAdapter extends RecyclerView.Adapter<AddActionAdapter.ViewHolder> {

    public static final int VIEW_ACTION_ITEM = 101;
    public static final int VIEW_ACTION_RECAP = 102;
    private String mCurrentStep;


    private Cursor mCursor;
    private Context mContext;
    private Callback mCallback;

    public void updateCurrentStep(String currentStep) {
        mCurrentStep = currentStep;
    }


    interface Callback {
        void setActionId(String actionId);

        void setVectorId(String vectorId);

        void sendToFirebase(String contentType, String actionId, String actionName);
    }

    public AddActionAdapter(Cursor cursor, Callback callback, String currentStep) {
        mCursor = cursor;
        mCallback = callback;
        mCurrentStep = currentStep;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public View divider;
        public ImageView vectorLogo;
        public ImageView clock;
        public TextView action;
        public TextView timeStart;
        public TextView title;
        public TextView textView;
        public ImageView imageView;
        public TextView label;
        public View mView;

        public ViewHolder(View view, int viewType) {
            super(view);
            this.mView = view;

            switch (viewType) {
                case VIEW_ACTION_RECAP: {
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                    vectorLogo = (ImageView) view.findViewById(R.id.vector_logo);
                    clock = (ImageView) view.findViewById(R.id.clock);
                    action = (TextView) view.findViewById(R.id.action);
                    timeStart = (TextView) view.findViewById(R.id.time);
                    break;
                }
                case ViewTypes.VIEW_TITLE: {
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                    divider = view.findViewById(R.id.divider);
                    title = (TextView) view.findViewById(R.id.title);
                    break;
                }
                case VIEW_ACTION_ITEM: {
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                    textView = (TextView) view.findViewById(R.id.textview);
                    break;
                }
                case ViewTypes.VIEW_VECTOR_ITEM: {
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                    imageView = (ImageView) view.findViewById(R.id.imageview);
                    label = (TextView) view.findViewById(R.id.label);
                    break;
                }
            }
        }
    }

    @Override
    public AddActionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        ViewHolder viewHolder = null;
        View view;
        switch (viewType) {
            case VIEW_ACTION_RECAP: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action_recap,
                        parent, false);
                viewHolder = new ViewHolder(view, VIEW_ACTION_RECAP);
                break;
            }
            case ViewTypes.VIEW_TITLE: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title,
                        parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_TITLE);
                break;
            }
            case VIEW_ACTION_ITEM: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_textview,
                        parent, false);
                viewHolder = new ViewHolder(view, VIEW_ACTION_ITEM);
                break;
            }
            case ViewTypes.VIEW_VECTOR_ITEM: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_imageview_label,
                        parent, false);
                viewHolder = new ViewHolder(view, ViewTypes.VIEW_VECTOR_ITEM);
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AddActionAdapter.ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_ACTION_RECAP: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                int vectorIdx = mCursor.getColumnIndex(TieUsContract.VectorTable.COLUMN_DATA);
                int actionIdx = mCursor.getColumnIndex(TieUsContract.ActionTable.COLUMN_NAME_RESOURCE_ID);
                int timeStartIdx = mCursor.getColumnIndex(TieUsContract.EventTable.COLUMN_TIME_START);
                String actionName;
                long timeStartLong;
                if (actionIdx != -1) {
                    actionName = mContext.getResources().getString(mCursor.getInt(actionIdx));
                    holder.action.setText(actionName);
                    if (vectorIdx != -1) {
                        int mimetypeIdx = mCursor.getColumnIndex(TieUsContract.VectorTable.COLUMN_MIMETYPE);

                        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" +
                                mCursor.getString(mimetypeIdx) +
                                " " + mCursor.getString(vectorIdx));
                        holder.vectorLogo.setVisibility(View.VISIBLE);
                        Tools.setVectorBackground(mContext, holder.vectorLogo,
                                mCursor.getString(mimetypeIdx),
                                mCursor.getString(vectorIdx));
                    }
                }
                break;
            }
            case ViewTypes.VIEW_TITLE: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                int visibility = position == 0 ? View.INVISIBLE : View.VISIBLE;
                holder.divider.setVisibility(visibility);
                holder.title.setText(mCursor.getString(MatrixCursors.TitleQuery.COL_TITLE));
                break;
            }
            case VIEW_ACTION_ITEM: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                holder.textView.setText(mContext.getString(mCursor.getInt(ActionDAO.ActionQuery.COL_ACTION_NAME_RESOURCE_ID)));
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition();
                        mCursor.moveToPosition(adapterPosition);
                        final String actionId = mCursor.getString(ActionDAO.ActionQuery.COL_ID);
                        final String actionName = mContext.getResources().getString(mCursor.getInt
                                (ActionDAO.ActionQuery.COL_ACTION_NAME_RESOURCE_ID));
                        final String contentTypeAction = mContext.getResources().getString(R.string.item_action);
                        mCallback.setActionId(actionId);
                        mCallback.sendToFirebase(contentTypeAction, actionId, actionName);
                    }
                });
                break;
            }
            case ViewTypes.VIEW_VECTOR_ITEM: {
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                holder.imageView.setBackground(null);
                Tools.setVectorBackground(mContext, holder.imageView, mCursor.getString(
                        VectorDAO.VectorQuery.COL_MIMETYPE),
                        mCursor.getString(VectorDAO.VectorQuery.COL_DATA)
                );

                holder.label.setText(mCursor.getString(VectorDAO.VectorQuery.COL_VECTOR_NAME));
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition();
                        mCursor.moveToPosition(adapterPosition);
                        final String vectorId = mCursor.getString(VectorDAO.VectorQuery.COL_ID);
                        final String vectorName = mCursor.getString(VectorDAO.VectorQuery.COL_VECTOR_NAME);
                        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + vectorId);
                        final String contentTypeVector = mContext.getResources().getString(R.string.item_vector);
                        mCallback.setVectorId(vectorId);
                        mCallback.sendToFirebase(contentTypeVector,vectorId, vectorName);
                    }
                });
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
        mCursor.moveToPosition(position);
        int viewtype = mCursor.getInt(mCursor.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE));
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "position " + position);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "viewtype " + viewtype);
        if (viewtype == ViewTypes.VIEW_ACTION) {
            viewtype = (mCurrentStep.equals(AddActionFragment.ZERO_STEP)) ? VIEW_ACTION_ITEM :
                    VIEW_ACTION_RECAP;
            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "mCurrentStep " + mCurrentStep);
            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "viewtype " + viewtype);
        }
        return viewtype;
    }

}
