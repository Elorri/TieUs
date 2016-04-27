package com.elorri.android.friendforcast.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.elorri.android.friendforcast.R;

/**
 * Created by Elorri on 26/04/2016.
 */
public class ValidateActionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_action);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(ValidateActionFragment.VALIDATE_ACTION_URI, getIntent().getData());

            ValidateActionFragment fragment = new ValidateActionFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
