package com.elorri.android.friendforcast.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
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
import com.elorri.android.friendforcast.activities.DetailActivity;
import com.elorri.android.friendforcast.data.AddActionData;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.EventDAO;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.LinkedList;

/**
 * Created by Elorri on 26/04/2016.
 */
public class AddActionFragment extends DialogFragment implements LoaderManager
        .LoaderCallbacks<Cursor>, AddActionAdapter.Callback {


    private static Uri mUri;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mSaveFab;


    //This variable will allow to choose the most suitable uri and display the correct data. It
    // will contain in order :
    // no data -> uri : URI_PAGE_SELECT_ACTION
    // actionId -> uri : URI_PAGE_ADD_ACTION_SELECT_VECTOR
    // actionId, vectorId, time -> uri : URI_PAGE_ADD_ACTION_VALIDATE
    private LinkedList<String> actionSteps = new LinkedList<>();

    private AddActionAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2]);
        View view = inflater.inflate(R.layout.fragment_add_action, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mSaveFab = (FloatingActionButton) view.findViewById(R.id.save_fab);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager
                .VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new AddActionAdapter(null, this, actionSteps.size());
        mRecyclerView.setAdapter(mAdapter);
        getDialog().setTitle(getResources().getString(R.string.action_add));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(AddActionData.LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (actionSteps.size()) {
            case 0: //We ask the user to select an action
                mUri = FriendForecastContract.AddActionData.URI_PAGE_SELECT_ACTION;
                break;
            case 1://We have the action, we ask for a vector and a dateStart
                mUri = FriendForecastContract.AddActionData.buildSelectVectorUri(actionSteps.get(0));
                break;
            case 3://We have the action, vector, and dateStart we ask for validation
                mUri = FriendForecastContract.AddActionData.buildValidateUri(actionSteps.get(0),
                        actionSteps.get(1), actionSteps.get(2));
                break;
            default:
                return null;
        }

        return new CursorLoader(getActivity(),
                mUri,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2] + "data " + data
                .getCount());
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


    @Override
    public void setActionId(String actionId) {
        actionSteps.add(actionId);
        mAdapter.updateActionStepSize(actionSteps.size());
        getLoaderManager().restartLoader(AddActionData.LOADER_ID, null, this);
    }

    @Override
    public void setVectorId(String vectorId) {
        actionSteps.add(vectorId);

        final DateListener dateListener = new DateListener();
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

    }


    @Override
    public void showFab(final String actionId, final String vectorId, final long timeStart) {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        mSaveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactId = getArguments().getCharSequence(DetailFragment.CONTACT_ID).toString();
                AddActionTask addActionTask = new AddActionTask();
                addActionTask.execute(contactId, actionId, vectorId, String.valueOf(timeStart));
            }
        });
        mSaveFab.setVisibility(View.VISIBLE);

    }

    private class DateListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            Calendar startDateCal = Calendar.getInstance();
            startDateCal.set(Calendar.YEAR, year);
            startDateCal.set(Calendar.MONTH, monthOfYear);
            startDateCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            final long startDate = DateUtils.setZeroDay(startDateCal.getTimeInMillis());
            actionSteps.add(String.valueOf(startDate));
            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + startDate);

//            AddActionFragment.this.getLoaderManager().restartLoader(AddActionData.LOADER_ID,
//                    null, AddActionFragment.this);
            String contactId = getArguments().getCharSequence(DetailFragment.CONTACT_ID).toString();
            AddActionTask addActionTask = new AddActionTask();
            addActionTask.execute(
                    contactId, actionSteps.get(0), actionSteps.get(1), actionSteps.get(2));
        }
    }

    private class AddActionTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            getContext().getContentResolver().insert(
                    FriendForecastContract.EventTable.CONTENT_URI,
                    EventDAO.getContentValues(params[0], params[1], params[2], Long.valueOf
                            (params[3])));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //getActivity().finish();
            getDialog().cancel();
            getActivity().getSupportFragmentManager().findFragmentByTag
                    (DetailActivity.DETAIL_FRAGMENT).onResume();
            super.onPostExecute(aVoid);
        }
    }
}
