package com.elorri.android.communication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Elorri on 11/04/2016.
 */
public class BoardFragment extends Fragment {


public BoardFragment() {
        // Required empty public constructor
        }


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        Log.d("Lifecycle", "" + Thread.currentThread().getStackTrace()[2]);
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        return view;
        }
        }
