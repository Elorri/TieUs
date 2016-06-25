/*
 * The MIT License (MIT)

 Copyright (c) 2016 ETCHEMENDY ELORRI

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package com.elorri.android.capstone.tieus.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.elorri.android.capstone.tieus.R;
import com.elorri.android.capstone.tieus.fragments.DetailFragment;

/**
 * Created by Elorri on 16/04/2016.
 * Activity used on phone to launch detail screen
 */
public class DetailActivity extends AppCompatActivity {


    public static final String DETAIL_FRAGMENT="detail_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("TieUs", Thread.currentThread().getStackTrace()[2]+"");
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


    @Override
    public void onAttachFragment(Fragment fragment) {
        Log.e("TieUs", Thread.currentThread().getStackTrace()[2]+"");
        super.onAttachFragment(fragment);
    }

    @Override
    public void onAttachFragment(android.support.v4.app.Fragment fragment) {
        Log.e("TieUs", Thread.currentThread().getStackTrace()[2]+"");
        super.onAttachFragment(fragment);
    }
}
