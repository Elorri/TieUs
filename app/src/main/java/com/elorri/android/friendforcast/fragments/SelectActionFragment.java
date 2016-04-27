package com.elorri.android.friendforcast.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.activities.SelectActionActivity;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ActionDAO;

/**
 * Created by Elorri on 26/04/2016.
 */
public class SelectActionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String SELECT_ACTION_URI = "uri";

    private static Uri mUri;
    private ListView mListview;


    private final String[] COLUMNS_TO_BE_BOUND = new String[]{FriendForecastContract.ActionTable.COLUMN_NAME};
    private final int[] ITEMS_ID_TO_FILL = new int[]{R.id.name};
    private SimpleCursorAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2]);
        View view = inflater.inflate(R.layout.fragment_select_action, container, false);

        mListview = (ListView) view.findViewById(R.id.listview);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
                Log.e("MealPlanner", Thread.currentThread().getStackTrace()[2] + "");
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                final String actionId = cursor.getString(ActionDAO.ActionQuery.COL_ID);
                ((SelectActionActivity) getActivity()).startSelectVectorActivity(actionId);

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(ActionDAO.ActionQuery.LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = null;

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(SELECT_ACTION_URI);
            if (mUri != null) {
                cursorLoader = new CursorLoader(getActivity(),
                        mUri,
                        ActionDAO.ActionQuery.PROJECTION,
                        null,
                        null,
                        ActionDAO.ActionQuery.SORT_ORDER);
            }
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            mAdapter = new SimpleCursorAdapter(getContext(),
                    R.layout.simple_item,
                    data,
                    COLUMNS_TO_BE_BOUND,
                    ITEMS_ID_TO_FILL,
                    0);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


}
