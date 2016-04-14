package com.elorri.android.communication;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elorri.android.communication.db.AndroidDAO;

/**
 * Created by Elorri on 13/04/2016.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private Cursor mCursor;

    public ContactAdapter(Cursor cursor) {
        mCursor = cursor;
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView contactName;
        ImageView icon;

        public ViewHolder(View view) {
            super(view);
            icon = (ImageView) view.findViewById(R.id.icon);
            contactName = (TextView) view.findViewById(R.id.contact_name);
        }
    }


    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.contactName.setText(mCursor.getString(AndroidDAO.ContactQuery.COL_CONTACT_NAME));
        final String photoUri = mCursor.getString(AndroidDAO.ContactQuery.COL_PHOTO_THUMBNAIL_URI);
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "photoUri " + photoUri);
        //mImageLoader.loadImage(photoUri, holder.icon);

    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor data) {
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
        mCursor = data;
        notifyDataSetChanged();
    }


}
