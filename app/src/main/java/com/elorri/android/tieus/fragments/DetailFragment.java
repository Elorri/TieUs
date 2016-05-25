package com.elorri.android.tieus.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.data.DetailData;
import com.elorri.android.tieus.data.FriendForecastContract;
import com.elorri.android.tieus.ui.DynamicHeightGradientTopAvatarView;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        DetailAdapter.Callback {
    public static final String CONTACT_ID = "contact_id";
    public static final String DETAIL_URI = "uri";
    private Uri mUri;
    private Uri mAndroidContactUri;

    private RecyclerView mRecyclerView;
    private DetailAdapter mAdapter;

    private String mContactTitle;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private DynamicHeightGradientTopAvatarView mAvatar;
    private FloatingActionButton mAddFab;
    private AppBarLayout mAppBarLayout;
    private Cursor mData;

    private Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item=inflateFragmentMenuItem();

        switch (item.getItemId()) {
            // When "edit" menu option selected
            case R.id.action_edit_contact:
                if(mAndroidContactUri!=null) {
                    // Standard system edit contact intent
                    Intent intent = new Intent(Intent.ACTION_EDIT, mAndroidContactUri);

                    // Because of an issue in Android 4.0 (API level 14), clicking Done or Back in the
                    // People app doesn't return the user to your app; instead, it displays the People
                    // app's contact list. A workaround, introduced in Android 4.0.3 (API level 15) is
                    // to set a special flag in the extended data for the Intent you send to the People
                    // app. The issue is does not appear in versions prior to Android 4.0. You can use
                    // the flag with any version of the People app; if the workaround isn't needed,
                    // the flag is ignored.
                    intent.putExtra("finishActivityOnSaveCompleted", true);

                    // Start the edit activity
                    startActivity(intent);
                    return true;
                }
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private MenuItem inflateFragmentMenuItem() {
        Menu menu = mToolbar.getMenu();
        if (null != menu) menu.clear();
        mToolbar.inflateMenu(R.menu.fragment_detail);
        return menu.findItem(R.id.action_edit_contact);
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("FF", "" + Thread.currentThread().getStackTrace()[2]);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        mCollapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbar.setTitle("");

        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mAdapter.simpleItemTouchCallBack);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mAddFab = (FloatingActionButton) view.findViewById(R.id.add_fab);

        return view;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.e("FF", "" + Thread.currentThread().getStackTrace()[2]);
        getLoaderManager().initLoader(DetailData.LOADER_ID, null, this);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isCollapsed = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0 && mContactTitle != null) {
                    mCollapsingToolbar.setTitle(mContactTitle);
                    mCollapsingToolbar.setContentScrimColor(getResources().getColor(R.color.primary));
                    isCollapsed = true;
                } else if (isCollapsed) {
                    mCollapsingToolbar.setTitle("");
                    isCollapsed = false;
                }
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        Log.e("FF", "" + Thread.currentThread().getStackTrace()[2]);
        if (mData != null) {
            Log.e("FF", "" + Thread.currentThread().getStackTrace()[2]);
            getLoaderManager().restartLoader(DetailData.LOADER_ID, null, this);
        }
        super.onResume();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d("FF", "" + Thread.currentThread().getStackTrace()[2]);
        CursorLoader cursorLoader = null;

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(DETAIL_URI);
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
        Log.d("FF", "" + Thread.currentThread().getStackTrace()[2]);
        if (data != null && data.moveToFirst()) {
            mData = data;
            mAdapter.swapCursor(data);
        }
        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contactId = FriendForecastContract.DetailData.getContactIdFromUri(mUri);
                //((DetailActivity) getActivity()).startAddActions(contactId);

                Bundle arguments = new Bundle();
                arguments.putCharSequence(DetailFragment.CONTACT_ID, contactId);
                AddActionFragment fragment = new AddActionFragment();
                fragment.setArguments(arguments);
                fragment.show(getFragmentManager(), getResources().getString(R.string.action_add));
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


    @Override
    public void setTitle(String title) {
        mContactTitle = title;
    }

    @Override
    public void setThumbnail(String uri, int color) {
        mAvatar.loadImage(uri, color);

    }

    @Override
    public void setAndroidContactUri(Uri uri) {
        mAndroidContactUri=uri;
    }

    @Override
    public void updateFragment() {
        getLoaderManager().restartLoader(DetailData.LOADER_ID, null, this);
    }

    @Override
    public void hideFab() {
        mAddFab.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showFab() {
        mAddFab.setVisibility(View.VISIBLE);
    }
}
