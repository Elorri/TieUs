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
import com.elorri.android.friendforcast.db.TemplateDAO;
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

                final DateListener dateListener = new DateListener(actionId);
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        dateListener,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(Color.parseColor(getResources().getString(R.string.accent)));
                dpd.show(getActivity().getFragmentManager(), getResources().getString(R.string
                        .due_date));
                dpd.setOnDateSetListener(dateListener);

                ((SelectTemplateActivity) getActivity()).startSelectTemplateActivity();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(TemplateDAO.TemplateQuery.LOADER_ID, null, this);
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


    private class DateListener implements DatePickerDialog.OnDateSetListener {

        private final String actionId;

        public DateListener(String actionId) {
            this.actionId = actionId;
        }

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            Calendar startDateCal = Calendar.getInstance();
            startDateCal.set(Calendar.YEAR, year);
            startDateCal.set(Calendar.MONTH, monthOfYear);
            startDateCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            final long startDate = DateUtils.setZeroDay(startDateCal.getTimeInMillis());


            //Add action with date to table event
            Thread background = new Thread(new Runnable() {
                Handler mHandler = new Handler();

                @Override
                public void run() {
                    Log.e("MealPlanner", Thread.currentThread().getStackTrace()[2] +
                            "background");
                    getContext().getContentResolver()
                            .insert(FriendForecastContract.EventTable.CONTENT_URI,
                                    EventDAO.getContentValues(
                                            FriendForecastContract.DetailData.getContactIdFromUri(mUri),
                                            actionId,
                                            startDate));
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("MealPlanner", Thread.currentThread().getStackTrace
                                    ()[2] + "uiThread");
                            mAlertDialog.cancel();
                            getLoaderManager().restartLoader(DetailData.LOADER_ID, null,
                                    DetailFragment.this);
                            Log.e("MealPlanner", Thread.currentThread().getStackTrace
                                    ()[2] + "uiThread");
                        }
                    });
                }
            });
            background.start();
        }
    }
}
