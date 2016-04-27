package com.elorri.android.friendforcast.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.fragments.SelectTemplateFragment;

/**
 * Created by Elorri on 26/04/2016.
 */
public class SelectTemplateActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_template);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(SelectTemplateFragment.SELECT_TEMPLATE_URI, getIntent().getData());

            SelectTemplateFragment fragment = new SelectTemplateFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    public void startValidateActionActivity() {
        startActivity(this, ValidateActionActivity.class);

    }
}
