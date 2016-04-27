package com.elorri.android.friendforcast.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.fragments.SelectVectorFragment;

/**
 * Created by Elorri on 26/04/2016.
 */
public class SelectVectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vector);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(SelectVectorFragment.SELECT_VECTOR_URI, getIntent().getData());

            SelectVectorFragment fragment = new SelectVectorFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    public void startSelectTemplateActivity() {
        startActivity(new Intent(this, SelectTemplateActivity.class));

    }
}