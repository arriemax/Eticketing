package com.example.onlineTicketChecker;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.content.Context;
public class AccountCheck extends AsyncTask<String, String, String> {

	JSONParser jParser = new JSONParser();
	// url to get all products list
	//private static String url = "http://10.0.2.2/android_connect/home.php";	
	private static String url = "home.php";	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	public static final String KEY_USERNAME = "username";
	int i=0;
	private Context parent;
	private TextView username,credits;
	private ProgressDialog pDialog;
	HashMap<String, String> accountDetail;
	JSONArray user = null;
	public AccountCheck(Context parent, TextView username,TextView credits) {
				
	            this.parent=parent;
	            this.username=username;
	            this.credits=credits;
	           
				
				
	}

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(this.parent);
				pDialog.setMessage("Wait.. Content Loading");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * getting All products from url
			 * */
			protected String doInBackground(String... args) {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				SessionManager session = new SessionManager(this.parent);
				HashMap<String, String> user=session.getUserDetails();
				params.add(new BasicNameValuePair("name", user.get("username").toString()));
				Log.d("name", user.get("username"));
				Log.d("fy","fy");
	          
	          
				// getting JSON string from URL
			
				JSONObject json = jParser.makeHttpRequest(url, "POST", params);
				
				
				Log.d("account: ", json.toString());

				try {
					 accountDetail = new HashMap<String,String>();
					JSONArray account= json.getJSONArray("account");
			        for(int i = 0; i < account.length(); i++){
			            
			            String name = account.getJSONObject(i).getString("name");
			            Log.d("name", name);
			            String credits = account.getJSONObject(i).getString("credits");
			            Log.d("credits",credits);
			            accountDetail.put("name", name);
			            Log.d("name",accountDetail.get("name"));
			            accountDetail.put("credits", credits);
			            Log.d("credits",accountDetail.get("credits"));
			            
			        }
			        i=1;
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
              
				return null;
			}

			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			protected void onPostExecute(String file_url) {
			pDialog.dismiss();

			}
			
			
			public HashMap<String, String> getAccountInfo()
			{  while(i==0);
				return accountDetail;
			}
	
	

}
