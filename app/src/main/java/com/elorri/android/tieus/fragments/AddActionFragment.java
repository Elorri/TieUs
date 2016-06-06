package com.elorri.android.tieus.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.activities.DetailActivity;
import com.elorri.android.tieus.activities.MainActivity;
import com.elorri.android.tieus.data.AddActionData;
import com.elorri.android.tieus.data.FriendForecastContract;
import com.elorri.android.tieus.db.EventDAO;
import com.elorri.android.tieus.extra.DateUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Elorri on 26/04/2016.
 * Created by Elorri on 26/04/2016.
 */
public class AddActionFragment extends DialogFragment implements LoaderManager
        .LoaderCallbacks<Cursor>, AddActionAdapter.Callback {


    private static Uri mUri;
    private RecyclerView mRecyclerView;


    //This variable will allow to choose the most suitable uri and display the correct data. It
    // will contain in order :
    // no data -> uri : URI_PAGE_SELECT_ACTION
    // actionId -> uri : URI_PAGE_ADD_ACTION_SELECT_VECTOR
    private Map<String, String> actionSteps = new HashMap<>();

    private AddActionAdapter mAdapter;
    public static final  String ZERO_STEP = "zero_step";
    private static final  String ACTION_STEP = "action_step";
    private static final  String VECTOR_STEP = "vector_step";
    private static final  String DATE_STEP = "date_step";
    private static final  String CURRENT_STEP="current_step";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2]);
        View view = inflater.inflate(R.layout.fragment_add_action, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager
                .VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        if(actionSteps.get(CURRENT_STEP)==null){
            actionSteps.put(CURRENT_STEP,ZERO_STEP);
        }

        mAdapter = new AddActionAdapter(null, this, actionSteps.get(CURRENT_STEP));
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
        switch (actionSteps.get(CURRENT_STEP)) {
            case ZERO_STEP: //We ask the user to select an action
                mUri = FriendForecastContract.AddActionData.URI_PAGE_SELECT_ACTION;
                break;
            case ACTION_STEP://We have the action, we ask for a vector and a dateStart
                mUri = FriendForecastContract.AddActionData.buildSelectVectorUri(actionSteps.get
                        (ACTION_STEP));
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
        actionSteps.put(ACTION_STEP, actionId);
        actionSteps.put(CURRENT_STEP, ACTION_STEP);
        mAdapter.updateCurrentStep(ACTION_STEP);
        getLoaderManager().restartLoader(AddActionData.LOADER_ID, null, this);
    }

    @Override
    public void setVectorId(String vectorId) {
        actionSteps.put(VECTOR_STEP, vectorId);
        actionSteps.put(CURRENT_STEP, VECTOR_STEP);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        final DateListener dateListener = new DateListener();
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                dateListener,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMinDate(Calendar.getInstance());//Set the min date to today
        dpd.setAccentColor(Color.parseColor(getResources().getString(R.string.accent)));
        dpd.show(getActivity().getFragmentManager(), getResources().getString(R.string
                .due_date));
        dpd.setOnDateSetListener(dateListener);
    }


    private class DateListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            Calendar startDateCal = Calendar.getInstance();
            startDateCal.set(Calendar.YEAR, year);
            startDateCal.set(Calendar.MONTH, monthOfYear);
            startDateCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            final long startDate = DateUtils.setZeroDay(startDateCal.getTimeInMillis());
            actionSteps.put(DATE_STEP, String.valueOf(startDate));
            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + startDate);

//            AddActionFragment.this.getLoaderManager().restartLoader(AddActionData.LOADER_ID,
//                    null, AddActionFragment.this);
            String contactId = getArguments().getCharSequence(DetailFragment.CONTACT_ID).toString();
            AddActionTask addActionTask = new AddActionTask();
            addActionTask.execute(
                    contactId, actionSteps.get(ACTION_STEP), actionSteps.get(VECTOR_STEP), actionSteps.get(DATE_STEP));
        }
    }

    private class AddActionTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + params[3]);
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
            getActivity().getSupportFragmentManager().findFragmentByTag(DetailActivity.DETAIL_FRAGMENT).onResume();
            if (getContext().getResources().getInteger(R.integer.orientation) == MainActivity.W700dp_LAND)
                ((MainActivity) getActivity()).getMainFragment().restartLoader();

            super.onPostExecute(aVoid);
        }
    }
}
