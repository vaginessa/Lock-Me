package com.suite.windows.protect.lockme;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class ProcessService extends IntentService
{

	public ProcessService()
	{
		super(ProcessService.class.getSimpleName());
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		// TODO Auto-generated method stub
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		Random rand = new Random();
		String target_service = getPrefs.getString("target_service", "http");
		String target_ip = getPrefs.getString("target_ip", "0.0.0.0");
		String target_port = getPrefs.getString("target_port", Integer.toString(rand.nextInt((65535 - 1001) + 1) + 1001));
		String target_secret = getPrefs.getString("target_secret", "none");
		//System.out.println(target_ip);
		//System.out.println(target_port);
		//System.out.println(target_secret);
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(target_service+"://"+target_ip+":"+target_port+"/"); 
		//Post Data
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair.add(new BasicNameValuePair("secret", target_secret));
		//Encoding POST data
		try
		{
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		}
		catch (UnsupportedEncodingException e)
		{
			// log exception
			//e.printStackTrace();
			//Toast.makeText(this, "Error...get back to your system", Toast.LENGTH_LONG).show();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			//Toast.makeText(this, "Error...get back to your system", Toast.LENGTH_LONG).show();
		}
		
		//making POST request.
		try
		{
			HttpResponse response = httpClient.execute(httpPost);
			// write response to log
			Log.d("Http Post Response:", response.toString());
			//Toast.makeText(this, "Looks like all went well", Toast.LENGTH_LONG).show();
		}
		catch (ClientProtocolException e)
		{
			// Log exception
			//e.printStackTrace();
			//Toast.makeText(this, "Error...get back to your system", Toast.LENGTH_LONG).show();
		}
		catch (IOException e)
		{
			// Log exception
			//e.printStackTrace();
			//Toast.makeText(this, "Error...get back to your system", Toast.LENGTH_LONG).show();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			//Toast.makeText(this, "Error...get back to your system", Toast.LENGTH_LONG).show();
		}
	}

}
