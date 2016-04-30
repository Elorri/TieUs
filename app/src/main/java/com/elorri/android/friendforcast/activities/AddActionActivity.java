package com.elorri.android.friendforcast.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.fragments.AddActionFragment;
import com.elorri.android.friendforcast.fragments.DetailFragment;

/**
 * Created by Elorri on 26/04/2016.
 */
public class AddActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
//            arguments.putParcelable(AddActionFragment.SELECT_ACTION_URI, getIntent().getData());
            arguments.putCharSequence(DetailFragment.CONTACT_ID, getIntent().getCharSequenceExtra
                    (DetailFragment.CONTACT_ID));

            AddActionFragment fragment = new AddActionFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }


}
