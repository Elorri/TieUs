package com.elorri.android.tieus.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.Toast;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.TieUsApplication;
import com.elorri.android.tieus.activities.MainActivity;
import com.elorri.android.tieus.data.DetailData;
import com.elorri.android.tieus.data.TieUsContract;
import com.elorri.android.tieus.extra.Tools;
import com.elorri.android.tieus.ui.GradientTopAvatarView;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Elorri on 16/04/2016.
 */
public abstract class AbstractDetailFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<Cursor>,
        DetailAdapter.Callback {
    public static final String CONTACT_ID = "contact_id";
    public static final String DETAIL_URI = "uri";
    private Uri mUri;
    private Uri mAndroidContactUri;

    private RecyclerView mRecyclerView;
    private DetailAdapter mAdapter;

    private String mContactTitle;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private GradientTopAvatarView mAvatar;
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
    public void onPrepareOptionsMenu(Menu menu) {
        if (null != menu) menu.clear();
        mToolbar.inflateMenu(R.menu.fragment_detail);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // When "edit" menu option selected
            case R.id.action_edit_contact:
                if (mAndroidContactUri != null) {
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

                    PackageManager packageManager = getActivity().getPackageManager();
                    if (intent.resolveActivity(packageManager) != null) {
                        // Start the edit activity
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string
                                .no_contacts_app), Toast
                                .LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
        }
        return super.onOptionsItemSelected(item);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        setCommonViews(view);
        setOthersViews(view);

        return view;
    }

    private void setOthersViews(View view) {
        switch (getResources().getInteger(R.integer.orientation)) {
            case MainActivity.PORT:
            case MainActivity.W600dp_PORT:
                mCollapsingToolbar =
                        (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
                mCollapsingToolbar.setTitle("");
                mAppBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mAvatar = (GradientTopAvatarView) view.findViewById(R.id.avatar);
                break;
            case MainActivity.LAND:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mAvatar = (GradientTopAvatarView) view.findViewById(R.id.avatar);
                break;
            case MainActivity.W700dp_LAND:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        }

    }

    private void setCommonViews(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager
                .VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new DetailAdapter(null, this);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mAdapter.simpleItemTouchCallBack);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mAddFab = (FloatingActionButton) view.findViewById(R.id.add_fab);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DetailData.LOADER_ID, null, this);
        switch (getResources().getInteger(R.integer.orientation)) {
            case MainActivity.PORT:
            case MainActivity.W600dp_PORT:
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
                break;
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        if (mData != null) {
            getLoaderManager().restartLoader(DetailData.LOADER_ID, null, this);
        }
        super.onResume();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
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
        if (data != null && data.moveToFirst()) {
            mData = data;
            mAdapter.swapCursor(data);
        }
        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToFirebase(FirebaseAnalytics.Event.SELECT_CONTENT, getContext().getResources
                        ().getString(R.string.item_button), null, getContext().getResources().getString(R.string.item_action));

                String contactId = TieUsContract.DetailData.getContactIdFromUri(mUri);
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
        if (getResources().getInteger(R.integer.orientation) == MainActivity.W700dp_LAND) {
            mToolbar.setTitle(title);
        }
    }

    @Override
    public void setThumbnail(String uri, int color) {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "orientation" + getResources()
                .getInteger(R
                        .integer.orientation));
        switch (getResources().getInteger(R.integer.orientation)) {
            case MainActivity.PORT:
            case MainActivity.LAND:
            case MainActivity.W600dp_PORT:
                mAvatar.loadImage(uri, color);
        }
    }

    @Override
    public void setAndroidContactUri(Uri uri) {
        mAndroidContactUri = uri;
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

    @Override
    public void setAvatarContentDescription(String contactName) {
        mAvatar.setContentDescription(contactName);
    }

    @Override
    public void startVector(Context context, String mimetype, String vectorData, String
            vectorName) {
        if (mimetype.equals(TieUsContract.VectorTable.MIMETYPE_VALUE_RESSOURCE)) {
            if (Long.valueOf(vectorData) == R.drawable.ic_meeting_24dp) {
                Toast.makeText(context, context.getResources().getString(
                        R.string.outside_event, vectorName), Toast.LENGTH_SHORT).show();
                return;
            } else if (Long.valueOf(vectorData) == R.drawable.ic_textsms_black_24dp) {
                Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                smsIntent.setType(context.getResources().getString(R.string.sms_intent_type));
                //TODO add phone nummber
//                smsIntent.putExtra("address", "0123456789");
//                smsIntent.putExtra("sms_body", "message");
                context.startActivity(smsIntent);
            }
        } else if (vectorData.equals(context.getResources().getString(R.string.vector_package_phone))) {
            Intent phoneIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            //TODO add real phone number here
//            Intent callIntent = new Intent(Intent.ACTION_CALL);
//            callIntent.setData(Uri.parse("tel:0377778888"));
            context.startActivity(phoneIntent);
        } else {
            Tools.launchExternalApp(context, vectorData, vectorName);
        }
    }


    @Override
    public void sendToFirebase(String event, String contentType, String itemId, String itemName) {
        Bundle bundle = new Bundle();
        if (itemId != null)
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, itemId);
        if (itemName != null)
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        if (contentType != null)
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType);
        ((TieUsApplication) getActivity().getApplication())
                .getFirebaseAnalytics().logEvent(event, bundle);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (Tools.isTablet(newConfig) && (Tools.isLandscape(newConfig))) {
            getActivity().finish();
        } else {
            getActivity().recreate();
        }
    }
}
