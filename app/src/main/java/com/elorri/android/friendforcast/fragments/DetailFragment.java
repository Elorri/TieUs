package com.elorri.android.friendforcast.fragments;

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
import android.view.View;
import android.view.ViewGroup;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.data.DetailData;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.ui.AvatarView;
import com.elorri.android.friendforcast.ui.DynamicHeightGradientTopAvatarView;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        DetailAdapter.Callback {
    public static final String CONTACT_ID = "contact_id";
    public static final String DETAIL_URI = "uri";
    private static Uri mUri;
    private RecyclerView mRecyclerView;
    private DetailAdapter mAdapter;

    private String mContactTitle;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private DynamicHeightGradientTopAvatarView mAvatar;
    private FloatingActionButton mAddFab;
    private int mAvatarColor;
    private AppBarLayout mAppBarLayout;
    private Cursor mData;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("FF", "" + Thread.currentThread().getStackTrace()[2]);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        mAvatarColor = getArguments().getInt(AvatarView.RANDOM_COLOR);
        mCollapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbar.setTitle("");

        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
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
    public void setThumbnail(String uri) {
        mAvatar.loadImage(uri, mAvatarColor);

    }

    @Override
    public void updateFragment() {
        getLoaderManager().restartLoader(DetailData.LOADER_ID, null, this);
    }


}
