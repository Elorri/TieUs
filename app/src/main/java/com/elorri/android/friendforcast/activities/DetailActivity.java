package com.elorri.android.friendforcast.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.fragments.DetailFragment;
import com.elorri.android.friendforcast.ui.AvatarView;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailFragment.DETAIL_URI, getIntent().getData());
            arguments.putInt(AvatarView.RANDOM_COLOR, getIntent().getIntExtra(AvatarView
                    .RANDOM_COLOR, 0));

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_fragment_container, fragment)
                    .commit();
        }
    }


    public void startAddActions(String contactId) {
        Intent intent=new Intent(this, AddActionActivity.class);
        intent.putExtra(DetailFragment.CONTACT_ID, contactId);
        startActivity(intent);
    }
}
