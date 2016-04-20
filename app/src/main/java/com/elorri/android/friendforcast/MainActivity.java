package com.elorri.android.friendforcast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.elorri.android.friendforcast.ui.AvatarView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
        setContentView(R.layout.activity_main);
    }



    public void onContactClicked(Uri uri, int avatarColor) {
        Intent intent = new Intent(this, DetailActivity.class);
        if (avatarColor != 0)
            intent.putExtra(AvatarView.RANDOM_COLOR, avatarColor);
        intent.setData(uri);
        startActivity(intent);
    }


}
