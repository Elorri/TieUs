package com.elorri.android.tieus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.fragments.DetailFragment;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailActivity extends AppCompatActivity {


    public static final String DETAIL_FRAGMENT="detail_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailFragment.DETAIL_URI, getIntent().getData());

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_fragment_container, fragment, DETAIL_FRAGMENT)
                    .commit();
        }
    }


    public void startAddActions(String contactId) {
        Intent intent=new Intent(this, AddActionActivity.class);
        intent.putExtra(DetailFragment.CONTACT_ID, contactId);
        startActivity(intent);
    }
}
