package com.elorri.android.friendforcast;

import android.database.Cursor;
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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.elorri.android.friendforcast.data.DetailQuery;
import com.elorri.android.friendforcast.ui.DynamicHeightGradientTopImageView;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String DETAIL_URI = "uri";
    private static Uri mUri;
    private RecyclerView mRecyclerView;
    private DetailAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2]);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        DynamicHeightGradientTopImageView avatar=(DynamicHeightGradientTopImageView) view.findViewById(R.id.avatar);
         FrameLayout avatarBg = (FrameLayout) view.findViewById(R.id.avatar_bg);
         TextView contactName= (TextView) view.findViewById(R.id.contact_name);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager
                .VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new DetailAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
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


}
