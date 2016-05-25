package com.elorri.android.tieus.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.extra.Status;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
        setContentView(R.layout.activity_main);
    }



    public void onContactClicked(Uri uri) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.setData(uri);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fragment_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_reset_help_messages) {
            Status.setDoneActionsAware(this, false);
            Status.setDeleteActionsAware(this, false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
