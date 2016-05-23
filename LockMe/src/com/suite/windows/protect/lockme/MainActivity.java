package com.suite.windows.protect.lockme;

import java.util.Random;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity
{
	Button lock_me;
	TextView tv;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean target_startup = getPrefs.getBoolean("target_startup", false);
		if(target_startup)
		{
			Intent i = new Intent(MainActivity.this, ProcessService.class);
			startService(i);
			Toast.makeText(MainActivity.this, "Request sent...wait for confirmation", Toast.LENGTH_LONG).show();
		}
        tv = (TextView) findViewById(R.id.textView1);
        lock_me = (Button) findViewById(R.id.LockMe);
        lock_me.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0)
			{
				tv.setText("Lock request sent...");
				Intent i = new Intent(MainActivity.this, ProcessService.class);
				startService(i);
				Toast.makeText(MainActivity.this, "Request sent...wait for confirmation", Toast.LENGTH_LONG).show();
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            Intent i = new Intent("com.suite.windows.protect.lockme.PREFS");
            startActivity(i);
        }
        else if (id == R.id.action_exit)
        {
            finish();
        }
        else
        {
        	
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
}
