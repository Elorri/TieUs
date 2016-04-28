package com.elorri.android.friendforcast.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.fragments.AddActionFragment;

/**
 * Created by Elorri on 26/04/2016.
 */
public class AddActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action);

        if (savedInstanceState == null) {
//            Bundle arguments = new Bundle();
//            arguments.putParcelable(AddActionFragment.SELECT_ACTION_URI, getIntent().getData());

            AddActionFragment fragment = new AddActionFragment();
//            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    public void startSelectVectorActivity(String actionId) {
        Intent intent=new Intent(this, SelectVectorActivity.class);
        intent.putExtra(ACTION_ID, actionId);
        startActivity(intent);

    }
}
