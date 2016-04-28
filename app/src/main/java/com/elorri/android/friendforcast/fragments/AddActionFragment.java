package com.elorri.android.friendforcast.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.elorri.android.friendforcast.data.AddActionData;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.LinkedList;

/**
 * Created by Elorri on 26/04/2016.
 */
public class AddActionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AddActionAdapter.Callback {

    public static final String SELECT_ACTION_URI = "uri";

    private static Uri mUri;
    private RecyclerView mRecyclerView;

    //This variable will allow to choose the most suitable uri and display the correct data. It
    // will contain in order :
    // no data -> uri : URI_PAGE_ADD_ACTION_SELECT_ACTION
    // actionId -> uri : URI_PAGE_ADD_ACTION_SELECT_VECTOR
    // actionId, vectorId -> uri : URI_PAGE_ADD_ACTION_SELECT_TEMPLATE
    // actionId, vectorId, templateId, timeStart -> uri : URI_PAGE_ADD_ACTION_VALIDATE
    private LinkedList<String> actionSteps = new LinkedList<>();

    private AddActionAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2]);
        View view = inflater.inflate(R.layout.fragment_select_action, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager
                .VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new AddActionAdapter(null, this);
        mRecyclerView.setAdapter(mAdapter);

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
                mUri = FriendForecastContract.AddActionData.URI_PAGE_ADD_ACTION_SELECT_ACTION;
                break;
            case 1://We have the action, we ask for a vector
                mUri = FriendForecastContract.AddActionData.URI_PAGE_ADD_ACTION_SELECT_VECTOR;
                break;
            case 2://We have the action and vector, we ask for a template and dateStart
                mUri = FriendForecastContract.AddActionData.URI_PAGE_ADD_ACTION_SELECT_TEMPLATE;
                break;
            case 4://We have the action, vector, template and dateStart we ask for validation
                mUri = FriendForecastContract.AddActionData.URI_PAGE_ADD_ACTION_VALIDATE;
                break;
            default:
                throw new IndexOutOfBoundsException();
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
        getLoaderManager().restartLoader(AddActionData.LOADER_ID, null, this);
    }

    @Override
    public void setVectorId(String vectorId) {
        actionSteps.add(vectorId);
        getLoaderManager().restartLoader(AddActionData.LOADER_ID, null, this);

    }

    @Override
    public void setTemplateId(String templateId) {
        actionSteps.add(templateId);

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

    private class DateListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            Calendar startDateCal = Calendar.getInstance();
            startDateCal.set(Calendar.YEAR, year);
            startDateCal.set(Calendar.MONTH, monthOfYear);
            startDateCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            final long startDate = DateUtils.setZeroDay(startDateCal.getTimeInMillis());
            actionSteps.add(String.valueOf(startDate));
            AddActionFragment.this.getLoaderManager().restartLoader(AddActionData.LOADER_ID,
                    null, AddActionFragment.this);
        }
    }
}
