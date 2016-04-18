package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.elorri.android.friendforcast.data.DetailQuery;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ActionDAO;
import com.elorri.android.friendforcast.db.EventDAO;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.elorri.android.friendforcast.ui.DynamicHeightGradientTopAvatarView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, DetailAdapter.Callback {

    public static final String DETAIL_URI = "uri";
    private static Uri mUri;
    private RecyclerView mRecyclerView;
    private DetailAdapter mAdapter;

    private String mContactTitle;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private DynamicHeightGradientTopAvatarView mAvatar;
    private AlertDialog mAlertDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2]);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        mCollapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbar.setTitle("");

        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isCollapsed = false;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0 && mContactTitle != null) {
//                    mCollapsingToolbar.setTitle(mContactTitle);
//                    isCollapsed = true;
//                } else if (isCollapsed) {
//                    mCollapsingToolbar.setTitle("");
//                    isCollapsed = false;
//                }
//            }
//        });

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAvatar = (DynamicHeightGradientTopAvatarView) view.findViewById(R.id.avatar);
        //view_no_avatar.setBackgroundResource(mThumbnail);
        //FrameLayout avatarBg = (FrameLayout) view.findViewById(R.id.avatar_bg);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager
                .VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new DetailAdapter(null, this);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton shareFab = (FloatingActionButton) view.findViewById(R.id.add_fab);
        shareFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                mAlertDialog = builder.create();
                FetchActionsTask actionsTask = new FetchActionsTask();
                actionsTask.execute();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DetailQuery.LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = null;

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(DETAIL_URI);
//            Log.e("Lifecycle", Thread.currentThread().getStackTrace()[2] + ": "+Utility.thread()+" : " +
//                    " : DetailFragment.mDetailListView :  change state");
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


    @Override
    public void setTitle(String title) {
        mContactTitle = title;
        mCollapsingToolbar.setTitle(mContactTitle);
    }

    @Override
    public void setThumbnail(String uri) {
        mAvatar.loadImage(uri);

    }

    @Override
    public void updateFragment() {
        getLoaderManager().restartLoader(DetailQuery.LOADER_ID, null, this);
    }


    private class FetchActionsTask extends AsyncTask<Void, Void, Cursor> {
        private final String[] COLUMNS_TO_BE_BOUND = new String[]{
                FriendForecastContract.ActionTable.COLUMN_NAME
        };

        private final int[] ITEMS_ID_TO_FILL = new int[]{
                R.id.name
        };

        @Override
        protected Cursor doInBackground(Void... params) {
            return getContext().getContentResolver().query(FriendForecastContract.ActionTable
                            .CONTENT_URI,
                    ActionDAO.ActionQuery.PROJECTION,
                    null,
                    null,
                    ActionDAO.ActionQuery.SORT_ORDER);
        }

        @Override
        protected void onPostExecute(Cursor result) {

            if (result != null) {
                SimpleCursorAdapter adapter =
                        new SimpleCursorAdapter(getContext(),
                                R.layout.simple_item,
                                result,
                                COLUMNS_TO_BE_BOUND,
                                ITEMS_ID_TO_FILL,
                                0);
                RelativeLayout alertView = (RelativeLayout) View.inflate(getContext(), R.layout.alert, null);
                ListView list = (ListView) View.inflate(getContext(), R.layout.alert_list, null);
                final FrameLayout list_container = (FrameLayout) alertView.findViewById(R.id.list_container);
                list_container.addView(list);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
                        Log.e("MealPlanner", Thread.currentThread().getStackTrace()[2] + "");
                        Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                        final String actionId = cursor.getString(ActionDAO.ActionQuery.COL_ID);

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
                    }
                });
                Button dismissButton = (Button) alertView.findViewById(R.id.dismiss_button);
                Button saveButton = (Button) alertView.findViewById(R.id.save_button);
                saveButton.setEnabled(false);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAlertDialog.cancel();
                    }
                });
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "yes", Toast
                                .LENGTH_SHORT).show();
                    }
                });

                list.setAdapter(adapter);
                //Must be done before mAlertDialog.show() Let you customize only the main
                // content, not the title and button
                //mAlertDialog.setView(alertView);
                mAlertDialog.show();
                //Must be done after mAlertDialog.show() Let you customize everything including
                // title and buttons.
                mAlertDialog.setContentView(alertView);
            }
        }

        private class DateListener implements DatePickerDialog.OnDateSetListener {

            private final String actionId;

            public DateListener(String actionId){
    this.actionId=actionId;
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
                                getLoaderManager().restartLoader(DetailQuery.LOADER_ID, null,
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


}
