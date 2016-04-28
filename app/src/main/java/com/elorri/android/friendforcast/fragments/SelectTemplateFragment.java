package com.elorri.android.friendforcast.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.activities.SelectTemplateActivity;
import com.elorri.android.friendforcast.data.DetailData;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.EventDAO;
import com.elorri.android.friendforcast.db.ActionVectorTemplatesDAO;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by Elorri on 26/04/2016.
 */
public class SelectTemplateFragment  extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String SELECT_TEMPLATE_URI = "uri";
    private static Uri mUri;
    private RecyclerView mRecyclerView;
    private SelectTemplateAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2]);
        View view = inflater.inflate(R.layout.fragment_select_template, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager
                .VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new SelectTemplateAdapter();
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton nextFab = (FloatingActionButton) view.findViewById(R.id.add_fab);
        nextFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                ((SelectTemplateActivity) getActivity()).startSelectTemplateActivity();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(ActionVectorTemplatesDAO.ActionVectorTemplatesQuery.LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = null;

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(SELECT_TEMPLATE_URI);
            if (mUri != null) {
                cursorLoader = new CursorLoader(getActivity(),
                        mUri,
                        null,
                        null,
                        null,
                        null);
            }
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            mAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }





    private class SelectTemplateAdapter  extends RecyclerView.Adapter<SelectTemplateAdapter.ViewHolder>{

        private Cursor mCursor;

        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public SelectTemplateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(SelectTemplateAdapter.ViewHolder holder, int position) {

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
    }



}
